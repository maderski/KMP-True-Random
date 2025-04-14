package com.maderskitech.kmptruerandomsdk

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform