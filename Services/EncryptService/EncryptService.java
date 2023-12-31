package Services.EncryptService;

import java.security.*;
import java.security.spec.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class EncryptService implements IEncryptService
{
    private static Random RNG = new SecureRandom();
    private static String CharPool = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static int ITERATIONS = 10000;
    private static int KEY_LENGTH = 256;

    @Override
    public String getSalt(int length) 
    {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) 
        {
            returnValue.append(CharPool.charAt(RNG.nextInt(CharPool.length())));
        }
        String fullSalt = returnValue.toString();
        return fullSalt;
    }

    private byte[] hash(char[] password, byte[] salt) 
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try 
        {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } 
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) 
        {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } 
        finally 
        {
            spec.clearPassword();
        }
    }

    @Override
    public String generateSecurePassword(String password, String salt) 
    {
        String returnValue = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        returnValue = Base64.getEncoder().encodeToString(securePassword);

        return returnValue;
    }

    @Override
    public boolean verifyUserPassword(String providedPassword, String securedPassword, String salt) 
    {
        boolean returnValue = false;

        String newSecurePassword = generateSecurePassword(providedPassword, salt);
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);

        return returnValue;
    }
}