package com.example.githubusers.ui.detail.tab

import android.os.Bundle
import android.util.Log
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
import com.example.githubusers.databinding.FragmentFollowerBinding
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
class FollowerFragment : Fragment() {
    private lateinit var binding: FragmentFollowerBinding
    private val detailViewModel : DetailViewModel by viewModels()

    @Inject
    lateinit var followerAdapter: FollowerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get userName
        var username = USERNAME
        //cal api
        detailViewModel.followerslUser(username)
        //display data
        lifecycleScope.launchWhenCreated {
            detailViewModel.stateFollowers.collectLatest {
                if (it != null) {
                    when (it.status) {
                        NetworkResult.Status.SUCCESS -> {
                            if (it.data != null) {
                                    followerAdapter.setData(it.data)
                                    binding.recyclerFollower.setupRecyclerView(LinearLayoutManager(requireContext()), followerAdapter)
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
        followerAdapter.setOnItemClickListener {
            val direction = MainFragmentDirections.actionMainFragmentToDetailFragment(it.login.toString(),it.id!!.toInt())
            findNavController().navigate(direction)
        }
    }
}