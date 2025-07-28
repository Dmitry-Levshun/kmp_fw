package org.scnsoft.fidekmp.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed class AppScreens(val title: String, val route: String) {
    object HomeScreen : AppScreens("Home", "homeScreen")
    object AddEditEmployeeScreen :
        AppScreens("Add/Edit Employee", "addEditEmployeeScreen")

    object EmployeeDetailScreen :
        AppScreens("Employee Details", "employeeDetailScreen")

    object Account : AppScreens("Account", "account")
    object Contact : AppScreens("Raise a Concern", "contact")
    object Help : AppScreens("Help", "help")
    object CameraScreen : AppScreens("Photo", "camera")
    object ChangePasswordScreen : AppScreens("Change Password", "changePassword")

    object QrCodeScreen : AppScreens("QR Code", "qrcode")
    object HabillageQrCodeScreen : AppScreens("Habillage", "habillage_scan")

    object AgreementScreen : AppScreens("Agreement", "agreement")
    object PreDeliveryInfoScreen : AppScreens("Order", "predelivery_info")
    object HabillageScanResultScreen : AppScreens("Result", "habillage_scan_result")

    object ProfileSupportScreen : AppScreens("Support", "profile_support")
    object Passport : AppScreens("Password","passport_main")
    object Inventory : AppScreens("Inventory","inventory_main")
    object Wallet : AppScreens("Wallet","wallet_main")
    object Cellar : AppScreens("Cellar","cellar_main")
    object WineDetails : AppScreens("WineDetails","cellar_wine_details")
    object PassportMain : AppScreens("WineDetails","passport_main")
    object PassportDetails : AppScreens("PassportDetails","passport_details")
    object CreatePassport : AppScreens("PassportDetails","passport_create")
    object CreatePassportQrCode : AppScreens("PassportDetails","passport_create_qrcode")
    object CreatePassportPage2 : AppScreens("PassportDetails","passport_create_page2")
    object RateScreen : AppScreens("RateWineScreen","cellar_wine_rating")
    object StandAloneScanResultBottleScreen : AppScreens("StandAloneScanResultBottleScreen","standalone_scanresult_bottle_screen")
    object StandAloneScanResultBoxScreen : AppScreens("StandAloneScanResultBoxScreen","standalone_scanresult_box_screen")
    object PrintSettingsScreen : AppScreens("PrintSettingsScreen","print_settings_screen")
    object PrintLabelScreen : AppScreens("PrintLabelScreen","print_label_screen")
    object NotificationScreen: AppScreens("NotificationScreen","notification_screen")
    object NotificationDetailsScreen: AppScreens("NotificationDetailsScreen", "notification_detail_screen")
    object InventoryCreatePassportScreen : AppScreens("InventoryCreatePassportScreen","inventory_create_passport_screen")
    object InventoryQrCodeScreen : AppScreens("InventoryQrCodeScreen","inventor_qr_code_screen")
    object MapViewScreen : AppScreens("Map","MapViewScreen")
    object UntrackedMainScreen : AppScreens("Untracked","UntrackedMainView")
    object InventoryScanResultScreen : AppScreens("InventoryScanResultScreen","inventory_scan_result_screen")
    object UntrackedSelectWineScreen : AppScreens("Untracked","UntrackedSelectWineScreen")
    object UntrackedAddWineScreen : AppScreens("Untracked","UntrackedAddWineScreen")
    object UntrackedWineDetailsScreen : AppScreens("Untracked","UntrackedWineDetailsScreen")
    object UntrackedMyReviewsScreen : AppScreens("Untracked","UntrackedMyReviewsScreen")
    object UntrackedPurchaseHestoryScreen : AppScreens("Untracked","UntrackedPurchaseHestoryScreen")
    object UntrackedAddReviewScreen : AppScreens("Untracked","UntrackedAddReviewScreen")

    @Serializable
    data class UntrackedReviewDetailsScreen(val reviewId: Int) : AppScreens("Untracked","UntrackedReviewDetailsScreen")

    object LogcatScreen : AppScreens("Untracked","LogcatScreen")
    object UntrackedAddCustomWineScreen : AppScreens("Untracked","UntrackedAddCustomWineScreen")

    fun routeWithArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
