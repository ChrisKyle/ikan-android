package me.chriskyle.library.net.request.param

import java.io.Serializable
import java.util.*

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
class OptionalParam : Serializable {

    private val params = HashMap<String, String>()

    constructor()

    constructor(inKey: String, inValue: String) {
        params.put(inKey, inValue)
    }

    constructor(input: Map<String, String>) {
        copyFrom(input)
    }

    fun put(inKey: String, inValue: Any): OptionalParam {
        params.put(inKey, inValue.toString())
        return this
    }

    operator fun get(inKey: String): Any {
        return params[inKey] as String
    }

    fun copyTo(output: MutableMap<String, String>?): OptionalParam {
        if (output == null) {
            return this
        }
        output.putAll(params)
        return this
    }

    fun copyFrom(input: Map<String, String>?): OptionalParam {
        if (input == null) {
            return this
        }
        params.putAll(input)
        return this
    }

    fun copyFrom(optionalParam: OptionalParam?): OptionalParam {
        return if (optionalParam == null) {
            this
        } else copyFrom(optionalParam.map)
    }

    val map: Map<String, String>
        get() {
            val copy = HashMap<String, String>()
            copy.putAll(params)
            return copy
        }

    companion object {

        private const val serialVersionUID = -1709890337329159315L
    }
}
