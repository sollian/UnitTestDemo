package com.example.unittest.kotlin

/**
 * @author shouxianli on 2021/5/11.
 */
class Kid(private val mother: Mother) {
    var money = 0
        private set

    fun wantMoney() {
        mother.inform(money)
        money += mother.giveMoney()
    }
}