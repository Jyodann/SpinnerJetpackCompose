package com.example.spinnerjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spinnerjetpackcompose.ui.theme.SpinnerJetpackComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpinnerJetpackComposeTheme {
               MyApp()
            }
        }
    }
}

class AppViewModel : ViewModel(){
    val gender: MutableState<String> = mutableStateOf("Male")
    val name: MutableState<String> = mutableStateOf("Jordan")

    val names: MutableState<List<String>> = mutableStateOf(listOf())
    init {
        viewModelScope.launch {
            delay(3000)
            names.value = listOf("Jordan", "Leonard")
        }
    }
}

@Composable
fun MyApp(appViewModel: AppViewModel = viewModel()){
    if (appViewModel.names.value.isNotEmpty()){
        Column {
            Text(text = "Currently Selected Name: ${appViewModel.name.value}")
            Text(text = "Currently Selected Gender: ${appViewModel.gender.value}")
            Row {
                DropDownMenu(
                    items = appViewModel.names.value,
                    selectedItem = appViewModel.name.value,
                    onValueChange = { appViewModel.name.value = it },
                    modifier = Modifier.weight(1f)
                )
                DropDownMenu(
                    items = listOf("Male", "Female"),
                    selectedItem = appViewModel.gender.value,
                    onValueChange = { appViewModel.gender.value = it },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }else {
        Text(text = "Loading Data...")
    }

}

@Composable
fun DropDownMenu(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedItem: String,
    dropdownLabel: String = "",
    onValueChange: (String) -> Unit,
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    Box (modifier = modifier
        .fillMaxWidth()
        .padding(4.dp)){
        Column {
            if (dropdownLabel.isNotEmpty()){
                Text(dropdownLabel)
            }
            TextField(
                value = selectedItem,
                onValueChange = onValueChange,
                trailingIcon = { Icon(Icons.Default.KeyboardArrowDown, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

        }
        Spacer(modifier = Modifier
            .matchParentSize()
            .background(Color.Transparent)
            .clickable { expanded = true })
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach {
                    item -> DropdownMenuItem(
                onClick = {
                    onValueChange(item)
                    expanded = false
                },
            ) {
                Text(text = item)
            }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewDropDown() {

}
