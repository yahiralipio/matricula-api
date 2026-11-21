package pe.edu.cibertec.matricula_api.screenplay;

import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

public class ConfiguracionApi {
    public static Ability paraElActor() {
        return CallAnApi.at("http://localhost:8080");
    }
}