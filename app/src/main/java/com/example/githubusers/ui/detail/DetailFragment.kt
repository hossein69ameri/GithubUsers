package com.example.githubusers.ui.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.githubusers.R
import com.example.githubusers.databinding.FragmentDetailBinding
import com.example.githubusers.util.NetworkResult
import com.example.githubusers.util.const.FOLLOWER
import com.example.githubusers.util.const.FOLLOWING
import com.example.githubusers.util.const.USERNAME
import com.example.githubusers.viewmodel.detail.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private var userName = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init toolbar
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarDetail)
        //init tabLayout an viewPager
        val tabTitle = arrayOf(FOLLOWER, FOLLOWING)
        val pagerAdapter = PagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.detailViewPager2.adapter = pagerAdapter
        TabLayoutMediator(binding.detailTabLayout, binding.detailViewPager2) { tab, position -> tab.text = tabTitle[position] }.attach()
        //get userName
        userName = args.detailUsername
        USERNAME = userName
        //call api
        detailViewModel.detailUser(userName)
        //display data
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

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail,menu)
        val favorite = menu.findItem(R.id.favoriteMenu_detail)
        favorite.setOnMenuItemClickListener {
            Snackbar.make(binding.root,"Favorite", Snackbar.LENGTH_SHORT).show()
            true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
}