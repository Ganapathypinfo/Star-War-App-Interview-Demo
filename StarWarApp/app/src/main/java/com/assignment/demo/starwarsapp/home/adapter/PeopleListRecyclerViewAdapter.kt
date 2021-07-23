package com.assignment.demo.starwarsapp.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.assignment.demo.starwarsapp.BuildConfig
import com.assignment.demo.starwarsapp.R
import com.assignment.demo.starwarsapp.datamodel.peoples.Results
import com.assignment.demo.starwarsapp.home.HomeFragment

class PeopleListRecyclerViewAdapter(
    private val fragment: Fragment,
    private val starwarsRoomDataModels: List<Results>
) :
    RecyclerView.Adapter<PeopleListRecyclerViewAdapter.starwarsViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PeopleListRecyclerViewAdapter.starwarsViewHolder {
        val view: View =
            LayoutInflater.from(fragment.context)
                .inflate(R.layout.row_recent_search, parent, false)
        return starwarsViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: PeopleListRecyclerViewAdapter.starwarsViewHolder,
        position: Int
    ) {
        val curr = starwarsRoomDataModels[position]
        holder.name.text = "Name: ${curr.name}"
        holder.gender.text =  "Gender: ${curr.gender.toUpperCase()}"
        holder.birthyear.text = "BirthYear: ${curr.birth_year.toUpperCase()}"

        holder.layout.setOnClickListener(View.OnClickListener { (fragment as HomeFragment).onRowItemClicked(curr.url.replace(BuildConfig.BASE_URL,""),"ListItem") })
    }

    override fun getItemCount(): Int {
        return if (starwarsRoomDataModels != null && !starwarsRoomDataModels.isNullOrEmpty())
            starwarsRoomDataModels.size
        else
            0;
    }


    class starwarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.tvStarName)
        var gender: TextView = itemView.findViewById(R.id.tvStarGender)
        var birthyear: TextView = itemView.findViewById(R.id.tvBirthYear)
        var layout: ConstraintLayout = itemView.findViewById(R.id.const_detials)




    }
}