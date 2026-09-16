package com.dwngkhoi.iohksu.domain.usecase

import com.dwngkhoi.iohksu.data.flash.FlashRepository
import com.dwngkhoi.iohksu.domain.model.FlashOperation

class ExecuteFlashOperationUseCase(private val repository: FlashRepository) {
    operator fun invoke(operation: FlashOperation) = repository.execute(operation)
}
