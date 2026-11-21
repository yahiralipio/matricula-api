package pe.edu.cibertec.matricula_api.steps;

import io.cucumber.java.es.*;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.hamcrest.Matchers;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;

public class MatriculaStepDefinitions {

    private String token;
    private int idMatricula;
    private Response response;

    @Dado("que el alumno {string} inicia sesión con la clave {string}")
    public void login(String usuario, String clave) {
        Map<String, String> body = new HashMap<>();
        body.put("username", usuario);
        body.put("password", clave);
        response = SerenityRest.given()
                .contentType("application/json")
                .body(body)
                .post("/api/auth/login");
        token = response.jsonPath().getString("accessToken");
    }

    @Cuando("cotizo el curso {string} para el alumno {string}")
    public void cotizar(String curso, String alumno) {
        Map<String, Object> body = new HashMap<>();
        body.put("codigoAlumno", alumno);
        body.put("cursos", new String[]{curso});
        response = SerenityRest.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(body)
                .post("/api/matriculas/cotizacion");
    }

    @Entonces("el código de respuesta es {int}")
    public void codigoRespuesta(int codigo) {
        response.then().statusCode(codigo);
    }

    @Entonces("la cotización tiene {int} créditos")
    public void creditos(int creditos) {
        assertThat(response.jsonPath().getInt("creditos"), Matchers.is(creditos));
    }

    @Entonces("el subtotal es {string}")
    public void subtotal(String subtotal) {
        assertThat(response.jsonPath().getDouble("subtotal"), Matchers.is(Double.parseDouble(subtotal)));
    }

    @Entonces("el descuento es {string}")
    public void descuento(String descuento) {
        assertThat(response.jsonPath().getDouble("descuento"), Matchers.is(Double.parseDouble(descuento)));
    }

    @Entonces("el total es {string}")
    public void total(String total) {
        assertThat(response.jsonPath().getDouble("total"), Matchers.is(Double.parseDouble(total)));
    }

    @Cuando("matriculo el curso {string} para el alumno {string}")
    public void matricular(String curso, String alumno) {
        Map<String, String> body = new HashMap<>();
        body.put("codigoAlumno", alumno);
        body.put("codigoCurso", curso);
        response = SerenityRest.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(body)
                .post("/api/matriculas");
        idMatricula = response.jsonPath().getInt("id");
    }

    @Entonces("la matrícula queda en estado {string}")
    public void estadoMatricula(String estado) {
        response.then().body("estado", Matchers.is(estado));
    }

    @Entonces("el monto de la matrícula es {string}")
    public void montoMatricula(String monto) {
        assertThat(response.jsonPath().getDouble("monto"), Matchers.is(Double.parseDouble(monto)));
    }

    @Entonces("la matrícula cumple el esquema {string}")
    public void cumpleEsquema(String esquema) {
        response.then().body(matchesJsonSchemaInClasspath("schemas/" + esquema));
    }

    @Cuando("pago la matrícula con el método {string}")
    public void pagar(String metodo) {
        Map<String, String> body = new HashMap<>();
        body.put("metodo", metodo);
        response = SerenityRest.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(body)
                .patch("/api/matriculas/" + idMatricula + "/pagar");
    }

    @Entonces("la fecha de pago tiene valor")
    public void fechaPago() {
        assertThat(response.jsonPath().getString("fechaPago"), Matchers.notNullValue());
    }

    @Cuando("consulto los pagos del alumno {string}")
    public void consultarPagos(String alumno) {
        response = SerenityRest.given()
                .header("Authorization", "Bearer " + token)
                .queryParam("alumno", alumno)
                .get("/api/pagos");
    }

    @Entonces("existe un pago para la matrícula con método {string}")
    public void existePago(String metodo) {
        // La respuesta de /api/pagos es una lista directa, no un objeto con "content"
        response.then().body("idMatricula", Matchers.hasItem(idMatricula));
        response.then().body("find { it.idMatricula == " + idMatricula + " }.metodo", Matchers.is(metodo));
    }

    @Entonces("el número de operación tiene formato OP-000000")
    public void formatoOperacion() {
        String numero = response.jsonPath().getString("find { it.idMatricula == " + idMatricula + " }.numeroOperacion");
        assertThat(numero, Matchers.matchesPattern("OP-\\d{6}"));
    }

    @Cuando("intento matricular el curso {string} para el alumno {string}")
    public void intentarMatricular(String curso, String alumno) {
        Map<String, String> body = new HashMap<>();
        body.put("codigoAlumno", alumno);
        body.put("codigoCurso", curso);
        response = SerenityRest.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(body)
                .post("/api/matriculas");
    }

    @Entonces("el código de error es {string}")
    public void codigoError(String codigo) {
        response.then().body("codigo", Matchers.is(codigo));
    }

    @Entonces("la respuesta cumple el esquema {string}")
    public void cumpleEsquemaError(String esquema) {
        response.then().body(matchesJsonSchemaInClasspath("schemas/" + esquema));
    }
}