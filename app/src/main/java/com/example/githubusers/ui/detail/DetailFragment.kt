package com.example.githubusers.ui.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.githubusers.R
import com.example.githubusers.data.database.UserEntity
import com.example.githubusers.databinding.FragmentDetailBinding
import com.example.githubusers.util.NetworkResult
import com.example.githubusers.util.const.*
import com.example.githubusers.viewmodel.detail.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private var userName = ""
    private var profile = ""
    private var usernameID = 0
    private var isFavorite = false

    @Inject
    lateinit var entity: UserEntity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get userName
        userName = args.detailUsername
        USERNAME = userName
        //get userID
        usernameID = args.userID
        //call api
        detailViewModel.detailUser(userName)
        //init toolbar
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarDetail)
        binding.toolbarDetail.apply {
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
        //init tabLayout an viewPager
        val tabTitle = arrayOf(FOLLOWER, FOLLOWING)
        val pagerAdapter = PagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.detailViewPager2.adapter = pagerAdapter
        TabLayoutMediator(binding.detailTabLayout, binding.detailViewPager2) { tab, position -> tab.text = tabTitle[position] }.attach()
        //display detail user
        lifecycleScope.launchWhenCreated {
            detailViewModel.stateDetail.collectLatest {
                if (it != null) {
                    when (it.status) {
                        NetworkResult.Status.SUCCESS -> {
                            it.data?.let { itData ->
                                entity.id = itData.id!!.toInt()
                                entity.bio = itData.bio.toString()
                                entity.name = itData.name.toString()
                                entity.following = itData.following.toString()
                                entity.follower = itData.followers.toString()
                                entity.image = itData.avatarUrl.toString()
                                entity.location = itData.location.toString()
                                entity.repo = itData.publicRepos.toString()
                                binding.apply {
                                    imageDetail.load(itData.avatarUrl)
                                    detailName.text = itData.name
                                    profile = itData.name.toString()
                                    detailBio.text = itData.bio
                                    detailNumberFollower.text = itData.followers.toString()
                                    detailNumberFollowing.text = itData.following.toString()
                                    detailNumberRepository.text = itData.publicRepos.toString()
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
        //exist user
        detailViewModel.existsFood(usernameID)
        lifecycleScope.launchWhenStarted {
            detailViewModel.isFavoriteData.collectLatest {
                isFavorite = it
                if (it) {
                    binding.detailFloat.setColorFilter(ContextCompat.getColor(requireContext(), R.color.lightPink))
                } else {
                    binding.detailFloat.setColorFilter(ContextCompat.getColor(requireContext(), R.color.eggPlant))
                }
            }
        }
        //save or delete user
        binding.detailFloat.setOnClickListener {
            if (isFavorite) {
                detailViewModel.deleteFood(entity)
                Snackbar.make(binding.root, DELETE_USER, Snackbar.LENGTH_SHORT).show()
            } else {
                detailViewModel.saveFood(entity)
                Snackbar.make(binding.root, SAVE_USER, Snackbar.LENGTH_SHORT).show()
            }
        }
        //set name toolbar
        binding.toolbarDetail.title = profile
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
        val favorite = menu.findItem(R.id.favoriteMenu_detail)
        favorite.setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_main_to_favorite)
            true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
}