package org.scnsoft.fidekmp.domain.usecase

import org.scnsoft.fidekmp.domain.repository.UntrackedWineRepository

class OnUntrackedMainWineSearchUseCase(
        private val repository: UntrackedWineRepository
    ) {
        suspend operator fun invoke(search: String) = repository.getUntrackedWines(name = search)
    }
