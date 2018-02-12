package me.chriskyle.library.net.request.param

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
object ParamProviderUtil {

    private val HTTP_ENCODE = "UTF-8"

    fun inflateQueries(params: Map<String, String>): String {
        if (params.size == 0) {
            return ""
        }

        val it = params.entries.iterator()
        val result = StringBuilder()
        var entry = it.next()
        try {
            result.append(entry.key)
                    .append("=")
                    .append(URLEncoder.encode(entry.value, HTTP_ENCODE))
            while (it.hasNext()) {
                entry = it.next()
                result.append("&")
                        .append(entry.key)
                        .append("=")
                        .append(URLEncoder.encode(entry.value, HTTP_ENCODE))

            }
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return result.toString()
    }
}