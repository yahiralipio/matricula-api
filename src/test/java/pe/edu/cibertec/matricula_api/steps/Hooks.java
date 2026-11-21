package pe.edu.cibertec.matricula_api.steps;

import io.cucumber.java.Before;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {
    @Before
    public void antesDeCadaEscenario() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("=================================================");
        System.out.println("ALUMNO : [YAHIR FERNANDO ALIPIO MIRANDA]");
        System.out.println("CODIGO : I202336956");
        System.out.println("FECHA  : " + LocalDateTime.now().format(formato));
        System.out.println("=================================================");
    }
}