package com.example.githubusers.ui.detail.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.R
import com.example.githubusers.databinding.FragmentFollowingBinding
import com.example.githubusers.ui.detail.DetailFragmentArgs
import com.example.githubusers.ui.main.MainFragmentDirections
import com.example.githubusers.util.NetworkResult
import com.example.githubusers.util.const.USERNAME
import com.example.githubusers.util.extention.setupRecyclerView
import com.example.githubusers.viewmodel.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private val detailViewModel : DetailViewModel by viewModels()

    @Inject
    lateinit var followingAdapter: FollowingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get userName
       var username = USERNAME
        //cal api
        detailViewModel.followinglUser(username)
        //display data
        lifecycleScope.launchWhenCreated {
            detailViewModel.stateFollowing.collectLatest {
                if (it != null) {
                    when (it.status) {
                        NetworkResult.Status.SUCCESS -> {
                            if (it.data != null) {
                                followingAdapter.setData(it.data)
                                binding.recyclerFollowing.setupRecyclerView(LinearLayoutManager(requireContext()), followingAdapter)
                            }
                        }
                        NetworkResult.Status.ERROR -> {
                            Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        //click
        followingAdapter.setOnItemClickListener {
            val direction = MainFragmentDirections.actionMainFragmentToDetailFragment(it.login.toString(),it.id!!.toInt())
            findNavController().navigate(direction)
        }
    }
}