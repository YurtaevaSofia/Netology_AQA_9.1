import com.github.javafaker.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class AuthInfo {

    public final String login;
    public final String password;
    public final String status;


    private static final Faker faker = new Faker(new Locale("en"));

    private static String generateLogin() {
        return faker.name().username();
    }

    private static String generatePassword() {
        return faker.internet().password();
    }

    protected AuthInfo(String status) {
        this.login = generateLogin();
        this.password = generatePassword();
        this.status = status;
    }



    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }
}
