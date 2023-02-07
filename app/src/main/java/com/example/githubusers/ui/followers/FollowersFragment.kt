package com.example.githubusers.ui.followers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.githubusers.R
import com.example.githubusers.databinding.FragmentFollowersBinding
import com.example.githubusers.ui.detail.PagerAdapter
import com.example.githubusers.util.const.FOLLOWER
import com.example.githubusers.util.const.FOLLOWING
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowersFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init toolbar
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarFollowers)
        binding.toolbarFollowers.apply {
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
        //init tabLayout an viewPager
        val tabTitle = arrayOf(FOLLOWER, FOLLOWING)
        val pagerAdapter = PagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.detailViewPager2.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayoutFollowers, binding.detailViewPager2) { tab, position -> tab.text = tabTitle[position] }.attach()
    }

}