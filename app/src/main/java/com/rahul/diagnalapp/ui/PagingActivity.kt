package com.rahul.diagnalapp.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.rahul.diagnalapp.R
import com.rahul.diagnalapp.data.Utils.ItemOffsetDecoration
import com.rahul.diagnalapp.data.repository.PagingRepository
import com.rahul.diagnalapp.data.retrofit.RetrofitService
import com.rahul.diagnalapp.databinding.ActivityPagingBinding
import com.rahul.diagnalapp.ui.adapter.MoviePagerAdapter
import com.rahul.diagnalapp.ui.viewModel.PagingViewModel
import com.rahul.dignaltask.data.network.repository.MyViewModelFactory
import kotlinx.coroutines.launch

class PagingActivity : AppCompatActivity() {
    lateinit var binding: ActivityPagingBinding
    lateinit var viewModel: PagingViewModel
    private val adapter = MoviePagerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = PagingRepository(retrofitService)

        //Handling orientation change for view
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerViewMovie.layoutManager = GridLayoutManager(applicationContext, 7)
        } else {
            binding.recyclerViewMovie.layoutManager = GridLayoutManager(applicationContext, 3)
        }
        //Equal space in each item of list
        val itemDecoration =
            ItemOffsetDecoration(
                applicationContext, R.dimen.item_space
            )
        binding.recyclerViewMovie.addItemDecoration(itemDecoration)
        binding.recyclerViewMovie.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        ).get(PagingViewModel::class.java)

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        adapter.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            )
                binding.progressDialog.visibility = View.VISIBLE
            else {
                binding.progressDialog.visibility = View.GONE
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                }

            }
        }


        lifecycleScope.launch {
            viewModel.getMovieList().observe(this@PagingActivity) {
                it?.let {
                    adapter.submitData(lifecycle, it)
                }
            }
        }
    }
}