package me.chriskyle.ikan.data.repository.datastore.local.cache.impl

import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import me.chriskyle.ikan.data.entity.AccountEntity
import me.chriskyle.ikan.data.repository.datastore.local.cache.AccountCache
import me.chriskyle.library.toolkit.data.PreferenceHelper

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/29.
 */
class AccountCacheImpl(context: Context) : AccountCache {

    private var ph: PreferenceHelper = PreferenceHelper.getInstance(context)

    override fun get(): AccountEntity? {
        val entityString = ph.getString(PH_KEY_ACCOUNT)

        return if (!TextUtils.isEmpty(entityString)) {
            Gson().fromJson<AccountEntity>(entityString, AccountEntity::class.java)
        } else {
            null
        }
    }

    override fun put(accountEntity: AccountEntity) {
        val jsonString = Gson().toJson(accountEntity)
        ph.put(PH_KEY_ACCOUNT, jsonString)?.commit()
    }

    override fun evict() {
        ph.put(PH_KEY_ACCOUNT, "")?.commit()
    }

    companion object {

        const val PH_KEY_ACCOUNT = "account"

        private var INSTANCE: AccountCacheImpl? = null

        fun getInstance(context: Context): AccountCacheImpl = INSTANCE
                ?: AccountCacheImpl(context).also { INSTANCE = it }
    }
}