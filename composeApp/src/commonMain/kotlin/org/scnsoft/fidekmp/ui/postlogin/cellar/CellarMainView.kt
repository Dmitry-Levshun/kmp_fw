package org.scnsoft.fidekmp.ui.postlogin.cellar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.scnsoft.fidekmp.ui.postlogin.cellar.untracked.UntrackedUserWinePaginatedListView
import org.scnsoft.fidekmp.ui.theme.primaryColor
import org.scnsoft.fidekmp.ui.theme.walletBackColor
import org.scnsoft.fidekmp.ui.utils.AppScreens
import org.scnsoft.fidekmp.ui.utils.BottomNavBar
import org.scnsoft.fidekmp.ui.utils.CustomTabs
import org.scnsoft.fidekmp.ui.utils.CustomToolbar
//import org.scnsoft.fidekmp.ui.utils.FilterDialog
//import org.scnsoft.fidekmp.ui.utils.FilterItem
import org.scnsoft.fidekmp.ui.utils.RoundButtonView
import org.scnsoft.fidekmp.ui.utils.SearchToolBar
import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.scnsoft.fidekmp.ui.theme.primaryColor
import app.cash.paging.compose.collectAsLazyPagingItems
import org.scnsoft.fidekmp.utils.toFixed


const val EN_PRIMEUR = 0
const val EN_LIVRABLE = 1
@Composable
fun CellarMainScreen(
    homeViewModel: ICellarModel,
    navController: NavHostController?,
) {
    var isSearch by remember {
        mutableStateOf(false)
    }
    val openFilterDialog = remember {
        mutableStateOf(false)
    }
    var route by remember {
        mutableStateOf("")
    }

//    val searchText by homeViewModel.searchText.collectAsState()
    val isConsumer by homeViewModel.isConsumer.collectAsState(initial = false)
    val profileInfo by homeViewModel.profileInfo.collectAsState()
    Napier.d("CellarMainScreen")
    val titleId = Res.string.my_cellar
    val backColor = primaryColor
    val searchText = ""//by homeViewModel.searchText.collectAsState()
    Scaffold(
        topBar = { if (!isSearch){
            CustomToolbar(
                title = stringResource(titleId),
                navController = navController!!,
                onSearchClick = { isSearch = !isSearch },
                onFilterClick = {
                    Napier.d("fun onFilerClick ${navController?.currentBackStackEntry?.destination?.route}")
                    route = navController?.currentBackStackEntry?.destination?.route ?: ""
                    openFilterDialog.value = true
                },

                backGroundColor = backColor)
        } else { SearchToolBar(
            searchText,
            onTextChange = {},//homeViewModel::onSearchTextChanged,
            onCloseClick = {Napier.d("Home onCloseClick ")
                isSearch = false},
//            onSearchClick = {}
            backGroundColor = backColor
        )
        }},
        bottomBar = { BottomNavBar(navController = navController, profileInfo.userType, profileInfo.permissions) },
        content = {
            CellarMainView(homeViewModel, navController)
//            if (appTabType == 0) HomeView(homeViewModel, navController)
//            else CellarMainView(viewModel = homeViewModel, navController = navController)
        },
        // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
        // how it should animate.

    )
//    if (openFilterDialog.value) FilterDialog(openFilterDialog, route, homeViewModel)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CellarMainView(viewModel: ICellarModel, navController: NavHostController?){
    var walletId by remember {
        mutableStateOf("")
    }
    var untrackerLoaded by remember {mutableStateOf(false)}
    val isLoading by viewModel.isloadingState.collectAsState()
    val isConsumer by viewModel.isConsumer.collectAsState(initial = false)
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = viewModel::getWines
    )
    val list = mutableListOf(stringResource(Res.string.en_primeur), stringResource(Res.string.en_livrable))
    if (isConsumer) list += stringResource(Res.string.untracked)
    val tabIndex by viewModel.cellarTabIndexState.collectAsState()
    val domainList by viewModel.domainListState.collectAsState(listOf())
    val untrackedUserWineList by viewModel.untrackedUserWineListState.collectAsState(initial = listOf())
    val untrackedPagedUserWineList = viewModel.untrackedUserWines.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    if (isConsumer && !untrackerLoaded) {
        untrackedPagedUserWineList.refresh()
        untrackerLoaded = true
    }

    var stickStatus by remember {
        mutableStateOf<String>("")
    }
    var vintage by remember {
        mutableStateOf("")
    }
    var vintageInd by remember {
        mutableStateOf(-1)
    }

    viewModel.selectCellarTab(tabIndex)
