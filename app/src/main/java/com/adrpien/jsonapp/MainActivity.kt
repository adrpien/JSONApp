package com.adrpien.jsonapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.adrpien.jsonapp.JSONDataClass as JSONDataClass

/*
REST API
Interfejs API, czyli aplikacyjny interfejs programistyczny, jest zestawem reguł definiujących sposób,
w jaki aplikacje lub urządzenia mogą się ze sobą łączyć i komunikować.
Interfejs API REST to interfejs API zgodny z zasadami projektowania REST,
czyli stylem architektury representational state transfer.
Z tego powodu interfejsy API REST są czasami nazywane interfejsami API zgodnymi ze specyfikacją REST.

API KEY
Klucz API, czyli klucz interfejsu programistycznego aplikacji, to fragment kodu oprogramowania.
Najczęściej ma postać wygenerowanego ciągu znaków.
Pozwala zidentyfikować użytkownika lub maszynę, która wywołuje komunikację.

JSON
JSON Javascript Object Notation - format wymiany danych komputerowych

COROUTINE
COROUTINE is Light weight thread

How to:
1. Add GSON dependecies
1. Creata Data class to keep JSON data
2. Create FlickrFetcher class
3. Add permission to manifest file
4. Create Coroutine in MainActivity which downloads data using Flickr API
   and serialize it into JSON data model class


 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start Coroutine
        val job = CoroutineScope(Dispatchers.IO).launch {
            // Log.d("HTTP_JSON", FlickrFetcher().getJSONString())
            // Gson is a class to serialization/deserialization JSON files
            val gson = Gson()
            val json = gson.fromJson(FlickrFetcher().getJSONString(), JSONDataClass::class.java)
            Log.d("JSON", json.age.toString())
        }
        job.start()

        if(job.isCompleted){
            job.cancel()
        }
    }
}