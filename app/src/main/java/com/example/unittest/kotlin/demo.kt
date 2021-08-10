//@file:JvmName("DemoGKt") //修改生成的java代码的类名

package com.example.unittest.kotlin

/**
 * @author shouxianli on 2021/5/12.
 */

data class Obj(val value: Int)

class Ext {
    fun Obj.extensionFunc() = value + 5
}

fun Obj.extensionFunc2() = value + 5

@JvmOverloads
fun topAdd(num1: String, num2: Int = 45) {
}

@JvmOverloads
fun topAdd(num1: Long, num2: Int = 45) {
}