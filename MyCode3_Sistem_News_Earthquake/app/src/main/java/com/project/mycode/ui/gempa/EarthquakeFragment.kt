package com.project.mycode.ui.gempa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.mycode.ViewModelFactory
import com.project.mycode.data.local.FeltEarthquakeDB
import com.project.mycode.data.local.NewEarthquakeDB
import com.project.mycode.databinding.FragmentEarthquakeBinding
import com.project.mycode.network.ResultResponse


@Suppress("UNREACHABLE_CODE")
class EarthquakeFragment : Fragment() {

    private var _binding: FragmentEarthquakeBinding? = null
    private val binding get() = _binding!!

    private lateinit var earthquakeViewModel: EarthquakeViewModel
    private lateinit var listFelt: AdapterEarthquakeFelt

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthquakeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        earthquakeViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(requireContext())
        )[EarthquakeViewModel::class.java]

        getEarthquakeNew()
        getEarthquakeFelt()

        (activity as AppCompatActivity?)!!.supportActionBar?.hide()
    }

    private fun getEarthquakeFelt() {
        listFelt = AdapterEarthquakeFelt(listOf())

        earthquakeViewModel.feltEarthquake().observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is ResultResponse.Loading -> {

                    }
                    is ResultResponse.Success -> {
                        val data = it.data
                        showRecyclerList(data)
                    }
                    is ResultResponse.Error -> {

                    }
                }
            }
        }

    }

    private fun showRecyclerList(earthquakeDBList: List<FeltEarthquakeDB>) {
        binding.rvDaftarGempa.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = AdapterEarthquakeFelt(earthquakeDBList)
        }
    }


    private fun getEarthquakeNew() {
        earthquakeViewModel.earthquakeNew().observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is ResultResponse.Loading -> {

                    }
                    is ResultResponse.Success -> {
                        val newData = it.data
                        data(newData)
                    }
                    is ResultResponse.Error -> {

                    }
                }
            }
        }
    }

    fun data(bind: NewEarthquakeDB) {
        binding.apply {
            tvDirasakan.text = bind.felt
            tvWilayah.text = bind.region
            tvMagnitude.text = bind.magnitude
            tvKedalaman.text = bind.depth
            tvClock.text = bind.clock
            tvDate.text = bind.date
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}