package io.papermc.discordlink

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class LoginListener(discordWrapper: DiscordWrapper): Listener {
    private val dw = discordWrapper

    @EventHandler
    fun onLogin(event: PlayerJoinEvent){
        val name = event.player.name
        dw.sendMessage("$name がログインしました")
    }

    @EventHandler
    fun onLogout(event: PlayerQuitEvent){
        val name = event.player.name
        dw.sendMessage("$name がログアウトしました")
    }
}