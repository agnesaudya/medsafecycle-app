package com.example.medsafecycle.home.hospital

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medsafecycle.HistoryResponseItem
import com.example.medsafecycle.R
import com.example.medsafecycle.UserPreference
import com.example.medsafecycle.databinding.FragmentHospitalHomeBinding
import com.example.medsafecycle.home.popup.HospitalPopup
import com.example.medsafecycle.home.popup.UploadPopup
import com.example.medsafecycle.limbah.*
import com.example.medsafecycle.viewmodel.AuthViewModelFactory
import com.example.medsafecycle.viewmodel.HistoryViewModel
import com.example.medsafecycle.viewmodel.HospitalHomeViewModel

class HospitalHomeFragment : Fragment() {

    private var _binding: FragmentHospitalHomeBinding? = null
    private val binding get() = _binding!!
    private val hospitalHomeViewModel: HospitalHomeViewModel by viewModels()
    private lateinit var adapter: HospitalHistoryAdapter
    private lateinit var mUserPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHospitalHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpButton()

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.layoutManager = layoutManager
        mUserPreference = UserPreference(requireActivity())
        hospitalHomeViewModel.getFixedHistory(token = mUserPreference.getToken().toString() )
        hospitalHomeViewModel.historyResponse.observe(viewLifecycleOwner) {listScan ->
            setRv(listScan)
        }
        hospitalHomeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }




        binding.redirectNearestWasteCompany.setOnClickListener {
            val query = "Perusahaan Limbah Medis Terdekat"
            val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(query)}")


            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

            mapIntent.setPackage("com.google.android.apps.maps")
            context?.let { it1 ->
                mapIntent.resolveActivity(it1.packageManager)?.let {
                    startActivity(mapIntent)
                }
            }

        }

        binding.cameraButton.setOnClickListener {
            val hospitalPopup = HospitalPopup()
            hospitalPopup.show(parentFragmentManager,"hospital_upload_popup")
        }
    }

    private fun setRv(listHistory: List<HistoryResponseItem>) {
        val adapter = HospitalHistoryAdapter(listHistory)
        binding.recyclerView.adapter = adapter


    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpButton() {
        binding.toHistoryButton.setOnClickListener {
            val moveIntent = Intent(requireContext(), HistoryLimbahActivity::class.java)
            startActivity(moveIntent)
        }
    }




}