//    val years = remember {
//        arrayOf("2020", "2021", "2022")
//    }
    val priceSt = domainList.flatMap{it.wines.map { it.price.priceSymbol } }.toSet()
    val priceVal = mutableListOf<Double>()
    priceSt.forEach{sym ->
        var pr = 0.0
        val vl =  domainList.flatMap{it.wines.map { if (it.price.priceSymbol == sym) it.price.value else 0.0  } }
        vl.forEach{ pr += it }
        priceVal += pr
    }
    Napier.d("CellarMainView $priceSt $priceVal")
    var totalPrice = ""
    priceSt.forEachIndexed { ind, sym ->
        totalPrice += "$sym ${priceVal[ind].toFixed(2)} | "
    }
    totalPrice = totalPrice.dropLast(2)
    Box(modifier = Modifier
        .fillMaxSize()
        .background(walletBackColor)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
/*
            Text(
                text = stringResource(id = R.string.my_cellar_value) + ": $totalPrice",
                style = textHeaderStyle,
                color = primaryTextColor
            )

 */
            CustomTabs(list, startInd = tabIndex, onClick = viewModel::selectCellarTab, isHigh = false)
            val  route = navController?.currentBackStackEntry?.destination?.route ?: ""
            val filterListMap by viewModel.filterItemsState.collectAsState()
            val filterList = filterListMap[route]
            Napier.d("CellarMainView $route $filterList ${filterListMap.keys}")
            if (filterList == null) viewModel.clearFilter()
//            filterList?.let { ShowFilter(it, onCloseClick ={ viewModel.updateFilter() } ) }
//            Spacer(modifier = Modifier.height(16.dp))
            if(tabIndex == EN_LIVRABLE) {
//                DropDownList(R.string.vintage, years, vintageInd) {
//                    vintageInd = it
//                    vintage = years[it]
//                }
                stickStatus = stringResource(Res.string.en_livrable)
            } else {
                stickStatus = stringResource(Res.string.en_primeur)
            }
/*
            when (tabIndex) {
                EN_PRIMEUR -> Column {
                    domainList[0].wines.forEach{item ->
                        TextInfoCard(item, backColor = Color.White, onItemClick = { item ->
                            Timber.d("ClientInfoView click $item")
                            viewModel.setWineDetailInfo(item)
                            navController?.navigate(AppScreens.WineDetails.route)
                        })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                EN_LIVRABLE -> CellarDomainListView(clientList = domainList, onItemClick = { item ->
                Timber.d("ClientInfoView click $item")
                    viewModel.setWineDetailInfo(item)
                    navController?.navigate(AppScreens.WineDetails.route)
                })
                }
*/
            PullRefreshIndicator(
                refreshing = isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            if (tabIndex < 2) {
                CellarDomainListView(
                    clientList = domainList,
                    pullRefreshState,
                    onItemClick = { item ->
                        Napier.d("ClientInfoView click $item")
                        viewModel.setWineDetailInfo(item)
                        navController?.navigate(AppScreens.WineDetails.route)
                    },
                    onBoxClick = viewModel::getWines
                )
            } else {
//                CellarUntrackedWineList(clientList = untrackedUserWineList.map{ UntrackedCellarWineItem.fromUntrackedUserWineItem(it) }, onItemClick = { item ->
                UntrackedUserWinePaginatedListView(untrackedPagedUserWineList, onItemClick = { item ->
                    Napier.d("ClientInfoView click $item")
                    scope.launch {
                        viewModel.getUntrackedUserWineById(item.id)
                        delay(300)
                        navController?.navigate(AppScreens.UntrackedWineDetailsScreen.route)
                    }
                })
            }
        }
        if (isConsumer) {
            RoundButtonView(
                text = stringResource(Res.string.add_untracked_wine),
                color = primaryColor,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 60.dp)
                    .align(Alignment.BottomCenter),
                enabled = true,
                onClick = {
                    viewModel.onUntrackWineSearch("")
                    navController?.navigate(AppScreens.UntrackedMainScreen.route)
                })
        }
        }

//        ClientInfoView(clientList = clientList)

}
/*
fun filterVintage(domainList: List<DomainInfo>, vintageYear: String): List<DomainInfo> {
    Timber.d("filterVintage $vintageYear")
    if (vintageYear.isEmpty() || vintageYear.contains("all", true)) return domainList
    return domainList.filter { it.wines.map { wine -> wine.itemName.contains(vintageYear, true)}.contains(true)}
}

@Composable
fun ShowFilterOld(filterlist: List<FilterEntity>, onCloseClick: (()->Unit)? = null) {
    var list = mutableListOf<FilterData>()
    filterlist.forEach{ filter ->
        filter.data.forEachIndexed lb1@ {ind, item ->
            if (ind != 0 && item.selected) list += item
        }
    }
    val len = list.sumOf { it.name.length }
    Timber.d("ShowFilter len:$len items:${list.map{it.name.length}}")

    Row() {

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(if (len > 43) 0.96f else 1f),
            horizontalArrangement = Arrangement.Start
        ) {
            this.items(list) { entry ->
                FilterItem(entry.name, onCloseClick = {
                    entry.selected = false
                    onCloseClick?.invoke()
                })
            }
        }

        if (len > 43) {
            Image(
                Icons.Filled.KeyboardArrowRight, contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(primaryColor),
                modifier = Modifier
                    .size(16.dp),
                alignment = Alignment.CenterEnd
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShowFilter(filterlist: List<FilterEntity>, onCloseClick: (()->Unit)? = null) {
    var list = mutableListOf<FilterData>()
    filterlist.forEach{ filter ->
        filter.data.forEachIndexed lb1@ {ind, item ->
            if (ind != 0 && item.selected) list += item
        }
    }
    FlowRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        list.forEach { entry ->
            FilterItem(entry.name, onCloseClick = {
                entry.selected = false
                onCloseClick?.invoke()
            })
        }
    }
}
*/
//@Preview(name = "CellarMainView")
//@Composable
//private fun PreviewCellarMainView() {
//    CellarMainView(null, null)
//}