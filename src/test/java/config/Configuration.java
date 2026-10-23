package config;

public final class Configuration {

    public static final String BASE_URL = "https://todo.ly/api";

    public static final String USER_ENDPOINT = "/user.json";
    public static final String TOKEN_ENDPOINT = "/authentication/token.json";
    public static final String PROJECTS_ENDPOINT = "/projects.json";
    public static final String PROJECT_BY_ID_ENDPOINT = "/projects/{id}.json";

    public static final String TOKEN_HEADER = "Token";
    public static final int HTTP_OK = 200;

    public static final String USER_EMAIL_PREFIX = "daniela.quispe.";
    public static final String USER_EMAIL_DOMAIN = "@mail.com";
    public static final String USER_FULL_NAME = "Daniela Quispe";
    public static final String USER_PASSWORD = "Qa123456!";

    public static final String FIELD_EMAIL = "Email";
    public static final String FIELD_FULL_NAME = "FullName";
    public static final String FIELD_PASSWORD = "Password";
    public static final String FIELD_CONTENT = "Content";
    public static final String FIELD_ID = "Id";
    public static final String FIELD_TOKEN_STRING = "TokenString";
    public static final String FIELD_ERROR_CODE = "ErrorCode";
    public static final String FIELD_ERROR_MESSAGE = "ErrorMessage";

    private Configuration() {
    }
}
