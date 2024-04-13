package ru.uniyar.auth

enum class Role(
    val manageUsers: Boolean = false,
    // admin
    val seeCampaigns: Boolean = true,
    // all but anon
    val manageOwnCharacters: Boolean = false,
    // user
    val manageAllCharacters: Boolean = false,
    // moderator
    val manageOwnCampaigns: Boolean = false,
    // user
    val manageAllCampaigns: Boolean = false,
    // redactor
) {
    ANONYMOUS(seeCampaigns = false),
    USER(manageOwnCharacters = true, manageOwnCampaigns = true),
    REDACTOR(manageAllCampaigns = true),
    MODERATOR(manageAllCharacters = true),
    ADMIN(manageUsers = true),
}
