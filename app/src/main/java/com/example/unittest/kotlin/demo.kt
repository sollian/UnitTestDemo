package com.example.unittest.kotlin

import kotlin.system.exitProcess

/**
 * @author shouxianli on 2021/5/12.
 */

data class Obj(val value: Int)

class Ext {
    fun Obj.extensionFunc() = value + 5
}

fun Obj.extensionFunc2() = value + 5
