package hu.study.helper.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import hu.study.model.entity.Token;

/**
 * Created by martin4955 on 2017. 06. 20..
 */
public class PasswordGenerator {
    // The higher the number of iterations the more
    // expensive computing the hash is for us and
    // also for an attacker.
    private static final int iterations = 20 * 1000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;

    /**
     * Computes a salted PBKDF2 hash of given plaintext password suitable for storing in a database. Empty passwords are not supported.
     *
     * @param password
     * @return the encoded password and the used salt with 'salt$hash' format.
     * @throws Exception
     */
    public static String getSaltedHash( final String password ) throws Exception {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        // store the salt with the password
        return Base64.getEncoder().encodeToString(salt) + "$" + hash(password, salt);
    }

    /**
     * Checks whether given plaintext password corresponds to a stored salted hash of the password.
     *
     * @param password
     * @param stored
     * @return
     * @throws Exception
     */
    public static boolean check( final String password, final String stored ) throws Exception {
        String[] saltAndPass = stored.split("\\$");
        if ( saltAndPass.length != 2 ) {
            throw new IllegalStateException("The stored password have the form 'salt$hash'");
        }
        String hashOfInput = hash(password, Base64.getDecoder().decode(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }

    /**
     * Create a token with a random string as token and 7 days as expiration date.
     *
     * @return the token object
     */
    public static Token createToken() {
        Random random = new SecureRandom();
        String tokenString = new BigInteger(130, random).toString(32);
        Token token = new Token();
        token.setToken(tokenString);
        LocalDate expirationDate = LocalDate.now().plusDays(7);
        token.setExpirationDate(java.sql.Date.valueOf(expirationDate));
        return token;
    }

    private static String hash( final String password, final byte[] salt ) throws Exception {
        if ( password == null || password.length() == 0 ) {
            throw new IllegalArgumentException("Empty passwords are not supported.");
        }
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iterations, desiredKeyLen));
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

}
