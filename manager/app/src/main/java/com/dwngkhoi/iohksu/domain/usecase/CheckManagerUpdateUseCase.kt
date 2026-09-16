package com.dwngkhoi.iohksu.domain.usecase

import com.dwngkhoi.iohksu.data.update.ManagerUpdateRepository
import com.dwngkhoi.iohksu.domain.model.ManagerUpdateChannel
import com.dwngkhoi.iohksu.domain.model.ManagerUpdateInfo

class CheckManagerUpdateUseCase(
    private val repository: ManagerUpdateRepository,
) {
    suspend operator fun invoke(channel: ManagerUpdateChannel): ManagerUpdateInfo? =
        when (channel) {
            ManagerUpdateChannel.STABLE -> repository.checkStableUpdate()
            ManagerUpdateChannel.BETA -> repository.checkBetaUpdate()
        }
}
