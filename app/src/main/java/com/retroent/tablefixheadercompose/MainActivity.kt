package com.retroent.tablefixheadercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.retroent.tablefixheadercompose.ui.theme.TableFIxHeaderComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TableFIxHeaderComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    drawUi()
                }
            }
        }
    }
}

val cellModifier = Modifier
    .padding(2.dp)
    .border(1.dp, Color.Gray, RectangleShape)
    .padding(1.dp)

@Composable
private fun drawUi() {

    val headers = arrayListOf("Fix header")

    for (i in 1..10) {
        headers.add("H $i")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(5.dp)
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
                        modifier = cellModifier
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(ScrollState(0))
                /*.scrollable(
                    orientation = Orientation.Horizontal,
                    state = rememberScrollableState(consumeScrollDelta = { delta ->
                        println("scrollabel $delta")
                        delta
                    })
                )*/
        ) {
            repeat(30) {
                createHorCell(it, scroller)
            }
        }

    }
}

@Composable
private fun createHorCell(row: Int, scroller: ScrollState) {
    Row {
        Text(
            modifier = cellModifier
                .width(80.dp),
            text = "R $row",
            textAlign = TextAlign.Center
        )
        Row(
            Modifier
                .fillMaxHeight()
                .horizontalScroll(scroller)
        ) {
            repeat(10) { col ->
                println("Row $col")
                Text(
                    modifier = cellModifier
                        .width(80.dp),
                    text = "C $row-$col",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TableFIxHeaderComposeTheme {
        drawUi()
    }
}