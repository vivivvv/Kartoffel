package com.app.mybase

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.mybase.adapters.CatAdapter
import com.app.mybase.base.BaseActivity
import com.app.mybase.databinding.ActivityMainBinding
import com.app.mybase.helper.ApisResponse
import com.app.mybase.model.ApiResponseData
import com.app.mybase.model.CatDetails
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : BaseActivity() {

    val TAG = this::class.java.name
    lateinit var binding: ActivityMainBinding
    lateinit var viewmodel: MainViewModel
    private lateinit var catRecyclerView: RecyclerView
    private lateinit var catAdapter: CatAdapter
    var catDetailsMap = LinkedHashMap<String, CatDetails>()
    var rvList = ArrayList<LinkedHashMap<String, CatDetails>>()
    private val productList = ArrayList<String>()

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewmodel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        binding.mainViewModel = viewmodel
        binding.lifecycleOwner = this@MainActivity
        // Perform api call
        performApi("1")
        // Initialize recycler view
        initializeCatRV()

        // Add button click listener
        binding.addBtn.setOnClickListener {
            rvList.add(catDetailsMap)
            catAdapter.addItem(productList, rvList, rvList.size - 1)
        }
    }

    private fun initializeCatRV() {
        catRecyclerView = binding.catRv
        catRecyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        catAdapter = CatAdapter(this@MainActivity)
        catRecyclerView.adapter = catAdapter
    }

    private fun performApi(catId: String) {
        viewmodel.getResponseData(catId).observe(this, Observer { apiResponse ->
            when (apiResponse) {
                is ApisResponse.Success -> {
                    Log.d(TAG, "getCat details: $apiResponse")
                    getCatData(apiResponse.response)
                }
                is ApisResponse.Error -> {
                    Log.d(TAG, "getCat details: error")
                }
                is ApisResponse.Loading -> {
                    viewmodel.showProgress()
                    viewmodel.hideAddBtn()
                }
                is ApisResponse.Complete -> {
                    viewmodel.hideProgress()
                    viewmodel.showAddBtn()
                }
                else -> {}
            }
        })
    }

    private fun getCatData(response: ApiResponseData) {
        // Add product list
        response.data.forEach {
            productList.add(it.product_name)
        }
        response.data.forEach {
            catDetailsMap[it.product_name] =
                CatDetails(it.product_price.toString(), "1", it.product_price.toString())
        }
    }

}