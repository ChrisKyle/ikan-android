package me.chriskyle.ikan.domain.exception

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/18.
 */
interface ErrorBundle {

    val exception: Exception?

    val errorMessage: String?
}
