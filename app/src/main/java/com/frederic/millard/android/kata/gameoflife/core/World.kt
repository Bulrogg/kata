package com.frederic.millard.android.kata.gameoflife.core

data class World(
    val rowCount: Int,
    val colCount: Int,
    val generationCount: Int,
    val cells: List<List<Boolean>>
)