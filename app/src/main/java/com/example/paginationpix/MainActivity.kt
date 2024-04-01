package com.example.paginationpix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paginationpix.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var adapter = PixaAdapter(arrayListOf())
    var oldWord = ""
    var newWord = ""
    var page = 1

    var isLoading = false
    var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        setClickers()
        addScrollListener()
    }

    private fun setAdapter() {
        binding.rvPhoto.adapter = adapter
    }

    private fun addScrollListener() {
        binding.rvPhoto.addOnScrollListener(object :
                PaginationScrollListener(binding.rvPhoto.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                requestByImage(newWord)
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })
    }

    private fun setClickers() {
        binding.btRequest.setOnClickListener {
            newWord = binding.edtPhoto.text.toString()
            if (oldWord != newWord) {
                requestByImage(newWord)
            } else {
                ++page
                requestByImage(oldWord)
            }
        }
    }

    private fun requestByImage(searchWord: String) {
        isLoading = true
        RetrofitService.api.getImages(searchWord = searchWord, page = page)
            .enqueue(object : Callback<PixaModel> {
                override fun onResponse(p0: Call<PixaModel>, response: Response<PixaModel>) {
                    if (response.isSuccessful) {
                        response.body()?.let { model ->
                            if (model.hits.isNotEmpty()) {
                                if (oldWord != newWord) {
                                    adapter.list = model.hits
                                } else {
                                    adapter.addList(model.hits)
                                }
                                oldWord = binding.edtPhoto.text.toString()
                                adapter.notifyDataSetChanged()
                                page++
                            } else {
                                isLastPage = true
                            }
                        }
                    }
                    isLoading = false
                }

                override fun onFailure(p0: Call<PixaModel>, error: Throwable) {
                    Log.e("LogError", "onFailure: ${error.message}")
                    isLoading = false
                }

            })
    }
}