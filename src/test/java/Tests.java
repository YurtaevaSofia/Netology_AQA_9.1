import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;


public class Tests {

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

    @Test
    void shouldLoginWhenActive() {
        AuthInfo authInfo = new AuthInfo("active");
        setUpUser(authInfo);
        open("http://localhost:9999");
        $("[data-test-id=login] input").sendKeys(authInfo.login);
        $("[data-test-id=password] input").sendKeys(authInfo.password);
        $("button[data-test-id=action-login]").click();
    }

    @Test
    void shouldNotLoginWhenBlocked() {
        AuthInfo authInfo = new AuthInfo("blocked");
        setUpUser(authInfo);
        open("http://localhost:9999");
        $("[data-test-id=login] input").sendKeys(authInfo.login);
        $("[data-test-id=password] input").sendKeys(authInfo.password);
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldNotLoginWhenWrongPassword() {
        Faker faker = new Faker(new Locale("en"));
        AuthInfo authInfo = new AuthInfo("blocked");
        setUpUser(authInfo);
        String wrongPassword = faker.internet().password();
        open("http://localhost:9999");
        $("[data-test-id=login] input").sendKeys(authInfo.login);
        $("[data-test-id=password] input").sendKeys(wrongPassword);
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginWhenWrongLogin() {
        Faker faker = new Faker(new Locale("en"));
        AuthInfo authInfo = new AuthInfo("blocked");
        setUpUser(authInfo);
        String wrongLogin = faker.name().username();
        open("http://localhost:9999");
        $("[data-test-id=login] input").sendKeys(wrongLogin);
        $("[data-test-id=password] input").sendKeys(authInfo.password);
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginWhenUserNotExist() {
        AuthInfo authInfo = new AuthInfo("active");
        open("http://localhost:9999");
        $("[data-test-id=login] input").sendKeys(authInfo.login);
        $("[data-test-id=password] input").sendKeys(authInfo.password);
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }
}



















