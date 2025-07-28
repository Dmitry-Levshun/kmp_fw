package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.aakira.napier.Napier
import org.koin.compose.viewmodel.koinViewModel
import org.scnsoft.fidekmp.ui.login.EmailConfirm
import org.scnsoft.fidekmp.ui.utils.AppScreens

@Composable
fun UntrackedNavigator() {
    val navController = rememberNavController()
    val viewModel: UntrackedViewModel = koinViewModel()
    NavHost(navController, startDestination = AppScreens.UntrackedMainScreen.route) {
        composable(route = AppScreens.UntrackedMainScreen.route) {
            UntrackedMainView(navController, viewModel)
        }
        composable(route = AppScreens.UntrackedSelectWineScreen.route) {
            UntrackedSelectWineScreen(navController, viewModel)
        }
        composable(route = AppScreens.UntrackedAddWineScreen.route) {
            UntrackedAddWineScreen(navController, viewModel)
        }
        composable(route = AppScreens.UntrackedWineDetailsScreen.route) {
            UntrackedWineDetailsScreen(navController, viewModel)
        }

        composable(route = AppScreens.UntrackedMyReviewsScreen.route) {
            UntrackedMyReviewsScreen(navController, viewModel)
        }
        composable(route = AppScreens.UntrackedPurchaseHestoryScreen.route) {
            UntrackedPurchaseHistoryScreen(navController, viewModel)
        }
        composable(route = AppScreens.UntrackedAddReviewScreen.route) {
            UntrackedAddReviewScreen(navController, viewModel)
        }
        composable<AppScreens.UntrackedReviewDetailsScreen> {  backStackEntry ->
            val details: AppScreens.UntrackedReviewDetailsScreen = backStackEntry.toRoute()
            val reviewIdInt = details.reviewId
            UntrackedReviewDetailsScreen(reviewIdInt, navController, viewModel)
        }
        composable(route = AppScreens.UntrackedAddCustomWineScreen.route) {
            UntrackedAddCustomWineScreen(navController, viewModel)
        }

    }
}