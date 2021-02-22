package com.toughproject.handycurrencyconverter

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.InputStream

fun readAsset(context: Context, fileName: String): String =
        context
                .assets
                .open(fileName)
                .bufferedReader()
                .use(BufferedReader::readText)
