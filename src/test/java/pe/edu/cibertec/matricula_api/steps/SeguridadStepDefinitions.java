package pe.edu.cibertec.matricula_api.steps;

import io.cucumber.java.es.*;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.interactions.Get;
import pe.edu.cibertec.matricula_api.screenplay.questions.CodigoDeError;
import pe.edu.cibertec.matricula_api.screenplay.tasks.CerrarSesion;
import pe.edu.cibertec.matricula_api.screenplay.tasks.IniciarSesion;
import pe.edu.cibertec.matricula_api.screenplay.tasks.RenovarToken;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.is;

public class SeguridadStepDefinitions {

    @Dado("que {word} tiene acceso a la API")
    public void actorTieneAcceso(String nombreActor) {
        OnStage.setTheStage(new OnlineCast());
        theActorCalled(nombreActor).whoCan(
                pe.edu.cibertec.matricula_api.screenplay.ConfiguracionApi.paraElActor()
        );
    }

    @Cuando("inicia sesión con el usuario {string} y la clave {string}")
    public void iniciaSesion(String usuario, String clave) {
        theActorInTheSpotlight().attemptsTo(
                IniciarSesion.conCredenciales(usuario, clave)
        );
    }

    @Cuando("intenta renovar el token con el refresh token guardado")
    public void renuevaToken() {
        String refreshToken = theActorInTheSpotlight().recall("refreshToken");
        theActorInTheSpotlight().attemptsTo(
                RenovarToken.con(refreshToken)
        );
    }

    @Cuando("intenta usar el refresh token original nuevamente")
    public void usaRefreshTokenOriginal() {
        String refreshToken = theActorInTheSpotlight().recall("refreshToken");
        theActorInTheSpotlight().attemptsTo(
                RenovarToken.con(refreshToken)
        );
    }

    @Cuando("cierra sesión")
    public void cierraSesion() {
        theActorInTheSpotlight().attemptsTo(
                CerrarSesion.actual()
        );
    }

    @Cuando("intenta consultar su perfil")
    public void consultaPerfil() {
        String token = theActorInTheSpotlight().recall("accessToken");
        theActorInTheSpotlight().attemptsTo(
                Get.resource("/api/auth/perfil")
                        .with(request -> request.header("Authorization", "Bearer " + token))
        );
    }

    @Cuando("intenta listar todos los alumnos")
    public void listaAlumnos() {
        String token = theActorInTheSpotlight().recall("accessToken");
        theActorInTheSpotlight().attemptsTo(
                Get.resource("/api/alumnos")
                        .with(request -> request.header("Authorization", "Bearer " + token))
        );
    }

    @Cuando("intenta iniciar sesión 5 veces con clave incorrecta para el usuario {string}")
    public void intentaCincoVeces(String usuario) {
        for (int i = 0; i < 5; i++) {
            theActorInTheSpotlight().attemptsTo(
                    IniciarSesion.conCredenciales(usuario, "ClaveIncorrecta" + i)
            );
        }
    }

    @Entonces("debería ver el código de respuesta {int}")
    public void verCodigoRespuesta(int codigo) {
        theActorInTheSpotlight().should(
                seeThat("El código de estado", actor -> SerenityRest.lastResponse().statusCode(), is(codigo))
        );
    }

    @Entonces("debería ver el código de error {string}")
    public void verCodigoError(String codigo) {
        theActorInTheSpotlight().should(
                seeThat("El código de error", CodigoDeError.obtenido(), is(codigo))
        );
    }
}