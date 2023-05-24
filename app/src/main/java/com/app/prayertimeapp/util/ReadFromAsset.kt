package com.app.prayertimeapp.util

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import org.json.JSONObject

const val CountryCityAssetFile = "countriesToCities.json"

class CountryCityData(context: Context){
    var jsonInString :String
    init {
        val inputStream = context.assets.open(CountryCityAssetFile)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        jsonInString =  String(buffer, Charsets.UTF_8)
    }


    fun getCountryList(): MutableList<String> {
        val countryList : MutableList<String> = mutableListOf<String>()
         JSONObject(jsonInString).keys().forEach {
            countryList.add(it)
        }
        return countryList
    }
    fun getCitiesInCountry(country: String):MutableList<String>{

        val jsonArray = JSONObject(jsonInString).getJSONArray(country)
        Log.d("ok22",MutableList(jsonArray.length()){jsonArray.getString(it)}.toString())
        return MutableList(jsonArray.length()){jsonArray.getString(it)}
    }

}