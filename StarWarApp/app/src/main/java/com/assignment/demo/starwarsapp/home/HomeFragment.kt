package com.assignment.demo.starwarsapp.home

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AutoCompleteTextView
import androidx.lifecycle.ViewModelProvider
import com.assignment.demo.starwarsapp.R
import com.assignment.demo.starwarsapp.api_service.StarWarsApiRequest
import com.assignment.demo.starwarsapp.base.BaseApiResponseModel
import com.assignment.demo.starwarsapp.base.BaseFragment
import com.assignment.demo.starwarsapp.constants.AppConstants
import com.assignment.demo.starwarsapp.databinding.FragmentHomeBinding
import com.assignment.demo.starwarsapp.datamodel.peoples.PeopleResponseModel
import com.assignment.demo.starwarsapp.datamodel.peoples.Results
import com.assignment.demo.starwarsapp.details.DetailsFragment
import com.assignment.demo.starwarsapp.home.adapter.AutoSuggestAdapter
import com.assignment.demo.starwarsapp.home.adapter.PeopleListRecyclerViewAdapter
import com.assignment.demo.starwarsapp.home.viewmodel.HomeViewModel
import com.assignment.demo.starwarsapp.home.viewmodel.HomeViewModelFactory
import com.assignment.demo.starwarsapp.repository.ApiRepository
import com.assignment.demo.starwarsapp.retrofit.ApiRetrofit
import com.assignment.demo.starwarsapp.utils.DataConverter
import com.assignment.demo.starwarsapp.utils.NotificationHelper
import com.assignment.demo.starwarsapp.view.MainActivity
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    private var nextPageUrl: String? = ""
    private var previousPageUrl: String? = ""

    @Inject
    lateinit var ApiRetrofit: ApiRetrofit

    @Inject
    lateinit var dataConverter: DataConverter

    @Inject
    lateinit var notificationHelper: NotificationHelper

    private lateinit var autoSuggestAdapter: AutoSuggestAdapter
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private val TRIGGER_AUTO_COMPLETE = 100
    private val AUTO_COMPLETE_DELAY: Long = 300
    private var handler: Handler? = null
    private lateinit var peopleListAdapter: PeopleListRecyclerViewAdapter

    private lateinit var starList: ArrayList<Results>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiRequest = ApiRetrofit.getRetrofitInstance()
            .create(StarWarsApiRequest::class.java)
        val apiRepository = ApiRepository(apiRequest)
        val factory = HomeViewModelFactory(apiRepository)
        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.btnNext.setOnClickListener {
            showProgressDialog()
            homeViewModel.getPeopleLoadMore(nextPageUrl)
            homeViewModel.getLoadMoreMutableLiveData()
                .observe(viewLifecycleOwner) { baseApiResponseModel: BaseApiResponseModel<PeopleResponseModel>? ->
                    hideProgressDialog()
                    if (baseApiResponseModel != null && baseApiResponseModel.isSuccessful) {
                        val apiResponseData =
                            baseApiResponseModel.apiResponseData as PeopleResponseModel
                        apiResponseData.let {
                            if (starList.isNotEmpty()) starList.clear()
                            starList.addAll(apiResponseData.results)
                            nextPageUrl = apiResponseData.next
                            previousPageUrl = apiResponseData.previous
                            showLoadMore()
                        }
                    } else {
                        val errorMsgString = resources.getString(R.string.error_msg)
                        notificationHelper.setSnackBar(binding.root, errorMsgString)
                    }
                }
        }
        binding.btnPrevious.setOnClickListener {
            showProgressDialog()
            homeViewModel.getPeopleLoadMore(previousPageUrl)
            homeViewModel.getLoadMoreMutableLiveData()
                .observe(viewLifecycleOwner) { baseApiResponseModel: BaseApiResponseModel<PeopleResponseModel>? ->
                    hideProgressDialog()
                    if (baseApiResponseModel != null && baseApiResponseModel.isSuccessful) {
                        val apiResponseData =
                            baseApiResponseModel.apiResponseData as PeopleResponseModel
                        apiResponseData.let {
                            if (starList.isNotEmpty()) starList.clear()
                            starList.addAll(apiResponseData.results)
                            nextPageUrl = apiResponseData.next
                            previousPageUrl = apiResponseData.previous
                            showLoadMore()
                        }
                    } else {
                        val errorMsgString = resources.getString(R.string.error_msg)
                        notificationHelper.setSnackBar(binding.root, errorMsgString)
                    }
                }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        setupSearchAutoCompleteView(binding.includeAutocompleteSearchLayout.autoCompleteSearch)
        starList = ArrayList()
    }

    override fun onResume() {
        super.onResume()
        binding.includeAutocompleteSearchLayout.autoCompleteSearch.text.clear()
    }


    private fun setupSearchAutoCompleteView(autoCompleteTextView: AutoCompleteTextView) {
        autoSuggestAdapter = AutoSuggestAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1
        )
        autoCompleteTextView.dropDownWidth = resources.displayMetrics.widthPixels - 250
        autoCompleteTextView.threshold = 3
        autoCompleteTextView.setAdapter(autoSuggestAdapter)
        autoCompleteTextView.onItemClickListener =
            OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
                onAutoCompleteRowClick(autoSuggestAdapter.getObject(position))
            }

        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                handler?.removeMessages(TRIGGER_AUTO_COMPLETE)
                handler?.sendEmptyMessageDelayed(
                    TRIGGER_AUTO_COMPLETE,
                    AUTO_COMPLETE_DELAY
                )
            }

            override fun afterTextChanged(s: Editable) {}
        })
        handler = Handler { msg: Message ->
            if (msg.what == TRIGGER_AUTO_COMPLETE) {
                val selectedText = autoCompleteTextView.text
                if (!TextUtils.isEmpty(selectedText)) {
                    binding.includeAutocompleteSearchLayout.progressLoading.visibility =
                        View.VISIBLE
                    fetchSearchDataFromApi(selectedText.toString())
                }
            }
            false
        }
    }


    private fun onAutoCompleteRowClick(result: Results) {
        binding.includeAutocompleteSearchLayout.autoCompleteSearch.text.clear()
        (activity as MainActivity).hideKeyboard()
        openDetailsFragment(dataConverter.getIdFromResult(result), AppConstants.SEARCH)
    }

    private fun initObservers() {
        showProgressDialog()
        homeViewModel.getPeoples()
        homeViewModel.getRemoteResponsePeopleMutableLiveData()
            .observe(viewLifecycleOwner) { baseApiResponseModel: BaseApiResponseModel<PeopleResponseModel>? ->
                hideProgressDialog()
                if (baseApiResponseModel != null && baseApiResponseModel.isSuccessful) {
                    val apiResponseData =
                        baseApiResponseModel.apiResponseData as PeopleResponseModel
                    apiResponseData?.let {
                        if (starList.isNotEmpty()) starList.clear()
                        starList.addAll(apiResponseData.results)
                        nextPageUrl = apiResponseData.next
                        previousPageUrl = apiResponseData.previous
                        showStarList(starList)
                    }

                } else {
                    val errorMsgString = resources.getString(R.string.error_msg)
                    notificationHelper.setSnackBar(binding.root, errorMsgString)
                }
            }

        homeViewModel.getPeopleResponseMutableLiveData()
            .observe(viewLifecycleOwner) { baseApiResponseModel ->
                binding.includeAutocompleteSearchLayout.progressLoading.visibility = View.INVISIBLE
                if (baseApiResponseModel != null && baseApiResponseModel.isSuccessful) {
                    val apiResponseData = baseApiResponseModel.apiResponseData
                    apiResponseData?.let {
                        val filteredData = homeViewModel.filterData(it, dataConverter)
                        populateSearchListData(filteredData)
                    }
                } else {
                    val errorMsgString = resources.getString(R.string.error_msg)
                    notificationHelper.setSnackBar(binding.root, errorMsgString)
                }
            }
    }

    private fun fetchSearchDataFromApi(searchQuery: String) {
        homeViewModel.searchPeoplesResponseLiveData(searchQuery)
    }

    private fun openDetailsFragment(selectedId: String, screen: String) {
        val detailsFragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putString("Screen", screen)
        bundle.putSerializable("selectedId", selectedId)
        detailsFragment.arguments = bundle
        (activity as MainActivity).addFragment(detailsFragment)
    }

    private fun populateSearchListData(peopleList: ArrayList<Results>) {
        autoSuggestAdapter.setData(peopleList)
        autoSuggestAdapter.notifyDataSetChanged()
    }

    fun onRowItemClicked(selectedId: String, screen: String) {
        openDetailsFragment(selectedId, screen)
    }


    private fun showStarList(remote: List<Results>) {
        peopleListAdapter = PeopleListRecyclerViewAdapter(this, remote)
        binding.recyclerView.visibility = View.VISIBLE
        binding.btnNext.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
        binding.recyclerView.adapter = peopleListAdapter
        if (previousPageUrl != null) binding.btnPrevious.visibility = View.VISIBLE
        else binding.btnPrevious.visibility = View.INVISIBLE
        peopleListAdapter.notifyDataSetChanged()

    }

    private fun showProgressDialog() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressDialog() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoadMore() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.btnNext.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
        if (previousPageUrl != null) binding.btnPrevious.visibility = View.VISIBLE
        else binding.btnPrevious.visibility = View.INVISIBLE
        peopleListAdapter.notifyDataSetChanged()
    }
}