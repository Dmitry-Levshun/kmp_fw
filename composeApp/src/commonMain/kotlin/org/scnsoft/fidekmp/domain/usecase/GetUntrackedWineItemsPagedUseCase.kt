package org.scnsoft.fidekmp.domain.usecase

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedWineItem
import org.scnsoft.fidekmp.domain.repository.UntrackedWineRepository

class GetUntrackedWineItemsPagedUseCase(
    private val repository: UntrackedWineRepository
) {
     suspend operator fun invoke(query: String): Flow<PagingData<UntrackedWineItem>> = repository.getWineItemsPaged(query)
}