package ru.uniyar.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLException


fun editCampaignName(campaignId: Int, newName: String) {
    try {
        return transaction {
            Campaigns.update ({
                (Campaigns.campaignID eq campaignId)
            }) {
                it[Campaigns.campaignName] = newName
            }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun addCharToCampaign(characterId: Int, userId: Int, campaignId: Int) {
    try {
        return transaction {
            CampaignUsers.update({
                (CampaignUsers.userID eq userId)
                    .and (CampaignUsers.campaignID eq campaignId)
            }) {
                it[CampaignUsers.characterID] = characterId
            }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun insertCharacter(character: Character) {
    try {
        return transaction {
            exec("SET FOREIGN_KEY_CHECKS=0")
            Characters.insert {
                it[userID] = character.userID
                it[name] = character.name
                it[characterClass] = character.characterClass
                it[race] = character.characterClass
                it[level] = character.level
            }
            exec("SET FOREIGN_KEY_CHECKS=1")
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun insertPost(campaignPost: CampaignPost){
    try {
        return transaction {
            exec("SET FOREIGN_KEY_CHECKS=0")
            CampaignPosts.insert {
                it[campaignID] = campaignPost.campaignID
                it[authorID] = campaignPost.authorID
                it[text] = campaignPost.text
                it[visibility] = campaignPost.visibility
                it[gameDate] = campaignPost.gameDate
                it[postDate] = campaignPost.postDate
            }
            exec("SET FOREIGN_KEY_CHECKS=1")
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun insertPlayer(userId: Int, campaignId: Int){
    try {
        return transaction {
            if (!isUserAlreadyInCampaign(userId, campaignId)) {
                CampaignUsers.insert {
                    it[userID] = userId
                    it[campaignID] = campaignId
                    it[playerRole] = PlayerRole.PLAYER
                    it[characterID] = null
                }
            }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

private fun isUserAlreadyInCampaign(userId: Int, campaignId: Int) : Boolean {
    try {
        return transaction {
            CampaignUsers
                .selectAll()
                .where {
                    (CampaignUsers.userID eq userId)
                        .and(CampaignUsers.campaignID eq campaignId)
                }.count() > 0
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun insertUser(user: User) {
    try {
        return transaction {
            if (findUserByName(user.userName) == null)
                Users.insert {
                    it[username] = user.userName
                    it[password] = user.password
                    it[userRole] = user.role
                }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun insertCampaign(campaign: Campaign) {
    try {
        return transaction {
            Campaigns.insert {
                it[campaignName] = campaign.campaignName
                it[ownerId] = campaign.ownerID
            }
            val curID = findCampIDByOwnerAndName(campaign.ownerID, campaign.campaignName)!!
            // !! используется, т.к. кампанию мы только что создали, значит, она точно есть
            campaignCreation(curID, campaign.ownerID)
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

private fun campaignCreation(campID: Int, usrID : Int) {
    try {
        return transaction {
            exec("SET FOREIGN_KEY_CHECKS=0")
            CampaignUsers.insert {
                it[campaignID] = campID
                it[userID] = usrID
                it[playerRole] = PlayerRole.MASTER
            }
            exec("SET FOREIGN_KEY_CHECKS=1")
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun deleteCharacterByID(id: Int) {
    try {
        return transaction {
            Characters.deleteWhere {
                Characters.characterID eq id
            }
            deleteFromCampaignUsersByCharID(id)
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

private fun deleteFromCampaignUsersByCharID(characterID : Int) {
    try {
        return transaction {
            CampaignUsers.deleteWhere {
                (CampaignUsers.characterID eq characterID)
            }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun deletePostByID(id: Int) {
    try {
        return transaction {
            CampaignPosts.deleteWhere {
                CampaignPosts.postID eq id
            }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun deleteCampaignByID(id: Int) {
    try {
        return transaction {
            deleteAllPostsByCampaignID(id)
            deleteFromCampaignUsersByCampaignID(id)
            Campaigns.deleteWhere {
                Campaigns.campaignID eq id
            }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

private fun deleteFromCampaignUsersByCampaignID(id: Int) {
    try {
        return transaction {
            CampaignUsers.deleteWhere {
                CampaignUsers.campaignID eq id
            }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

private fun deleteAllPostsByCampaignID(id: Int) {
    try {
        return transaction {
            CampaignPosts.deleteWhere {
                CampaignPosts.campaignID eq id
            }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun deleteUserById(id: Int) {
    try {
        return transaction {
            Users.deleteWhere {
                Users.userID eq id
            }
            deleteFromCampaignUsersByUserID(id)
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

private fun deleteFromCampaignUsersByUserID(id : Int) {
    try {
        return transaction {
            CampaignUsers.deleteWhere {
                CampaignUsers.userID eq id
            }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun deleteFromCampaignUsersByUserIDCampaignID(userId: Int, campId: Int) {
    try {
        return transaction {
            CampaignUsers.deleteWhere {
                (CampaignUsers.userID eq userId)
                    .and(CampaignUsers.campaignID eq campId)
                    .and(CampaignUsers.playerRole eq PlayerRole.PLAYER)
            }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}


fun findPostByID(id : Int) : CampaignPost? {
    try {
        return transaction {
            CampaignPosts
                .selectAll()
                .where {
                    CampaignPosts.postID eq id
                }.firstOrNull()
                ?.let {
                    CampaignPost(
                        it[CampaignPosts.postID],
                        it[CampaignPosts.campaignID],
                        it[CampaignPosts.authorID],
                        it[CampaignPosts.text],
                        it[CampaignPosts.visibility],
                        it[CampaignPosts.gameDate],
                        it[CampaignPosts.postDate],
                    )
                }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun findMasterIDByCampID(campID: Int) : Int? {
    try {
        return transaction {
            CampaignUsers
                .selectAll()
                .where {
                    (CampaignUsers.campaignID eq campID)
                        .and(CampaignUsers.playerRole eq PlayerRole.MASTER)
                }.firstOrNull()
                ?.get(CampaignUsers.userID)
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun findCampIDByPostID(id : Int) : Int? {
    try {
        return transaction {
            CampaignPosts
                .selectAll()
                .where {
                    CampaignPosts.postID eq id
                }.firstOrNull()
                ?.get(CampaignPosts.campaignID)
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun findCampIDByOwnerAndName(ownerID: Int, name: String) : Int? {
    try {
        return transaction {
            Campaigns
                .selectAll()
                .where {
                    (Campaigns.ownerId eq ownerID)
                        .and(Campaigns.campaignName eq name)
                }.firstOrNull()
                ?.get(Campaigns.campaignID)
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}


fun findCharacterIDByNameUserClassRaceLevel(character: Character) : Int? {
    try {
        return transaction {
            Characters
                .selectAll()
                .where {
                    (Characters.userID eq character.userID)
                        .and(Characters.name eq character.name)
                        .and(Characters.characterClass eq character.characterClass)
                        .and(Characters.level eq character.level)
                        .and(Characters.race eq character.race)
                }.firstOrNull()?.get(Characters.characterID)
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun findCharactersByUserID(id: Int): List<Character> {
    try {
        return transaction {
            Characters
                .selectAll()
                .where {
                    Characters.userID eq id
                }
                .map {
                    Character(
                        it[Characters.characterID],
                        it[Characters.userID],
                        it[Characters.name],
                        it[Characters.characterClass],
                        it[Characters.race],
                        it[Characters.level]
                    )
                }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun findCharacterByID(id: Int) : Character? {
    try {
        return transaction {
            Characters
                .selectAll()
                .where {
                    Characters.characterID eq id
                }
                .firstOrNull()?.let {
                    Character(
                        characterID = it[Characters.characterID],
                        userID = it[Characters.userID],
                        name = it[Characters.name],
                        characterClass = it[Characters.characterClass],
                        race = it[Characters.race],
                        level = it[Characters.level],
                    )
                }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun findUserByID(id: Int) : User? {
    try {
        return transaction {
            Users.selectAll()
                .where {
                    Users.userID eq id
                }
                .firstOrNull()?.let {
                    User(
                        userID = it[Users.userID],
                        userName = it[Users.username],
                        password = it[Users.password],
                        role = it[Users.userRole],
                    )
                }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun findUserByName(name: String) : User? {
    try {
        return transaction {
            Users.selectAll()
                .where {
                Users.username eq name
                }
                .firstOrNull()?.let {
                    User(
                        userID = it[Users.userID],
                        userName = it[Users.username],
                        password = it[Users.password],
                        role = it[Users.userRole],
                    )
                }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun findCampaignByID(id : Int) : Campaign? {
    try {
        return transaction {
            Campaigns.selectAll()
                .where {
                    Campaigns.campaignID eq id
                }
                .firstOrNull()?.let {
                Campaign(
                    campaignID = it[Campaigns.campaignID],
                    campaignName = it[Campaigns.campaignName],
                    ownerID = it[Campaigns.ownerId],
                )
            }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun fetchAllPostsOfOneCampaign(campaignID: Int): List<CampaignPost> {
    try {
        return transaction {
            CampaignPosts
                .selectAll()
                .where {
                    CampaignPosts.campaignID eq campaignID
                }
                .map {
                    row ->
                    CampaignPost(
                        row[CampaignPosts.postID],
                        row[CampaignPosts.campaignID],
                        row[CampaignPosts.authorID],
                        row[CampaignPosts.text],
                        row[CampaignPosts.visibility],
                        row[CampaignPosts.gameDate],
                        row[CampaignPosts.postDate],
                    )
                }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun fetchAllCharacters(): List<Character> {
    try {
        return transaction {
            Characters
                .selectAll()
                .map {
                    row ->
                    Character(
                        row[Characters.characterID],
                        row[Characters.userID],
                        row[Characters.name],
                        row[Characters.characterClass],
                        row[Characters.race],
                        row[Characters.level],
                    )
                }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun fetchAllUsers(): List<User> {
    try {
        return transaction {
            Users
                .selectAll()
                .map {
                    row ->
                    User(
                        row[Users.userID],
                        row[Users.username],
                        row[Users.password],
                        row[Users.userRole],
                    )
            }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun fetchAllPlayersByCampaignID(campID: Int) : MutableList<CampaignUser> {
    try {
     return transaction {
         CampaignUsers
             .selectAll()
             .where {
                 (CampaignUsers.campaignID eq campID)
                     .and(CampaignUsers.playerRole eq PlayerRole.PLAYER)
             }.map {
                 row ->
                 CampaignUser(
                     row[CampaignUsers.id],
                     row[CampaignUsers.userID],
                     row[CampaignUsers.campaignID],
                     row[CampaignUsers.playerRole],
                     row[CampaignUsers.characterID],
                 )
             }.toMutableList()
     }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun fetchAllCampaigns() : MutableList<Campaign> {
    try {
        return transaction {
            Campaigns.selectAll().map { row ->
                Campaign(
                    row[Campaigns.campaignID],
                    row[Campaigns.campaignName],
                    row[Campaigns.ownerId],
                )
            }.toMutableList()
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}
