package com.adven.game

import com.badlogic.gdx.utils.JsonReader
import org.junit.Test

class PropsTest {

    @Test
    fun name() {
        val value = JsonReader().parse(ClassLoader.getSystemResource(CONFIG_PATH).openStream())
        println(Props(value.map { it.name to it.asString() }.toMap()).downKey)
    }
}

