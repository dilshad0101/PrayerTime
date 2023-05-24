package com.app.prayertimeapp.signup

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import java.lang.reflect.Type

data class Location(val lat: Double, val lon: Double)
interface OpenStreetMapService {
    @GET("/search")
    fun getCoordinates(
        @Query("q") query: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 1
    ): Call<List<Location>>
}

suspend fun getCoordinates(country: String, city: String): Pair<Double, Double>? {
    val baseUrl = "https://nominatim.openstreetmap.org"
    val path = "/search"
    val url = (baseUrl + path).toHttpUrl().newBuilder()
        .addQueryParameter("q", "$city, $country")
        .addQueryParameter("format", "json")
        .addQueryParameter("limit", "1")
        .build()

    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .build()


    return withContext(Dispatchers.IO) {
        try {
            val response: Response = client.newCall(request).execute()
            val responseBody: String? = response.body?.string()

            if (response.isSuccessful && responseBody != null) {
                val gson = Gson()
                val listType: Type = object : TypeToken<List<Location>>() {}.type
                val locations: List<Location> = gson.fromJson(responseBody, listType)

                if (locations.isNotEmpty()) {
                    val location: Location = locations[0]
                    Log.d("1122 check",location.lat.toString())
                    Pair(location.lat, location.lon)
                } else {
                    null
                }
            } else {
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}


