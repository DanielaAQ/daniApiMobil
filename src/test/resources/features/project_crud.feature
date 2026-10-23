# language: es
@crud
Característica: CRUD de proyectos en Todo.ly
  Como usuario de Todo.ly
  Quiero crear, actualizar y eliminar proyectos
  Para gestionar mis tareas organizadas por proyecto

  Escenario: Crear, actualizar y eliminar un proyecto con un usuario nuevo
    Dado creo un usuario nuevo
    Y obtengo el token de autenticación del usuario
    Cuando creo un proyecto con nombre "Proyecto QA"
    Entonces el código de respuesta es 200
    Y el campo "Content" es "Proyecto QA"
    Cuando actualizo el proyecto con nombre "Proyecto QA Editado"
    Entonces el código de respuesta es 200
    Y el campo "Content" es "Proyecto QA Editado"
    Cuando elimino el proyecto
    Entonces el código de respuesta es 200
    Y el campo "Deleted" es "true"
