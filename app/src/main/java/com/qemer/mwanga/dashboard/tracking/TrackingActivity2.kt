package com.qemer.mwanga.dashboard.tracking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.ActivityTracking2Binding
import com.qemer.mwanga.utils.Tracking

class TrackingActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityTracking2Binding
    private lateinit var selectedRating: MutableList<MutableList<Int>>
    var selfCare = 0
    var dailyLiving = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTracking2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        selfCare =  getIntent().getIntExtra("selfCare" , 0)
        dailyLiving = getIntent().getIntExtra("dailyLiving", 0)
        Log.d("debug", selfCare.toString())


//        fetchDailyLiving()

        val track2 = binding.bntNext
        track2.setOnClickListener {
////            val accumulatedData = intent.getSerializableExtra("accumulatedData") as? AccumulatedData
//            if (accumulatedData != null) {
//                val totalSum = updateTotalSum()
//                accumulatedData.total += totalSum

            val intent = Intent(this, TrackingActivity3::class.java)
//                intent.putExtra("accumulatedData", accumulatedData)
            startActivity(intent)
        }
//        }




        val track22 = binding.btnBack
        track22.setOnClickListener {
            val intent = Intent(this, TrackingActivity1::class.java)
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


//    private fun fetchDailyLiving() {
//        lifecycleScope.launch {
//            try {
//                val apiDailyLivingInterface: ApiDailyLivingInterface = ApiDailyLiving.buildClient(ApiDailyLivingInterface::class.java)
//
//                val request = DailyLivingrequest(
//                    chore = 3,
//                    cooking = 2,
//                    washing = 3,
//                    playing = 5,
//                    child = "1"
//                )
//
//                val response: Response<DailyLivingResponse> = apiDailyLivingInterface.postData(request)
//
//                if (response.isSuccessful) {
//                    val dailyLivingData = response.body()
//                    if (dailyLivingData != null) {
//                        val choreActivities = dailyLivingData.chore
//                        val cooking = dailyLivingData.cooking
//                        val washing = dailyLivingData.washing
//                        val playing = dailyLivingData.playing
//                        val child = dailyLivingData.child
//
//                    }
//                } else {
//                }
//            } catch (e: Exception) {
//            }
//        }
//
//    }




    private fun setActiveRating(cardIndex: Int, ratingIndex: Int) {
        val ratingTextView = getRatingTextView(cardIndex, ratingIndex)

        ratingTextView.setBackgroundResource(R.drawable.circle_background)

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
        for (cardIndex in 0 until 4) {
            for (ratingIndex in 0 until 5) {
                if (selectedRating[cardIndex][ratingIndex] == 1) {
                    totalSum += ratingIndex + 1
                }
            }
        }
        dailyLiving = totalSum

        Tracking.dailyLiving  = totalSum
        totalSum += Tracking.selfCare
        binding.textView11.text = " SC : ${Tracking.selfCare} + DL : $dailyLiving = $totalSum"
    }
}
