package com.example.cmovies.network

import android.content.Context
import android.net.Uri
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class NetworkUtils {
    private var context: Context? = null

    fun NetworkUtils(context: Context) {
        this.context = context
    }

    companion object{
        /**
         * Checks internet connectivity using InetAddress as opposed to ConnectivityManager.
         *
         * @return The Boolean to confirm if we're connected to the internet or not.
         */
        fun isInternetAvailable(): Boolean {
            return try {
                val ipAddr: InetAddress = InetAddress.getByName("google.com")
                //You can replace it with your name
                !ipAddr.equals("")
            } catch (e: Exception) {
                false
            }
        }

        /**
         * Builds the URL used to query GitHub.
         *
         * @return The URL to use to query the Harry Potter API.
         */
        fun buildUrl(endpoint: String?): URL? {
            //We are building a url this way to cinvert Android based url to Java.
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
            //Create HTTPURLConnection object.
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            return try {
                val `in`: InputStream = urlConnection.getInputStream()

                //*This buffers the data, handles character encoding and allocates and dellocates the
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