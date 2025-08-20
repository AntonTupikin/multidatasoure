package com.example.multidatasoure.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordGeneratorUtils {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";

    private static String generate(int length, String alphabet) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(alphabet.charAt(RANDOM.nextInt(alphabet.length())));
        }
        return returnValue.toString();
    }

    public static String generatePassword(int length) {
        return generate(length, ALPHABET);
    }

    public static String generateEmailVerificationCode(int length) {
        return generate(length, NUMBERS);
    }
}
