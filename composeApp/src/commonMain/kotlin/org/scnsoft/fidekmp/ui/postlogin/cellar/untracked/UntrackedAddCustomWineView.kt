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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedWinePropertyBox
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedWinePropertyDateBox
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import org.scnsoft.fidekmp.ui.UiResult
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.scnsoft.fidekmp.utils.currentUtcDateTime
import org.jetbrains.compose.resources.stringArrayResource
import org.scnsoft.fidekmp.ui.login.DropDownMenu
import org.scnsoft.fidekmp.ui.theme.*
import org.scnsoft.fidekmp.ui.utils.AppScreens
import org.scnsoft.fidekmp.ui.utils.RoundButtonView
import org.scnsoft.fidekmp.ui.utils.uiResultDialog
import org.scnsoft.fidekmp.utils.toSimpleMonthString
import org.scnsoft.fidekmp.utils.toSimpleString
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.utils.mSecToLocalDateTime

@Composable
fun UntrackedAddCustomWineScreen(
    navController: NavHostController?,
    homeViewModel: IUntrackedModel,
) {
    Napier.d("UntrackedAddCustomWineScreen")
    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(
                title = stringResource(Res.string.add_untracked_wine),
                navController = navController!!
//                backGroundColor = seccondaryButtonColor
            )
        },
        content = {
            UntrackedAddCustomWineView(navController, homeViewModel)
        },
        // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
        // how it should animate.

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UntrackedAddCustomWineView(navController: NavHostController?,
                         viewModel: IUntrackedModel
) {
    val bottleList by viewModel.bottleList.collectAsState(listOf())
    val uiResult by viewModel.uiResult.collectAsState()
    val customWines by viewModel.untrackedCustomWines.collectAsState(listOf())
    val customWineProducers by viewModel.untrackedCustomWineProducers.collectAsState(listOf())
    val customWineProducersNames = customWineProducers.map { it.producerName ?: ""}.filter { it.isNotBlank() }.toSet().toMutableList()
    val openResultDialog = remember { mutableStateOf(false) }
    if (uiResult != null) openResultDialog.value = true
    val openDateDialog = remember { mutableStateOf(false) }
    val onVintageMenu = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var dueDate by remember { mutableStateOf(currentUtcDateTime().toSimpleString()) }
    var purchaseDate: LocalDateTime? by remember { mutableStateOf(null) }
    var vendor by remember { mutableStateOf("") }

    val addWineNameText = stringResource(Res.string.add_wine_name)
    var wineName by remember { mutableStateOf(addWineNameText) }
    var domainName by remember { mutableStateOf("") }

    var producerName by remember { mutableStateOf("") }
    var countryName by remember { mutableStateOf("") }

    var regionName by remember { mutableStateOf("") }
    var appellation by remember { mutableStateOf("") }
    var classification by remember { mutableStateOf("") }
    val wineTypes = stringArrayResource(Res.array.wine_types)
    val wineColors = stringArrayResource(Res.array.wine_colors)
    var wineColor by remember { mutableStateOf(wineColors.first()) }
    var wineType by remember { mutableStateOf(wineTypes.first()) }

    var capacityText by remember { mutableStateOf(bottleList.firstOrNull{it.amountInLiters == 0.75}?.typeName ?: "0,75 L") }
    var quantity by remember { mutableStateOf(0) }
    var unitPrice by remember { mutableStateOf(0.0) }
    var vintage: Int? by remember { mutableStateOf(null) }
    val onCapacityMenu = remember { mutableStateOf(false) }
    val capacityMenuItems =  bottleList.map { it.typeName }.toTypedArray()
    var capacity by remember { mutableStateOf(0.75) }
    val vintageMenuItems = remember {
        val y = currentUtcDateTime().year - 100 + 1901
        Array(100) { (it + y).toString() }.reversedArray()
    }
    val classificationTypes = stringArrayResource(Res.array.classification_types)
    val config = LocalWindowInfo.current.containerSize
    val screenHeight = config.height
    Napier.d("UntrackedAddCustomWineView h:$screenHeight ${bottleList.map{it.typeName}} ${capacityMenuItems.size}")
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(untrackedBackgroundColor)
            .padding(vertical = 16.dp)
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
        Column(Modifier.padding(horizontal = 16.dp)) {

            UntrackedWineSearchPropertyBox(startDrawRes = Res.drawable.ic_message_edit,
                wineName,
                items = customWines.map { it.wineName },
                endDrawRes = Res.drawable.ic_add_circle,
                onTextChange = { txt ->
                    Napier.d("UntrackedAddCustomWineView wine $txt")
                    if (txt.startsWith("*")) {
                        wineName = txt.substring(1)
                        val id = customWines.firstOrNull { it.wineName == wineName }?.id
                        if (id != null) {
                            scope.launch {
                                viewModel.getUntrackedWineById(id)
                                navController?.navigate(AppScreens.UntrackedAddWineScreen.route)
                            }
                        }
                    } else {
                        wineName = txt
                        viewModel.onUntrackCustomWineSearch(txt)
                    }
                },
                label = stringResource(Res.string.add_wine_name) +"*"
            )
            Spacer(modifier = Modifier.height(16.dp))
            UntrackedWineSearchPropertyBox(
                startDrawRes = Res.drawable.ic_message_edit,
                domainName,
                items = customWineProducersNames,
                endDrawRes = Res.drawable.ic_add_circle,
                onTextChange = { txt ->
                    Napier.d("UntrackedAddCustomWineView domain $txt")
                    if (txt.startsWith("*")) domainName = txt.substring(1) else {
                        domainName = txt;
                        viewModel.onUntrackCustomProducerSearch(txt)
                    }
                },
                label = stringResource(Res.string.add_domain) +"*"
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        WineTypeSelectBox(onSelected = {Napier.d("WineTypeSelectBox onSelected  $it"); wineType = wineTypes[it]})
        Spacer(modifier = Modifier.height(16.dp))
        SelectWineColorBox(onSelected = {Napier.d("SelectWineBox onSelected  $it"); wineColor = wineColors[it]})
        Spacer(modifier = Modifier.height(16.dp))
        WineBottleInfoBox(capacity = capacityText, quantity = quantity, vintage = vintage, onVintageChange = { onVintageMenu.value = true}, onQuantityChange = { quantity = it}, onCapacityChange = { onCapacityMenu.value = true})
        Column(Modifier.padding(horizontal = 16.dp)) {

            Spacer(modifier = Modifier.height(16.dp))
            UntrackedWinePropertyDateBox(startDrawRes = Res.drawable.ic_calendar,
                if (purchaseDate == null) (stringResource(Res.string.date_of_purchase) + "*").toAnnotated() else burgundiHilightedDateText(purchaseDate!!.toSimpleMonthString()),
                endDrawRes = Res.drawable.ic_choosed_circle,
                onClick = { openDateDialog.value = true })
            Spacer(modifier = Modifier.height(16.dp))
            UntrackedWinePropertyBox(
                startDrawRes = Res.drawable.ic_building,
                vendor,
                endDrawRes = Res.drawable.ic_add_circle,
                onTextChange = { vendor = it },
                label = stringResource(Res.string.add_vendor)
            )
            Spacer(modifier = Modifier.height(16.dp))
            UntrackedWinePropertyBox(
                startDrawRes = Res.drawable.ic_receipt_add,
                if (unitPrice == 0.0) stringResource(Res.string.add_unit_price) else unitPrice.toString(),
                endDrawRes = Res.drawable.ic_add_circle,
                label = stringResource(Res.string.add_unit_price), isNumeric = true,
                onTextChange = { unitPrice = it.toDoubleOrNull() ?: unitPrice })
            Spacer(modifier = Modifier.height(16.dp))
            UntrackedWinePropertyBox(
                startDrawRes = Res.drawable.ic_message_edit,
                producerName,
                endDrawRes = Res.drawable.ic_add_circle,
                label = stringResource(Res.string.add_producer_name),
                onTextChange = { producerName = it })
            Spacer(modifier = Modifier.height(16.dp))
            UntrackedWinePropertyBox(
                startDrawRes = Res.drawable.ic_global_edit,
                countryName,
                endDrawRes = Res.drawable.ic_add_circle,
                label = stringResource(Res.string.add_country),
                onTextChange = { countryName = it })
            Spacer(modifier = Modifier.height(16.dp))
            UntrackedWinePropertyBox(
                startDrawRes = Res.drawable.ic_global_edit,
                regionName,
                endDrawRes = Res.drawable.ic_add_circle,
                label = stringResource(Res.string.add_region),
                onTextChange = { regionName = it })
            Spacer(modifier = Modifier.height(16.dp))
            UntrackedWinePropertyBox(
                startDrawRes = Res.drawable.ic_message_edit,
                appellation,
                endDrawRes = Res.drawable.ic_add_circle,
                label = stringResource(Res.string.appellation),
                onTextChange = { appellation = it })
            Spacer(modifier = Modifier.height(16.dp))
            DropDownListIcons(
                Res.drawable.ic_message_edit,
                Res.string.classification,
                classificationTypes.toTypedArray(),
                onClick = { classification = classificationTypes[it] })

        /*
                    UntrackedWinePropertyBox(
                        startDrawRes = R.drawable.ic_message_edit,
                        classification,
                        endDrawRes = R.drawable.ic_add_circle,
                        label = stringResource(id = R.string.classification),
                        onTextChange = { classification = it })

         */
            Spacer(modifier = Modifier.height(16.dp))
//        WineBottleInfoBox(capacity = capacityText, quantity = quantity, onQuantityChange = { quantity = it}, onCapacityChange = { onCapacityMenu = true})
            RoundButtonView(
                Res.string.add_to_my_cellar,
                modifier = Modifier
                    .padding(vertical = 24.dp),
                onClick = {
//                val extUrl = untrackWine.idUrl
                    Napier.d("UntrackedAddCustomWineView addWine vintage:$vintage capacity:$capacity quantity:$quantity unitPrice:$unitPrice vendor:$vendor color:$wineColor purchaseDate:$purchaseDate producer:$producerName country:$countryName region:$regionName appellation:$appellation classification:$classification wineType:$wineType")
//                viewModel.addUntrackedWine(vintage = vintage, bottleVolume = capacity, externalWineIdUrl = extUrl, qty = quantity, price = unitPrice, vendorName = vendor, dateOfPurchase = purchaseDate)
                    viewModel.addUntrackedCustomWine(vintage = vintage!!,
                        bottleVolume = capacity,
                        wineType = wineType,
                        qty = quantity,
                        price = unitPrice,
                        name = wineName,
                        producerName = producerName,
                        vendorName = vendor,
                        country = countryName,
                        region = regionName,
                        appellation = appellation,
                        classification = classification,
                        color = wineColor,
                        dateOfPurchase = purchaseDate!!)

                },
                enabled =  quantity > 0 && domainName.isNotEmpty() && wineName.isNotEmpty() && wineColor.isNotEmpty() && wineType.isNotEmpty() && vintage != null && purchaseDate != null
            )
        }
    }
    if (onVintageMenu.value) DropDownMenu(onVintageMenu, vintageMenuItems, onClick = { ind->
        onVintageMenu.value = false
        vintage = vintageMenuItems[ind].toIntOrNull() ?: 2024
    })
    if (onCapacityMenu.value) DropDownMenu(onCapacityMenu, capacityMenuItems, onClick = { ind->
        onCapacityMenu.value = false
        capacity = bottleList.firstOrNull{it.typeName == capacityMenuItems[ind]}?.amountInLiters ?: 0.0
        capacityText = "$capacity L"//volumeDoubleToString(capacity)
    })

    if (openResultDialog.value && uiResult != null) uiResultDialog(openDialog = openResultDialog, result = uiResult!!, msgId = Res.string.wine_added, onClick = {
        scope.launch {
            val isError = uiResult is UiResult.Error
            viewModel.resetUiResult()
            delay(100)
            if (!isError) navController?.navigate(AppScreens.Cellar.route)//navigate(AppScreens.Inventory.route)
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
                            purchaseDate = mSecToLocalDateTime(datePickerState.selectedDateMillis!!)
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
data class RadioItem(
    val stringRes: StringResource,
    val imageRes: DrawableResource
)

@Composable
private fun SelectWineColorBox(onSelected: (Int)->Unit) {
    val radioOptions = listOf(
        RadioItem(stringRes = Res.string.white, imageRes = Res.drawable.white_wine),
        RadioItem(stringRes = Res.string.orange, imageRes = Res.drawable.orange_wine),
        RadioItem(stringRes = Res.string.red, imageRes = Res.drawable.red_wine),
        RadioItem(stringRes = Res.string.rose, imageRes = Res.drawable.rose_wine)
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0] ) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White))
        {
        Text(stringResource(Res.string.wine_color) + "*",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp, start = 16.dp),
            style = textExplanationStyle12, textAlign = TextAlign.Start, color = burgundiColor)

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            radioOptions.forEachIndexed { index, item ->
                Column(
                    Modifier
                        .fillMaxWidth(0.25f)
                        .selectable(
                            selected = (item == selectedOption),
                            onClick = {
                                Napier.d("SelectWineBox onc1 $index")
                                onOptionSelected(item)
                                onSelected.invoke(index)
                            }
                        )
                        .padding(vertical = 4.dp, horizontal = 4.dp)
                        .weight(1f)
                        .border(
                            1.dp,
                            if (item == selectedOption) primaryColor else Color.Transparent,
                            RoundedCornerShape(8.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(item.stringRes),
                        style = untrackedWinetype12,
                        color = if (item == selectedOption) burgundiColor else primaryTextColor
                    )
                   Image(
                        painter = painterResource(item.imageRes),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(60.dp, 175.dp)
                    )
                    RadioButton(
                        selected = (item == selectedOption),
                        onClick = { Napier.d("SelectWineBox onc2 $index");onOptionSelected(item); onSelected.invoke(index) },
                        colors = RadioButtonDefaults.colors(selectedColor = primaryColor)

                    )

                }
            }
        }
    }
}
@Composable
private fun WineTypeSelectBox(onSelected: (Int)->Unit) {
    val radioOptions = listOf(stringResource(Res.string.still), stringResource(Res.string.fuzzy))
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0] ) }
    Column(modifier = Modifier.background(Color.White)) {
        Text(stringResource(Res.string.wine_type) + "*",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp),
            style = textInfoStyle, color = burgundiColor)

        radioOptions.forEachIndexed { index, text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            onSelected.invoke(index)
                        }
                    )
                    .padding(horizontal = 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text); onSelected.invoke(index) },
                    colors = RadioButtonDefaults.colors(selectedColor = primaryColor)
                )
                Text(
                    text = text,
                    style = textExplanationStyle14,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun burgundiHilightedDateText(dateText: String): AnnotatedString {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = burgundiColor)){
            append(dateText)
        }
        val startIndex = dateText.length - 4
        if (startIndex != -1) {
            addStyle(
                style = SpanStyle(color = primaryTextColor),
                start = startIndex,
                end = startIndex + 4
            )
        }
    }
    return annotatedString
}

