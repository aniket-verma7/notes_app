package com.project.notesapp.encoder_decoder;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordEncoderDecoder {
   public static String encrypt(String password) throws Exception {
       SecretKeySpec key = generateKey("CDj$vZg$*doDmBTpq@x`b\"}JZnJv5/");
       Cipher cipher = Cipher.getInstance("AES");
       cipher.init(Cipher.ENCRYPT_MODE,key);
       byte[] passwordByte = cipher.doFinal(password.getBytes());
       return Base64.encodeToString(passwordByte,Base64.DEFAULT);
   }

   public static String decrypt(String password) throws Exception{
       SecretKeySpec key = generateKey("CDj$vZg$*doDmBTpq@x`b\"}JZnJv5/");
       Cipher cipher = Cipher.getInstance("AES");
       cipher.init(Cipher.DECRYPT_MODE,key);
       byte[] decodedValue = Base64.decode(password,Base64.DEFAULT);
       byte[] decValue = cipher.doFinal(decodedValue);
       return new String(decValue);
   }

    private static SecretKeySpec generateKey(String password) throws NoSuchAlgorithmException {
       final MessageDigest digest = MessageDigest.getInstance("SHA-256");
       byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
       digest.update(bytes,0,bytes.length);
       byte[] key = digest.digest();
       return new SecretKeySpec(key,"AES");
    }

}
