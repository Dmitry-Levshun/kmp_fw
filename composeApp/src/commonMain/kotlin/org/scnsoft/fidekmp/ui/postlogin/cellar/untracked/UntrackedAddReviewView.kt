package org.scnsoft.fidekmp.ui.postlogin.cellar.untracked

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.LocalInspectionMode
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.painterResource
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.scnsoft.fidekmp.utils.toSimpleMonthString
import org.scnsoft.fidekmp.utils.toSimpleString
import org.scnsoft.fidekmp.utils.volumeDoubleToString
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedWineDetailBox
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedWinePropertyBox
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedWinePropertyDateBox
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.WinePropertyButtonBox
import io.github.aakira.napier.Napier
import org.scnsoft.fidekmp.ui.theme.primaryColor
import org.scnsoft.fidekmp.ui.theme.seccondaryButtonColor
import org.scnsoft.fidekmp.ui.theme.textHeaderStyle16
import org.scnsoft.fidekmp.ui.theme.textInfoStyle14
import org.scnsoft.fidekmp.ui.theme.untrackedBackgroundColor
import org.scnsoft.fidekmp.ui.utils.CustomToolbarWithBackArrow
import org.scnsoft.fidekmp.ui.utils.RoundButtonView
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scnsoft.fidekmp.ui.UiResult
import org.scnsoft.fidekmp.utils.currentUtcDateTime
import org.scnsoft.fidekmp.ui.utils.uiResultDialog
import org.scnsoft.fidekmp.utils.mSecToLocalDateTime

@Composable
fun UntrackedAddReviewScreen(
    navController: NavHostController?,
    homeViewModel: IUntrackedModel,
) {
    Napier.d("UntrackedSelectWineScreen")
    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(
                title = stringResource(Res.string.add_review),
                navController = navController!!
//                backGroundColor = seccondaryButtonColor
            )
        },
