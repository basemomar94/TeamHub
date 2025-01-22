package org.zayn.teamhub.core.services

interface ISessionManager {

    fun putUserId(id: String)
    fun getUserId(): String?
}