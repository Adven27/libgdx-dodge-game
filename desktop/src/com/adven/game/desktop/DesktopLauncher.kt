package com.adven.game.desktop

import com.adven.game.Config
import com.adven.game.DodgeGame
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

fun main(args: Array<String>) {
    val cfg = LwjglApplicationConfiguration()
    cfg.width = Config.WIDTH
    cfg.height = Config.HEIGHT
    LwjglApplication(DodgeGame(), cfg)
}
