package ru.uniyar.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.Calendar

class JwtTools(
    private val secret: String,
    private val issue: String,
    private val lifetime: Int,
) {
    private val algorithm: Algorithm = Algorithm.HMAC512(secret)
    private val verifier: JWTVerifier =
        JWT.require(algorithm)
            .withIssuer(issue)
            .build()

    fun createUserJwt(userId: String): String {
        return try {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, lifetime)
            val token =
                JWT.create()
                    .withIssuer(issue)
                    .withExpiresAt(calendar.time)
                    .withSubject(userId)
                    .sign(algorithm)
            token
        } catch (exception: JWTCreationException) {
            ""
        }
    }

    fun verifyToken(token: String): String? {
        return try {
            val decodedJWT: DecodedJWT = verifier.verify(token)
            decodedJWT.subject
        } catch (exception: JWTVerificationException) {
            null
        }
    }
}
