package org.scnsoft.fidekmp.di

import org.scnsoft.fidekmp.data.api.deliveryinstructions.DeliveryInstructionApi
import org.scnsoft.fidekmp.data.api.deliveryinstructions.DeliveryInstructionApiImpl
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.module
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.http.HttpStatusCode
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.scnsoft.fidekmp.data.api.ApiRequestSender
import org.scnsoft.fidekmp.data.api.ErrorParser
import org.scnsoft.fidekmp.data.api.auth.AuthApi
import org.scnsoft.fidekmp.data.api.auth.AuthApiImpl
import org.scnsoft.fidekmp.data.api.auth.Authenticator
import org.scnsoft.fidekmp.data.api.notification.NotificationApi
import org.scnsoft.fidekmp.data.api.notification.NotificationApiImpl
import org.scnsoft.fidekmp.data.api.profile.ProfileApi
import org.scnsoft.fidekmp.data.api.profile.ProfileApiImpl
import org.scnsoft.fidekmp.domain.repository.ProfileRepository
import org.scnsoft.fidekmp.data.settings.AppSettingsDataSource
import org.scnsoft.fidekmp.data.settings.AppSettingsDataSourceImpl
import org.scnsoft.fidekmp.domain.usecase.OnUntrackedMainWineSearchUseCase
import org.scnsoft.fidekmp.data.repository.ProfileRepositoryImpl
import org.scnsoft.fidekmp.domain.usecase.AddUntrackedCustomWineUseCase
import org.scnsoft.fidekmp.domain.usecase.AddUntrackedWineReviewUseCase
import org.scnsoft.fidekmp.domain.usecase.GetUntrackedUserWineByIdUseCase
import org.scnsoft.fidekmp.domain.usecase.GetUntrackedUserWineItemsPagedUseCase
import org.scnsoft.fidekmp.domain.usecase.GetUntrackedWineByIdUseCase
import org.scnsoft.fidekmp.domain.usecase.GetUntrackedWineItemsPagedUseCase
import org.scnsoft.fidekmp.domain.usecase.GetUntrackedWineProducersUseCase
import org.scnsoft.fidekmp.domain.usecase.GetUntrackedWinesFlowUseCase
import org.scnsoft.fidekmp.domain.usecase.OnAddUntrackedWineUseCase
import org.scnsoft.fidekmp.domain.usecase.GetCurrentProfileUseCase
import org.scnsoft.fidekmp.getPlatform
import org.scnsoft.fidekmp.ui.login.UserLoginViewModel
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedViewModel
import io.github.farhazulmullick.lenslogger.plugin.network.LensHttpLogger
import org.scnsoft.fidekmp.data.repository.LoginRepositoryImpl
import org.scnsoft.fidekmp.data.repository.NotificationRepositoryImpl
import org.scnsoft.fidekmp.data.repository.UntrackedWineRepositoryImpl
import org.scnsoft.fidekmp.domain.repository.LoginRepository
import org.scnsoft.fidekmp.domain.repository.NotificationRepository
import org.scnsoft.fidekmp.domain.repository.UntrackedWineRepository

const val BASE_URL = "http://ec2-15-237-3-118.eu-west-3.compute.amazonaws.com/api/v1/"

suspend fun loadTokens1(): BearerTokens? {
    return BearerTokens("abc123", "xyz111")
}

