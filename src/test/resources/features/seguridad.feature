# language: es
Característica: Seguridad de la sesión
  Como usuario de la API de matrícula
  Quiero que mis sesiones sean seguras
  Para evitar accesos no autorizados

  Escenario: Rotación del refresh token
    Dado que "Pedro" tiene acceso a la API
    Cuando inicia sesión con el usuario "i202610006" y la clave "Cibertec2026"
    Y intenta renovar el token con el refresh token guardado
    Entonces debería ver el código de respuesta 200
    Cuando intenta usar el refresh token original nuevamente
    Entonces debería ver el código de respuesta 401
    Y debería ver el código de error "REFRESH_REUTILIZADO"

  Escenario: Cierre de sesión
    Dado que "Pedro" tiene acceso a la API
    Cuando inicia sesión con el usuario "i202610006" y la clave "Cibertec2026"
    Y cierra sesión
    Entonces debería ver el código de respuesta 204
    Cuando intenta consultar su perfil
    Entonces debería ver el código de respuesta 401
    Y debería ver el código de error "TOKEN_INVALIDO"

  Escenario: Bloqueo de cuenta por intentos fallidos
    Dado que "Jorge" tiene acceso a la API
    Cuando intenta iniciar sesión 5 veces con clave incorrecta para el usuario "i202610004"
    Entonces debería ver el código de respuesta 423
    Y debería ver el código de error "CUENTA_BLOQUEADA"
    Cuando inicia sesión con el usuario "i202610004" y la clave "Cibertec2026"
    Entonces debería ver el código de respuesta 423
    Y debería ver el código de error "CUENTA_BLOQUEADA"

  Escenario: Alumno sin permiso de administrador
    Dado que "Pedro" tiene acceso a la API
    Cuando inicia sesión con el usuario "i202610006" y la clave "Cibertec2026"
    Y intenta listar todos los alumnos
    Entonces debería ver el código de respuesta 403
    Y debería ver el código de error "ACCESO_DENEGADO"