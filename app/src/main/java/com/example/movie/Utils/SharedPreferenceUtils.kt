package com.example.movie.Utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object SharedPreferenceUtils {

    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    private const val myPrefsName = "MyPrefsFile"
    private const val tokenKey = "token_key"

    fun setToken(token: String, context: Context) {
        val sharedPreferences = EncryptedSharedPreferences
            .create(myPrefsName,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        val sharedPrefsEditor = sharedPreferences.edit()
        sharedPrefsEditor.putString(tokenKey, token)
        sharedPrefsEditor.apply()
    }

    fun getToken(context: Context): String {
        val sharedPreferences = EncryptedSharedPreferences
            .create(myPrefsName,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        var token = sharedPreferences.getString(tokenKey, "Not Available")
        if(token==null){
            return ""
        }else{
            return token
        }
    }

}