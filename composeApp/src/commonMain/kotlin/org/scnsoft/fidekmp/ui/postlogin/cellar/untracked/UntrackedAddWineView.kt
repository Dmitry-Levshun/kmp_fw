package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.scnsoft.fidekmp.utils.toSimpleMonthString
import org.scnsoft.fidekmp.utils.toSimpleString
import org.scnsoft.fidekmp.ui.UiResult
import org.scnsoft.fidekmp.ui.theme.*
import org.scnsoft.fidekmp.ui.utils.AppScreens
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import org.scnsoft.fidekmp.ui.utils.RoundButtonView
import org.scnsoft.fidekmp.ui.utils.getBottleImageByColor
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.burgundiHilightedDateText
import org.jetbrains.compose.resources.painterResource
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.IUntrackedModel
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedDetailWineItem
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedModelPreview
import org.scnsoft.fidekmp.ui.theme.textInfoStyle14
import org.jetbrains.compose.resources.stringResource
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.utils.currentUtcDateTime
import androidx.compose.ui.platform.LocalWindowInfo
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.scnsoft.fidekmp.ui.login.DropDownMenu
import org.scnsoft.fidekmp.ui.utils.uiResultDialog
import org.scnsoft.fidekmp.utils.getTickCount
import org.scnsoft.fidekmp.utils.mSecToLocalDateTime
import org.scnsoft.fidekmp.utils.toLocalDateTime

