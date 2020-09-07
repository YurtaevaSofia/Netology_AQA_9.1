import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("en"));

    public static String generateLogin() {

        return faker.name().username();
    }

    public static String generatePassword() {

        return faker.internet().password();
    }

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void setUpUser(AuthInfo authInfo) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(authInfo) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }


    public static AuthInfo createUser(String status) {
        AuthInfo authInfo = new AuthInfo(generateLogin(), generatePassword(), status);
        setUpUser(authInfo);
        return authInfo;
    }
    public static AuthInfo notCreateUser(String status) {
        AuthInfo authInfo = new AuthInfo(generateLogin(), generatePassword(), status);
        return authInfo;
    }
}
