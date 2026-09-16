package com.dwngkhoi.iohksu.domain.text

fun interface TextTransliterator {
    fun transliterate(value: String): String
}
