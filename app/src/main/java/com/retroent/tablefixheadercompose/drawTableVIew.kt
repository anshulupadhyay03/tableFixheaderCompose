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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

val cellModifier = Modifier
    .border(1.dp, Color.Gray, RectangleShape)

@Composable
fun drawUi(
    firstHeader: String,
    headers: MutableList<String>,
    rowColumns: MutableMap<String, ArrayList<String>>
) {
    val stateRowX = rememberLazyListState() // State for the first Row, X
    val stateRowY = rememberLazyListState() // State for the second Row, Y

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 1.dp)
    ) {
        createHeaderRow(firstHeader,headers,stateRowY)
        createBodyRow(rowColumns,stateRowX)

        //This might seem crazy

        LaunchedEffect(stateRowX.firstVisibleItemScrollOffset) {
            if(stateRowY.isScrollInProgress.not())
            stateRowY.scrollToItem(
                stateRowX.firstVisibleItemIndex,
                stateRowX.firstVisibleItemScrollOffset
            )
        }

        LaunchedEffect(stateRowY.firstVisibleItemScrollOffset) {
            if(stateRowX.isScrollInProgress.not())
            stateRowX.scrollToItem(
                stateRowY.firstVisibleItemIndex,
                stateRowY.firstVisibleItemScrollOffset
            )
        }

    }
}

@Composable
private fun createBodyRow(
    rowColumns: MutableMap<String, ArrayList<String>>,
    listState: LazyListState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        rowColumns.forEach { (key, value) ->
            item {
                createHorCell(row = key, value,listState)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun createHeaderRow(
    firstHeader: String,
    headers: MutableList<String>,
    listState: LazyListState
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
            Text(
                modifier = Modifier
                    .height(40.dp)
                    .width(80.dp)
                    .zIndex(15f)
                    .background(Color.LightGray)
                    .wrapContentHeight(Alignment.CenterVertically),
                text = firstHeader
            )
        }
        items(headers) {
            Text(
                modifier = Modifier
                    .height(40.dp)
                    .width(80.dp)
                    .border(1.dp, Color.Black, RectangleShape)
                    .wrapContentHeight(Alignment.CenterVertically),
                text = it,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun createHorCell(
    row: String,
    collist: ArrayList<String>,
    listState: LazyListState
) {

    Row() {
        Text(
            modifier = cellModifier
                .width(80.dp)
                .zIndex(15f)
                .background(Color.LightGray),
            text = row,
            textAlign = TextAlign.Center
        )

        LazyRow(state = listState) {
            items(collist) {
                Text(
                    modifier = cellModifier
                        .width(80.dp),
                    text = it,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}