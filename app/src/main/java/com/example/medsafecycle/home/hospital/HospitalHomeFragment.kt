package com.example.medsafecycle.home.hospital

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.medsafecycle.R
import com.example.medsafecycle.databinding.FragmentHospitalHomeBinding
import com.example.medsafecycle.limbah.HistoryLimbahActivity
import com.example.medsafecycle.limbah.HistoryLimbahAdapter
import com.example.medsafecycle.limbah.LimbahDummy
import com.example.medsafecycle.limbah.LimbahNotFoundActivity

class HospitalHomeFragment : Fragment() {

    private var _binding: FragmentHospitalHomeBinding? = null
    private val binding get() = _binding!!

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
        setUpRecyclerView()

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

        // TODO : Ganti aja ye
        binding.cameraButton.setOnClickListener {
            redirectResultNotFound()
        }
    }

    private fun redirectResultNotFound() {
        val i = Intent(requireContext(), LimbahNotFoundActivity::class.java)
        startActivity(i)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.setHasFixedSize(true)

        list.addAll(getListDummy())
        showRecyclerList()
    }

    private fun setUpButton() {
        binding.toHistoryButton.setOnClickListener {
            val moveIntent = Intent(requireContext(), HistoryLimbahActivity::class.java)
            startActivity(moveIntent)
        }
    }

    // TODO : Jangan lupa sesuain ini sama output API, terutama bagian pas nambahin list
    private fun showRecyclerList() {
        val listAdapter = HistoryLimbahAdapter(list)
        binding.recyclerView.adapter = listAdapter
    }

    // TODO : Ini buat bikin dummy. Nanti hapus ini dan di values.strings.xml

    private val list = ArrayList<LimbahDummy>()
    private fun getListDummy(): ArrayList<LimbahDummy> {

        val dataJenis = resources.getStringArray(R.array.data_jenis)
        val dataTanggal = resources.getStringArray(R.array.data_tanggal)

        val listDummy = ArrayList<LimbahDummy>()
        for (i in dataJenis.indices) {
            val dummy = LimbahDummy(dataJenis[i], dataTanggal[i])
            listDummy.add(dummy)
        }

        // TODO : Di halaman ini cuma perlu max 3, nanti sesuain yaa
        if (listDummy.size > 3) {
            return ArrayList(listDummy.subList(0, 3))
        } else {
            return listDummy
        }
    }

}