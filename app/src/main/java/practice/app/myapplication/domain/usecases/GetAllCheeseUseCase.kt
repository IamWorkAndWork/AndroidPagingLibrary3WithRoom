package practice.app.myapplication.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import practice.app.myapplication.data.db.CheeseDb
import practice.app.myapplication.data.model.Cheese

interface GetAllCheeseUseCase {
    suspend fun execute(): Flow<PagingData<Cheese>>
}

class GetAllCheeseUseCaseImpl(private val cheeseDb: CheeseDb) : GetAllCheeseUseCase {

    override suspend fun execute(): Flow<PagingData<Cheese>> {

        val pagingConfig = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 200
        )

        val allCheeses = Pager(pagingConfig) {
            cheeseDb.cheeseDao().allCheesesByName()
        }.flow

        return allCheeses
    }

}