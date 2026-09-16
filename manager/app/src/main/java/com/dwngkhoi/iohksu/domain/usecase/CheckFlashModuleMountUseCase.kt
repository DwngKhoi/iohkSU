package com.dwngkhoi.iohksu.domain.usecase

import com.dwngkhoi.iohksu.data.flash.FlashRepository

class CheckFlashModuleMountUseCase(private val repository: FlashRepository) {
    suspend operator fun invoke(uri: String) = repository.moduleNeedsMount(uri)
}
