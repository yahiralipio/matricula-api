# language: es
Característica: Matrícula del alumno
  Como alumno del Instituto CIBERTEC
  Quiero cotizar, matricularme y pagar un curso
  Para completar mi matrícula en el periodo abierto

  Antecedentes:
    Dado que el alumno "i202610006" inicia sesión con la clave "Cibertec2026"

  Escenario: Cotizar el curso DM5512
    Cuando cotizo el curso "DM5512" para el alumno "i202610006"
    Entonces el código de respuesta es 200
    Y la cotización tiene 4 créditos
    Y el subtotal es "480.0"
    Y el descuento es "0.0"
    Y el total es "480.0"

  Escenario: Matricularse y pagar el curso DM5512
    Cuando matriculo el curso "DM5512" para el alumno "i202610006"
    Entonces el código de respuesta es 201
    Y la matrícula queda en estado "REGISTRADA"
    Y el monto de la matrícula es "480.0"
    Y la matrícula cumple el esquema "matricula.json"
    Cuando pago la matrícula con el método "TRANSFERENCIA"
    Entonces el código de respuesta es 200
    Y la matrícula queda en estado "PAGADA"
    Y la fecha de pago tiene valor
    Cuando consulto los pagos del alumno "i202610006"
    Entonces existe un pago para la matrícula con método "TRANSFERENCIA"
    Y el número de operación tiene formato OP-000000

  Escenario: No se puede matricular en QA5517 por prerrequisito pendiente
    Cuando intento matricular el curso "QA5517" para el alumno "i202610006"
    Entonces el código de respuesta es 422
    Y el código de error es "PRERREQUISITO_PENDIENTE"
    Y la respuesta cumple el esquema "error.json"