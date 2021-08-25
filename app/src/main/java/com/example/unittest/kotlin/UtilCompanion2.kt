package com.example.unittest.kotlin

/**
 * @author shouxianli on 2021/5/11.
 */
class UtilCompanion2 {
    companion object {
        @JvmStatic
        fun ok(): String {
            print("UtilCompanion2#ok")
            return "UtilCompanion.ok()"
        }
    }
}