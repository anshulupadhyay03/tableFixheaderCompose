package com.retroent.tablefixheadercompose

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

val cellModifier = Modifier
    .border(1.dp, Color.Gray, RectangleShape)

@Composable
fun <H,R,C> DrawTableUi(
    headers: MutableList<H>,
    rowColumns: MutableMap<R, ArrayList<C>>,
    fixedHeaderView: @Composable () -> Unit,
    headerView : @Composable (header:H) -> Unit,
    fixedBodyView : @Composable (fixedBody:R) -> Unit,
    bodyView : @Composable (columns:C) -> Unit
) {
    val bodyListState = rememberLazyListState() // State for the first Row, X
    val headerListState = rememberLazyListState() // State for the second Row, Y
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 1.dp)
    ) {
        CreateHeaderRow(headers, headerListState,fixedHeaderView,headerView)
        CreateBodyRow(rowColumns, bodyListState,fixedBodyView,bodyView)
    }

    LaunchedEffect(bodyListState.firstVisibleItemScrollOffset) {
        if (!headerListState.isScrollInProgress)
            headerListState.scrollToItem(
                bodyListState.firstVisibleItemIndex,
                bodyListState.firstVisibleItemScrollOffset
            )
    }
    LaunchedEffect(headerListState.firstVisibleItemScrollOffset) {
        if (!bodyListState.isScrollInProgress)
            bodyListState.scrollToItem(
                headerListState.firstVisibleItemIndex,
                headerListState.firstVisibleItemScrollOffset
            )
    }

}

@Composable
private fun <R,C> CreateBodyRow(
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
                CreateBodyCells(row = key, value, listState,fixedBodyView,bodyView)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun <H> CreateHeaderRow(
    headers: MutableList<H>,
    listState: LazyListState,
    fixedHeaderView: @Composable () -> Unit,
    headerView: @Composable (what:H) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .padding(1.dp)
            .background(Color.Gray),
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

@Composable
private fun <R,C>CreateBodyCells(
    row: R,
    collist: ArrayList<C>,
    listState: LazyListState,
    fixedBodyView: @Composable (fixedBody: R) -> Unit,
    bodyView: @Composable (columns: C) -> Unit
) {

    Row {
        fixedBodyView(row)
        LazyRow(state = listState) {
            items(collist) {
                bodyView(it)
            }
        }
    }
}