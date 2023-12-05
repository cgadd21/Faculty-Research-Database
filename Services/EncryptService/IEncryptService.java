package Service.EncryptService;

import java.security.*;
import java.util.*;
import javax.crypto.*;

public interface IEncryptService
{
    String getSalt(int length);
    String generateSecurePassword(String password, String salt);
    boolean verifyUserPassword(String providedPassword, String securedPassword, String salt);
}