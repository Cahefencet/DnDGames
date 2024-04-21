package ru.uniyar.db

import ru.uniyar.auth.Role
import java.time.LocalDate
import java.time.LocalDateTime

data class User(
    val userID: Int,
    val userName: String,
    val password: String,
    val role: Role,
)

data class Campaign(
    val campaignID: Int,
    val campaignName: String,
    val ownerID: Int,
)

data class Character(
    val characterID: Int,
    val userID: Int,
    val name: String,
    val characterClass: String,
    val race: String,
    val level: Int,
)

data class CampaignUser(
    val id: Int,
    val userID: Int,
    val campaignID: Int,
    val playerRole: PlayerRole,
    val characterID: Int?,
)

data class CampaignPost(
    val postId: Int,
    val campaignID: Int,
    val authorID: Int,
    val text: String,
    val visibility: Visibility,
    val gameDate: LocalDate,
    val postDate: LocalDateTime,
)


data class UserCharacter(val user : User, val player: CampaignUser, val character: Character?)
