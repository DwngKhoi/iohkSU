package com.dwngkhoi.iohksu.domain.usecase

import com.dwngkhoi.iohksu.data.application.ApplicationControlRepository

class RebootUseCase(
    private val repository: ApplicationControlRepository,
) {
    suspend operator fun invoke(reason: String = "") = repository.reboot(reason)
}
