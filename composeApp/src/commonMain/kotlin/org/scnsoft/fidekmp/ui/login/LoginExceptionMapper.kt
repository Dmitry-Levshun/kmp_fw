package org.scnsoft.fidekmp.ui.login

import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.login_error_generic
import fidekmp.composeapp.generated.resources.login_error_no_connection
import kotlinx.io.IOException
import org.jetbrains.compose.resources.StringResource

class LoginExceptionMapper  {

    fun map(exception: Throwable?): StringResource = when (exception) {
        is IOException -> Res.string.login_error_no_connection
        else -> Res.string.login_error_generic
    }
}
