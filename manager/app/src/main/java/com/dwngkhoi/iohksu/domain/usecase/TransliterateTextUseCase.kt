package com.dwngkhoi.iohksu.domain.usecase

import com.dwngkhoi.iohksu.domain.text.TextTransliterator

class TransliterateTextUseCase(private val transliterator: TextTransliterator) {
    operator fun invoke(value: String): String = transliterator.transliterate(value)
}
