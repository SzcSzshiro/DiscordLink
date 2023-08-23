package io.papermc.discordlink

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {
    private val discordWrapper = DiscordWrapper()

    override fun onEnable() {
        val pm = Bukkit.getPluginManager()
        pm.registerEvents(LoginListener(discordWrapper), this)
    }
}