fun String.toAnnotated(color: Color = primaryTextColor): AnnotatedString {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = color)) {
            append(this@toAnnotated)
        }
    }
    return annotatedString
}

@Composable
private fun WineBottleInfoBox(capacity: String, quantity: Int, vintage: Int?, onVintageChange: ((Int)-> Unit)? = null, onCapacityChange: ((String)-> Unit)? = null, onQuantityChange: ((Int)-> Unit)? = null)
{
    var localQuantity by remember { mutableStateOf(quantity) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(horizontal = 16.dp)) {
        Text(
            stringResource(Res.string.bottles),
            modifier = Modifier.padding(all = 16.dp),
            color = primaryTextColor,
            style = untrackedWinetype24
        )
        Row(modifier = Modifier.padding(horizontal = 16.dp),horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f))
            {
                Text(stringResource(Res.string.vintage) + "*", color = burgundiColor, textAlign = TextAlign.Center)
                Row {
                    Text(
                        text = if (vintage == null) "        "/*stringResource(id = R.string.vintage)*/ else "${vintage}",
                        color = Color.Black,
                        style = untrackedWinetype16
                    )
                    Image(
                        painter = painterResource(Res.drawable.ic_arrow_circle_down),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable { onVintageChange?.invoke(0) },
                        contentDescription = null
                    )
                }
            }

            Column(modifier = Modifier.weight(1f), ) {
                Text(stringResource(Res.string.capacity) + "*", color = burgundiColor, textAlign = TextAlign.Center)
                Row {
                    Text(
                        text = capacity,
                        color = Color.Black,
                        style = textHeaderStyle16
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
                .weight(1f)
                .padding(bottom = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(stringResource(Res.string.quantity) + "*", color = burgundiColor, textAlign = TextAlign.Center)
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
fun UntrackedWineSearchPropertyBox(startDrawRes: DrawableResource, text: String, items: List<String> = listOf(),endDrawRes: DrawableResource, label: String = "",
                             onClick: (()-> Unit)? = null,
                             onTextChange: ((String)-> Unit)? = null
) {
    var hasFocus by remember { mutableStateOf(false) }
    var enableEdit by remember { mutableStateOf(true) }

    Column(
        Modifier
            .height(if (!hasFocus || items.isEmpty()) 60.dp else 200.dp)
            .background(Color.White, RoundedCornerShape(16.dp)),
        verticalArrangement = Arrangement.Top) {
        Row(
            Modifier
                .height(60.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    hasFocus = it.hasFocus
                }
                .background(Color.White, RoundedCornerShape(16.dp)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(startDrawRes),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onClick?.invoke() },
                contentDescription = null
            )
            if (onTextChange == null || !enableEdit) {
                Text(
                    text = text,
                    color = Color.Black,
                    style = textHeaderStyle16
                )
            } else {
                EditUntrackedText(label = label, text = text, onTextChange = onTextChange!!)
            }
            Image(
                painter = painterResource(endDrawRes),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { },
                contentDescription = null
            )

        }
        if (hasFocus && text !=label && text.isNotEmpty() && items.size > 1) {
            LazyColumn(Modifier.fillMaxWidth()) {
                items(items = items.take(10)) { fi ->
                    Text(
                        fi,
                        Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .clickable {
                                Napier.d("UntrackedWineSearchPropertyBox $fi"); onTextChange?.invoke(
                                "*$fi"
                            ); enableEdit = false; hasFocus = false
                            },
                        style= textExplanationStyle12
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownListIcons(leadIconId: DrawableResource, labelId: StringResource, accList: Array<String>, startIndex: Int = -1, onClick: (Int)->Unit) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var accType by remember {
        mutableStateOf(if (startIndex >= 0) accList[startIndex] else "")
    }
    var selectedIndex by remember {
        mutableIntStateOf(-1)
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = !isExpanded
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp)),
    ) {
        TextField(
            value = accType,
            onValueChange = {},
            readOnly = true,
            textStyle = textCenterStyle,
            leadingIcon = { Image(painter = painterResource(leadIconId), contentDescription = null) },
            trailingIcon = {
//                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                IconButton(
                    onClick = { },//isExpanded = !isExpanded },
                    modifier = Modifier.testTag("passwordVisibilityToggle")
                ) {
                    Image(
                        painter = painterResource( if (isExpanded) Res.drawable.ic_arrow_circle_up else Res.drawable.ic_arrow_circle_down),
                        contentDescription = null
                        )
                    }
            },
            label = {
                Text( text = stringResource(labelId), modifier = Modifier.padding(start = 32.dp))
            },
//            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = primaryTextColor,
//            cursorColor = backGroundColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            },
        ) {
            accList.forEachIndexed{index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    onClick(index)
                    accType = s
                    isExpanded = false
                }) {
                    Text(s)
                }

            }
        }
    }
}


@Composable
@Preview
private fun UntrackedAddCustomWinePreview() {
//    UntrackedAddCustomWineView(null, UntrackedModelPreview())
    val classificationTypes = stringArrayResource(Res.array.classification_types)

    Column {
        UntrackedAddCustomWineView(null, UntrackedModelPreview())
//        SelectWineColorBox({})
//    WineBottleInfoBox(capacity = "0.75 L", quantity = 1, vintage = 2023)
//        WineTypeSelectBox({})
//        DropDownListIcons(
//            R.drawable.ic_message_edit,
//            R.string.classification,
//            classificationTypes,
//            onClick = { })

    }


}