package com.example.unittest.kotlin

import kotlin.system.exitProcess

/**
 * @author shouxianli on 2021/5/11.
 */
class Mother() {
    constructor(money: Int) : this()
    constructor(name: String) : this()

    private var age: Int = 2

    var name = "Tim"

    fun inform(money: Int) {
        println("妈妈我现在有 $money 元，我要跟你拿钱！")
    }

    fun giveMoney(): Int {
        println("giveMoney")
        return 100
    }

    fun shopping(year: Int, month: Int, day: Int): String {
        println("shopping")
        return feedGood()
    }

    private fun feedGood(): String {
        return "happy"
    }

    private fun buy(goods: String): Int {
        return 30
    }

    fun getAge() = age
    fun setAge(value: Int) {
        age = value
    }

    fun invokeBuy(value: String) = buy(value)


    fun quit(status: Int): Nothing {
        exitProcess(status)
    }

    fun add(num1: Int, num2: Int): Int {
        return num1 + num2
    }
}