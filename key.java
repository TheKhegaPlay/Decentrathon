package main.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.CryptoUtils;
import org.web3j.crypto.WalletUtils;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

class Key {
    String address;
    String privateKey;
}

class EncryptedKey {
    String address;
    CryptoJSON crypto;
}

class CryptoJSON {
    String cipher;
    String ciphertext;
    CipherParams cipherparams;
    String kdf;
    KDFParams kdfparams;
    String mac;
}

class CipherParams {
    String iv;
}

class KDFParams {
    int dklen;
    int n;
    int p;
    int r;
    String salt;
}

public class Main {
    public static String encryptKey(Key key, String auth, int scryptN, int scryptP) throws IOException, CipherException {
        byte[] keyBytes = key.privateKey.getBytes();
        Credentials credentials = Credentials.create(auth);
        String fileName = WalletUtils.generateWalletFile(auth, CryptoUtils.generateFullScryptParams(scryptN, scryptP), keyBytes, true);
        return WalletUtils.loadCredentials(auth, fileName).getEcKeyPair().getPrivateKey().toString(16);
    }

    public static String decryptKey(String keyJson, String auth) throws IOException, CipherException {
        ObjectMapper objectMapper = new ObjectMapper();
        EncryptedKey encryptedKey = objectMapper.readValue(keyJson, EncryptedKey.class);
        Credentials credentials = WalletUtils.loadCredentials(auth, encryptedKey.getAddress());
        return credentials.getEcKeyPair().getPrivateKey().toString(16);
    }

    public static void main(String[] args) throws IOException, CipherException {
        Key key = new Key();
        key.address = "0x1234567890abcdef";
        key.privateKey = "0123456789abcdef";

        String auth = "password123";
        int scryptN = 16384;
        int scryptP = 1;

        String encryptedKeyJson = encryptKey(key, auth, scryptN, scryptP);
        System.out.println("Encrypted Key JSON: " + encryptedKeyJson);

        String decryptedPrivateKey = decryptKey(encryptedKeyJson, auth);
        System.out.println("Decrypted Private Key: " + decryptedPrivateKey);
    }
}
