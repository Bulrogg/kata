package com.frederic.millard.android.kata.gameoflife

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.LayoutParams
import android.widget.LinearLayout.VERTICAL
import androidx.appcompat.app.AppCompatActivity
import com.frederic.millard.android.kata.R
import com.frederic.millard.android.kata.gameoflife.controller.GameOfLifeController
import com.frederic.millard.android.kata.gameoflife.core.GameOfLifeInteractor
import com.frederic.millard.android.kata.gameoflife.core.LivingCellAroundCalculator
import com.frederic.millard.android.kata.gameoflife.core.NextGenerationCalculator
import com.frederic.millard.android.kata.gameoflife.core.World
import com.frederic.millard.android.kata.gameoflife.presentation.GameOfLifeDisplay
import com.frederic.millard.android.kata.gameoflife.presentation.GameOfLifePresenter
import com.frederic.millard.android.kata.gameoflife.repository.WorldRepository
import kotlinx.android.synthetic.main.activity_game_of_life_custom_view.*


class GameOfLifeCustomViewActivity : AppCompatActivity(), GameOfLifeDisplay {

    companion object {
        fun getIntent(context: Context) = Intent(context, GameOfLifeCustomViewActivity::class.java)
        private const val CELL_WIDTH_PX = 30
        private const val CELL_HEIGHT_PX = 30
    }

    private lateinit var controller: GameOfLifeController

    private val cellsMap: MutableMap<Pair<Int, Int>, CellView> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_of_life_custom_view)

        startButton.setOnClickListener { controller.start() }
        pauseButton.setOnClickListener { controller.stop() }

        injectObject()

        initDynamicWorld()
    }

    private fun initDynamicWorld() {
        worldContent.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                worldContent.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val rowCount = worldContent.height / CELL_HEIGHT_PX
                val colCount = worldContent.width / CELL_WIDTH_PX
                initWorldDisplay(rowCount, colCount)
                controller.initWorld(rowCount, colCount)
                controller.changeGenerationTimeValue(700L)
            }
        })
    }

    private fun initWorldDisplay(rowCount: Int, colCount: Int) {
        val rowLinearLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = VERTICAL
        }
        for (row in 0 until rowCount) {
            val colLinearLayout = LinearLayout(this).apply {
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                orientation = HORIZONTAL
                for (col in 0 until colCount) {
                    val cellView = initializeCellView(row, col)
                    cellsMap[row to col] = cellView
                    addView(cellView)
                }
            }
            rowLinearLayout.addView(colLinearLayout)
        }
        worldContent.addView(rowLinearLayout)
    }

    private fun initializeCellView(row: Int, col: Int): CellView {
        val cellView = CellView(this@GameOfLifeCustomViewActivity)
        cellView.isActivated = false
        cellView.setOnClickListener { controller.toggleCell(row, col) }
        return cellView
    }

    private fun injectObject() {
        val repository = WorldRepository()
        val presenter = GameOfLifePresenter(this)
        val nextGenerationCalculator = NextGenerationCalculator(LivingCellAroundCalculator())
        val interactor = GameOfLifeInteractor(presenter, repository, nextGenerationCalculator)
        controller = GameOfLifeController(interactor)
    }

    override fun displayWorld(world: World) {
        Handler(Looper.getMainLooper()).post {
            with(world) {
                generationTextView.text = generationCount.toString()
                for (row in cells.indices) {
                    val colCells = cells[row]
                    for (col in colCells.indices) {
                        cellsMap[row to col]?.isActivated = colCells[col]
                    }
                }
            }
        }
    }

    class CellView(context: Context) : FrameLayout(context) {
        init {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.view_cell, this, true)
            layoutParams = LayoutParams(CELL_WIDTH_PX, CELL_HEIGHT_PX)
        }
    }
}