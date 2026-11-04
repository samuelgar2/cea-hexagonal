package com.cea.domain.event;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonaRegistradaTest {

    @Test
    void debeCrearEventoConIdentificadorValido() {
        Integer id = 1;

        PersonaRegistrada evento = new PersonaRegistrada(id);

        assertEquals(id, evento.getIdPersona());
        assertNotNull(evento.getFechaRegistro());
    }

    @Test
    void noDebeCrearEventoSinIdentificador() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PersonaRegistrada(null)
        );
    }

    @Test
    void noDebeCrearEventoConIdentificadorInvalido() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PersonaRegistrada(0)
        );
    }
}