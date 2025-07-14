package org.scnsoft.fidekmp.domain.usecase

import org.scnsoft.fidekmp.domain.repository.UntrackedWineRepository

class GetUntrackedWineByIdUseCase(
    private val repository: UntrackedWineRepository
) {
    suspend operator fun invoke(id: Int) = repository.getUntrackedWineById(id)
}