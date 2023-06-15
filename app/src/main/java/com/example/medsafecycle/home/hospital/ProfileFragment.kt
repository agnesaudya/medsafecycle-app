package com.example.medsafecycle.home.hospital

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.medsafecycle.ProfileResponse
import com.example.medsafecycle.UserPreference
import com.example.medsafecycle.databinding.FragmentProfileBinding
import com.example.medsafecycle.landing.LandingActivity
import com.example.medsafecycle.viewmodel.auth.ProfileViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels ()
    private lateinit var mUserPreference: UserPreference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUserPreference = UserPreference(requireActivity())
        profileViewModel.getUserinfo(mUserPreference.getToken().toString())
        profileViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        profileViewModel.profileResponse.observe(requireActivity()) {
            showResult(it)
        }

        binding.logout.setOnClickListener{
            mUserPreference.removePref()
            val moveIntent = Intent(requireActivity(), LandingActivity::class.java)
            startActivity(moveIntent)
            requireActivity().finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility  = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showResult(res: ProfileResponse) {
        if(res.username!=null){
            setProfile(res)
        }

    }

    private fun setProfile(profile: ProfileResponse) {
        binding.apply {
            email.text=profile.userEmail
            nama.text=profile.username
            alamat.text=profile.userAddress
        }

    }
}
