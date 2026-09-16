package com.dwngkhoi.iohksu.domain.usecase

import com.dwngkhoi.iohksu.data.logging.BugreportRepository
import java.io.File

class GenerateBugreportUseCase(
    private val repository: BugreportRepository,
) {
    operator fun invoke(): File = repository.create()
}
