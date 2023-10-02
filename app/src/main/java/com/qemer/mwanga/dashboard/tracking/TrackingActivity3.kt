package com.qemer.mwanga.dashboard.tracking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.ActivityTracking3Binding

class TrackingActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityTracking3Binding
    private lateinit var selectedRating: MutableList<MutableList<Int>>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityTracking3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val track3 = binding.bntNext
        track3.setOnClickListener {
            val intent = Intent(this, TrackingActivity4::class.java)
            startActivity(intent)
            finish()
        }
        val track33 = binding.btnBack
        track33.setOnClickListener {
            val intent = Intent(this, TrackingActivity2::class.java)
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
            // Add cases for other cardIndexes (1, 2, 3, 4) similarly
            else -> throw IllegalArgumentException("Invalid cardIndex")
        }
    }

    private fun updateTotalSum() {
        var totalSum = 0
        for (cardIndex in 0 until 4) {
            for (ratingIndex in 0 until 5) {
                if (selectedRating[cardIndex][ratingIndex] == 1) {
                    totalSum += ratingIndex + 1 // Add 1 to convert from 0-based to 1-based ratings
                }
            }
        }
        binding.textView11.text = "SC + DL + MA = $totalSum"
    }
}