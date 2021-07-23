package com.assignment.demo.starwarsapp.home.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.assignment.demo.starwarsapp.R
import com.assignment.demo.starwarsapp.datamodel.peoples.Results
import com.assignment.demo.starwarsapp.utils.DataConverter
import com.assignment.demo.starwarsapp.utils.NotificationHelper

class AutoSuggestAdapter(context: Context, @LayoutRes private val layoutResource: Int) :
    ArrayAdapter<Results>(context, layoutResource),
    Filterable {

    private var resultData: MutableList<Results>? = ArrayList()
    private val dataConverter = DataConverter()

    fun setData(list: List<Results>) {
        resultData!!.clear()
        resultData!!.addAll(list!!)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: TextView = convertView as TextView? ?: LayoutInflater.from(context)
            .inflate(layoutResource, parent, false) as TextView
        view.text = resultData?.get(position)?.let { prepareDropdownText(it) }
        return view
    }

    override fun getCount(): Int {
        return if (resultData != null && resultData!!.size > 0) {
            resultData!!.size
        } else 0
    }

    override fun getItem(position: Int): Results {
        return resultData!![position]
    }

    /**
     * Used to Return the full object directly from adapter.
     *
     * @param position
     * @return
     */
    fun getObject(position: Int): Results {
        return resultData!![position]
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    filterResults.values = resultData
                    filterResults.count = resultData!!.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else if (constraint != null && constraint.length > 3) {
                    showNoDataSnackBarMessage()
                    notifyDataSetInvalidated()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }

    private fun prepareDropdownText(result: Results): String {
        return dataConverter.prepareDisplayIdFromResult(result)
    }

    private fun showNoDataSnackBarMessage() {
        try {
            val rootView =
                (context as Activity?)!!.window.decorView.findViewById<View>(R.id.content)
            NotificationHelper().setSnackBar(
                rootView,
                context.getString(R.string.search_no_result_found)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}