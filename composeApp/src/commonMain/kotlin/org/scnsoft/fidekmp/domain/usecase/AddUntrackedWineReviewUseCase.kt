package org.scnsoft.fidekmp.domain.usecase

import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineReviewRequest
import org.scnsoft.fidekmp.domain.repository.UntrackedWineRepository

class AddUntrackedWineReviewUseCase (
    private val repository: UntrackedWineRepository
) {
    suspend operator fun invoke(request: UntrackedWineReviewRequest) = repository.addUntrackedWineReview(request)
}