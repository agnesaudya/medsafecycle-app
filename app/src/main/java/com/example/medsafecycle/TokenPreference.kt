package com.example.medsafecycle

import android.content.Context

internal class TokenPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val TOKEN = "token"


    }
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun setToken(token: String) {
        val editor = preferences.edit()
        editor.putString(TOKEN,token)

        editor.apply()
    }

    fun removeToken(){
        val editor = preferences.edit()
        editor.clear()

        editor.apply()
    }
    fun getToken(): String? {

        return preferences.getString(TOKEN, "")


    }
}