@Composable
fun UntrackedAddWineScreen(
    navController: NavHostController?,
    homeViewModel: IUntrackedModel,
) {
    val backList = navController?.currentBackStack?.value?.map{it.destination.route}//  backStackList(
    Napier.d("UntrackedAddWineScreen back:$backList")
    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(
                title = stringResource(Res.string.add_untracked_wine),
                navController = navController!!
            )
        },
        content = {
            UntrackedAddWineView(navController, homeViewModel)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UntrackedAddWineView(navController: NavHostController?,
                            viewModel: IUntrackedModel
) {
    val bottleList by viewModel.bottleList.collectAsState(listOf())
    val wineList by viewModel.untrackedWineInfoState.collectAsState(listOf())
    val untrackWine = wineList.firstOrNull() ?: return
    val wine = UntrackedDetailWineItem.fromUntrackedWineItem(untrackWine)
    val uiResult by viewModel.uiResult.collectAsState()
    val openResultDialog = remember { mutableStateOf(false) }
    val openDateDialog = remember { mutableStateOf(false) }
    var onMenu by remember { mutableStateOf(false) }
    val onVintageMenu = remember { mutableStateOf(false) }
    var dueDate by remember { mutableStateOf(currentUtcDateTime().toSimpleString()) }
    val datePickerState = rememberDatePickerState()//initialSelectedDateMillis = getTickCount(), initialDisplayedMonthMillis = getTickCount())
    var purchaseDate: LocalDate? by remember { mutableStateOf(null) }
    val vendorText = stringResource(Res.string.add_vendor)
    var vendor by remember { mutableStateOf(vendorText) }
    val unitPriceText = stringResource(Res.string.add_unit_price)
    var capacityText by remember { mutableStateOf(bottleList.firstOrNull{it.amountInLiters == 0.75}?.typeName ?: "0.75 L") }
    var quantity by remember { mutableStateOf(0) }
    var unitPrice by remember { mutableStateOf(0.0) }
    var vintage by remember { mutableStateOf(0) }
    val onCapacityMenu = remember { mutableStateOf(false) }
    val capacityMenuItems = remember { bottleList.map { it.typeName }.toTypedArray() }
    var capacity by remember { mutableStateOf(0.75) }
    var confirmClicked by remember { mutableStateOf(false) }

    val vintageMenuItems = remember {
        val y = currentUtcDateTime().year - 100 + 1901
        Array(100) { (it + y).toString() }.reversedArray()
    }
    wine.vintage = vintage
    val config = LocalWindowInfo.current
    val screenHeight = config.containerSize.height
    Napier.d("UntrackedAddWineView h:$screenHeight  ${bottleList.size} ")
    val scope = rememberCoroutineScope()
    LaunchedEffect(uiResult) {
        if (uiResult != null) {
            if (uiResult is UiResult.Error) {
                confirmClicked = false
            }
            openResultDialog.value = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(untrackedBackgroundColor)
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(Res.string.add_wine_to_my_cellar),
            style = untrackedWinetype24,
            textAlign = TextAlign.Center,
            color = primaryTextColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        )
        UntrackedWineDetailBox(wine, onClick = { Napier.d("click vintage");onVintageMenu.value = true})
        Spacer(modifier = Modifier.height(16.dp))
        UntrackedWinePropertyDateBox(startDrawRes = Res.drawable.ic_calendar,
            if (purchaseDate == null) stringResource(Res.string.date_of_purchase).toAnnotated()  else burgundiHilightedDateText(purchaseDate!!.toSimpleMonthString()),
            endDrawRes = Res.drawable.ic_choosed_circle,
            onClick = {openDateDialog.value = true})
        Spacer(modifier = Modifier.height(16.dp))
        UntrackedWinePropertyBox(startDrawRes = Res.drawable.ic_building, vendor, endDrawRes = Res.drawable.ic_add_circle, onTextChange = {vendor = it}, label = stringResource(Res.string.add_vendor))
        Spacer(modifier = Modifier.height(16.dp))
        UntrackedWinePropertyBox(startDrawRes = Res.drawable.ic_empty_wallet, if (unitPrice == 0.0) stringResource(Res.string.add_unit_price) else unitPrice.toString(), endDrawRes = Res.drawable.ic_add_circle, label = stringResource(Res.string.add_unit_price), isNumeric = true, onTextChange = { unitPrice = it.toDoubleOrNull() ?: unitPrice})
        Spacer(modifier = Modifier.height(16.dp))
        WineBottleInfoBox(capacity = capacityText, quantity = quantity, onQuantityChange = { quantity = it}, onCapacityChange = { Napier.d("click Capacity");onCapacityMenu.value = true})
        RoundButtonView(
            Res.string.add_to_my_cellar,
            modifier = Modifier
                .padding(vertical = 24.dp),
            onClick = {
                if (confirmClicked) return@RoundButtonView
                confirmClicked = true;
                val extUrl = untrackWine.idUrl
                Napier.d("UntrackedAddWineView addWine vintage:$vintage capacity:$capacity quantity:$quantity unitPrice:$unitPrice vendor:$vendor extUrl:$extUrl purchaseDate:$purchaseDate")
                viewModel.addUntrackedWine(vintage = vintage, bottleVolume = capacity, externalWineIdUrl = extUrl, qty = quantity, price = unitPrice, vendorName = vendor, dateOfPurchase = purchaseDate!!.toLocalDateTime())
            },
            enabled = capacity > 0.0 && quantity > 0 && unitPrice > 0.0 && vendor.isNotEmpty() && purchaseDate != null && vintage > 0
        )
    }
    if (onVintageMenu.value) DropDownMenu(onVintageMenu, vintageMenuItems, onClick = { ind->
        Napier.d("click vintage $ind")
        onVintageMenu.value = false
        vintage = vintageMenuItems[ind].toIntOrNull() ?: 2024
        wine.vintage = vintage
    })
    if (onCapacityMenu.value) DropDownMenu(onCapacityMenu, capacityMenuItems, onClick = { ind->
        onCapacityMenu.value = false
        capacity = bottleList.firstOrNull{it.typeName == capacityMenuItems[ind]}?.amountInLiters ?: 0.0
        capacityText = capacityMenuItems[ind]
    })

    if (openResultDialog.value && uiResult != null) uiResultDialog(openDialog = openResultDialog, result = uiResult!!, msgId = Res.string.wine_added, onClick = {
        scope.launch {
            viewModel.resetUiResult()
            delay(200)
            navController?.navigate(AppScreens.Cellar.route)//navigate(AppScreens.Inventory.route)
        }
    })


    if (openDateDialog.value) {
            DatePickerDialog(
                onDismissRequest = {
                    openDateDialog.value = false
                },
                confirmButton = {
                    Button(
                        onClick = {
                            Napier.d("date onClick ${datePickerState.selectedDateMillis}")
                            if (datePickerState.selectedDateMillis != null) {
                                dueDate =
                                    mSecToLocalDateTime(datePickerState.selectedDateMillis!!).toSimpleString()
                                purchaseDate = mSecToLocalDateTime(datePickerState.selectedDateMillis!!).date
                            }
                            Napier.d(" date state $dueDate")
                            openDateDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor)
                    ) {
                        Text(stringResource(Res.string.OK).uppercase(), color = Color.White)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDateDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = seccondaryButtonColor)

                    ) {
                        Text(stringResource(Res.string.cancel).uppercase(), color = Color.White)
                    }
                },
                colors = DatePickerDefaults.colors()
            ) {
                DatePicker(
                    state = datePickerState
                )
        }
    }
}

@Composable
fun UntrackedWineDetailBox(item: UntrackedDetailWineItem, fShowVintageImage: Boolean = true,
                           onClick: (()-> Unit)? = null
) {
    Column(
        Modifier
//            .height(270.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.dp, Color.White, RoundedCornerShape(16.dp))) {
        Text(
            text = item.wineName,
            modifier = Modifier.padding(start = 16.dp),
            color = primaryTextColor,
            style = untrackedWinetype24
        )
        val drawRes = getBottleImageByColor(item.color)
        val painter = painterResource(drawRes)
        Row(
            Modifier
                .height(180.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = item.domainName,
                    color = burgundiColor,
                    style = untrackedWinetype12,
                )
                Text(
                    text = item.country,
                    color = burgundiColor,
                    style = untrackedWinetype12,
                )
                Text(
                    text = item.region,
                    color = burgundiColor,
                    style = untrackedWinetype12,
                )
                Text(
                    text = item.appellation,
                    color = burgundiColor,
                    style = untrackedWinetype12,
                )
                Text(
                    text = item.classification,
                    color = burgundiColor,
                    style = untrackedWinetype12,
                )

                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(Res.string.vintage),
                    style = untrackedWinetype12,
                    color = burgundiColor,
                )
                Row(modifier = Modifier.clickable { if (fShowVintageImage) onClick?.invoke() }, horizontalArrangement = Arrangement.SpaceBetween)
                {
                    Text(
                        text = if (item.vintage == 0) stringResource(Res.string.select_vintage) else "${item.vintage}",
                        color = Color.Black,
                        style = untrackedWinetype16
                    )
                    if (fShowVintageImage) {
                        Image(
                            painter = painterResource(Res.drawable.ic_arrow_circle_down),
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .clickable { onClick?.invoke() },
                            contentDescription = null
                        )
                    }
                }
            }
            Image(painter = painter,
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(50.dp, 170.dp)
                    )

        }
    }
}

