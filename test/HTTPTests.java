import com.jayway.restassured.response.Response;
import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;

public class HTTPTests {

    String url = "http://localhost:8080/tripPlanner/";

    @Test
    public void loginInCorrect()
            throws ClientProtocolException, IOException{
        Response fieldResponse =
                given ().
                parameter("nickname", "qwerty").
                parameter("password", "1234").

                when()
                    .post(url + "login/").

                then()
                    .assertThat()
                    .log().ifError()
                    .statusCode(200)
                    .extract().response();

        Assert.assertFalse(fieldResponse.asString().contains("isError"));
    }
}
