package com.frederic.millard.android.kata.gameoflife.core

data class World(
    val height: Int,
    val width: Int,
    val generationCount: Int,
    val cells: List<List<Cell>>
)

data class Cell(val isLiving: Boolean)

//data class WorldRules(val bornRule: List<Int>, val dieRule: List<Int>)