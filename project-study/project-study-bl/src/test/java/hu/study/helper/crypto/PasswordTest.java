package hu.study.helper.crypto;

import hu.study.model.entity.Token;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by martin4955 on 2017. 06. 20..
 */
public class PasswordTest {

    @Test
    public void generatedPwdSouldBeTheSame() throws Exception {
        String pwd = "Almafa1234";
        String hashedPassword = PasswordGenerator.getSaltedHash(pwd);
        boolean isTheSame = PasswordGenerator.check(pwd, hashedPassword);
        assertThat(isTheSame, is(true));
    }

    @Test
    public void generatedTokenShouldContaindRandomString() {
        Token token = PasswordGenerator.createToken();
        assertThat(token.getToken(), is(notNullValue()));
    }

    @Test
    public void expirationDateShouldBeSevenDaysLater() {
        Date date = Date.valueOf(LocalDate.now().plusDays(7));
        Token token = PasswordGenerator.createToken();
        assertThat(token.getExpirationDate(), is(equalTo(date)));
    }

}