//        bottomBar = { BottomNavBar(navController = navController, profileInfo.userType, profileInfo.permissions) },
        content = {
            UntrackedAddReviewView(navController, homeViewModel)
        },
        // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
        // how it should animate.

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UntrackedAddReviewView(navController: NavHostController?,
                           viewModel: IUntrackedModel
) {
    val wineList by viewModel.untrackedUserWineInfoState.collectAsState(listOf())
    val untrackWine = if (LocalInspectionMode.current) untrackedUserWineItemById else wineList.firstOrNull() ?: return
    val uiResult by viewModel.uiResult.collectAsState()
    val wine = UntrackedDetailWineItem.fromUntrackedUserWineItem(untrackWine)
//    val map = mutableMapOf("0.7 L" to 0, "0.5 L" to 2, "0.3 L" to 3)
    val mapIn = untrackWine.volumesToBottles.map {volumeDoubleToString(it.volume) to it.qty}.toMap()
    val volumesToBottlesMut = untrackWine.volumesToBottles.map{ VolumesToBottleMutable.fromVolumesToBottle(it)}.toMutableList()
    volumesToBottlesMut.forEach { it.qty = 0 }
    val pairs = remember {
        val p: List<Pair<String, VolumesToBottleMutable>> = volumesToBottlesMut.map {
            Pair(volumeDoubleToString(it.volume), it) }
        mutableStateOf(p)
    }
    val map = remember {
        val map1 = untrackWine.volumesToBottles.map {volumeDoubleToString(it.volume) to 0}.toMap()
        mutableStateOf(map1.toMutableMap())
    }
    val openDateDialog = remember { mutableStateOf(false) }
    val openResultDialog = remember { mutableStateOf(false) }
    var locationOfTest by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0) }
    var tastingWith by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var review by remember { mutableStateOf("") }
    var dateOfTest: LocalDate? by remember { mutableStateOf(null) }
    var dueDate by remember { mutableStateOf(currentUtcDateTime().toSimpleString()) }
    val datePickerState = rememberDatePickerState()
    val scope = rememberCoroutineScope()
    val config = LocalWindowInfo.current
    val screenHeight = config.containerSize.height
    var confirmClicked by remember { mutableStateOf(false) }
    Napier.d("UntrackedAddReviewView $screenHeight")
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
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UntrackedWineDetailBox(wine, fShowVintageImage = false)
        Spacer(modifier = Modifier.height(16.dp))
        UntrackedWineExpandableQuantityBox(atartDrawRes = Res.drawable.ic_bottle, stringResource(Res.string.quantity), endDrawRes = Res.drawable.ic_add_circle, valuesMap = map.value,
            onClick = { key, value ->
            Napier.d("UntrackedWineExpandableQuantityBox onClick $key $value")
            if (value >= 0 && value <= mapIn[key]!!) {
                val mp = map.value
                map.value = mutableMapOf()
                val p1 = pairs.value
                pairs.value = listOf()
                p1.find { it.first == key }?.second?.qty = value
                mp[key] = value
                map.value = mp
                pairs.value = p1
                Napier.d("UntrackedWineExpandableQuantityBox onClick res ${map.value} ${pairs.value} p1:$p1")

            }
        })
        Spacer(modifier = Modifier.height(16.dp))
        UntrackedWinePropertyDateBox(startDrawRes = Res.drawable.ic_calendar, if (dateOfTest == null) stringResource(Res.string.date_of_tasting).toAnnotated() else burgundiHilightedDateText(dateOfTest!!.toSimpleMonthString()),
            endDrawRes = Res.drawable.ic_add_circle, onClick = {openDateDialog.value = true})
        Spacer(modifier = Modifier.height(16.dp))
        UntrackedWinePropertyBox(startDrawRes = Res.drawable.ic_building, stringResource(Res.string.location_of_tasting), endDrawRes = Res.drawable.ic_add_circle, label = stringResource(Res.string.location_of_tasting), onTextChange = { locationOfTest = it})
        Spacer(modifier = Modifier.height(16.dp))
        UntrackedWinePropertyBox(startDrawRes = Res.drawable.ic_crown, stringResource(Res.string.add_rating), endDrawRes = Res.drawable.ic_add_circle, label = stringResource(Res.string.add_rating), onTextChange = { rating = it.toIntOrNull() ?: 0})
        Spacer(modifier = Modifier.height(16.dp))
        UntrackedWinePropertyBox(startDrawRes = Res.drawable.ic_people, stringResource(Res.string.tasting_with), endDrawRes = Res.drawable.ic_add_circle, label = stringResource(Res.string.tasting_with), onTextChange = { tastingWith = it})
        Spacer(modifier = Modifier.height(16.dp))
        UntrackedWinePropertyBox(startDrawRes = Res.drawable.ic_speedometer, stringResource(Res.string.course), endDrawRes = Res.drawable.ic_add_circle, label = stringResource(Res.string.course), onTextChange = { course = it})
        Spacer(modifier = Modifier.height(16.dp))
        UntrackedWinePropertyBox(startDrawRes = Res.drawable.ic_message_edit, stringResource(Res.string.add_review), endDrawRes = Res.drawable.ic_add_circle, label = stringResource(Res.string.add_review), onTextChange = { review = it})
        Row(
                modifier = Modifier
                    .padding(vertical = 24.dp)
        ) {
            RoundButtonView(
                Res.string.cancel, modifier = Modifier
//                        .align(Alignment.BottomCenter)
                    .weight(1f)
                    .padding(/*vertical = 24.dp,*/ horizontal = 8.dp),
                color = seccondaryButtonColor,
                onClick = {
                    navController?.popBackStack()
                }, enabled = true
            )
            RoundButtonView(
                textId = Res.string.confirm,
                modifier = Modifier
//                        .align(Alignment.BottomCenter)
                    .weight(1f)
                    .padding(/*vertical = 24.dp,*/ horizontal = 8.dp),
                onClick = {
                    if (confirmClicked) return@RoundButtonView
                    confirmClicked = true;
                    viewModel.addUntrackedWineReview(userExternalWine = untrackWine.externalWine.idUrl ?: "/api/v1/wineyard/wines/user/external/${untrackWine.id}",
                        course = course,
                        rating = rating,
                        dateOfTest = dateOfTest!!,
                        review = review,
                        tastedWith = tastingWith,
                        locationOfTest = locationOfTest,
                        volumes = pairs.value.map { it.second })
                }, enabled = dateOfTest != null)
        }
    }
    if (openResultDialog.value && uiResult != null) uiResultDialog(openDialog = openResultDialog, result = uiResult!!, msgId = Res.string.review_added, onClick = {
        scope.launch {
            viewModel.resetUiResult()
            viewModel.getUntrackedUserWineById(untrackWine.id)
//            delay(300)
            navController?.popBackStack()
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
                            dateOfTest = mSecToLocalDateTime(datePickerState.selectedDateMillis!!).date
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
fun UntrackedWineExpandableQuantityBox(atartDrawRes: DrawableResource, text: String, endDrawRes: DrawableResource, valuesMap: Map<String, Int>,
                                       onClick: ((String, Int)-> Unit)? = null,
                                       onTextChange: ((String)-> Unit)? = null
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp)),
    ) {
    Row(
        Modifier
            .height(60.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(atartDrawRes),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable { },
            contentDescription = null
        )
//        Text(
//            text = text,
//            color = Color.Black,
//            style = textHeaderStyle16
//        )
        Text( modifier = Modifier
            .padding(start = 32.dp)
            .weight(1f),
            text = text,
            style = textInfoStyle14
        )

        Image(
            painter = painterResource(endDrawRes),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable { expanded = !expanded },
            contentDescription = null
        )
    }
        if (expanded) {
            Column {
                valuesMap.keys.forEach { key ->
                    Row(modifier = Modifier.padding(start = 16.dp),) {
                        Text(
                            text = key,
                            color = Color.Black,
                            style = textHeaderStyle16
                        )
                        Row {
                            Image(
                                painter = painterResource(Res.drawable.ic_minus_circle),
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clickable {
                                        valuesMap[key]?.let {
                                            if (it > 0) onClick?.invoke(key, it - 1);
                                        }
                                    },
                                contentDescription = null
                            )

                            Text(
                                text = "${valuesMap[key]}",
                                color = Color.Black,
                                style = textHeaderStyle16
                            )
                            Image(
                                painter = painterResource(Res.drawable.ic_add_circle),
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clickable {
                                        valuesMap[key]?.let {
                                            onClick?.invoke(key, it + 1);
                                        }
                                    },
                                contentDescription = null
                            )
                        }
                    }
                }

            }
        }

//    }
}
}

@Composable
private fun ReviewInfoBox()
{
    val date = currentUtcDateTime().date
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        for (i in 1..3) {
            WinePropertyButtonBox(
                stringResource(Res.string.date_of_tasting),
                date.toSimpleMonthString(),
                Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
@Preview
private fun UUntrackedAddReviewPreview() {
    UntrackedAddReviewView(null, UntrackedModelPreview())
}