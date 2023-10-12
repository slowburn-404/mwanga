package com.qemer.mwanga.dashboard.tracking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.qemer.mwanga.R
import com.qemer.mwanga.api.ApiService
import com.qemer.mwanga.dashboard.home.HomeFragment
import com.qemer.mwanga.databinding.ActivityTracking1Binding
import com.qemer.mwanga.models.SelfCareRequest
import com.qemer.mwanga.models.SelfaCareResponse
import com.qemer.mwanga.utils.Tracking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TrackingActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityTracking1Binding
    private lateinit var selectedRating: MutableList<MutableList<Int>>
    var selfCare = 0
//
//    val retrofit = Retrofit.Builder()
//        .baseUrl("https://qemer-backend-764e0de661a5.herokuapp.com/api/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()

//    val apiService = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityTracking1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val track = binding.btnBack
        track.setOnClickListener {
            val intent = Intent(this, HomeFragment :: class.java)
            startActivity(intent)
            finish()
        }

        val tracking = binding.bntNext
        tracking.setOnClickListener {
            val intent = Intent(this, TrackingActivity2::class.java)
//            intent.putExtra("selfCare" , selfCare)
//            intent.putExtra("dailyLiving", dailyLiving)
            startActivity(intent)
            finish()
        }

        selectedRating = MutableList(4) { MutableList(5) { 0 } }

        for (cardIndex in 0 until 4) {
            for (ratingIndex in 0 until 5) {
                val ratingTextView = getRatingTextView(cardIndex, ratingIndex)
                ratingTextView.setOnClickListener {
                    // Toggle the selected state (0 or 1)
                    selectedRating[cardIndex][ratingIndex] =
                        if (selectedRating[cardIndex][ratingIndex] == 0) 1 else 0
                    setActiveRating(cardIndex, ratingIndex)
                }
            }
        }
    }
    private fun setActiveRating(cardIndex: Int, ratingIndex: Int) {
        val ratingTextView = getRatingTextView(cardIndex, ratingIndex)

        // Set background color for the selected rating
        ratingTextView.setBackgroundResource(R.drawable.circle_background)

        // Update UI for other ratings (e.g., reset their backgrounds)
        for (i in 0 until 5) {
            if (i != ratingIndex) {
                val otherRatingTextView = getRatingTextView(cardIndex, i)
                otherRatingTextView.setBackgroundResource(R.drawable.circle_background_light)
            }
        }
        updateTotalSum()
    }

    private fun getRatingTextView(cardIndex: Int, ratingIndex: Int): TextView {
        return when (cardIndex) {
            0 -> when (ratingIndex) {
                0 -> binding.tvOne
                1 -> binding.tvTwo
                2 -> binding.tvThree
                3 -> binding.tvFour
                4 -> binding.tvFive
                else -> throw IllegalArgumentException("Invalid ratingIndex for card 0")
            }

            1 -> when (ratingIndex) {
                0 -> binding.tvOne1Cooking
                1 -> binding.tvTwoCooking
                2 -> binding.tvThree1Cooking
                3 -> binding.tvFourCooking
                4 -> binding.tvFive1
                else -> throw IllegalArgumentException("Invalid ratingIndex for card 0")
            }

            2 -> when (ratingIndex) {
                0 -> binding.tvOnePlaying
                1 -> binding.tvTwoPlaying
                2 -> binding.tvThreePlaying
                3 -> binding.tvFourPlaying
                4 -> binding.tvFivePlaying
                else -> throw IllegalArgumentException("Invalid ratingIndex for card 0")
            }
            3 -> when (ratingIndex) {
                0 -> binding.tvOneWashing
                1 -> binding.tvTwoWashing
                2 -> binding.tvThreeWashing
                3 -> binding.tvFourWashing
                4 -> binding.tvFiveWashing
                else -> throw IllegalArgumentException("Invalid ratingIndex for card 0")
            }
            else -> throw IllegalArgumentException("Invalid cardIndex")
        }
    }

    private fun updateTotalSum() {
        var totalSum = 0
        var rating = IntArray(4)
        for (cardIndex in 0 until 4) {
            for (ratingIndex in 0 until 5) {
                if (selectedRating[cardIndex][ratingIndex] == 1) {
                    rating[cardIndex] = ratingIndex+1
                    totalSum += ratingIndex + 1 // Add 1 to convert from 0-based to 1-based ratings
                }
            }
        }

        Tracking.selfCare = totalSum
        binding.textView11.text = " SC = $totalSum"

        val selfCareRequest =  SelfCareRequest(
            bathing =  rating[0],
            toilet =  rating [1],
            dressing =  rating[2],
            eating =  rating[3],
            total = totalSum,
            child = 3
        )

//        val call = apiService.postSelfCareData("https://qemer-backend-764e0de661a5.herokuapp.com/api/self_care/", "application/json", "", selfCareRequest)

//        call.enqueue(object : Callback<SelfaCareResponse> {
//            override fun onResponse(call: Call<SelfaCareResponse>, response: Response<SelfaCareResponse>) {
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    Log.d("debug" , response.body().toString())
//
//
//                } else {
//                    // Handle error
//                    // You can get the error details from response.errorBody()
//                    Log.d("debug" , response.errorBody().toString())
//                }
//            }
//
//            override fun onFailure(call: Call<SelfaCareResponse>, t: Throwable) {
//            }
//        })

    }
}