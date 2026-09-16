package com.dwngkhoi.iohksu.domain.usecase

import com.dwngkhoi.iohksu.data.AppSettingsRepository
import com.dwngkhoi.iohksu.data.startup.ApplicationInitializationRepository
import com.dwngkhoi.iohksu.data.startup.StartupRepository

class InitializeApplicationUseCase(
    private val settingsRepository: AppSettingsRepository,
    private val startupRepository: StartupRepository,
    private val initializationRepository: ApplicationInitializationRepository,
) {
    suspend operator fun invoke() {
        runCatching {
            settingsRepository.preload()
            initializationRepository.initialize()
        }.onSuccess {
            startupRepository.markReady()
        }.onFailure { error ->
            startupRepository.markFailed(error)
        }
    }
}
