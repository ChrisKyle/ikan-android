package me.chriskyle.ikan.presentation.exception

import android.content.Context
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.exception.BalanceNotEnoughException
import me.chriskyle.ikan.data.exception.BuyFailException
import me.chriskyle.ikan.data.exception.UnKnownException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/20.
 */
object ErrorMessageFactory {

    fun create(context: Context, exception: Exception): String {
        var message = context.getString(R.string.unknown_exception)

        if (exception is UnknownHostException || exception is ConnectException) {
            message = context.getString(R.string.exception_msg_network)
        } else if (exception is SocketTimeoutException) {
            message = context.getString(R.string.net_socket_timeout_exception)
        } else if (exception is UnKnownException) {
            message = context.getString(R.string.unknown_exception)
        } else if (exception is BalanceNotEnoughException) {
            message = context.getString(R.string.balance_is_not_enough)
        } else if (exception is BuyFailException) {
            message = context.getString(R.string.buy_feed_fail)
        }

        return message
    }
}
