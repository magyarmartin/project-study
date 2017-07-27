package hu.study.helper.crypto;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by martin4955 on 2017. 06. 20..
 */
public class PasswordTest {

    @Test
    public void generatedPwdSouldBeTheSame() throws Exception {
        String pwd = "Almafa1234";
        PasswordGenerator pwdGen = new PasswordGenerator();
        String hashedPassword = pwdGen.getSaltedHash(pwd);
        boolean isTheSame = pwdGen.check(pwd, hashedPassword);
        assertThat(isTheSame, is(true));
    }

}
