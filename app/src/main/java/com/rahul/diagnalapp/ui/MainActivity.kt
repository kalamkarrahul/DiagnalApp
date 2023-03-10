package com.rahul.diagnalapp.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.rahul.diagnalapp.MovieApplication
import com.rahul.diagnalapp.R
import com.rahul.diagnalapp.data.Utils.DataChangeListener
import com.rahul.diagnalapp.data.Utils.ItemOffsetDecoration
import com.rahul.diagnalapp.data.Utils.LiveDataInternetConnections
import com.rahul.diagnalapp.data.models.Movie
import com.rahul.diagnalapp.databinding.ActivityMainBinding
import com.rahul.diagnalapp.ui.adapter.MovieRecyclerAdapter
import com.rahul.diagnalapp.ui.viewModel.MainViewModel
import com.rahul.diagnalapp.ui.viewModel.MainViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity(), DataChangeListener {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var adapter: MovieRecyclerAdapter
    private var movieList = arrayListOf<Movie>()
    var pageNumber = 1

    private lateinit var cld: LiveDataInternetConnections

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as MovieApplication).applicationComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

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

        adapter = MovieRecyclerAdapter(this)

        binding.recyclerViewMovie.adapter = adapter

        fetchData()

        viewModel.movies.observe(this@MainActivity) {
            binding.progressDialog.visibility = View.GONE
            movieList.addAll(it.results)
            adapter.setList(movieList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun fetchData() {
        cld = LiveDataInternetConnections(application)
        cld.observe(this) { isConnected ->
            if (isConnected) {
                viewModel.getMovieList(pageNumber)
            } else {
                binding.textViewNoMatch.text = "No internet connection!"
            }
        }
    }

    override fun loadMore() {
        if (isDueToSearch)
            return
        pageNumber++
        fetchData()
    }

    var isDueToSearch = false

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)
        val searchView: SearchView = searchItem.getActionView() as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    isDueToSearch = true
                    if (p0.length > 2)
                        search(p0)
                    else {
                        isDueToSearch = false
                        setDefault()
                    }
                }
                return true
            }

            override fun onQueryTextChange(msg: String): Boolean {
                isDueToSearch = true
                if (msg.length > 2)
                    search(msg)
                else {
                    isDueToSearch = false
                    setDefault()
                }
                return true
            }
        })
        return true
    }

    private fun setDefault() {
        binding.textViewNoMatch.visibility = View.GONE
        binding.recyclerViewMovie.apply {
            (adapter as MovieRecyclerAdapter).setList(movieList)
            adapter?.notifyDataSetChanged()
        }
    }

    private var matchedMovie: ArrayList<Movie> = arrayListOf()
    private fun search(text: String?) {

        binding.textViewNoMatch.visibility = View.GONE
        if (TextUtils.isEmpty(text)) {
            setDefault()
            return
        }
        matchedMovie = arrayListOf()

        text?.let {
            movieList.forEach { movie ->
                if (movie.title.contains(text, true)) {
                    matchedMovie.add(movie)
                }
            }
            updateRecyclerView()
            if (matchedMovie.isEmpty()) {
                binding.textViewNoMatch.visibility = View.VISIBLE
            } else {
                binding.textViewNoMatch.visibility = View.GONE
            }
            updateRecyclerView()
        }
    }

    private fun updateRecyclerView() {
        binding.recyclerViewMovie.apply {
            (adapter as MovieRecyclerAdapter).setList(matchedMovie)
            adapter?.notifyDataSetChanged()
        }
    }
}