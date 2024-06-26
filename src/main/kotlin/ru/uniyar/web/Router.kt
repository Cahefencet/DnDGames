package ru.uniyar.web

import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static
import ru.uniyar.web.handlers.*

fun router() =
    routes(
        "/" bind GET to RootHandler(),
        "/Campaigns" bind GET to CampaignsHandler(),
        "/Campaigns/{campaignID}" bind GET to CampaignHandler(),
        "/Campaigns/{campaignID}/Users" bind GET to CampaignUsersHandler(),
        "/Campaigns/{campaignID}/Users" bind POST to AddPlayerHandler(),
        "/Campaigns/{campaignID}/delete" bind GET to DeleteCampaignConfirmationHandler(),
        "/Campaigns/{campaignID}/delete" bind POST to DeleteCampaignHandler(),
        "/Campaigns/{campaignID}/edit" bind GET to EditCampaignConfirmationHandler(),
        "/Campaigns/{campaignID}/edit" bind POST to EditCampaignHandler(),
        "/Campaigns-new" bind GET to NewCampaignHandler(),
        "/Campaigns-new" bind POST to CampaignCreationHandler(),
        "/Users" bind GET to UsersHandler(),
        "/Users/{userID}" bind GET to UserHandler(),
        "/Users/{userID}/delete" bind GET to DeleteUserConfirmationHandler(),
        "/Users/{userID}/delete" bind POST to DeleteUserHandler(),
        "/Registration" bind GET to RegistrationHandler(),
        "/Registration" bind POST to UserCreationHandler(),
        "/Login" bind GET to LoginFormHandler(),
        "/Login" bind POST to LoginHandler(),
        "/Logout" bind GET to LogoutHandler(),
        "/Characters" bind GET to CharactersHandler(),
        "/Characters/{characterID}" bind GET to CharacterHandler(),
        "/Characters-new" bind GET to NewCharacterHandler(),
        "/Characters-new" bind POST to CharacterCreationHandler(),
        "/Characters/{characterID}/delete" bind GET to DeleteCharacterConfirmationHandler(),
        "/Characters/{characterID}/delete" bind POST to DeleteCharacterHandler(),
        "/Characters/{characterID}/edit" bind GET to EditCharacterConfirmationHandler(),
        "/Characters/{characterID}/edit" bind POST to EditCharacterHandler(),
        "/Post/{campaignID}" bind GET to NewPostHandler(),
        "/Post/{campaignID}" bind POST to PostCreationHandler(),
        "/Posts/{postID}/delete" bind GET to DeletePostConfirmationHandler(),
        "/Posts/{postID}/delete" bind POST to DeletePostHandler(),
        "/Posts/{campaignID}/edit/{postID}" bind GET to EditPostConfirmationHandler(),
        "/Posts/{campaignID}/edit/{postID}" bind POST to EditPostHandler(),
        "/Kick/{campaignID}/{userID}" bind GET to KickUserFromCampaignConfirmationHandler(),
        "/Kick/{campaignID}/{userID}" bind POST to KickUserFromCampaignHandler(),
        "/Choose/{campaignID}/{userID}" bind GET to ShowCharactersToChooseHandler(),
        "/Choose/{campaignID}/{userID}" bind POST to ChooseCharacterHandler(),
        "/Administrators" bind GET to AdministrationHandler(),
        "/Administrators/new" bind GET to AdminRegistrationHandler(),
        "/Administrators/new" bind POST to AdminCreationHandler(),
        "/DeleteMePlease" bind GET to SelfDeleteConfirmationHandler(),
        "/DeleteMePlease" bind POST to SelfDeleteHandler(),
        static(ResourceLoader.Classpath("ru/uniyar/public")),
    )
