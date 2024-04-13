package ru.uniyar

import config.AppConfig.Companion.readConfiguration
import org.http4k.server.Netty
import org.http4k.server.asServer
import ru.uniyar.auth.Users
import ru.uniyar.db.fetchAllUsers
import ru.uniyar.db.findCampaignByID
import ru.uniyar.db.findUserByID
import ru.uniyar.utils.DBConnection
import ru.uniyar.utils.getApp

fun main() {
    DBConnection.connect()

//    val user = findUserByID(3)
//    if (user != null){
//        println(
//            "user: " +
//            user.userID.toString() + " | name: " + user.userName + " | " +
//            user.password + " | role: " + user.role.toString()
//        )
//    }
//    val campaign = findCampaignByID(1)
//    if (campaign != null)
//        println(
//            "campaign: " +
//            campaign.campaignID.toString() + " | name: "
//                    + campaign.campaignName + " | owner ID: "
//                    + campaign.ownerID.toString()
//        )
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
