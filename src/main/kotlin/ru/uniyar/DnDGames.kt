package ru.uniyar

import config.AppConfig.Companion.readConfiguration
import org.http4k.server.Netty
import org.http4k.server.asServer
import ru.uniyar.auth.Users
import ru.uniyar.db.*
import ru.uniyar.utils.DBConnection
import ru.uniyar.utils.getApp

fun main() {
    DBConnection.connect()

    val user = findUserByID(3)
    if (user != null){
        println(
            "user: " +
            user.userID.toString() + " | name: " + user.userName + " | " +
            user.password + " | role: " + user.role.toString()
        )
    }

    val campaign = findCampaignByID(83)
    if (campaign != null)
        println(
            "campaign: " +
            campaign.campaignID.toString() + " | name: "
                    + campaign.campaignName + " | owner ID: "
                    + campaign.ownerID.toString()
        )

    val character = findCharacterByID(4)
    if (character != null)
        println(
            "character: " +
            character.characterID.toString() + " | name: "
            + character.name + " | class: " + character.characterClass
            + " | level: " + character.level.toString() + " | race: "
            + character.race + " | owner: " + character.userID
        )

    val post = fetchAllPostsOfOneCampaign(83)
    val str = StringBuilder()
    post.forEach {
        str.append("post: " + it.postId + " | Campaign: " + it.campaignID
                + " | author: " + it.authorID + " | text: " + it.text
                + " | visibility: " + it.visibility.toString() + " | game date: "
                + it.gameDate.toString() + " | post date: " + it.postDate.toString()
        )
    }
    println(str)



    val server =
        getApp(Users(fetchAllUsers()))
            .asServer(
                Netty(
                    readConfiguration()
                        .webConfig
                        .webPort,
                ),
            ).start()

    println("Server started on http://localhost:${server.port()}")
}
