package org.scnsoft.fidekmp.di

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.module
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.http.HttpStatusCode
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.scnsoft.fidekmp.data.api.auth.AuthApi
import org.scnsoft.fidekmp.data.api.auth.AuthApiImpl
import org.scnsoft.fidekmp.data.api.auth.Authenticator
import org.scnsoft.fidekmp.data.api.notification.NotificationApi
import org.scnsoft.fidekmp.data.api.notification.NotificationApiImpl
import org.scnsoft.fidekmp.data.api.profile.ProfileApi
import org.scnsoft.fidekmp.data.api.profile.ProfileApiImpl
import org.scnsoft.fidekmp.data.repository.LoginRepository
import org.scnsoft.fidekmp.data.repository.NotificationRepository
import org.scnsoft.fidekmp.data.repository.ProfileRepository
import org.scnsoft.fidekmp.data.settings.AppSettingsDataSource
import org.scnsoft.fidekmp.data.settings.AppSettingsDataSourceImpl
import org.scnsoft.fidekmp.domain.repository.LoginRepositoryImpl
import org.scnsoft.fidekmp.domain.repository.NotificationRepositoryImpl
import org.scnsoft.fidekmp.domain.repository.ProfileRepositoryImpl
import org.scnsoft.fidekmp.getPlatform
import org.scnsoft.fidekmp.ui.login.UserLoginViewModel

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


    single<AppSettingsDataSource> { AppSettingsDataSourceImpl() }
    single<LoginRepository> { LoginRepositoryImpl(get(), get(), authenticator) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
    single<NotificationRepository> { NotificationRepositoryImpl(get(), get(), get()) }

//    single<MuseumStorage> { InMemoryMuseumStorage() }
//    single {
//        MuseumRepository(get(), get()).apply {
//            initialize()
//        }
//    }
}

val viewModelModule = module {
//    factoryOf(::UserLoginViewModel)
//    factoryOf(::DetailViewModel)
    viewModelOf(::UserLoginViewModel)
}

fun initKoin(config: KoinAppDeclaration? = null) {
    Napier.base(DebugAntilog())
    startKoin {
        config?.invoke(this)
        modules(
            dataModule,
            viewModelModule,
        )
    }
}