@Composable
fun UntrackedWinePropertyBox(startDrawRes: DrawableResource, text: String, endDrawRes: DrawableResource, label: String = "",
                          isNumeric: Boolean = false,
                          onClick: (()-> Unit)? = null,
                          onTextChange: ((String)-> Unit)? = null
) {
    Napier.d("UntrackedWinePropertyBox $text $onClick $onTextChange")
    Row(
        Modifier
            .height(60.dp)
            .fillMaxWidth()
            .clickable { if (onTextChange == null) onClick?.invoke() }
            .background(Color.White, RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {

        Image(painter = painterResource(startDrawRes),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable { onClick?.invoke() },
            contentDescription = null)
        if (onTextChange == null) {
        Text( modifier = Modifier
            .padding(start = 32.dp)
            .weight(1f),
                text = text,
                color = primaryTextColor,
                style = textInfoStyle14
            )
        } else {
            EditUntrackedText(label = label, text = text, isNumeric = isNumeric, onTextChange = onTextChange)
        }
        Image(painter = painterResource(endDrawRes),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable { },
            contentDescription = null)

    }
}
@Composable
fun UntrackedWinePropertyDateBox(startDrawRes: DrawableResource, text: AnnotatedString, endDrawRes: DrawableResource,
                                 onClick: (()-> Unit)? = null, ) {
    Row(
        Modifier
            .height(60.dp)
            .fillMaxWidth()
            .clickable { onClick?.invoke() }
            .background(Color.White, RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {

        Image(painter = painterResource(startDrawRes),
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .clickable { onClick?.invoke() },
            contentDescription = null)

        Text( modifier = Modifier
            .padding(start = 32.dp)
            .weight(1f),
                text = text,
                style = textInfoStyle14
        )
        Image(painter = painterResource(endDrawRes),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable { },
            contentDescription = null)
    }
}

@Composable
 fun EditUntrackedText(label: String, text: String, isNumeric: Boolean = false, onTextChange: ((String)-> Unit)?) {
    var content by remember { mutableStateOf("") }
    val keyboadType = if (isNumeric) KeyboardType.Number else KeyboardType.Text
    TextField(
        modifier = Modifier.fillMaxWidth(0.8f),
        value = content,
        onValueChange = {
            content = it
            Napier.d(" onValueChange tx:$it")
            onTextChange?.invoke(it)
        },
        textStyle = textInfoStyle14,
        label ={ Text(text = label, style = textInfoStyle14) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            textColor = primaryTextColor,
//            cursorColor = backGroundColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = keyboadType),
        readOnly = onTextChange == null
//            keyboardActions = KeyboardActions(onSearch = { onSearchClick(text) }),
    )
}
@Composable
private fun WineBottleInfoBox(capacity: String, quantity: Int, onCapacityChange: ((String)-> Unit)? = null, onQuantityChange: ((Int)-> Unit)? = null)
{
    var localQuantity by remember { mutableStateOf(quantity) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)) {
        Text(
            stringResource(Res.string.bottles),
            modifier = Modifier.padding(all = 16.dp),
            color = primaryTextColor,
            style = untrackedWinetype18
        )
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Column(modifier = Modifier.width(200.dp), ) {
                Text(stringResource(Res.string.capacity), color = burgundiColor, textAlign = TextAlign.Center)
                Row(modifier = Modifier.clickable { onCapacityChange?.invoke(capacity) }) {
                    Text(
                        text = capacity,
                        color = Color.Black,
                        style = untrackedWinetype16
                    )
                    Image(
                        painter = painterResource(Res.drawable.ic_arrow_circle_down),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable { onCapacityChange?.invoke(capacity) },
                        contentDescription = null
                    )
                }
            }
            Column(modifier = Modifier
                .width(120.dp)
                .padding(bottom = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(stringResource(Res.string.quantity), color = burgundiColor, textAlign = TextAlign.Center)
                Row {
                    Image(
                        painter = painterResource(Res.drawable.ic_minus_circle),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable { localQuantity--; onQuantityChange?.invoke(localQuantity) },
                        contentDescription = null
                    )

                    Text(
                        text = "$localQuantity",
                        color = Color.Black,
                        style = textHeaderStyle16
                    )
                    Image(
                        painter = painterResource(Res.drawable.ic_add_circle),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable { localQuantity++; onQuantityChange?.invoke(localQuantity) },
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun UntrackedAddWinePreview() {
    UntrackedAddWineView(null, UntrackedModelPreview())
}