package com.adven.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

class GameScreen : Screen {
    private lateinit var viewport: Viewport
    private lateinit var camera: DebugCameraController
    private lateinit var renderer: ShapeRenderer
    private lateinit var player: Player
    private var obstacleTimer = 0f
    private var obstacles = GdxArray<Obstacle>()

    override fun show() {
        camera = DebugCameraController()
        camera.position.set(Config.W_CENTER_X, Config.W_CENTER_Y, 0f)

        viewport = FitViewport(Config.W_WIDTH, Config.W_HEIGHT, camera)
        renderer = ShapeRenderer()

        player = Player()
        player.setPosition(Config.W_CENTER_X, 1f)
    }

    override fun render(delta: Float) {
        player.update()
        camera.update()

        obstacles.update()
        obstacles.spawn(Gdx.graphics.deltaTime)

        clearScreen()

        renderer.projectionMatrix = camera.combined

        renderer.draw {
            player.drawDebug(this)
            obstacles.forEach { it.drawDebug(this) }
        }

        viewport.drawGrid(renderer)
    }

    override fun hide() = dispose()
    override fun pause() {}
    override fun resume() {}
    override fun resize(width: Int, height: Int) = viewport.update(width, height, true)
    override fun dispose() = renderer.dispose()

    private fun GdxArray<Obstacle>.update() = forEach { it.update() }
    private fun GdxArray<Obstacle>.spawn(delta: Float) {
        obstacleTimer += delta
        if (obstacleTimer > Config.OBSTACLE_SPAWN_TIME) {
            obstacleTimer = 0f
            add(Obstacle().apply {
                setPosition(MathUtils.random(0f, Config.W_WIDTH), Config.W_HEIGHT)
            })
        }
    }
}

