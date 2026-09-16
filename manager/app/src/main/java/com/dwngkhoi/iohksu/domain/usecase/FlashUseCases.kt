package com.dwngkhoi.iohksu.domain.usecase

import com.dwngkhoi.iohksu.data.flash.FlashRepository

class ObserveKernelFlashUseCase(private val repository: FlashRepository) {
    operator fun invoke() = repository.kernelFlashSession
}

class StartKernelFlashUseCase(private val repository: FlashRepository) {
    operator fun invoke(uri: String, selectedSlot: String?) =
        repository.startKernelFlash(uri, selectedSlot)
}
