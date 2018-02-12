package me.chriskyle.ikan.presentation.event

import me.chriskyle.ikan.data.entity.AccountEntity

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/29.
 */
class LoginEvent(val accountEntity: AccountEntity) : Event