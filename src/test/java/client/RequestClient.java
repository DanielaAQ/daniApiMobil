package client;

import config.Configuration;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class RequestClient {

    private RequestSpecification baseRequest() {
        return given()
                .baseUri(Configuration.BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    private RequestSpecification tokenRequest(String token) {
        return baseRequest().header(Configuration.TOKEN_HEADER, token);
    }

    public Response post(String endpoint, JSONObject body) {
        return send(baseRequest().body(body.toString()).log().all().when().post(endpoint));
    }

    public Response post(String endpoint, String token, JSONObject body) {
        return send(tokenRequest(token).body(body.toString()).log().all().when().post(endpoint));
    }

    public Response getWithBasicAuth(String endpoint, String username, String password) {
        return send(baseRequest()
                .auth().preemptive().basic(username, password)
                .log().all()
                .when().get(endpoint));
    }

    public Response get(String endpoint, String token, Object... pathParams) {
        return send(tokenRequest(token).log().all().when().get(endpoint, pathParams));
    }

    public Response put(String endpoint, String token, JSONObject body, Object... pathParams) {
        return send(tokenRequest(token).body(body.toString()).log().all().when().put(endpoint, pathParams));
    }

    public Response delete(String endpoint, String token, Object... pathParams) {
        return send(tokenRequest(token).log().all().when().delete(endpoint, pathParams));
    }

    private Response send(Response response) {
        return response.then().log().all().extract().response();
    }
}
