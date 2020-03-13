package com.frederic.millard.android.kata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.frederic.millard.android.kata.gameoflife.GameOfLifeConsoleActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        gameOfLifeConsoleButton.setOnClickListener { startActivity(GameOfLifeConsoleActivity.getIntent(this)) }
    }
}
