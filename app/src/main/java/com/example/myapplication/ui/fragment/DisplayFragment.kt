package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.utils.Constants
import com.example.title.databinding.FragmentDisplayBinding

//displayfragment to show the contents of the post.
class DisplayFragment : Fragment() {
    lateinit var displayBinding: FragmentDisplayBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        displayBinding = FragmentDisplayBinding.inflate(layoutInflater)
        arguments?.let {

            displayBinding.body.text=it.getString(Constants.BODY)?: ""
            displayBinding.title.text=it.getString(Constants.TITLE)?: ""
        }


        return displayBinding.root
    }

}