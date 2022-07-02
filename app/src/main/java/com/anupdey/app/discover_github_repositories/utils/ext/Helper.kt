package com.anupdey.app.discover_github_repositories.utils.ext

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