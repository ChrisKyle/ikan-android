package me.chriskyle.ikan.data.repository.datastore.params.provider

import me.chriskyle.ikan.data.repository.datastore.params.Constants
import me.chriskyle.library.net.request.param.SimpleParamProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/15.
 */
class AccountParamProvider : SimpleParamProvider() {

    fun accountId(accountId: Long): AccountParamProvider {
        append(ACCOUNT_ID, accountId)
        return this
    }

    fun openId(openId: String?): AccountParamProvider {
        append(OPEN_ID, openId)
        return this
    }

    fun nickname(nickname: String?): AccountParamProvider {
        append(NICKNAME, nickname)
        return this
    }

    fun avatar(avatarUrl: String?): AccountParamProvider {
        append(AVATAR, avatarUrl)
        return this
    }

    fun gender(sex: String?): AccountParamProvider {
        append(GENDER, sex)
        return this
    }

    fun bindType(bindType: Int?): AccountParamProvider {
        append(BIND_TYPE, bindType)
        return this
    }

    fun getAccountId() = optionalParam[ACCOUNT_ID] as String

    companion object {

        const val ACCOUNT_ID = Constants.API.ACCOUNT_ID
        const val OPEN_ID = Constants.API.OPEN_ID
        const val NICKNAME = Constants.API.NICKNAME
        const val AVATAR = Constants.API.AVATAR
        const val GENDER = Constants.API.GENDER
        const val BIND_TYPE = Constants.API.BIND_TYPE
    }
}