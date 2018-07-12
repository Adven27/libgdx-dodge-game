package com.adven.game

import com.adven.game.Config.W_HEIGHT
import com.adven.game.Config.W_WIDTH
import com.badlogic.gdx.Input.Keys.LEFT
import com.badlogic.gdx.Input.Keys.RIGHT
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle

sealed class Creature(private val bounds: Circle = Circle(0f, 0f, 0.4f)) {
    protected var x = 0f
        set(value) {
            field = value clamped (bounds.radius to W_WIDTH - bounds.radius)
            bounds.setPosition(x, y)
        }
    protected var y = 0f
        set(value) {
            field = value clamped (bounds.radius to W_HEIGHT - bounds.radius)
            bounds.setPosition(x, y)
        }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun drawDebug(renderer: ShapeRenderer) = renderer.circle(bounds)
}

class Player(bounds: Circle = Circle(0f, 0f, 0.4f), private val speed: Float = 0.2f) : Creature(bounds) {
    fun update() {
        x += when {
            RIGHT.pressed() -> speed
            LEFT.pressed() -> -speed
            else -> 0f
        }
    }
}

class Obstacle(bounds: Circle = Circle(0f, 0f, 0.3f), private var ySpeed: Float = 0.1f) : Creature(bounds) {
    fun update() {
        y -= ySpeed
    }
}