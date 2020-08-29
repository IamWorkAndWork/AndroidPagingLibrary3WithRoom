package practice.app.myapplication

import android.content.Context
import kotlinx.coroutines.ExperimentalCoroutinesApi
import practice.app.myapplication.data.db.CheeseDb
import practice.app.myapplication.domain.usecases.*
import practice.app.myapplication.presentation.CheeseViewModel

@ExperimentalCoroutinesApi
object InjectorUtils {

    fun provideCheeseDb(context: Context): CheeseDb {
        return CheeseDb.get(context)
    }

    fun provideInsertUseCase(cheeseDb: CheeseDb): InsertCheeseUseCaseImpl {
        return InsertCheeseUseCaseImpl(cheeseDb)
    }

    fun provideDeleteUseCase(cheeseDb: CheeseDb): DeleteCheeseUseCaseImpl {
        return DeleteCheeseUseCaseImpl(cheeseDb)
    }

    fun provideGetAllCheeseUseCase(cheeseDb: CheeseDb): GetAllCheeseUseCaseImpl {
        return GetAllCheeseUseCaseImpl(cheeseDb)
    }

//    fun provideCheeseViewModelFactory(cheeseDb: CheeseDb): CheeseViewModel.Factory {
//        return CheeseViewModel.Factory(
//            provideInsertUseCase(cheeseDb),
//            provideDeleteUseCase(cheeseDb),
//            provideGetAllCheeseUseCase(cheeseDb)
//        )
//    }

    fun provideCheeseViewModelFactory(context: Context): CheeseViewModel.Factory {
        val cheeseDb = provideCheeseDb(context)
        return CheeseViewModel.Factory(
            provideInsertUseCase(cheeseDb),
            provideDeleteUseCase(cheeseDb),
            provideGetAllCheeseUseCase(cheeseDb)
        )
    }

//    fun provideCheeseViewModelFactory(
//        insertCheeseUseCase: InsertCheeseUseCase,
//        deleteCheeseUseCase: DeleteCheeseUseCase,
//        getAllCheeseUseCase: GetAllCheeseUseCase
//    ): CheeseViewModel.Factory {
//        return CheeseViewModel.Factory(
//            insertCheeseUseCase,
//            deleteCheeseUseCase,
//            getAllCheeseUseCase
//        )
//    }

}