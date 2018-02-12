package me.chriskyle.ikan.data.repository.datastore.local.cache.impl

import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import me.chriskyle.ikan.data.entity.TokenEntity
import me.chriskyle.ikan.data.repository.datastore.local.cache.TokenCache
import me.chriskyle.library.toolkit.data.PreferenceHelper

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
class TokenCacheImpl(context: Context) : TokenCache {

    private var ph: PreferenceHelper = PreferenceHelper.getInstance(context)

    override fun get(): TokenEntity? {
        val tokenEntityString = ph.getString(PH_KEY_TOKEN)

        return if (!TextUtils.isEmpty(tokenEntityString)) {
            Gson().fromJson<TokenEntity>(tokenEntityString, TokenEntity::class.java)
        } else {
            null
        }
    }

    override fun put(tokenEntity: TokenEntity) {
        val jsonString = Gson().toJson(tokenEntity)
        ph.put(PH_KEY_TOKEN, jsonString)?.commit()
    }

    override fun evict() {
        ph.put(PH_KEY_TOKEN, "")?.commit()
    }

    companion object {

        const val PH_KEY_TOKEN = "ph_key_token"

        @Volatile
        private var INSTANCE: TokenCacheImpl? = null

        fun getInstance(context: Context): TokenCacheImpl = INSTANCE ?: synchronized(this) {
            INSTANCE ?: TokenCacheImpl(context).also { INSTANCE = it }
        }
    }
}
