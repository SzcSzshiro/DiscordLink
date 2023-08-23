package io.papermc.discordlink

import com.google.gson.Gson
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.*
import java.util.logging.Level
import java.util.logging.Logger

class DiscordWrapper {
    private val jda: JDA
    private val textChannel: TextChannel

    init {
        val discordData = loadDiscordData()
        val token = discordData.botToken
        val channelID = discordData.channelId.toLong()

        jda = JDABuilder
            .createDefault(token, GatewayIntent.GUILD_MESSAGES)
            .setRawEventsEnabled(true)
            .setActivity(Activity.playing("minecraft"))
            .build()
        jda.awaitReady()
        textChannel = jda.getTextChannelById(channelID)
            ?: throw RuntimeException("Text channel is not found.")
    }

    fun sendMessage(msg: String){
        kotlin.runCatching {
            textChannel.sendMessage(msg).queue()
        }.onFailure {
            Logger.getLogger(this.javaClass.name).log(Level.WARNING, it.toString())
        }
    }

    private fun loadDiscordData(): DiscordData {
        var br: BufferedReader? = null
        var str = ""
        try {
            val inst = javaClass.getResourceAsStream("/discord.json")!!
            br = BufferedReader(InputStreamReader(inst))
            str = br.readLines().joinToString("")
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            br?.close()
        }
        return Gson().fromJson(str, DiscordData::class.java)
    }
}