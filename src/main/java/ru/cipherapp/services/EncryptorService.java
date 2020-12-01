package ru.cipherapp.services;

import org.apache.tomcat.util.codec.binary.Base64;
import ru.cipherapp.models.EncryptKey;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/*
encryption service which uses crypto.Cipher standard library
 */

public class EncryptorService {

    //custom logic for encryptor
    private static String initVector = "random_salt12345";

    public static String encrypt(String value, EncryptKey encryptKey) {

        checkKey(encryptKey);

        String key = encryptKey.getKey();
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return "problem with encryption";
    }

    //validating key length (must be of size 16)
    private static EncryptKey checkKey(EncryptKey encryptKey) {

        if(encryptKey.getKey().length() < 16){
            while(encryptKey.getKey().length() < 16 ){
                encryptKey.setKey(encryptKey.getKey()+1);
            }
        }

        if(encryptKey.getKey().length() > 16){
            encryptKey.setKey(encryptKey.getKey().substring(0, 16));
        }

        return encryptKey;
    }


    public static String decrypt(String value, EncryptKey encryptKey){

        checkKey(encryptKey);

        String key = encryptKey.getKey();

        try{
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

            return new String(cipher.doFinal(java.util.Base64.getDecoder().decode(value)));

        } catch (Exception e){
            System.err.println(e.toString());
        }
        return "problem with decryption";
    }

}
