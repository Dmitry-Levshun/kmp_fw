package org.scnsoft.fidekmp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.scnsoft.fidekmp.ui.theme.splashColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
    fun SplashView() {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(splashColor),
            ) {
//                Text(stringResource(id = R.string.app_name).uppercase(), modifier = Modifier.align(Alignment.Center), style = SplashText, color = Color.White)
                Image(
                    painter = painterResource(Res.drawable.ic_logo_fide_new),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
//                    colorFilter = ColorFilter.tint(
//                        colorResource(id = R.color.primaryColor),
//                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(300.dp, 200.dp)
//                        .clip(RoundedCornerShape(50)),
                )

            }
    }

    @Preview
    @Composable
    fun DefaultSplashPreview() {
//        WineAppTheme {
            SplashView()
//        }
    }
