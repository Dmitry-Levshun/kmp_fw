package org.scnsoft.fidekmp.domain.usecase

import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedCustomWineRequest
import org.scnsoft.fidekmp.domain.repository.UntrackedWineRepository

class AddUntrackedCustomWineUseCase(
    private val repository: UntrackedWineRepository
) {
    suspend operator fun invoke(request: UntrackedCustomWineRequest) = repository.addUntrackedCustomWine(request)
}