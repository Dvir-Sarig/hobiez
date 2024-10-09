package services

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import users.User
import javax.crypto.SecretKey

class TokenService {
    companion object{
        val SECRET_KEY: SecretKey = Keys.hmacShaKeyFor("LweG8rf27vX8wYdK9sHwPzQdRtZ7Vm5R".toByteArray())
    }

    fun generateToken(user: User): String {
        return Jwts.builder()
            .setSubject(user.id.toString())
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact()
    }
}

