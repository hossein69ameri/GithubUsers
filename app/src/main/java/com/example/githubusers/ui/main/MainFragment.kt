package com.example.githubusers.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.R
import com.example.githubusers.databinding.FragmentMainBinding
import com.example.githubusers.util.NetworkResult
import com.example.githubusers.util.extention.setupRecyclerView
import com.example.githubusers.util.extention.visibilityLoading
import com.example.githubusers.viewmodel.main.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var mainAdapter: MainAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init toolbar
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarMain)
        //show empty state
        binding.emptyMain.visibilityLoading(true,binding.recyclerMain)
        //searchBar
        binding.searchUser.addTextChangedListener {
            if (it.toString().isNotEmpty()){
                binding.emptyMain.visibilityLoading(false,binding.recyclerMain)
                mainViewModel.searchUsers(it.toString())
            }else {
                binding.emptyMain.visibilityLoading(true,binding.recyclerMain)
            }
        }
        //display data
        lifecycleScope.launchWhenCreated {
            mainViewModel.stateSearch.collectLatest {
                if (it != null) {
                    when (it.status) {
                        NetworkResult.Status.SUCCESS -> {
                            if (it.data != null) {
                                it.data.items?.let { itData ->
                                    Log.e("TAG", "${itData.size}", )
                                    mainAdapter.setData(itData)
                                    binding.recyclerMain.setupRecyclerView(LinearLayoutManager(requireContext()), mainAdapter)
                                }
                            }
                        }
                        NetworkResult.Status.ERROR -> {
                            Toast.makeText(
                                requireContext(),
                                it.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        mainAdapter.setOnItemClickListener {
            val directions = MainFragmentDirections.actionMainFragmentToDetailFragment(it.login.toString(),it.id!!.toInt())
            findNavController().navigate(directions)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main,menu)
        val favorite = menu.findItem(R.id.favoriteMenu_main)
        favorite.setOnMenuItemClickListener {
            Snackbar.make(binding.root,"Favorite",Snackbar.LENGTH_SHORT).show()
            true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
}