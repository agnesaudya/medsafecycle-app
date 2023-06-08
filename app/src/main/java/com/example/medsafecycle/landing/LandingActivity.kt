package com.example.medsafecycle.landing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.medsafecycle.R

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        supportActionBar?.hide()

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val fragments = listOf(FirstLandingFragment(), SecondLandingFragment(), ThirdLandingFragment())

        val adapter = LandingPagerAdapter(supportFragmentManager, lifecycle, fragments)
        viewPager.adapter = adapter

        val nextButton: Button = findViewById(R.id.to_two)
        nextButton.setOnClickListener {

            // if Last Page
            if (viewPager.currentItem == 2){
                viewPager.currentItem = 0

            } else {
                val nextPage = viewPager.currentItem + 1
                viewPager.currentItem = nextPage
            }
        }
    }


}