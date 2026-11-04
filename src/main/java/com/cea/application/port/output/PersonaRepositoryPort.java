package com.cea.application.port.output;

import com.cea.domain.persona.Persona;

import java.util.Optional;

public interface PersonaRepositoryPort {

    Persona guardar(Persona persona);

    Optional<Persona> buscarPorId(Integer idPersona);

    boolean existePorId(Integer idPersona);
}

