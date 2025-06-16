package org.scnsoft.fidekmp.data.settings

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.io.IOException
import org.scnsoft.fidekmp.data.storage.createDataStore

class AppSettingsDataSourceImpl : AppSettingsDataSource {

    val dataStore = createDataStore()
    override val autoStartDialogShown: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.AUTO_START_DIALOG_SHOWN] ?: false
        }
    override suspend fun clearData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    override suspend fun updateAutoStartDialogShown(shown: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.AUTO_START_DIALOG_SHOWN] = shown
        }
    }

    override val vpnEnabled: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.VPN_ENABLED] ?: false
        }

    override suspend fun updateVpnEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.VPN_ENABLED] = enabled
        }
    }

    override val dohUrl: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.DOH_URL]
        }

    override val deviceId: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.DEVICE_ID]
        }

    override suspend fun updateDevice(dohUrl: String, deviceId: String) {
        dataStore.edit { preferences ->
            preferences[Keys.DOH_URL] = dohUrl
            preferences[Keys.DEVICE_ID] = deviceId
        }
    }

    override suspend fun clearDevice() {
        dataStore.edit { preferences ->
            preferences.remove(Keys.DOH_URL)
            preferences.remove(Keys.DEVICE_ID)
        }
    }

    override val userName: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.USER_NAME]
        }

    override suspend fun updateName(userName: String) {
        dataStore.edit { preferences ->
            preferences[Keys.USER_NAME] = userName
        }
    }

    override val secretKey: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.SECRET_KEY]
        }

    override suspend fun setSecretKey(secretKey: String) {
        Napier.d("setSecretKey $secretKey")
        dataStore.edit { preferences ->
            preferences[Keys.SECRET_KEY] = secretKey
        }
    }

    override val refreshToken: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.REFRESH_TOKEN]
        }

    override suspend fun setRefreshToken(token: String) {
        Napier.d(" setRefreshToken ")
        dataStore.edit { preferences ->
            preferences[Keys.REFRESH_TOKEN] = token
        }
    }

    override val profileInfo: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.PROFILE_INFO]
        }

    override suspend fun setProfileInfo(profileInfo: String) {
        Napier.d("setProfileInfo ")
        dataStore.edit { preferences ->
            preferences[Keys.PROFILE_INFO] = profileInfo
        }
    }
    override val syncInstructions: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.SYNC_INSTRUCTIONS]
        }

    override suspend fun setSyncInstructions(syncInstructionStr: String) {
        Napier.d("setSyncInstructions ")
        dataStore.edit { preferences ->
            preferences[Keys.SYNC_INSTRUCTIONS] = syncInstructionStr
        }
    }

    override val syncInstructionsIntermediate: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.SYNC_INSTRUCTIONS_INTERMEDIATE]
        }
    override suspend fun setSyncInstructionsIntermediate(syncInstructions: String) {
        Napier.d("setSyncInstructions ")
        dataStore.edit { preferences ->
            preferences[Keys.SYNC_INSTRUCTIONS_INTERMEDIATE] = syncInstructions
        }

    }

    override val wines: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.WINES]
        }

    override suspend fun setWines(wines: String) {
        Napier.d("setWines ")
        dataStore.edit { preferences ->
            preferences[Keys.WINES] = wines
        }
    }

    override val digitalPassportTransfers: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.DIGITAL_PASSPORT_TRANSFERS]
        }
    override suspend fun setDigitalPassportTransfers(transfers: String) {
        Napier.d("setDigitalPassportTransfers ")
        dataStore.edit { preferences ->
            preferences[Keys.DIGITAL_PASSPORT_TRANSFERS] = transfers
        }
    }
    override val contacts: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.CONTACTS]
        }
    override suspend fun setContacts(contactStr: String) {
        Napier.d("setContacts ")
        dataStore.edit { preferences ->
            preferences[Keys.CONTACTS] = contactStr
        }
    }

    override val scannedData: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.SCANNED_DATA]
        }

    override suspend fun setScannedData(scannedDataStr: String) {
        Napier.d("setScannedData ")
        dataStore.edit { preferences ->
            preferences[Keys.SCANNED_DATA] = scannedDataStr
        }

    }
    override val isConnectedFlow: Flow<Boolean>
        get() = isConnectedFlowFunc()
    private fun isConnectedFlowFunc(): Flow<Boolean> = flow {
        while (true) {
//            emit(context.isConnected())
            delay(1000)
        }
    }

    override val printers: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[Keys.PRINTERS]
        }

    override suspend fun savePrinters(printersStr: String) {
        Napier.d("savePrinters ")
        dataStore.edit { preferences ->
            preferences[Keys.PRINTERS] = printersStr
        }
    }

    private object Keys {
        val VPN_ENABLED = booleanPreferencesKey("vpnEnabled")
        val DOH_URL = stringPreferencesKey("dohUrl")
        val DEVICE_ID = stringPreferencesKey("deviceId")
        val USER_NAME = stringPreferencesKey("userName")
        val AUTO_START_DIALOG_SHOWN = booleanPreferencesKey("autoStartDialogShown")
        val SECRET_KEY = stringPreferencesKey("secretKey")
        val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
        val PROFILE_INFO = stringPreferencesKey("profileInfo")
        val SYNC_INSTRUCTIONS = stringPreferencesKey("syncinstructions")
        val WINES = stringPreferencesKey("wines")
        val DIGITAL_PASSPORT_TRANSFERS = stringPreferencesKey("digitalpassporttransfers")
        val CONTACTS = stringPreferencesKey("contacts")
        val SCANNED_DATA = stringPreferencesKey("scanned_data")
        val PRINTERS = stringPreferencesKey("printers")
        val SYNC_INSTRUCTIONS_INTERMEDIATE = stringPreferencesKey("sync_instructions_intermediate")
    }

}
