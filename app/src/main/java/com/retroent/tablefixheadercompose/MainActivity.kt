package com.retroent.tablefixheadercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
                    SetUp()
                    //DUME()
                }
            }
        }
    }
}

@Composable
fun SetUp() {
    val headers = arrayListOf("Fix header")
    for (i in 1..20) {
        headers.add("H $i")
    }

    val rowColumns = mutableMapOf<String, ArrayList<String>>()
    repeat(100) { row ->
        val rowKey = "Row$row"
        val listofCols = arrayListOf<String>()
        repeat(20) { col ->
            listofCols.add("Col$row-$col")
        }
        rowColumns[rowKey] = listofCols
    }
    val firstHeader = headers.removeAt(0)

    DrawTableUi(
        headers,
        rowColumns,
        fixedHeaderView = {
            Text(
                modifier = Modifier
                    .height(40.dp)
                    .width(80.dp)
                    .zIndex(15f)
                    .background(Color.LightGray)
                    .wrapContentHeight(Alignment.CenterVertically),
                text = firstHeader
            )
        },
        headerView = {
            Text(
                modifier = Modifier
                    .height(40.dp)
                    .width(80.dp)
                    .border(1.dp, Color.Black, RectangleShape)
                    .wrapContentHeight(Alignment.CenterVertically),
                text = it,
                textAlign = TextAlign.Center
            )
        },
        fixedBodyView = {
            Text(
                modifier = cellModifier
                    .width(80.dp)
                    .zIndex(15f)
                    .background(Color.LightGray),
                text = it,
                textAlign = TextAlign.Center
            )
        },
        bodyView = {
            Text(
                modifier = cellModifier
                    .width(80.dp),
                text = it,
                textAlign = TextAlign.Center
            )
        }
    )

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TableFIxHeaderComposeTheme {
        //drawUi()
    }
}

