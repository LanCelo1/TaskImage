package com.example.taskapp.presentation.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.taskapp.R
import com.example.taskapp.data.local.MySharedPreference
import com.example.taskapp.data.model.ImageData
import com.example.taskapp.databinding.ScreenMainBinding
import com.example.taskapp.presentation.presenter.viewmodel.MainScreenViewModel
import com.example.taskapp.presentation.presenter.viewmodel.impl.MainScreenViewModelImpl
import com.example.taskapp.presentation.ui.adapter.ImageHrAdapter
import com.example.taskapp.presentation.ui.adapter.ImageVrAdapter
import com.example.taskapp.utils.LocaleHelper.setLocale
import com.example.taskapp.utils.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {
    @Inject
    lateinit var sharedPreference: MySharedPreference
    private var vrPage : Int = 1
    private var hrPage : Int = 1
    private val binding : ScreenMainBinding by viewBinding(ScreenMainBinding::bind)
    private val viewModel : MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val hrAdapter : ImageHrAdapter by lazy { ImageHrAdapter() }
    private val vrAdapter : ImageVrAdapter by lazy { ImageVrAdapter() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val position = sharedPreference.language
        setLocale(
            context,
            if (position == 2) "ru" else if (position == 1) "uz" else "en"
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerView()
        setUpObservers()
        setUpListeners()
        loadViews()
    }

    private fun setUpListeners() {
        binding.setting.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreen_to_settingScreen)
        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpObservers() {
        viewModel.getErrorLiveData.observe(this@MainScreen,errorObserver)
        viewModel.hrImageDataListLiveData.observe(viewLifecycleOwner,hrImageDataListObserver)
        NetworkMonitor.onNetworkStateChanged.observe(viewLifecycleOwner,onNetworkStateChangedObserver)
        viewModel.vrImageDataListLiveData.observe(viewLifecycleOwner,vrImageDataListObserver)
    }

    private fun loadViews() {
        viewModel.loadHrViews(hrPage)
        viewModel.loadVrViews(vrPage)
        binding.progress.visibility = View.VISIBLE
       /* val position = sharedPreference.language
        Toast.makeText(requireContext(), "shared = ${sharedPreference.language}", Toast.LENGTH_SHORT).show()
        setLocale(requireContext(),if (position == 2) "ru" else if (position == 1) "uz" else "en")*/
    }

    private fun setUpRecyclerView() {
        binding.apply {
            hrRecyclerview.apply {
                setHasFixedSize(false)
                isNestedScrollingEnabled = false
                adapter = hrAdapter.apply {
                    pagingListener {
                        if (it > hrPage){
                            viewModel.loadHrViews(it)
                            hrPage ++
                        }
                    }
                }
            }
            hrRecyclerview.layoutManager = LinearLayoutManager(this@MainScreen.requireContext(),RecyclerView.HORIZONTAL,false)
            vrRecyclerview.apply {
                setHasFixedSize(false)
                isNestedScrollingEnabled = false
                adapter = vrAdapter.apply {
                    pagingListener {
                        if (it > vrPage){
                            viewModel.loadVrViews(it)
                            vrPage ++
                        }
                    }
                }
            }
            vrRecyclerview.layoutManager = LinearLayoutManager(this@MainScreen.requireContext())
        }
    }

    private val hrImageDataListObserver = Observer<List<ImageData>>{
        binding.progress.visibility = View.GONE
        val list = ArrayList(hrAdapter.currentList)
        list.addAll(it)
        hrAdapter.submitList(list)
    }

    private val vrImageDataListObserver = Observer<List<ImageData>>{
        binding.progress.visibility = View.GONE
        val list = ArrayList(vrAdapter.currentList)
        list.addAll(it)
        vrAdapter.submitList(list)
    }

    private val errorObserver = Observer<String>{
        binding.progress.visibility = View.GONE
        binding.notConnection.visibility = View.VISIBLE
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }

    private val onNetworkStateChangedObserver = Observer<Boolean> {
        if (it){
            binding.progress.visibility = View.VISIBLE
            viewModel.loadHrViews(hrPage)
            viewModel.loadVrViews(vrPage)
            binding.notConnection.visibility = View.GONE
            binding.progress.visibility = View.GONE
        }else {
            binding.notConnection.visibility = View.VISIBLE
        }
    }

}