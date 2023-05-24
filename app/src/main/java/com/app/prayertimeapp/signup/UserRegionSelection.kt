package com.app.prayertimeapp.signup

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.prayertimeapp.util.CountryCityData
import kotlinx.coroutines.*
import java.util.*

data class Country(val id: Int,val name: String)
data class City(val id: Int,  val name:String,val country: String)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun RegionSelection(
    navController: NavController,
    onRegionSubmit:(country: String,city:String) -> Unit
){
    val countryCityData = CountryCityData(LocalContext.current)

    val countryList = countryCityData.getCountryList()
    val countries = remember {
        derivedStateOf { countryList }
    }
    val focusRequester = remember{FocusRequester()}
    var selectedCountry by remember {
        mutableStateOf("")
    }

    var selectedCity by remember {
        mutableStateOf("")
    }

    var showCountryLazyList by remember{ mutableStateOf(false) }

    var showCityLazyList by remember {
        mutableStateOf(false)
    }

    var cityFieldError by remember {
        mutableStateOf(false)
    }

    var countryFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var cityFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(Modifier.padding(top = 40.dp)) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
                .padding(horizontal = 20.dp)
            ){
                Text("Select Your Region", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
            }

            Column(modifier = Modifier.padding(vertical = 40.dp)) {
                OutlinedTextField(value = countryFieldValue, onValueChange = {
                    countryFieldValue  = it
                },
                    label = { Text(text = "Country", fontSize = 16.sp)},
                    placeholder = { Text(text = "Search Country", fontSize = 16.sp)},
                    maxLines = 1,
                    modifier = Modifier
                        .onFocusChanged { showCountryLazyList = it.isFocused }
                        .fillMaxWidth()

                        .padding(start = 30.dp, end = 40.dp)
                )
                AnimatedContent(targetState = showCountryLazyList) {
                    if(it){
                        LazyColumn(modifier = Modifier
                            .sizeIn(maxHeight = 350.dp)
                            .padding(horizontal = 32.dp, vertical = 10.dp),

                            contentPadding = PaddingValues(1.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                            horizontalAlignment = Alignment.Start
                        ){
                            val filteredList:MutableList<String> =
                                if (countryFieldValue.text.isEmpty()){
                                    countries.value
                                }else{
                                    val resultList = mutableListOf<String>()
                                    for (items in countries.value){
                                        if(items.lowercase(Locale.getDefault())
                                                .contains(countryFieldValue.text
                                                    .lowercase(Locale.getDefault()))){

                                            resultList.add(items)
                                        }

                                    }
                                    resultList
                                }

                            this.items(filteredList){
                                TextButton(onClick = {
                                    selectedCountry = it
                                    countryFieldValue = TextFieldValue(it)
                                    focusRequester.requestFocus()
                                    showCountryLazyList = false
                                },
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text(text = it, textAlign = TextAlign.Start)
                                }
                            }
                        }
                    }
                }



            }

            Column {
                OutlinedTextField(value = cityFieldValue, onValueChange = {
                    cityFieldValue  = it
                },
                    isError = cityFieldError,
                    label = { Text(text = "City",fontSize = 16.sp)},
                    placeholder = { Text(text = "Search City", fontSize = 16.sp)},

                    maxLines = 1,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            cityFieldError = it.isFocused && selectedCountry.isBlank()
                            showCityLazyList = selectedCountry.isNotBlank()
                        }
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.CenterStart)
                        .padding(start = 30.dp, end = 30.dp),
                )
                if (cityFieldError){
                    Text(
                        "Select a country first!", fontSize = 13.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(start = 40.dp, end = 100.dp),
                    )
                }

                val cityList =
                    if(selectedCountry == ""){
                        mutableListOf<String>()
                    }else{
                        val cities = remember {
                            derivedStateOf { countryCityData.getCitiesInCountry(selectedCountry) }
                        }

                        val resultList = mutableListOf<String>()
                        if (cityFieldValue.text.isEmpty()){
                            cities.value
                        }else{
                            for(i in cities.value){
                                if(i.lowercase(Locale.getDefault())
                                        .contains(cityFieldValue.text.lowercase(Locale.getDefault())))
                                    resultList.add(i)
                            }
                            resultList
                        }
                    }
                AnimatedContent(targetState = showCityLazyList) {
                    if (it){
                        LazyColumn(modifier = Modifier
                            .sizeIn(maxHeight = 500.dp)
                            .padding(horizontal = 32.dp, vertical = 10.dp),
                        ){
                            this.items(cityList){
                                TextButton(onClick = {
                                    selectedCity = it
                                    cityFieldValue = TextFieldValue(it)
                                    showCityLazyList = false
                                    Log.d("ok22", "selected country:${selectedCountry}\n city: $it")
                                }) {
                                    Text(text = it)
                                }
                            }
                        }
                    }

                }
                val scope = rememberCoroutineScope()
                if(selectedCountry.isNotBlank() && selectedCity.isNotBlank())
                    TextButton(onClick = { onRegionSubmit.invoke(selectedCountry,selectedCity) }) {
                        Text(text = "Submit")
                    }

            }

        }
    }




}