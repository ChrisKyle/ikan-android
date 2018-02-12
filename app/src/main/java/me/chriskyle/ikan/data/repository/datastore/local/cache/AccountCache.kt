package me.chriskyle.ikan.data.repository.datastore.local.cache

import me.chriskyle.ikan.data.entity.AccountEntity

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/29.
 */
interface AccountCache {

    fun get(): AccountEntity?

    fun put(accountEntity: AccountEntity)

    fun evict()
}
