package com.retroent.tablefixheadercompose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun drawUi(headers: MutableList<String>, rowColumns: MutableMap<String, ArrayList<String>>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 1.dp)
    ) {
        val scroller = rememberScrollState()
        val headerScroller: ScrollState by remember { mutableStateOf(scroller) }
        val firstHeader = headers.removeAt(0)
        Row(
            modifier = Modifier
                .padding(1.dp)
                .background(Color.Gray),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Text(
                modifier = Modifier
                    .height(40.dp)
                    .width(80.dp)
                    .border(1.dp, Color.Black, RectangleShape)
                    .wrapContentHeight(Alignment.CenterVertically),
                text = firstHeader,

                )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(headerScroller)

            ) {
                headers.forEach {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                /*.verticalScroll(ScrollState(0))*/
        ) {
            rowColumns.forEach { (key, value) ->
                println("rowcols : $key")
                item{
                    createHorCell(row = key,value, scroller = scroller)
                }
            }

        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun createHorCell(row: String, collist: ArrayList<String>, scroller: ScrollState) {
    LazyRow{
        stickyHeader {
            Text(
                modifier = cellModifier
                    .width(80.dp)
                    .zIndex(15f)
                    .background(Color.LightGray),
                text = row,
                textAlign = TextAlign.Center
            )
        }
        item {
            Row(
            ) {
                collist.forEach {
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
    /*Row {
        Text(
            modifier = cellModifier
                .width(80.dp)
                .zIndex(15f)
                .background(Color.LightGray),
            text = row,
            textAlign = TextAlign.Center
        )
        Row(
            Modifier.horizontalScroll(scroller)
        ) {
            collist.forEach {
                Text(
                    modifier = cellModifier
                        .width(80.dp),
                    text = it,
                    textAlign = TextAlign.Center
                )
            }
        }
    }*/
}