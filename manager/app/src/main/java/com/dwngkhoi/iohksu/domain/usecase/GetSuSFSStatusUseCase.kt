package com.dwngkhoi.iohksu.domain.usecase

import com.dwngkhoi.iohksu.data.susfs.SuSFSRepository

class GetSuSFSStatusUseCase(private val repository: SuSFSRepository) {
    suspend operator fun invoke() = repository.getStatus()
}

