package com.rahul.diagnalapp.ui

import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rahul.diagnalapp.R
import com.rahul.diagnalapp.data.Utils.DataChangeListener
import com.rahul.diagnalapp.data.Utils.ItemOffsetDecoration
import com.rahul.diagnalapp.data.models.local.Content
import com.rahul.diagnalapp.data.models.local.LocalResponse
import com.rahul.diagnalapp.databinding.ActivityLocalBinding
import com.rahul.diagnalapp.ui.adapter.MovieAdapterLocal
import java.io.IOException

class LocalActivity : AppCompatActivity(), DataChangeListener {
    lateinit var binding: ActivityLocalBinding
    private var movieList = arrayListOf<Content>()
    lateinit var adapter: MovieAdapterLocal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val localResponse = readJsonFile(pageNumber)
        movieList.addAll(localResponse.page.contentItems.content)

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

        adapter = MovieAdapterLocal(this)
        adapter.setList(movieList)
        binding.recyclerViewMovie.adapter = adapter
        binding.progressDialog.visibility = View.GONE
    }

    private fun readJsonFile(pageNumber: Int): LocalResponse {
        var jsonString: String = ""
        try {
            jsonString = assets.open(getFileName(pageNumber))
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.d("Exception ", "" + ioException)
        }

        val movieListResponse = object : TypeToken<LocalResponse>() {}.type
        return Gson().fromJson(jsonString, movieListResponse)
    }

    private fun getFileName(pageNumber: Int): String {
        var fileName: String = ""
        if (pageNumber == 1) {
            fileName = "CONTENTLISTINGPAGE-PAGE1.json"
        } else if (pageNumber == 2) {
            fileName = "CONTENTLISTINGPAGE-PAGE2.json"
        } else {
            fileName = "CONTENTLISTINGPAGE-PAGE3.json"
        }
        return fileName
    }

    var pageNumber = 1
    override fun loadMore() {
        pageNumber++
        if (pageNumber > 3)
            return

        val localResponse = readJsonFile(pageNumber)
        movieList.addAll(localResponse.page.contentItems.content)
        adapter.setList(movieList)
        // adapter.notifyDataSetChanged()
        binding.recyclerViewMovie.post(Runnable { adapter.notifyDataSetChanged() })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)
        val searchView: SearchView = searchItem.getActionView() as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    if (p0.length > 2)
                        search(p0)
                    else
                        setDefault()
                }
                return true
            }

            override fun onQueryTextChange(msg: String): Boolean {
                if (msg.length > 2)
                    search(msg)
                else
                    setDefault()
                return true
            }
        })
        return true
    }

    private fun setDefault() {
        binding.textViewNoMatch.visibility = View.GONE
        binding.recyclerViewMovie.apply {
            (adapter as MovieAdapterLocal).setList(movieList)
            adapter?.notifyDataSetChanged()
        }
    }

    private var matchedMovie: ArrayList<Content> = arrayListOf()
    private fun search(text: String?) {

        binding.textViewNoMatch.visibility = View.GONE
        if (TextUtils.isEmpty(text)) {
            setDefault()
            return
        }
        matchedMovie = arrayListOf()

        text?.let {
            movieList.forEach { movie ->
                if (movie.name.contains(text, true)) {
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
            (adapter as MovieAdapterLocal).setList(matchedMovie)
            adapter?.notifyDataSetChanged()
        }
    }
}