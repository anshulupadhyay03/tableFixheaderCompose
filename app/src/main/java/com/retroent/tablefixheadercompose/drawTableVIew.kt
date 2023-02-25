package com.retroent.tablefixheadercompose

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SomeTests() {

    val stateRowX = rememberLazyListState() // State for the first Row, X
    val stateRowY = rememberLazyListState() // State for the second Row, Y
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollableState { delta ->
        scope.launch {
            stateRowX.scrollBy(-delta)
            stateRowY.scrollBy(-delta)
        }
        delta
    }

    /*LaunchedEffect(stateRowX.firstVisibleItemScrollOffset) {
        println("stateRowX")
        if (!stateRowY.isScrollInProgress)
            stateRowY.scrollToItem(
                stateRowX.firstVisibleItemIndex,
                stateRowX.firstVisibleItemScrollOffset
            )
    }
    LaunchedEffect(stateRowY.firstVisibleItemScrollOffset) {
        println("headerListState")
        if (!stateRowX.isScrollInProgress)
            stateRowX.scrollToItem(
                stateRowY.firstVisibleItemIndex,
                stateRowY.firstVisibleItemScrollOffset
            )
    }*/

    Column(
        modifier = Modifier
            .background(Color.Magenta)
    ) {

        LazyRow(
            state = stateRowX
        ) {
            stickyHeader {
                Text(text = "Fixed :-")
            }
            items(LoremIpsum(10).values.toList()) {
                Text(text = it)
            }
        }

        LazyColumn(
        ) {
            // repeat(10) {
            item {
                LazyRow(
                    state = stateRowY
                ) {
                    stickyHeader {
                        Text(text = "Fixed $1:")
                    }
                    items(LoremIpsum(10).values.toList()) {
                        Text(text = it)
                    }
                }
            }
            //}
        }
    }
}

val cellModifier = Modifier
    .border(1.dp, Color.Gray, RectangleShape)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <H, R, C> DrawTableUi(
    headers: MutableList<H>,
    rowColumns: MutableMap<R, ArrayList<C>>,
    fixedHeaderView: @Composable () -> Unit,
    headerView: @Composable (header: H) -> Unit,
    fixedBodyView: @Composable (fixedBody: R) -> Unit,
    bodyView: @Composable (columns: C) -> Unit
) {

    val bodyListState = rememberLazyListState() // State for the first Row, X
    val headerListState = rememberLazyListState() // State for the second Row, Y
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CreateHeaderRow(headers, headerListState, fixedHeaderView, headerView)
        CreateBodyRow(rowColumns, bodyListState, fixedBodyView, bodyView)
        LazyRow {
            stickyHeader {
                fixedHeaderView.invoke()
            }
            items(headers) {
                headerView(it)
            }

        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(rowColumns.keys.toList(), key = { it.hashCode() }) {
                Row {
                    fixedBodyView(it)
                    LazyRow {
                        items(rowColumns[it]!!.toList()) {
                            bodyView(it)
                        }

                    }

                }
            }
        }
    }

    LaunchedEffect(bodyListState.firstVisibleItemScrollOffset) {
        println("bodyListState")
        if (!headerListState.isScrollInProgress)
            headerListState.scrollToItem(
                bodyListState.firstVisibleItemIndex,
                bodyListState.firstVisibleItemScrollOffset
            )
    }
    LaunchedEffect(headerListState.firstVisibleItemScrollOffset) {
        println("headerListState")
        if (!bodyListState.isScrollInProgress)
            bodyListState.scrollToItem(
                headerListState.firstVisibleItemIndex,
                headerListState.firstVisibleItemScrollOffset
            )
    }
}

@Composable
fun <R, C> CreateBodyRow(
    rowColumns: MutableMap<R, ArrayList<C>>,
    listState: LazyListState,
    fixedBodyView: @Composable (fixedBody: R) -> Unit,
    bodyView: @Composable (columns: C) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        rowColumns.forEach { (key, value) ->
            item {
                CreateBodyCells(row = key, value, listState, fixedBodyView, bodyView)
            }
        }
    }
}

@Composable
fun <R, C> CreateBodyCells(
    row: R,
    collist: ArrayList<C>,
    listState: LazyListState,
    fixedBodyView: @Composable (fixedBody: R) -> Unit,
    bodyView: @Composable (columns: C) -> Unit
) {

    Row {
        fixedBodyView(row)
        LazyRow(state = listState) {
            items(items = collist, key = { it.hashCode() }) {
                bodyView(it)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <H> CreateHeaderRow(
    headers: MutableList<H>,
    listState: LazyListState,
    fixedHeaderView: @Composable () -> Unit,
    headerView: @Composable (what: H) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .background(Color.LightGray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        state = listState,

        ) {
        stickyHeader {
            fixedHeaderView()
        }
        items(headers) {
            headerView(it)
        }
    }
}