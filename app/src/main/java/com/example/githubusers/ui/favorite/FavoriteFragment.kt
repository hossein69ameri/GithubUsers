package com.example.githubusers.ui.favorite

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.R
import com.example.githubusers.databinding.FragmentFavoriteBinding
import com.example.githubusers.util.extention.setupRecyclerView
import com.example.githubusers.util.extention.visibilityLoading
import com.example.githubusers.viewmodel.favorite.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteViewModel : FavoriteViewModel by viewModels()

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init toolbar
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.favoriteToolbar)
        binding.favoriteToolbar.apply {
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
        //get favorite list
       favoriteViewModel.usersList()
        lifecycleScope.launchWhenStarted {
            favoriteViewModel.stateList.collectLatest {
                if (it != null){
                    if (it.isEmpty){
                            binding.favoriteEmpty.visibilityLoading(true,binding.favoriteRecycler)
                    } else {
                        binding.favoriteEmpty.visibilityLoading(false,binding.favoriteRecycler)
                        favoriteAdapter.setData(it.data!!)
                        binding.favoriteRecycler.setupRecyclerView(LinearLayoutManager(requireContext()),favoriteAdapter)
                    }
                }

            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favorite, menu)
        val setting_menu = menu.findItem(R.id.settingMenu)
        setting_menu.setOnMenuItemClickListener {
            Snackbar.make(binding.root, "Setting menu", Snackbar.LENGTH_SHORT).show()
            true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

}