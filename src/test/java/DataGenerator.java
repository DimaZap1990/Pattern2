import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private DataGenerator() {
    }

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    public static Faker faker = new Faker(new Locale("ru"));

    public static void setUpAccount(AccountInfo account) {
        given()
                .spec(requestSpec)
                .body(account)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getLogin() {
        return faker.name().username();
    }

    public static String getPassword() {
        return faker.internet().password();
    }

    public static class Registration {
        private Registration() {
        }

        public static AccountInfo getAccount(String status) {
            AccountInfo account = new AccountInfo(getLogin(), getPassword(), status);
            return account;
        }

        public static AccountInfo getRegisteredAccount(String status) {
            var registeredAccount = getAccount(status);
            setUpAccount(registeredAccount);

            return registeredAccount;
        }
    }
}
