package me.chriskyle.ikan.data.repository.datastore.local.cache

import me.chriskyle.ikan.data.entity.TokenEntity

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
interface TokenCache {

    fun get(): TokenEntity?

    fun put(tokenEntity: TokenEntity)

    fun evict()
}
