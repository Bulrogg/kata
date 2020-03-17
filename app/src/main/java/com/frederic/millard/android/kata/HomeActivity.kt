package com.frederic.millard.android.kata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.frederic.millard.android.kata.gameoflife.GameOfLifeConsoleActivity
import com.frederic.millard.android.kata.gameoflife.GameOfLifeCustomViewActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        gameOfLifeConsoleButton.setOnClickListener { startActivity(GameOfLifeConsoleActivity.getIntent(this)) }
        gameOfLifeCustomViewButton.setOnClickListener { startActivity(GameOfLifeCustomViewActivity.getIntent(this)) }
    }
}
