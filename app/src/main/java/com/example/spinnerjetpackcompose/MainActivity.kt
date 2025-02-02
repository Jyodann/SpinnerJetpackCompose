package com.example.spinnerjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.spinnerjetpackcompose.ui.theme.SpinnerJetpackComposeTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpinnerJetpackComposeTheme {
                val names = listOf<String>("Jordan", "Leonard")
                var selectedName by rememberSaveable { mutableStateOf("Jordan") }
                Scaffold { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding),
                        ) {
                        Text(selectedName)
                        Spinner(
                            itemList = names,
                            selectedItem = selectedName,
                            modifier = Modifier.padding(4.dp),
                            onItemSelected = { selectedName = it }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun Spinner(
        itemList: List<String>,
        selectedItem: String,
        onItemSelected: (selectedItem: String) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        // Check to see if DropDown List should be shown:
        var expanded by rememberSaveable {
            mutableStateOf(false)
        }

        var tempSelectedItem = selectedItem

        // Automatically selects first item if nothing is selected:
        if (tempSelectedItem.isBlank() && itemList.isNotEmpty()) {
            tempSelectedItem = itemList.first()
            onItemSelected(tempSelectedItem)
        }

        // The Spinner itself:
        OutlinedButton(onClick = {
            expanded = !expanded
        },  enabled = itemList.isNotEmpty(),
            modifier = modifier) {

            // The text for the Selected Item:
            Text(
                text = tempSelectedItem,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            // Changes Icon if DropDown List is expanded or not:
            Icon(if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null)

            // Fills up DropDownMenu based on Items passed in:
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                itemList.forEach { item ->
                    DropdownMenuItem(text = {
                        Text(text = item)
                    },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                        })
                }
            }
        }
    }
}