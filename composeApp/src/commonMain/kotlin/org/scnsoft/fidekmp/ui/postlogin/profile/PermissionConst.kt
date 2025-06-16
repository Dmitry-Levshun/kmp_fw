package org.scnsoft.fidekmp.ui.postlogin.profile

const val HOME_PERMISSION =  "home_page"
const val CELLAR_PERMISSION =  "my_cellar"
const val ADDWINE_PERMISSION =  "add_wine"
const val DPT_PERMISSION =  "passports_transfer"
const val CREATEDPT_PERMISSION =  "create_passports_transfer"
const val DI_PERMISSION =  "delivery_instructions"
const val HISTORY_PERMISSION =  "history"
const val CONTACTS_PERMISSION =  "contacts"
const val CUSTOMERS_PERMISSION =  "customers"
const val PROFILE_PERMISSION =  "account_settings"
const val USERMANAGEMENT_PERMISSION =  "users_management"
const val LOGS_PERMISSION =  "activity_logs"
const val SCANNING_PERMISSION =  "mobile_scanning"
const val INVENTORY_PERMISSION =  "mobile_inventory"
const val PERSONAL_SALES_PERMISSION =  "mobile_personal_sales"
const val NOTIFICATIONS_PERMISSION =  "notifications"
const val WALLET_PERMISSION =  "mobile_wallet"
const val MOBILE_ORDERS =  "mobile_orders"
enum class BottomBarUserType { WINEYARD, INTERMEDIATE, CONSUMER }

//"permissions":["home_page","my_cellar","passports_transfer","create_passports_transfer","delivery_instructions","history",
// "contacts","customers","account_settings","users_management","activity_logs","mobile_scanning",
// "mobile_wallet"]
//  wineshop
//["home_page","my_cellar","passports_transfer","create_passports_transfer","delivery_instructions",
// "history","contacts","customers",
// "account_settings","users_management","activity_logs",
// "mobile_scanning","mobile_wallet","mobile_orders"]
// consumer
//"permissions":["my_cellar","passports_transfer","create_passports_transfer","account_settings",
// "notifications","mobile_scanning","mobile_wallet","mobile_personal_sales"]
// winemaker
//"permissions":["home_page","my_cellar","add_wine","passports_transfer","create_passports_transfer",
// "delivery_instructions","history","contacts","customers","account_settings","users_management",
// "notifications","activity_logs","mobile_scanning","mobile_inventory",
// "mobile_personal_sales","mobile_orders"]