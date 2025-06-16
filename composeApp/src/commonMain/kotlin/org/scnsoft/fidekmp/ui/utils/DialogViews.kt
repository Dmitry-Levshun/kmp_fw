package org.scnsoft.fidekmp.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.ui.UiResult
import org.scnsoft.fidekmp.ui.theme.textExplanationStyle
import org.scnsoft.fidekmp.ui.theme.*

@Composable
fun Dialog(
    modifier: Modifier = Modifier,
    titleResId: StringResource? = null,
    messageResId: StringResource? = null,
    message: String?,
    onClick: () -> Unit
) {
    /*
    ForegroundContent(background = Color.White/*MaterialTheme.colors.surface*/) {
        Column(
            modifier = modifier
                .padding(all = 16.dp)
        ) {
            titleResId?.let {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(titleResId),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )
            }
            val text = if (message.isNullOrBlank()) stringResource(messageResId) else message
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = text,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                onClick = onClick,
                shape = RoundedCornerShape(50)
            ) {
                Text(stringResource(R.string.OK), color = Color.White)
            }
        }
    }
     */
    val msgSt = if (message.isNullOrBlank()) stringResource(messageResId!!) else message
    AlertDialog(
        onDismissRequest = {
        },
        title = {
            titleResId?.let {Text(text = stringResource(it)) }
        },
        text = {
            Text(text = msgSt,
                style = textExplanationStyle10,
            )
        },

        confirmButton = {
            RoundButtonView(
                textId = Res.string.OK,
//                text = "D'ACCORD",
                color = primaryColor,
                fontSize = 14.sp,
                modifier = Modifier
                    .width(130.dp)
                    .padding(top = 8.dp, bottom = 16.dp, end = 8.dp),
                enabled = true,
                onClick = {
                    onClick.invoke()
                })
        },
    )
}

@Composable
fun ForegroundContent(background: Color, content: @Composable () -> Unit) {
    Surface(color = Color.Black.copy(0.2F)) {
        Box(
            modifier = Modifier
                .padding(all = 32.dp)
                .wrapContentSize()
                .background(background, MaterialTheme.shapes.small),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun uiResultDialog(openDialog: MutableState<Boolean>, result: UiResult, onClick: ()->Unit, msgId: StringResource? = null, msgText: String? =null) {
    var showDetails by remember{ mutableStateOf(false) }
    val scroll = rememberScrollState(0)
    val clipboard = LocalClipboardManager.current
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
            onClick.invoke()
        },
        title = {
            if (result is UiResult.Success) Text(stringResource(Res.string.success), color = ForestGreen, style = textExplanationStyle16)
            else  Text(stringResource(Res.string.error), color = nfcFailedBackColor, style = textExplanationStyle16)
        },
        text = {
            if (result is UiResult.Success) Text(if (msgId != null) stringResource(msgId) else msgText ?: "")
            else if (result is UiResult.SyncError && result.batchId != null) Text(stringResource(Res.string.sync_error_batchId, "${result.batchId}"))
            else if (result is UiResult.Error && result.errorMessage != null)  {
                val text = result.errorMessage
                Text(text = text,
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(scroll)
                        .clickable { clipboard.setText(buildAnnotatedString {  append(text) }) })
            } else {
                Column {
                    Text(stringResource(Res.string.error_something_went_wrong))
                    Text(text = stringResource(Res.string.details), modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable { showDetails = !showDetails }, color = linkColor)
                    if (showDetails) {
                        val text = (result as UiResult.Error).errorMessage ?: ""
                        Text(text = text,
                            Modifier
                                .fillMaxWidth()
                                .verticalScroll(scroll)
                                .clickable { clipboard.setText(buildAnnotatedString {  append(text) }) })
                    }
                }
            }
        },
        confirmButton = {
            RoundButtonView(
                textId = Res.string.OK,
                color = primaryColor,
                fontSize = 14.sp,
                modifier = Modifier
                    .width(150.dp)
                    .padding(top = 8.dp, bottom = 16.dp, end = 8.dp),
                enabled = true,
                onClick = {
                    openDialog.value = false
                    onClick.invoke()
                })
        }
    )
}

@Preview
@Composable
private fun PreviewDialog() {
    Dialog(
        Modifier.testTag("loginError"),
        Res.string.error_something_went_wrong,
        null,
        "all is bad",
        {})

}