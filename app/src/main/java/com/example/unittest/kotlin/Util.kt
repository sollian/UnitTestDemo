package com.example.unittest.kotlin

/**
 * @author shouxianli on 2021/5/11.
 */
class Util {
    fun ok() {
        UtilJava.ok()
        UtilKotlin.ok()
        UtilKotlinSingleton.ok()
        UtilCompanion.ok()
    }
}