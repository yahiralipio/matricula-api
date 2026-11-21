# language: es
Característica: Calificación de matrículas
  Como docente del Instituto CIBERTEC
  Quiero registrar las notas de mis alumnos
  Para que su historial académico se actualice

  Esquema del escenario: El docente registra la nota y el historial se actualiza
    Dado que la alumna "i202610005" inicia sesión con la clave "Cibertec2026"
    Y consulta sus matrículas para obtener el id de "PI5620"
    Dado que el docente "D0001" inicia sesión con la clave "Cibertec2026"
    Cuando registra la nota <nota> en la matrícula
    Entonces debería ver el código de respuesta 200
    Cuando la alumna "i202610005" consulta el historial de "i202610005"
    Entonces el historial muestra el promedio <promedio>
    Y el historial muestra <creditos> créditos aprobados

    Ejemplos:
      | nota | promedio | creditos |
      | "16" | "15.69"  | "13"     |
      | "12.5" | "14.08" | "7"    |

  Escenario: Un docente que no dicta el curso intenta registrar la nota
    Dado que la alumna "i202610005" inicia sesión con la clave "Cibertec2026"
    Y consulta sus matrículas para obtener el id de "PI5620"
    Dado que el docente "D0002" inicia sesión con la clave "Cibertec2026"
    Cuando registra la nota "15" en la matrícula
    Entonces debería ver el código de respuesta 403
    Y debería ver el código de error "ACCESO_DENEGADO"

  Escenario: No se puede calificar una matrícula que no está pagada
    Dado que la alumna "i202610005" inicia sesión con la clave "Cibertec2026"
    Y consulta sus matrículas para obtener el id de "PS5511"
    Dado que el docente "D0001" inicia sesión con la clave "Cibertec2026"
    Cuando registra la nota "15" en la matrícula
    Entonces debería ver el código de respuesta 409
    Y debería ver el código de error "MATRICULA_NO_PAGADA"