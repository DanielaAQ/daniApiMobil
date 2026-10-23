# Todo.ly API Testing

Automatización de pruebas de API para [Todo.ly](https://todo.ly/api) ([documentación](http://todo.ly/ApiWiki/)) con **Java 17**, **Gradle**, **Cucumber 7** y **RestAssured 5**.

## Flujo automatizado

Un escenario en `project_crud.feature` que:

1. Crea un usuario nuevo con un email único (`POST /user.json`).
2. Obtiene su token con Basic Auth (`GET /authentication/token.json`).
3. Crea un proyecto (`POST /projects.json`).
4. Actualiza el proyecto (`PUT /projects/{id}.json`).
5. Elimina el proyecto y verifica `"Deleted": true` (`DELETE /projects/{id}.json`).

> Todo.ly responde HTTP 200 incluso cuando hay errores. Por eso cada paso valida además que el body **no** contenga `ErrorCode`.

## Requisitos

- JDK 17 o superior (el código se compila con `--release 17`)
- Conexión a internet (para la API y para descargar las dependencias)
- No hace falta instalar Gradle: el proyecto incluye el Gradle Wrapper

## Estructura

```
.
├── build.gradle
├── settings.gradle
├── gradlew / gradlew.bat
└── src/test
    ├── java
    │   ├── config/Configuration.java    # BASE_URL, endpoints y constantes
    │   ├── client/RequestClient.java    # get/post/put/delete reutilizables con log().all()
    │   ├── steps/ProjectSteps.java      # Step definitions en español
    │   └── runner/RunnerTest.java       # Suite JUnit Platform + motor Cucumber
    └── resources
        ├── features/project_crud.feature
        └── junit-platform.properties    # glue y plugins de Cucumber
```

## Ejecución

```bash
# Windows
gradlew clean test

# Linux / macOS
./gradlew clean test
```

## Reportes

- Reporte de Cucumber: `build/cucumber-report.html`
- Reporte de Gradle: `build/reports/tests/test/index.html`
