package me.chriskyle.ikan.presentation.event

import me.chriskyle.ikan.data.entity.AccountEntity

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/20.
 */
class AccountUpdateEvent(var account: AccountEntity?)  : Event