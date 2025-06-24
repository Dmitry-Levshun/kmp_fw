package org.scnsoft.fidekmp.ui.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.scnsoft.fidekmp.ui.theme.*

@Composable
fun WineBottleLabel(modifier: Modifier, data: WineBottle) {
    Column(modifier = modifier.clip(RoundedCornerShape(8.dp))) {
        Text(data.winaName, style = textExplanationStyle)
        Text(data.bottleVolume, style = textExplanationStyle)
        Text(data.bottleId, style = textInfoStyle)
    }
}

data class WineBottle(
    val winaName: String,
    val bottleVolume: String,
    val bottleId: String
)

fun getBottleImageByColor(color: String?): DrawableResource {
    val clr = color?.lowercase()
    return when (clr) {
        "white" -> Res.drawable.white_wine
        "red" -> Res.drawable.red_wine
        "orange" -> Res.drawable.orange_wine
        "rose" -> Res.drawable.rose_wine
        else -> Res.drawable.red_wine
    }
}