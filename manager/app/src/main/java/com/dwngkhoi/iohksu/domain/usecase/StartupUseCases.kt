package com.dwngkhoi.iohksu.domain.usecase

import com.dwngkhoi.iohksu.data.startup.StartupRepository

class ObserveStartupStateUseCase(
    private val repository: StartupRepository,
) {
    operator fun invoke() = repository.state
}
