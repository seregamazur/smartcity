package com.smartcity.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {

    private static Logger logger = LoggerFactory.getLogger(EncryptionUtil.class);

    public static String encryptPassword(String plainTextPassword) {

        if (plainTextPassword == null) {
            return null;
        }

        String algorithm = "SHA";
        byte[] plainText = plainTextPassword.getBytes();

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException e) {
            logger.error("Password encrypt exception. Message: {}", e.getMessage());
            return null;
        }

        md.reset();
        md.update(plainText);
        byte[] encodedPassword = md.digest();
        StringBuilder encryptedPassword = new StringBuilder();

        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                encryptedPassword.append("0");
            }

            encryptedPassword.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }

        return encryptedPassword.toString();
    }
}
