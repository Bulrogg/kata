package com.frederic.millard.android.kata.gameoflife.core

data class World(
    val height: Int,
    val width: Int,
    val generationCount: Int,
    val cells: List<List<Boolean>>
)