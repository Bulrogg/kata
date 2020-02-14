package com.frederic.millard.android.kata.gameoflife.repository

import com.frederic.millard.android.kata.gameoflife.core.World

class WorldRepository {

    private var world: World? = null


    fun storeWorld(world: World) {
        this.world = world
    }

    fun getStoredWorld() = world

}