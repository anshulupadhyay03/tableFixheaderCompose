package com.retroent.tablefixheadercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

    val rowColumns = mutableMapOf<String,ArrayList<String>>()
    repeat(100){ row->
        val rowKey = "Row$row"
        val listofCols = arrayListOf<String>()
        repeat(20){ col->
            listofCols.add("Col$row-$col")
        }
        rowColumns[rowKey] = listofCols
    }
    val firstHeader = headers.removeAt(0)

    drawUi(firstHeader,headers = headers, rowColumns)

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TableFIxHeaderComposeTheme {
        //drawUi()
    }
}

