package com.cea.infrastructure.adapter.output.memory;

import com.cea.domain.municipio.Municipio;
import com.cea.domain.persona.FechaNacimiento;
import com.cea.domain.persona.Persona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PersonaInMemoryAdapterTest {

    private PersonaInMemoryAdapter repositorio;
    private Persona persona;
    private Municipio municipio;

    @BeforeEach
    void setUp() {
        repositorio = new PersonaInMemoryAdapter();

        municipio = new Municipio(
                1, 1, "001", "Cartagena"
        );

        persona = new Persona(
                "Stivenson",
                "Garcia",
                FechaNacimiento.crear(
                        LocalDate.of(2000, 5, 15)
                ),
                municipio
        );
    }

    @Test
    void debeGuardarUnaPersona() {
        Persona registrada = repositorio.guardar(persona);

        assertNotNull(registrada.getIdPersona());
        assertTrue(
                repositorio.existePorId(registrada.getIdPersona())
        );
    }

    @Test
    void debeBuscarUnaPersonaPorSuId() {
        Persona registrada = repositorio.guardar(persona);

        Optional<Persona> resultado =
                repositorio.buscarPorId(registrada.getIdPersona());

        assertTrue(resultado.isPresent());
        assertEquals(registrada, resultado.get());
    }

    @Test
    void noDebeEncontrarUnaPersonaInexistente() {
        Optional<Persona> resultado =
                repositorio.buscarPorId(999);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void debeVerificarSiUnaPersonaExiste() {
        Persona registrada = repositorio.guardar(persona);

        assertTrue(
                repositorio.existePorId(registrada.getIdPersona())
        );

        assertFalse(
                repositorio.existePorId(999)
        );
    }
}