val authenticator = object : Authenticator {
    override var token: String? = null
    override var tokenType: String? = null
    override var onRefresh: (() -> Unit)? = null
    override fun updateToken(tokenType: String, token: String) {
        this.token = token
        this.tokenType = tokenType
    }

    override fun clearToken() {
        token = null
        tokenType = null
    }

    override fun setOnRefreshFunc(onRefresh: () -> Unit) {
        this.onRefresh = onRefresh
    }
}
val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        val platform = getPlatform()
        HttpClient {
            defaultRequest {
                // add base url for all request
                url(BASE_URL)
                headers.append("Authorization", "${authenticator.tokenType} ${authenticator.token}")
                headers.append("Content-Type", "application/json; charset=utf-8")
                headers.append("X-User-Agent", "FIDEWine/${platform.appVersionName} ${platform.name}")
                headers.append("X-Locale", platform.language)

            }
            install(ContentNegotiation) {
                // TODO Fix API so it serves application/json
                json(json, contentType = ContentType.Any)
            }
            // set default request parameters
            /*
            install(Auth) {
                bearer {
                    loadTokens {
                        // Load tokens from a local storage and return them as the 'BearerTokens' instance
                        BearerTokens("abc123", "xyz111")
                    }
                }
            }
             */
/*
            install(Logging) {

                logger = object: Logger {
                    override fun log(message: String) {
                        Napier.v("HTTP Client", null, message)
                    }
                }

//                logger = Logger.DEFAULT
                level = LogLevel.ALL
//                filter { request ->
//                    request.url.host.contains("ktor.io")
//                }
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }
 */
           //------
            LensHttpLogger {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d(message = message)
                    }
                }
            }.also {
                // setup up nappier logger.
                Napier.base(DebugAntilog())
            }
           //------

            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, request ->
                    if (exception is ResponseException && exception.response.status == HttpStatusCode.Unauthorized) {
                        // Обновим токен
                        authenticator.onRefresh?.invoke()
                    }
                }
            }
//            install(UserAgent) {
//                agent = "Ktor client"
//            }
        }
    }

    single<AuthApi> { AuthApiImpl(get()) }
    single<ProfileApi> { ProfileApiImpl(get()) }
    single<NotificationApi> { NotificationApiImpl(get()) }
    single<DeliveryInstructionApi> { DeliveryInstructionApiImpl(get()) }

    single<AppSettingsDataSource> { AppSettingsDataSourceImpl() }
    single<LoginRepository> { LoginRepositoryImpl(get(), get(), authenticator) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
    single<NotificationRepository> { NotificationRepositoryImpl(get(), get(), get()) }
//    single<UntrackedWineRepository> { ::UntrackedWineRepositoryImpl }
    singleOf (::UntrackedWineRepositoryImpl) { bind<UntrackedWineRepository>() }


//    single<MuseumStorage> { InMemoryMuseumStorage() }
//    single {
//        MuseumRepository(get(), get()).apply {
//            initialize()
//        }
//    }
}
val domainModule = module {
//    factoryOf(::UseCase)
}

val viewModelModule = module {
//    factoryOf(::UserLoginViewModel)
//    factoryOf(::DetailViewModel)
    viewModelOf(::UserLoginViewModel)

}
val appModule = module {
    singleOf( ::UserLoginViewModel )
    factory { OnUntrackedMainWineSearchUseCase(get()) }
    factoryOf (::OnUntrackedMainWineSearchUseCase)
    factoryOf (::OnAddUntrackedWineUseCase)
    factoryOf (::GetUntrackedWineByIdUseCase)
    factoryOf (::AddUntrackedWineReviewUseCase)
    factoryOf (::GetUntrackedWinesFlowUseCase)
    factoryOf (::GetUntrackedWineProducersUseCase)
    factoryOf (::AddUntrackedCustomWineUseCase)
    factoryOf (::GetUntrackedWineItemsPagedUseCase)
    factoryOf (::GetUntrackedUserWineByIdUseCase)
    factoryOf (::GetUntrackedUserWineItemsPagedUseCase)
    factoryOf (::GetCurrentProfileUseCase)
    singleOf  (::UntrackedViewModel)
}
val errorParserModule = module {
    single { Json { ignoreUnknownKeys = true } }
    single { ErrorParser(get()) }
    single { ApiRequestSender(get()) }
}
fun initKoin(config: KoinAppDeclaration? = null) {
    Napier.base(DebugAntilog())
    startKoin {
        config?.invoke(this)
        modules(
            dataModule,
            appModule,
            errorParserModule
//            viewModelModule,
        )
    }
}
