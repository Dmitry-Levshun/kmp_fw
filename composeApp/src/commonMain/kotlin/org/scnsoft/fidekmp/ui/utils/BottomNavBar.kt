package org.scnsoft.fidekmp.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.DrawableResource
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.ui.postlogin.profile.BottomBarUserType
import org.scnsoft.fidekmp.ui.postlogin.profile.*
import org.scnsoft.fidekmp.ui.theme.primaryColor

sealed class BottomNavigationScreens(val route: String, val icon: DrawableResource, val selectedIcon:  DrawableResource) {
    object Inventory : BottomNavigationScreens("inventory_main", Res.drawable.ic_inventory, Res.drawable.ic_inventory_active)
    object Scan : BottomNavigationScreens("homeScreen", Res.drawable.ic_habillage, Res.drawable.ic_habillage_active)
    object Passport : BottomNavigationScreens("passport_main", Res.drawable.ic_dpt, Res.drawable.ic_dpt_active)
    object Cellar : BottomNavigationScreens("cellar_main", Res.drawable.ic_cellar_v, Res.drawable.ic_cellar_active_v)  //ic_cellar_act_blue_png  ic_cellar_active_v
    object Wallet : BottomNavigationScreens("wallet_main", Res.drawable.ic_wallet, Res.drawable.ic_wallet_act)

}

val navList = listOf(BottomNavigationScreens.Scan, BottomNavigationScreens.Passport, BottomNavigationScreens.Inventory, BottomNavigationScreens.Cellar)//, BottomNavigationScreens.Wallet)
val navIntermediateList = listOf(BottomNavigationScreens.Scan, BottomNavigationScreens.Passport, BottomNavigationScreens.Cellar)
val navConsumerList = listOf(BottomNavigationScreens.Passport, BottomNavigationScreens.Cellar, BottomNavigationScreens.Wallet)

@Composable
fun BottomNavBar(navController: NavHostController?, userType: BottomBarUserType = BottomBarUserType.WINEYARD, permissions: List<String> = listOf()
//                 items: List<BottomNavigationScreens>
) {
    val navListX = when (userType) {
        BottomBarUserType.WINEYARD -> navList
        BottomBarUserType.INTERMEDIATE -> navIntermediateList
        BottomBarUserType.CONSUMER -> navConsumerList
    }.toMutableList()

    if (!permissions.contains(MOBILE_ORDERS)) navListX.remove(BottomNavigationScreens.Scan)
    if (!permissions.contains(INVENTORY_PERMISSION)) navListX.remove(BottomNavigationScreens.Inventory)
    if (!permissions.contains(CELLAR_PERMISSION)) navListX.remove(BottomNavigationScreens.Cellar)
    if (!permissions.contains(WALLET_PERMISSION)) navListX.remove(BottomNavigationScreens.Wallet)

    BottomNavigation(backgroundColor = Color.White) {
        val currentRoute = currentRoute(navController)
        Napier.d("BottomNavBar $currentRoute")
        navListX.forEach { screen ->
//            Timber.d("BottomNavigationScreens ${screen.route}")
            val isSelected = screen.getSelection(currentRoute)
            val ic = if (isSelected) painterResource(screen.selectedIcon) else painterResource(screen.icon)
            BottomNavigationItem(
                icon = { Image(ic, "", Modifier.size(24.dp, 24.dp), colorFilter = ColorFilter.tint(primaryColor)) },//{ Icon(ImageVector.vectorResource(if (isSelected) screen.selectedIcon else screen.icon), "") },
                selected = isSelected,
                alwaysShowLabel = false, // This hides the title for the unselected items
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.route) {
                        Napier.d("BottomNavBar click ${screen.route}")
                        try {
                            navController?.navigate(screen.route)
                        } catch (e: Exception) {
                            Napier.e("BottomNavigation error",e)
                        }
                    }
                }
            )
        }
    }
}

fun BottomNavigationScreens.getSelection(routeParam: String?): Boolean {
    val ls = routeParam?.split('_') ?: return false
//    Timber.d("BottomNavBar getSelection p:$routeParam ls:$ls")
    return this.route.contains(ls[0])
}

@Composable
private fun currentRoute(navController: NavHostController?): String? {
    return navController?.currentBackStackEntry?.destination?.route
}

@Preview
@Composable
private fun PreviewBottomNavBar() {
    BottomNavBar(null, permissions = listOf(MOBILE_ORDERS, INVENTORY_PERMISSION, CELLAR_PERMISSION))
}