package com.fe.customizekeyboard.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fe.customizekeyboard.adapter.KeyboardListAdapter
import com.fe.customizekeyboard.api.models.Keyboard
import com.fe.customizekeyboard.api.service.ApiService
import com.fe.customizekeyboard.databinding.FragmentProductBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductBinding.inflate(inflater , container , false)
        return binding.root ;


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState) ;
        loadKeyboardfromsServer() ;
    }


    fun loadKeyboardfromsServer() {
        val httpClient = Retrofit.Builder()
            .baseUrl("https://api-keyboardcustomize.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create service object
        var apiService: ApiService = httpClient.create(ApiService::class.java)

        //Load products list from server
        val task: Call<List<Keyboard>> = apiService.loadData();
        task.enqueue(object: Callback<List<Keyboard>> {
            override fun onResponse(
                call: Call<List<Keyboard>>,
                response: Response<List<Keyboard>>
            ) {
                if (response.isSuccessful) {
                    showKeyboardList(response.body()!!)
                } else {
                    Toast.makeText(context, "Load keyboard list failed!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Keyboard>>, t: Throwable) {
                Toast.makeText(context, "Load keyboard list failed!", Toast.LENGTH_LONG).show()
                Log.e("[KeyboardFragment]", "Load keyboard failed: " + t.message)
                t.printStackTrace()
            }
        })

    }
    private fun showKeyboardList(productsList: List<Keyboard>) {

        // Create layout manager

        val layoutManager = GridLayoutManager(context, 2)

        binding.recyclerView.setLayoutManager(layoutManager)

        // Create adapter
        val adapter = KeyboardListAdapter()
        adapter.submitList(productsList)
        binding.recyclerView.setAdapter(adapter)
    }

}

