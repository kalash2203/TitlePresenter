package com.example.myapplication.ui.fragment

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.adapter.MTitleAdapter
import com.example.myapplication.repository.Repository
import com.example.myapplication.viewModelFactory.TitleRecordViewModelFactory
import com.example.myapplication.viewmodel.TitleRecordViewModel
import com.example.title.databinding.FragmentTitleBinding

//populartitle to show the title of the post inside the recyclerview.

class PopularTitleFragment : Fragment() {
    lateinit var popularTitleBinding: FragmentTitleBinding
    val repository = Repository()
    val viewModelFactory = TitleRecordViewModelFactory(repository)
    val viewModel by viewModels<TitleRecordViewModel> {
        viewModelFactory
    }
    private val REQUEST_ACCESSIBILITY = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        popularTitleBinding = FragmentTitleBinding.inflate(layoutInflater)
        //call this function to fetch the response from server in first time load and later from ROOM DB
        viewModel.mTitleRecord(requireContext())
        //observe livedata object for data changes and then load data inside recycler view
        viewModel.movieLiveData.observe(viewLifecycleOwner, Observer {
            Log.e("Kalash",it.toString())
            val TitleAdapter = MTitleAdapter(it, requireContext(),requireActivity())
            popularTitleBinding.recyclerView.adapter = TitleAdapter
        })
        //on clicking FAB button call request the permission to allow accessibility service
        popularTitleBinding.requestAccessibility.setOnClickListener {
            requestAccessibilityPermission()
        }

        return popularTitleBinding.root
    }


    private fun requestAccessibilityPermission() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivityForResult(intent, REQUEST_ACCESSIBILITY)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //check request code
        if (requestCode == REQUEST_ACCESSIBILITY) {
            //check if permission is allowed or denied
            if (isAccessibilityEnabled()) {
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(),"Permission Not Granted",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isAccessibilityEnabled(): Boolean {
        val accessibilityManager = requireContext().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledServices = accessibilityManager.getEnabledAccessibilityServiceList(
            AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        //use enabled services to get list of enabled services and then check if MyAccessibilityService is enabled or not
        //by checking the service id.
        for (enabledService in enabledServices) {
            if (enabledService.getId().equals(activity?.packageName + "/com.example.myapplication.ui.services.MyAccessibilityService")) {
                return true
            }
        }
        return false
    }

}