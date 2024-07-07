package com.example.biometricauthdemo

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.google.gson.Gson
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

interface CryptographyManager {

    fun getInitializedCipherForEncryption(): Cipher

    fun getInitializedCipherForDecryption(cipherText: ByteArray): Cipher

    fun encryptData(plainText: String, cipher: Cipher): CiphertextWrapper

    fun decryptData(cipherText: ByteArray, cipher: Cipher): String


    fun persistCiphertextWrapperToSharedPreferences(
        ciphertextWrapper: CiphertextWrapper,
        context: Context,
        filename: String,
        mode: Int,
        prefKey: String,
    )

    fun getCiphertextWrapperFromSharedPreferences(
        context: Context,
        filename: String,
        mode: Int,
        prefKey: String,
    ): CiphertextWrapper?
}

fun CryptographyManager(): CryptographyManager = CryptographyManagerImpl()

private class CryptographyManagerImpl : CryptographyManager {

    private val KEY_SIZE = 256
    private val KEY_ALIAS = "MyKeyAlias"
    private val ANDROID_KEYSTORE = "AndroidKeyStore"
    private val ENCRYPTION_BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
    private val ENCYRPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
    private val ENCRYPTION_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
    private val AES_MODE = "$ENCRYPTION_ALGORITHM/$ENCRYPTION_BLOCK_MODE/$ENCYRPTION_PADDING"
    private val IV_SIZE = 16

    override fun getInitializedCipherForEncryption(): Cipher {
        val cipher = getCipher()
        val secretKey = getSecretKey()
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher
    }

    override fun getInitializedCipherForDecryption(
        cipherText: ByteArray
    ): Cipher {
        val cipher = getCipher()
        val secretKey = getSecretKey()
        val iv = cipherText.copyOfRange(0, IV_SIZE)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
        return cipher

    }

    override fun encryptData(plainText: String, cipher: Cipher): CiphertextWrapper {
        val iv = cipher.iv
        val cipherText = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        return CiphertextWrapper(iv + cipherText, iv)
    }

    override fun decryptData(cipherText: ByteArray, cipher: Cipher): String {
        val actualCipherText = cipherText.copyOfRange(IV_SIZE, cipherText.size)
        val decryptedData = cipher.doFinal(actualCipherText)
        return String(decryptedData, Charsets.UTF_8)
    }

    override fun persistCiphertextWrapperToSharedPreferences(
        ciphertextWrapper: CiphertextWrapper,
        context: Context,
        filename: String,
        mode: Int,
        prefKey: String
    ) {
        val json = Gson().toJson(ciphertextWrapper)
        context.getSharedPreferences(filename, mode).edit().putString(prefKey, json).apply()
    }

    override fun getCiphertextWrapperFromSharedPreferences(
        context: Context,
        filename: String,
        mode: Int,
        prefKey: String
    ): CiphertextWrapper? {
        val json = context.getSharedPreferences(filename, mode).getString(prefKey, null)
        return Gson().fromJson(json, CiphertextWrapper::class.java)
    }

    private fun getCipher(): Cipher {
        return Cipher.getInstance(AES_MODE)
    }

    private fun getSecretKey(): SecretKey {
        val keystore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keystore.load(null)
        keystore.getKey(KEY_ALIAS, null)?.let { return it as SecretKey }

        return generateKey()
    }

    private fun generateKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM, ANDROID_KEYSTORE)

        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(ENCRYPTION_BLOCK_MODE)
            .setEncryptionPaddings(ENCYRPTION_PADDING)
            .setKeySize(KEY_SIZE)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        return keyGenerator.generateKey()
    }

}