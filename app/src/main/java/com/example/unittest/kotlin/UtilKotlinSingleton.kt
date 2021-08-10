package com.example.unittest.kotlin

/**
 * @author shouxianli on 2021/5/11.
 */
object UtilKotlinSingleton {

//    val utilJava = UtilJava.create()

    init {
        println("UtilKotlinSingleton#初始化块")
    }

    fun ok(): String {
        println("UtilKotlinSingleton.ok")
        return "UtilKotlinSingleton.ok()"
    }

    fun call1() {
        println("call1")
        call2("")
    }

    private fun call2(str: String) {
        println("call2")
    }
}