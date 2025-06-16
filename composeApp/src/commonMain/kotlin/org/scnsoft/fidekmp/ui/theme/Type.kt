package org.scnsoft.fidekmp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.Font
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val genosFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.genos_bold, FontWeight.Bold),
        Font(Res.font.genos_regular, FontWeight.Normal),
        Font(Res.font.genos_light, FontWeight.Light),
    )
val poppinsFamily
    @Composable
    get() = FontFamily(
    Font(Res.font.poppins_bold, FontWeight.Bold),
    Font(Res.font.poppins_semibold, FontWeight.SemiBold),
    Font(Res.font.poppins_regular, FontWeight.Normal),
    Font(Res.font.poppins_light, FontWeight.Light),
)
val didotFamily
    @Composable
    get() = FontFamily(
    Font(Res.font.didot_bold, FontWeight.Bold),
    Font(Res.font.gfsdidot_regular, FontWeight.Normal),
    )

val SplashText
    @Composable
    get() = TextStyle(
    fontFamily = genosFamily,
    fontWeight = FontWeight.W700,
    fontSize = 40.sp
)

val textGradientStyle
    @Composable
    get() = TextStyle(
        fontFamily = genosFamily,
    fontWeight = FontWeight.W700,
    fontSize = 40.sp,
    brush = gradientBrush
)

val textHeaderStyle
    @Composable
    get() = TextStyle(
    fontFamily = poppinsFamily,
    fontWeight = FontWeight.W600,
    fontSize = 18.sp
)
val textHeaderStyle14 @Composable get() = textHeaderStyle.copy(fontSize = 14.sp)
val textHeaderStyle16 @Composable get() = textHeaderStyle.copy(fontSize = 16.sp)
val textHeaderStyle18 @Composable get() = textHeaderStyle.copy(fontSize = 18.sp)
val textHeaderStyle12 @Composable get() = textHeaderStyle.copy(fontSize = 12.sp)
val textHeaderStyle24 @Composable get() = textHeaderStyle.copy(fontSize = 24.sp)
val textHeaderStyle32 @Composable get() = textHeaderStyle.copy(fontSize = 32.sp)
val textHeaderStyle48 @Composable get() = textHeaderStyle.copy(fontSize = 48.sp)
val textExplanationStyle @Composable get() = TextStyle(
    fontFamily = poppinsFamily,
    fontWeight = FontWeight.W500,
//    fontSize = 14.sp,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    )
)

val textExplanationStyle14 @Composable get() = textExplanationStyle.copy(fontSize = 14.sp)
val textExplanationStyle12 @Composable get() = textExplanationStyle.copy(fontSize = 12.sp)
val textExplanationStyle10 @Composable get() = textExplanationStyle.copy(fontSize = 10.sp)
val textExplanationStyle16 @Composable get() = textExplanationStyle.copy(fontSize = 16.sp)
val textExplanationStyle18 @Composable get() = textExplanationStyle.copy(fontSize = 18.sp)

val textCenterStyle @Composable get() = TextStyle(
//    fontFamily = poppinsFamily,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.FirstLineTop
    )
)

val textInfoStyle @Composable get() = TextStyle(
    fontFamily = poppinsFamily,
    fontWeight = FontWeight.W400,
    fontSize = 12.sp
)
val textInfoStyle16 @Composable get() = textInfoStyle.copy(fontSize = 16.sp)
val textInfoStyle14 @Composable get() = textInfoStyle.copy(fontSize = 14.sp)
val textInfoStyle22 @Composable get() = textInfoStyle.copy(fontSize = 22.sp)
val textInfoStyle18 @Composable get() = textInfoStyle.copy(fontSize = 18.sp)

val untrackedWinetype @Composable get() = TextStyle(
        fontFamily = didotFamily,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp
)

val untrackedWinetype32 @Composable get() = untrackedWinetype.copy(fontSize = 32.sp)
val untrackedWinetype16 @Composable get() = untrackedWinetype.copy(fontSize = 16.sp)
val untrackedWinetype12 @Composable get() = untrackedWinetype.copy(fontSize = 12.sp)
val untrackedWinetype24 @Composable get() = untrackedWinetype.copy(fontSize = 24.sp)
val untrackedWinetype18 @Composable get() = untrackedWinetype.copy(fontSize = 18.sp)
