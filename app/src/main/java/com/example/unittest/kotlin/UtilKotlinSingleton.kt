package com.example.unittest.kotlin

/**
 * @author shouxianli on 2021/5/11.
 */
object UtilKotlinSingleton {
    fun ok(): String {
        return "UtilKotlinSingleton.ok()"
    }

    fun call1() {
        println("call1")
        call2("")
    }

    private fun call2(str:String) {
        println("call2")
    }
}