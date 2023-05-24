package com.app.prayertimeapp.data

import android.content.Context
const val MAIN_KEY = "main"
const val KEY = "child"
const val SECOND_KEY = "secondKey"

fun saveToSharedPref(context: Context,value1:String,value2:String,key:Int = 0){
    val sharedPreferences = context.getSharedPreferences(MAIN_KEY, Context.MODE_PRIVATE)
    if(key == 0){
        val editor = sharedPreferences.edit()
        editor.putStringSet(KEY, setOf(value1,value2))
        editor.apply()
    }else{
        val editor = sharedPreferences.edit()
        editor.putStringSet(SECOND_KEY, setOf(value1,value2))
        editor.apply()
    }

}

fun getSharedPref(context: Context, key:Int = 0): Pair<String,String>? {
    val sharedPreferences = context.getSharedPreferences(MAIN_KEY, Context.MODE_PRIVATE)
    if (key == 0){
        val coordinates = sharedPreferences.getStringSet(KEY,null)
        return if (coordinates != null) {
            if(coordinates.contains(null)){
                null
            }
            else{
                Pair(coordinates.first(),coordinates.last())
            }

        }
        else{
            null
        }
    }else{
        val cityAndCountry = sharedPreferences.getStringSet(SECOND_KEY,null)
        return if (cityAndCountry != null) {
            if(cityAndCountry.contains(null)){
                null
            }
            else{
                Pair(cityAndCountry.first(),cityAndCountry.last())
            }

        }else{
            null
        }
    }

}