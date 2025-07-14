package org.scnsoft.fidekmp.domain.usecase

import org.scnsoft.fidekmp.domain.repository.UntrackedWineRepository

class GetUntrackedWineProducersUseCase(
    private val repository: UntrackedWineRepository
) {
    suspend operator fun invoke(query: String) = repository.getUntrackedWineProducers(1, 30, query)
}