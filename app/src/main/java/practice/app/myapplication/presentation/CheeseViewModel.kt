package practice.app.myapplication.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import practice.app.myapplication.data.model.Cheese
import practice.app.myapplication.domain.usecases.GetAllCheeseUseCase
import practice.app.myapplication.domain.usecases.InsertCheeseUseCase
import practice.app.myapplication.domain.usecases.DeleteCheeseUseCase

@ExperimentalCoroutinesApi
class CheeseViewModel(
    private val insertCheeseUseCase: InsertCheeseUseCase,
    private val deleteCheeseUseCase: DeleteCheeseUseCase,
    private val getAllCheeseUseCase: GetAllCheeseUseCase
) : ViewModel() {

    private val TAG by lazy {
        javaClass.simpleName
    }

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception L ${throwable.localizedMessage}")
    }
    private val allCheeses = MutableLiveData<PagingData<Cheese>>()

    fun onAllCheeses(): LiveData<PagingData<Cheese>> = allCheeses

    init {
        fetchAllCheeses()
    }

    fun fetchAllCheeses() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            getAllCheeseUseCase.execute()
                .collectLatest {
                    withContext(Dispatchers.Main) {
                        allCheeses.value = it
                    }
                }
        }
    }

    fun insertCheese(text: CharSequence) {
        if (text.isEmpty())
            return

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            insertCheeseUseCase.execute(text)
        }
    }

    fun deleteCheese(cheese: Cheese) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            deleteCheeseUseCase.execute(cheese)
        }
    }

    private fun onError(message: String) {
        Log.e(TAG, "error : $message")
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    class Factory(
        private val insertCheeseUseCase: InsertCheeseUseCase,
        private val deleteCheeseUseCase: DeleteCheeseUseCase,
        private val getAllCheeseUseCase: GetAllCheeseUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CheeseViewModel(
                insertCheeseUseCase, deleteCheeseUseCase, getAllCheeseUseCase
            ) as T
        }

    }

}