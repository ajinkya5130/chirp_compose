package com.plcoding.core.domain.logging

interface CustomLogger {
    fun debugLog(message: String)
    fun infoLog(message: String)
    fun warnLog(message: String)
    fun errorLog(message: String, throwable: Throwable? = null)
}