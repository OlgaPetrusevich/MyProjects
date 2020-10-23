package com.gmail.petrusevich.volha.fitnessapp.presentation

import android.webkit.JavascriptInterface

class WebAppImpl(
    private val num01: Int,
    private val num02: Int,
    private val num03: Int,
    private val num04: Int,
    private val num05: Int

) {

    @JavascriptInterface
    fun getNum1(): Int = num01

    @JavascriptInterface
    fun getNum2(): Int = num02

    @JavascriptInterface
    fun getNum3(): Int = num03

    @JavascriptInterface
    fun getNum4(): Int = num04

    @JavascriptInterface
    fun getNum5(): Int = num05

}