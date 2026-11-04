
package com.cea.domain.persona;

import com.cea.domain.exception.ExcepcionDominio;
import com.cea.domain.municipio.Municipio;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonaTest {

    private FechaNacimiento obtenerFecha() {
        return FechaNacimiento.crear(
                LocalDate.of(2000, 5, 15)
        );
    }

    private Municipio obtenerMunicipio() {
        return new Municipio(1, 10, "001", "Cartagena");
    }

    @Test
    void debeCrearPersonaValida() {
        Persona persona = new Persona(
                "Stivenson",
                "Garcia Romero",
                obtenerFecha(),
                obtenerMunicipio()
        );

        assertNotNull(persona);
        assertNull(persona.getIdPersona());
        assertNull(persona.getEventoRegistro());

        persona.asignarIdentificador(1);

        assertEquals(1, persona.getIdPersona());
        assertNotNull(persona.getEventoRegistro());

        assertEquals(
                persona.getIdPersona(),
                persona.getEventoRegistro().getIdPersona()
        );
    }

    @Test
    void noDebeCrearPersonaSinNombres() {
        assertThrows(
                ExcepcionDominio.class,
                () -> new Persona(
                        "",
                        "Garcia Romero",
                        obtenerFecha(),
                        obtenerMunicipio()
                )
        );
    }

    @Test
    void noDebeCrearPersonaSinApellidos() {
        assertThrows(
                ExcepcionDominio.class,
                () -> new Persona(
                        "Stivenson",
                        " ",
                        obtenerFecha(),
                        obtenerMunicipio()
                )
        );
    }

    @Test
    void noDebeCrearPersonaSinFechaNacimiento() {
        assertThrows(
                ExcepcionDominio.class,
                () -> new Persona(
                        "Stivenson",
                        "Garcia Romero",
                        null,
                        obtenerMunicipio()
                )
        );
    }

    @Test
    void noDebeCrearPersonaSinMunicipioNacimiento() {
        assertThrows(
                ExcepcionDominio.class,
                () -> new Persona(
                        "Stivenson",
                        "Garcia Romero",
                        obtenerFecha(),
                        null
                )
        );
    }

    @Test
    void debeActualizarNombres() {
        Persona persona = new Persona(
                "Stivenson",
                "Garcia Romero",
                obtenerFecha(),
                obtenerMunicipio()
        );

        persona.actualizarNombres("Samuel");

        assertEquals("Samuel", persona.getNombres());
    }
}

