package org.scnsoft.fidekmp.domain.usecase

import org.scnsoft.fidekmp.domain.repository.ProfileRepository

class GetCurrentProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke() = repository.getCurrentProfile()
}