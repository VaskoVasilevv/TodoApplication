package todoapp.services.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Auth {

  public static boolean isAuthorized(String password, String userDetail) {

    BCrypt.Result verify = BCrypt.verifyer().verify(userDetail.toCharArray(),password);
    return verify.verified;
  }
}
