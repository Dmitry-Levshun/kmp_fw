package org.scnsoft.fidekmp.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppScreens(val title: String, val route: String, var icon: ImageVector) {
    object HomeScreen : AppScreens("Home", "homeScreen", Icons.Default.Home)
    object AddEditEmployeeScreen :
        AppScreens("Add/Edit Employee", "addEditEmployeeScreen", Icons.Default.Home)

    object EmployeeDetailScreen :
        AppScreens("Employee Details", "employeeDetailScreen", Icons.Default.Home)

    object Account : AppScreens("Account", "account", Icons.Default.AccountCircle)
    object Contact : AppScreens("Raise a Concern", "contact", Icons.Default.Email)
    object Help : AppScreens("Help", "help", Icons.Default.Info)
    object CameraScreen : AppScreens("Photo", "camera", Icons.Default.Home)
    object ChangePasswordScreen : AppScreens("Change Password", "changePassword", Icons.Default.Home)

    object QrCodeScreen : AppScreens("QR Code", "qrcode", Icons.Default.Home)
    object HabillageQrCodeScreen : AppScreens("Habillage", "habillage_scan", Icons.Default.Home)

    object AgreementScreen : AppScreens("Agreement", "agreement", Icons.Default.Home)
    object PreDeliveryInfoScreen : AppScreens("Order", "predelivery_info", Icons.Default.Home)
    object HabillageScanResultScreen : AppScreens("Result", "habillage_scan_result", Icons.Default.Home)

    object ProfileSupportScreen : AppScreens("Support", "profile_support", Icons.Default.Home)
    object Passport : AppScreens("Password","passport_main", Icons.Default.Home)
    object Inventory : AppScreens("Inventory","inventory_main", Icons.Default.Home)
    object Wallet : AppScreens("Wallet","wallet_main", Icons.Default.Home)
    object Cellar : AppScreens("Cellar","cellar_main", Icons.Default.Home)
    object WineDetails : AppScreens("WineDetails","cellar_wine_details", Icons.Default.Home)
    object PassportMain : AppScreens("WineDetails","passport_main", Icons.Default.Home)
    object PassportDetails : AppScreens("PassportDetails","passport_details", Icons.Default.Home)
    object CreatePassport : AppScreens("PassportDetails","passport_create", Icons.Default.Home)
    object CreatePassportQrCode : AppScreens("PassportDetails","passport_create_qrcode", Icons.Default.Home)
    object CreatePassportPage2 : AppScreens("PassportDetails","passport_create_page2", Icons.Default.Home)
    object RateScreen : AppScreens("RateWineScreen","cellar_wine_rating", Icons.Default.Home)
    object StandAloneScanResultBottleScreen : AppScreens("StandAloneScanResultBottleScreen","standalone_scanresult_bottle_screen", Icons.Default.Home)
    object StandAloneScanResultBoxScreen : AppScreens("StandAloneScanResultBoxScreen","standalone_scanresult_box_screen", Icons.Default.Home)
    object PrintSettingsScreen : AppScreens("PrintSettingsScreen","print_settings_screen", Icons.Default.Home)
    object PrintLabelScreen : AppScreens("PrintLabelScreen","print_label_screen", Icons.Default.Home)
    object NotificationScreen: AppScreens("NotificationScreen","notification_screen", Icons.Default.Home)
    object NotificationDetailsScreen: AppScreens("NotificationDetailsScreen","notification_detail_screen", Icons.Default.Home)
    object InventoryCreatePassportScreen : AppScreens("InventoryCreatePassportScreen","inventory_create_passport_screen", Icons.Default.Home)
    object InventoryQrCodeScreen : AppScreens("InventoryQrCodeScreen","inventor_qr_code_screen", Icons.Default.Home)
    object MapViewScreen : AppScreens("Map","MapViewScreen", Icons.Default.Home)
    object UntrackedMainScreen : AppScreens("Untracked","UntrackedMainView", Icons.Default.Home)
    object InventoryScanResultScreen : AppScreens("InventoryScanResultScreen","inventory_scan_result_screen", Icons.Default.Home)
    object UntrackedSelectWineScreen : AppScreens("Untracked","UntrackedSelectWineScreen", Icons.Default.Home)
    object UntrackedAddWineScreen : AppScreens("Untracked","UntrackedAddWineScreen", Icons.Default.Home)
    object UntrackedWineDetailsScreen : AppScreens("Untracked","UntrackedWineDetailsScreen", Icons.Default.Home)
    object UntrackedMyReviewsScreen : AppScreens("Untracked","UntrackedMyReviewsScreen", Icons.Default.Home)
    object UntrackedPurchaseHestoryScreen : AppScreens("Untracked","UntrackedPurchaseHestoryScreen", Icons.Default.Home)
    object UntrackedAddReviewScreen : AppScreens("Untracked","UntrackedAddReviewScreen", Icons.Default.Home)
    object UntrackedReviewDetailsScreen : AppScreens("Untracked","UntrackedReviewDetailsScreen", Icons.Default.Home)
    object LogcatScreen : AppScreens("Untracked","LogcatScreen", Icons.Default.Home)
    object UntrackedAddCustomWineScreen : AppScreens("Untracked","UntrackedAddCustomWineScreen", Icons.Default.Home)

    fun routeWithArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
