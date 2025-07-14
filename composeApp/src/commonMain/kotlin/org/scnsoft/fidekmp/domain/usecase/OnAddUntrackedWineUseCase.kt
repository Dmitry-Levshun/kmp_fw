package org.scnsoft.fidekmp.domain.usecase

import org.scnsoft.fidekmp.data.api.untracked.dto.AddUntrackedWineRequest
import org.scnsoft.fidekmp.domain.repository.UntrackedWineRepository

class OnAddUntrackedWineUseCase(
    private val repository: UntrackedWineRepository
) {
    suspend operator fun invoke(request: AddUntrackedWineRequest) = repository.addUntrackedWine(request)
}