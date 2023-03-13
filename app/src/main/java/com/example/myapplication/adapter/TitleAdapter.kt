package com.example.myapplication.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.myapplication.models.Title
import com.example.myapplication.utils.Constants
import com.example.title.R
import com.example.title.databinding.ItemTitleBinding

// TitleAdapter file For showing list of titles inside the layout file item_title.
class MTitleAdapter(val list: List<Title>, val context:Context,val activity: Activity) : RecyclerView.Adapter<MTitleAdapter.TitleViewHolder>() {
 class TitleViewHolder(val binding:ViewBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
     val view= ItemTitleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       val TitleViewHolder =  TitleViewHolder(view)
       return TitleViewHolder
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
       with(holder) {
           val result = list.get(position)
           binding.root.setOnClickListener {
               //on clicking on each title view ,new fragment will be opened showing its content.
               Navigation.findNavController(activity  ,R.id.nav_host_fragment)
                   .navigate(R.id.action_popularTitleFragment_to_displayFragment, bundleOf(
                       Constants.TITLE to result.title,
                       Constants.BODY to result.body
                   )

                   )}

           (  this.binding  as ItemTitleBinding ).movieDesc.text = result.title

       }
    }
    override fun getItemCount(): Int {
       return list.size
    }
}
