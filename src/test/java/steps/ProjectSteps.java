package steps;

import client.RequestClient;
import config.Configuration;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import io.restassured.response.Response;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectSteps {

    private final RequestClient client = new RequestClient();

    private Response response;
    private String userEmail;
    private String token;
    private Object projectId;

    @Dado("creo un usuario nuevo")
    public void createNewUser() {
        userEmail = Configuration.USER_EMAIL_PREFIX + System.currentTimeMillis() + Configuration.USER_EMAIL_DOMAIN;

        JSONObject body = new JSONObject()
                .put(Configuration.FIELD_EMAIL, userEmail)
                .put(Configuration.FIELD_FULL_NAME, Configuration.USER_FULL_NAME)
                .put(Configuration.FIELD_PASSWORD, Configuration.USER_PASSWORD);

        response = client.post(Configuration.USER_ENDPOINT, body);

        assertSuccessfulResponse("Creación de usuario");
        assertEquals(userEmail, bodyAsJson().optString(Configuration.FIELD_EMAIL),
                "El email del usuario creado no coincide con el enviado");
    }

    @Y("obtengo el token de autenticación del usuario")
    public void obtainAuthenticationToken() {
        response = client.getWithBasicAuth(Configuration.TOKEN_ENDPOINT, userEmail, Configuration.USER_PASSWORD);

        assertSuccessfulResponse("Obtención de token");
        token = bodyAsJson().optString(Configuration.FIELD_TOKEN_STRING, null);
        assertNotNull(token, "La respuesta no contiene el campo " + Configuration.FIELD_TOKEN_STRING);
        assertFalse(token.isBlank(), "El token de autenticación está vacío");
    }

    @Cuando("creo un proyecto con nombre {string}")
    public void createProject(String projectName) {
        response = client.post(Configuration.PROJECTS_ENDPOINT, token, contentBody(projectName));

        assertSuccessfulResponse("Creación de proyecto");
        projectId = bodyAsJson().opt(Configuration.FIELD_ID);
        assertNotNull(projectId, "La respuesta no contiene el " + Configuration.FIELD_ID + " del proyecto");
    }

    @Cuando("actualizo el proyecto con nombre {string}")
    public void updateProject(String newProjectName) {
        response = client.put(Configuration.PROJECT_BY_ID_ENDPOINT, token, contentBody(newProjectName), projectId);

        assertSuccessfulResponse("Actualización de proyecto");
    }

    @Cuando("elimino el proyecto")
    public void deleteProject() {
        response = client.delete(Configuration.PROJECT_BY_ID_ENDPOINT, token, projectId);

        assertSuccessfulResponse("Eliminación de proyecto");
    }

    @Entonces("el código de respuesta es {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode(),
                "Código HTTP inesperado. Body: " + response.getBody().asString());
    }

    @Y("el campo {string} es {string}")
    public void verifyFieldValue(String fieldName, String expectedValue) {
        JSONObject body = bodyAsJson();
        assertTrue(body.has(fieldName),
                "La respuesta no contiene el campo '" + fieldName + "'. Body: " + body);
        assertEquals(expectedValue, String.valueOf(body.get(fieldName)),
                "Valor inesperado en el campo '" + fieldName + "'");
    }

    private JSONObject contentBody(String content) {
        return new JSONObject().put(Configuration.FIELD_CONTENT, content);
    }

    private JSONObject bodyAsJson() {
        return new JSONObject(response.getBody().asString());
    }

    private void assertSuccessfulResponse(String operation) {
        String rawBody = response.getBody().asString();
        assertEquals(Configuration.HTTP_OK, response.getStatusCode(),
                operation + ": código HTTP inesperado. Body: " + rawBody);

        JSONObject body = bodyAsJson();
        assertFalse(body.has(Configuration.FIELD_ERROR_CODE),
                operation + " falló con ErrorCode " + body.opt(Configuration.FIELD_ERROR_CODE)
                        + ": " + body.optString(Configuration.FIELD_ERROR_MESSAGE));
    }
}
