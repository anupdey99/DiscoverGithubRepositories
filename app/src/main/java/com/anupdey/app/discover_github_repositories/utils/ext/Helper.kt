package com.anupdey.app.discover_github_repositories.utils.ext

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun convertStarCount(count: Int): String {
    if (count < 1000) return count.toString()
    return "${(count/1000f).roundToInt()}k"
}

fun formatDate(input: String?, inputFormat: String = "yyyy-MM-dd'T'HH:mm:ss'Z'", outputFormat: String = "MM-dd-yyyy HH:mm"): String {
    input ?: return ""
    val sdf1 = SimpleDateFormat(inputFormat, Locale.US)
    val sdf2 = SimpleDateFormat(outputFormat, Locale.US)

    return try {
        val date = sdf1.parse(input)
        if (date != null) {
            sdf2.format(date)
        } else {
            input
        }
    } catch (e: Exception) {
        e.printStackTrace()
        input
    }
}

fun isNetworkAvailable(application: Context): Boolean {
    val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(network) ?: return false
        // Check if connected any network
        actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        // Check if only WIFI/CELLULAR/ETHERNET/VPN
        /*connected = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
            else -> false
        }*/
    } else {
        connectivityManager.activeNetworkInfo?.isConnected == true
    }
}