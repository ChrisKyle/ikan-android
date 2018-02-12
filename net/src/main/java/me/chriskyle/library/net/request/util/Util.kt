package me.chriskyle.library.net.request.util

import java.io.UnsupportedEncodingException
import java.net.URLDecoder

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/7/21.
 */
object Util {

    fun urlDecode(url: String): String {
        return try {
            var prevURL = ""
            var decodeURL = url
            while (prevURL != decodeURL) {
                prevURL = decodeURL
                decodeURL = URLDecoder.decode(decodeURL, "UTF-8")
            }
            decodeURL
        } catch (e: UnsupportedEncodingException) {
            "Issue while decoding" + e.message
        }

    }
}
