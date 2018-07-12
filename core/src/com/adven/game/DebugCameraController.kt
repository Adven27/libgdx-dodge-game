package com.adven.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.JsonReader

class DebugCameraController : OrthographicCamera() {
    private val config = ConfigLoader().load()
    override fun update() {
        val delta = Gdx.graphics.deltaTime
        val moveSpeed = config.moveSpeed * delta
        val zoomSpeed = config.zoomSpeed * delta
        when {
            config.rightKey.pressed() -> position.add(moveSpeed, 0f, 0f)
            config.leftKey.pressed() -> position.add(-moveSpeed, 0f, 0f)
            config.downKey.pressed() -> position.add(0f, -moveSpeed, 0f)
            config.upKey.pressed() -> position.add(0f, moveSpeed, 0f)
            config.zoomInKey.pressed() -> zoom = (zoom + zoomSpeed) clamped config.zoomBounds
            config.zoomOutKey.pressed() -> zoom = (zoom - zoomSpeed) clamped config.zoomBounds
            config.resetKey.pressed() -> {
                zoom = 1f
                position.set(0f, 0f, 0f)
            }
        }
        super.update()
    }
}

class Props(map: Map<String, Any>) {
    val leftKey: String by map
    val rightKey: String by map
    val upKey: String by map
    val downKey: String by map
    val zoomInKey: String by map
    val zoomOutKey: String by map
    val resetKey: String by map
    val moveSpeed = map["moveSpeed"].toString().toFloat()
    val zoomSpeed = map["zoomSpeed"].toString().toFloat()
    val zoomBounds = map["maxZoomIn"].toString().toFloat() to map["maxZoomOut"].toString().toFloat()
}

const val CONFIG_PATH = "debug/debug.json"

private class ConfigLoader {
    private val log = logger<Props>()
    private val fileHandle = CONFIG_PATH.toInternalFile()
    private val defaults = mapOf(
        "moveSpeed " to 20f,
        "zoomSpeed " to 2f,
        "rightKey " to "D",
        "leftKey " to "A",
        "downKey " to "S",
        "upKey " to "W",
        "zoomInKey " to "Page Up",
        "zoomOutKey " to "Page Down",
        "resetKey " to "Home"
    )

    fun load() = Props(
        if (fileHandle.exists()) {
            try {
                JsonReader().parse(fileHandle).map { it.name to it.asString() }.toMap().apply {
                    log.info("Config $CONFIG_PATH loaded: $this")
                }
            } catch (e: Exception) {
                defaults.apply {
                    log.error("Error while loading $CONFIG_PATH. Using defaults: $this", e)
                }
            }
        } else {
            defaults.apply {
                log.info("Config $CONFIG_PATH not found. Using defaults: $this")
            }
        }
    )
}