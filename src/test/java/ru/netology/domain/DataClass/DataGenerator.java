package ru.netology.domain.DataClass;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    public DataGenerator() {
    }
    private static final Faker faker = new Faker(new Locale("en"));
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void makeRequest(RegistrationInfo registrationInfo) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(registrationInfo) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }
    public static String getRandomLogin() {
        return faker.name().username();
    }
    public static String getRandomPassword() {
        return faker.internet().password();
    }

    public static RegistrationInfo getRegisteredUser(String status) {
        RegistrationInfo registrationInfo = new RegistrationInfo(getRandomLogin(), getRandomPassword(),status);
        makeRequest(registrationInfo);
        return registrationInfo;
    }
    public static RegistrationInfo getInvalidPasswordUser(String status) {
        String login = getRandomLogin();
        makeRequest(new RegistrationInfo(login, getRandomPassword(),status));
        return new RegistrationInfo(login, getRandomPassword(), status);
    }
    public static RegistrationInfo getInvalidLoginUser(String status) {
        String password = getRandomPassword();
        makeRequest(new RegistrationInfo(getRandomLogin(), password, status));
        return new RegistrationInfo(getRandomLogin(), password, status);
    }

    public static RegistrationInfo getNotRegisteredUser(String status) {
        String login = getRandomLogin();
        String password = getRandomPassword();
        return new RegistrationInfo(login, password, status);
    }
}