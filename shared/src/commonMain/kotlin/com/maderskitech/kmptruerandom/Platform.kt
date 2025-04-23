package com.maderskitech.kmptruerandom

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform