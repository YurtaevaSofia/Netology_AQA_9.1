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




    protected AuthInfo(String login, String password, String status) {
        this.login = login;
        this.password = password;
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
