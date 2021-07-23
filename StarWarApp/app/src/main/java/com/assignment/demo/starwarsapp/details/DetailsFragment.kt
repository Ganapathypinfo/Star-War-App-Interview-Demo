package com.assignment.demo.starwarsapp.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.assignment.demo.starwarsapp.R
import com.assignment.demo.starwarsapp.api_service.StarWarsApiRequest
import com.assignment.demo.starwarsapp.base.BaseFragment
import com.assignment.demo.starwarsapp.constants.AppConstants
import com.assignment.demo.starwarsapp.databinding.FragmentDetailsBinding
import com.assignment.demo.starwarsapp.datamodel.peoples.Results
import com.assignment.demo.starwarsapp.details.viewmodel.DetailViewModelFactory
import com.assignment.demo.starwarsapp.details.viewmodel.DetailsViewModel
import com.assignment.demo.starwarsapp.repository.ApiRepository
import com.assignment.demo.starwarsapp.retrofit.ApiRetrofit
import com.assignment.demo.starwarsapp.utils.ImageUtils
import com.assignment.demo.starwarsapp.utils.NotificationHelper
import javax.inject.Inject

class DetailsFragment : BaseFragment() {

    @Inject
    lateinit var ApiRetrofit: ApiRetrofit

    @Inject
    lateinit var notificationHelper: NotificationHelper

    @Inject
    lateinit var imageUtils: ImageUtils


    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding
    private var selectedId : String = ""
    private var screen : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiRequest = ApiRetrofit.getRetrofitInstance()
            .create(StarWarsApiRequest::class.java)
        val apiRepository = ApiRepository(apiRequest)
        val factory = DetailViewModelFactory(apiRepository)
        detailsViewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screen = arguments?.let { detailsViewModel.getScreen(it) }.toString()
        selectedId = arguments?.let { detailsViewModel.getData(it) }.toString()
        if(screen!=null&& screen.contains(AppConstants.LIST_ITEM))
        {
            selectedId?.let { fetchstarwarsListDetailsFromApi(it) }

        }
        else
        {
            selectedId?.let { fetchstarwarsDetailsFromApi(it) }
        }
        initObserver()
    }

    private fun initObserver() {
        detailsViewModel.getPeopleResultsMutableLiveData()
            .observe(viewLifecycleOwner) { baseApiResponseModel ->
                hideProgressDialog()
                if (baseApiResponseModel != null && baseApiResponseModel.isSuccessful) {
                    val apiResponseData = baseApiResponseModel.apiResponseData
                    apiResponseData?.let {
                        populateResult(it as Results)
                    }

                } else {
                    //TODO handle api error here
                    val errorMsgString = resources.getString(R.string.error_msg)
                    notificationHelper.setSnackBar(binding.root, errorMsgString)
                }
            }
    }

    private fun fetchstarwarsDetailsFromApi(selectedId: String) {
        showProgressDialog()
        detailsViewModel.fetchPeopleResultsResponseLiveData(selectedId)
    }

    private fun fetchstarwarsListDetailsFromApi(selectedId: String) {
        showProgressDialog()
        detailsViewModel.fetchListItemPeopleResultsResponseLiveData(selectedId)
    }

    private fun populateResult(results: Results) {
        binding.tvStarName.text = results.name.capitalize()
        binding.tvStarHeight.text = results.height.toString().capitalize()+" "+"cms"
        binding.tvStarHairColor.text = results.hair_color.capitalize()
        binding.tvSkinColor.text = results.skin_color.capitalize()
        binding.tvStarEyeColor.text = results.eye_color.capitalize()
        binding.tvStarMass.text = results.mass.toString().capitalize()
        binding.tvStarBirthYear.text = results.birth_year.capitalize()
        binding.tvStarGender.text = results.gender.capitalize()
        val created=results.created
        val createDate = created.split("T".toRegex())[0]
        val edited=results.edited
        val editDate = edited.split("T".toRegex())[0]
        binding.tvStarCreated.text= createDate
        binding.tvStarEdited.text= editDate

    }

    private fun showProgressDialog() {
        binding.progressBar.visibility = View.VISIBLE
    }


    private fun hideProgressDialog() {
        binding.progressBar.visibility = View.GONE
    }

}