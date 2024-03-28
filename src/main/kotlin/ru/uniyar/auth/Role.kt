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
    val manageCampaigns: Boolean = false,
    // redactor
) {
    ANONYMOUS(seeCampaigns = false),
    USER(manageOwnCharacters = true),
    REDACTOR(manageCampaigns = true),
    MODERATOR(manageAllCharacters = true),
    ADMIN(manageUsers = true),
}
