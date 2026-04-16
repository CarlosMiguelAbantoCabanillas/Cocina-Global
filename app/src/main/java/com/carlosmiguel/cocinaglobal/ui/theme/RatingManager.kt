
package com.carlosmiguel.cocinaglobal.ui.theme

import android.content.Context
import androidx.core.content.edit

object RatingManager {
    private const val PREFS_NAME = "rating_prefs"

    fun getRating(context: Context, recipeName: String): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(recipeName, 0)
    }

    fun setRating(context: Context, recipeName: String, rating: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit {
            putInt(recipeName, rating)
        }
    }

    fun getAllRatings(context: Context): Map<String, Int> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.all.mapNotNull { entry ->
            val rating = entry.value as? Int
            if (rating != null && rating > 0) {
                entry.key to rating
            } else {
                null
            }
        }.toMap()
    }
}