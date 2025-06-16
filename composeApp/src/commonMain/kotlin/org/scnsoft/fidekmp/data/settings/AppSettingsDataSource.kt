package org.scnsoft.fidekmp.data.settings

import kotlinx.coroutines.flow.Flow

interface AppSettingsDataSource {
    val autoStartDialogShown: Flow<Boolean>
    suspend fun updateAutoStartDialogShown(shown: Boolean)

    val vpnEnabled: Flow<Boolean>
    suspend fun updateVpnEnabled(enabled: Boolean)

    val dohUrl: Flow<String?>
    val deviceId: Flow<String?>
    suspend fun updateDevice(dohUrl: String, deviceId: String)
    suspend fun clearDevice()

    val userName: Flow<String?>
    suspend fun updateName(userName: String)

    val secretKey: Flow<String?>
    suspend fun setSecretKey(secretKey: String)

    val refreshToken: Flow<String?>
    suspend fun setRefreshToken(token: String)

    val profileInfo: Flow<String?>
    suspend fun setProfileInfo(profileInfo: String)
    val syncInstructions: Flow<String?>
    suspend fun setSyncInstructions(syncInstructions: String)

    val syncInstructionsIntermediate: Flow<String?>
    suspend fun setSyncInstructionsIntermediate(syncInstructions: String)

    val wines: Flow<String?>
    suspend fun setWines(wines: String)
    val digitalPassportTransfers: Flow<String?>
    suspend fun setDigitalPassportTransfers(transfers: String)
    val contacts: Flow<String?>
    suspend fun setContacts(contactStr: String)
//    fun isConnected(): Boolean
    val isConnectedFlow: Flow<Boolean>
    val scannedData: Flow<String?>
    suspend fun setScannedData(scannedDataStr: String)
    val printers: Flow<String?>
    suspend fun savePrinters(printersStr: String)
    suspend fun clearData()
}
