package com.cdg.skeleton.common.cipher

import org.slf4j.LoggerFactory
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.interfaces.RSAPrivateCrtKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAPublicKeySpec
import java.util.Base64

class RsaEncryptor {
    val log = LoggerFactory.getLogger(this::class.java)

    fun generateRsaPrivateKeyFromStringKey(key: String): PrivateKey? {
        return runCatching {
            val byteArray: ByteArray = Base64.getDecoder().decode(key.toByteArray())
            val privateKey = KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(byteArray))
            return privateKey
        }.onFailure { e ->
            log.error(e.message, e)
        }.getOrNull()
    }

    fun extractPublicKeyFromPrivateKey(privateKey: PrivateKey): PublicKey? {
        if (privateKey is RSAPrivateCrtKey) {
            val module = privateKey.modulus
            val publicExponent = privateKey.publicExponent

            val publicKeySpec = RSAPublicKeySpec(module, publicExponent)

            val keyFactory = KeyFactory.getInstance("RSA")
            return keyFactory.generatePublic(publicKeySpec)
        } else {
            log.error("주어진 privateKey가 RSAPrivateCrtKey가 아닙니다. 공개키를 추출할 수 없습니다.")
            return null
        }
    }

    fun convertPublicKeyToString(publicKey: PublicKey): String {
        return Base64.getEncoder().encodeToString(publicKey.encoded)
    }
}
