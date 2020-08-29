package practice.app.myapplication.domain.usecases

import practice.app.myapplication.data.db.CheeseDb
import practice.app.myapplication.data.model.Cheese

interface DeleteCheeseUseCase {
    suspend fun execute(cheese: Cheese)
}

class DeleteCheeseUseCaseImpl(private val cheeseDb: CheeseDb) : DeleteCheeseUseCase {

    override suspend fun execute(cheese: Cheese) {
        cheeseDb.cheeseDao().delete(cheese)
    }

}