package org.scnsoft.fidekmp.domain.usecase

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.scnsoft.fidekmp.data.api.untracked.dto.UntrackedUserWineItem
import org.scnsoft.fidekmp.domain.repository.UntrackedWineRepository

class GetUntrackedUserWineItemsPagedUseCase(
    private val repository: UntrackedWineRepository
) {
    operator fun invoke(): Flow<PagingData<UntrackedUserWineItem>> = repository.getUserWineItemsPaged("")
}