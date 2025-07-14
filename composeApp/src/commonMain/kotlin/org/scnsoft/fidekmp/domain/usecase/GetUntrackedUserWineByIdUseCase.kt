package org.scnsoft.fidekmp.domain.usecase

import org.scnsoft.fidekmp.domain.repository.UntrackedWineRepository

class GetUntrackedUserWineByIdUseCase(
    private val repository: UntrackedWineRepository
) {
    suspend operator fun invoke(id: Int) = repository.getUntrackedUserWineById(id)
}