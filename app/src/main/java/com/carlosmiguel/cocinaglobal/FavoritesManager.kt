package com.carlosmiguel.cocinaglobal

import android.content.Context
import androidx.core.content.edit

object FavoritesManager {
    private const val PREFS_NAME = "favorites_prefs"
    private const val KEY_FAVORITES = "favorite_recipes"

    fun getFavorites(context: Context): Set<String> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
    }

    fun isFavorite(context: Context, recipeName: String): Boolean {
        return getFavorites(context).contains(recipeName)
    }

    fun addFavorite(context: Context, recipeName: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val current = getFavorites(context).toMutableSet()
        current.add(recipeName)
        prefs.edit { putStringSet(KEY_FAVORITES, current) }
    }

    fun removeFavorite(context: Context, recipeName: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val current = getFavorites(context).toMutableSet()
        current.remove(recipeName)
        prefs.edit { putStringSet(KEY_FAVORITES, current) }
    }

    fun toggleFavorite(context: Context, recipeName: String) {
        if (isFavorite(context, recipeName)) {
            removeFavorite(context, recipeName)
        } else {
            addFavorite(context, recipeName)
        }
    }
}