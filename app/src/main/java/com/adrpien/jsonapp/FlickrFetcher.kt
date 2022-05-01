package com.adrpien.jsonapp

import android.net.Uri
import android.util.Log
import org.json.JSONException
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

data class JSONDataClass(
    val age: Int,
    val isWoman: Boolean,
    val name: String
)

/*
   Function getJSONString creates query and calls method getUrlString.
   Method getUrlString convert ByteArray from getUrlBytes to String and returns it.
   Method get UrlBytes uses query to create url connection,
   then write bytes from inputStream to ByteArray and returns it.
 */
class FlickrFetcher {

    // Unique api key
    val API_KEY: String = "9ffe97542882000304183b2590d40ea8"

    // This method creates url connection and download bytes (data)
    fun getUrlBytes(urlSpec: String): ByteArray{

        // Creating HttpsURLConnection
        val url = URL(urlSpec)
        val connection =  url.openConnection() as HttpsURLConnection

        // Use block "try/catch" in order to manage code execution problems
        try {

            val out = ByteArrayOutputStream()
            val input = connection.inputStream

            // Throw Exception if there is problem with connection
            if(connection.responseCode != HttpsURLConnection.HTTP_OK){
                throw IOException(connection.responseMessage)
            }

            var bytesRead: Int

            // buffer is ByteArray which keep portion of  out data
            val buffer =  ByteArray(1024)

            // Read bytes form inputStream in 1024 bytes portions and then write them into out ByteArrayOutputStream
            do{

                // Reads some number of bytes from the input stream and stores them into the buffer array
                // The number of bytes actually read is returned as an integer.
                // saving inputStream to buffer, method read returns number of bytes
                // bytesRead is number of bytes stored in buffer
                bytesRead = input.read(buffer)

                // Function write takes bytes from buffer and writes them to ByteArrayOutputStream
                // Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                out.write(buffer, 0, bytesRead)
                var outsize = out.size()
            } while(input.read(buffer)>0)

            // Rememeber to close ByteArrayOutputStream a the end
            out.close()

            // Return var out as ByteArray
            return out.toByteArray()
        }

        // Catch in case of  connection error
        catch (e: IOException){
            Log.e("ERROR_HTTP_CONNECTION", e.message.toString())

            // Method has to return ByteArray type
            return ByteArray(0)
        }

        finally {

            // Remember to disconnect at the end
            connection.disconnect()
        }
    }

    // This method convert bytes into String
    fun getUrlString(urlSpec: String): String{
        return String(getUrlBytes(urlSpec))
    }

    // This method creates URL, which is used then in getURLString
    // (which use method getURLBytes inside of itseld) and then returns JSON String
    fun getJSONString(): String {

        /*
        var jsonString = "UPS!"
        try {

            // Creating String query
            val url: String = Uri.parse("https://www.flickr.com/services/rest/")
                .buildUpon()
                .appendQueryParameter("method", "flickr.photos.getRecent")
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .appendQueryParameter("extras", "url_s")
                .build().toString()

            // Using String query as argument of getURLString method
            jsonString = getUrlString(url)

        } catch (je: JSONException){
            Log.e("JSON_ERROR", je.message.toString())
        }
        */

        // JSON String
        var jsonString = """{ 
                "name": "Edyta",
                "isWoman": true,
                "age": 23 
                }"""
        .trimIndent()



        return jsonString
    }

}