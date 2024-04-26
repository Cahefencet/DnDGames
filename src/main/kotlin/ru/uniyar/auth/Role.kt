package ru.uniyar.auth

enum class Role(
    val manageUsers: Boolean = false,
    // admin
    val manageAllCharacters: Boolean = false,
    // moderator
    val manageOwnCharacters: Boolean = false,
    // user
    val manageOwnCampaigns: Boolean = false,
    // user
    val manageAllCampaigns: Boolean = false,
    // redactor
) {
    ANONYMOUS,
    USER(manageOwnCharacters = true, manageOwnCampaigns = true),
    REDACTOR(manageAllCampaigns = true),
    MODERATOR(manageAllCharacters = true),
    ADMIN(manageUsers = true),
}
