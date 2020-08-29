package practice.app.myapplication.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.cheese_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import practice.app.myapplication.InjectorUtils
import practice.app.myapplication.R
import practice.app.myapplication.data.db.CheeseDb
import practice.app.myapplication.data.model.Cheese
import practice.app.myapplication.domain.usecases.DeleteCheeseUseCaseImpl
import practice.app.myapplication.domain.usecases.GetAllCheeseUseCaseImpl
import practice.app.myapplication.domain.usecases.InsertCheeseUseCaseImpl

@ExperimentalCoroutinesApi
class CheeseActivity : AppCompatActivity() {

    private val adapter: CheeseAdapter by lazy {
        CheeseAdapter()
    }

    private val cheeseViewModel: CheeseViewModel by viewModels() {
        InjectorUtils.provideCheeseViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cheese_main)

        initWidget()
        initViewModel()
        initListener()
    }

    private fun initListener() {
        cheeseOkButton.setOnClickListener {
            cheeseViewModel.insertCheese(cheeseEditText.text.toString())
            cheeseEditText.setText("")
        }

        adapter.onClickItem = { cheese ->
            dialogDelete(cheese)
        }
    }

    private fun dialogDelete(cheese: Cheese) {
        val title = String.format(getString(R.string.you_want_to_delete), cheese.name)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            cheeseViewModel.deleteCheese(cheese)
        }
        builder.setNegativeButton(android.R.string.no, null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun initViewModel() {
        cheeseViewModel.onAllCheeses().observe(this, Observer {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        })
    }

    private fun initWidget() {
        cheeseRecyclerView.apply {
            adapter = this@CheeseActivity.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}