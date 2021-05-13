package com.example.cmovies.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.example.cmovies.thread.AppExecutors
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class NetworkUtils {

    companion object{
        /**
         * Checks internet connectivity
         * We can use based on the type of connectivity.
         * @return The Boolean to confirm if we're connected to the internet or not.
         */
        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.i("NetworkUtils", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i("NetworkUtils", "NetworkCapabilities.TRANSPORT_WIFI")

                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i("NetworkUtils", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }

        /**
         * Builds the URL used to query GitHub.
         *
         * @return The URL to use to query the Harry Potter API.
         */
        fun buildUrl(endpoint: String?): URL? {
            Log.i("NetworkUtils: ", "buildUrl()")
            //We are building a url this way to convert Android based url to Java.
            //Also the methods buildUpon and build() reduce
            val buildUri: Uri = Uri.parse(endpoint).buildUpon().build()
            var url: URL? = null
            try {
                url = URL(buildUri.toString())
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            return url
        }

        @Throws(IOException::class)
        fun getResponseFromHttpUrl(url: URL): String? {
            Log.i("NetworkUtils: ", "getResponseFromHttpUrl()")
            //Create HTTPURLConnection object.
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            return try {
                val `in`: InputStream = urlConnection.inputStream

                //*This buffers the data, handles character encoding and allocates and de-allocates the
                //*//buffers as needed
                val scanner = Scanner(`in`)
                scanner.useDelimiter("\\A")
                val hasInput: Boolean = scanner.hasNext()
                if (hasInput) {
                    scanner.next()
                } else {
                    null
                }
            } finally {
                urlConnection.disconnect()
            }
        }
    }
}