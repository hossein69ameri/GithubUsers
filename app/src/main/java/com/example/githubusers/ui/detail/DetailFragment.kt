package com.example.githubusers.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.githubusers.R
import com.example.githubusers.databinding.FragmentDetailBinding
import com.example.githubusers.util.NetworkResult
import com.example.githubusers.viewmodel.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private var userName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userName = args.detailUsername
        detailViewModel.detailUser(userName)
        lifecycleScope.launchWhenCreated {
            detailViewModel.stateDetail.collectLatest {
                if (it != null) {
                    when (it.status) {
                        NetworkResult.Status.SUCCESS -> {
                            it.data?.let { itData ->
                                binding.apply {
                                    imageDetail.load(itData.avatarUrl)
                                    detailName.text = itData.name
                                    detailBio.text = itData.bio
                                    detailNumberFollower.text = itData.followers.toString()
                                    detailNumberFollowing.text = itData.following.toString()
                                    detailNumberRepository.text = itData.publicRepos.toString()
                                }
                            }

                        }
                        NetworkResult.Status.ERROR -> {
                            Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }


    }

}