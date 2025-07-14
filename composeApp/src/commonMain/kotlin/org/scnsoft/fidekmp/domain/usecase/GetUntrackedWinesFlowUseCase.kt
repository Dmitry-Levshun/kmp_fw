package org.scnsoft.fidekmp.domain.usecase

import org.scnsoft.fidekmp.domain.repository.UntrackedWineRepository

class GetUntrackedWinesFlowUseCase(
    private val repository: UntrackedWineRepository
) {
    suspend operator fun invoke(query: String) = repository.getUntrackedWinesFlow(1, 30, query)
}