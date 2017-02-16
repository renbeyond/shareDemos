package com.example.demo.peng.function.fingerdetect.crypto;

import android.hardware.fingerprint.FingerprintManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/// <summary>
/// Sample code for building a CryptoWrapper
/// </summary>
public class CryptoObjectHelper
{
    // This can be key name you want. Should be unique for the app.
    static final String KEY_NAME = "com.createchance.android.sample.fingerprint_authentication_key";

    // We always use this keystore on Android.
    static final String KEYSTORE_NAME = "AndroidKeyStore";

    // Should be no need to change these values.
    static final String KEY_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;
    static final String BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC;
    static final String ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7;
    static final String TRANSFORMATION = KEY_ALGORITHM + "/" +
    BLOCK_MODE + "/" +
    ENCRYPTION_PADDING;
    final KeyStore _keystore;
    Cipher cipher;

    public CryptoObjectHelper() throws Exception
    {
        _keystore = KeyStore.getInstance(KEYSTORE_NAME);
        _keystore.load(null);
    }

    public FingerprintManager.CryptoObject buildCryptoObject() throws Exception
    {
        Cipher cipher = createCipher(true);
        return new FingerprintManager.CryptoObject(cipher);
    }

    Cipher createCipher(boolean retry) throws Exception
    {
        Key key = GetKey();
        cipher = Cipher.getInstance(TRANSFORMATION);
        initCipher();
        return cipher;
    }

    Key GetKey() throws Exception
    {
        Key secretKey;
        if(!_keystore.isKeyEntry(KEY_NAME))
        {
            CreateKey();
        }

        secretKey = _keystore.getKey(KEY_NAME, null);
        return secretKey;
    }

    void CreateKey() throws Exception
    {
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM, KEYSTORE_NAME);
        KeyGenParameterSpec keyGenSpec =
                new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(BLOCK_MODE)
                        .setEncryptionPaddings(ENCRYPTION_PADDING)
                        .setUserAuthenticationRequired(true)
                        .build();
        keyGen.init(keyGenSpec);
        keyGen.generateKey();
    }

    public boolean initCipher() {
        try {
            _keystore.load(null);
            SecretKey key = (SecretKey) _keystore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }
}