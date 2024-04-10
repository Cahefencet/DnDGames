package ru.uniyar.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import ru.uniyar.auth.Role

object Users : Table() {
    val userID = integer("user_id")
    val username = varchar("username", 50)
    val password = varchar("hashed_password", 255)
    val userRole = enumerationByName("user_role", 20, Role::class)
}

object Campaigns : Table() {
    val campaignID = integer("campaign_id")
    val campaignName = varchar("campaign_name", 100)
    val ownerId = integer("owner_id")
}

object Characters: Table() {
    val characterID = integer("character_id")
    val userID = integer("user_id")
    val name = varchar("name", 100)
    val characterClass = varchar("class", 50)
    val race = varchar("race", 50)
    val level = integer("level")
}

object CampaignUsers: Table() {
    val id = integer("id")
    val userID = integer("user_id")
    val campaignID = integer("campaign_id")
    val playerRole = enumerationByName("player_role", 20, PlayerRole::class)
}

object CampaignPosts  : Table() {
    val postId = integer("post_id")
    val campaignID = integer("campaign_id")
    val authorID = integer("author_id")
    val text = mediumText("text")
    val visibility = enumerationByName("visibility", 20, Visibility::class)
    val gameDate = date("game_date")
    val postDate = datetime("post_date")
}

enum class Visibility{
    MASTER,
    PLAYER,
    ALL,
}

enum class PlayerRole{
    MASTER,
    PLAYER,
    NONE,
}