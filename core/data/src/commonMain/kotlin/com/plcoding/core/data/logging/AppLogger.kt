package com.plcoding.core.data.logging

import co.touchlab.kermit.Logger
import com.plcoding.core.domain.logging.CustomLogger

object AppLogger : CustomLogger {
    override fun debugLog(message: String) {
        Logger.d(message)
    }

    override fun infoLog(message: String) {
        Logger.i(message)
    }

    override fun warnLog(message: String) {
        Logger.w(message)
    }

    override fun errorLog(message: String, throwable: Throwable?) {
        Logger.e(message, throwable)
    }
}