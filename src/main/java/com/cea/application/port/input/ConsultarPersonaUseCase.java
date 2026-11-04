package com.cea.application.port.input;

import com.cea.domain.persona.Persona;

import java.util.Optional;

public interface ConsultarPersonaUseCase {

    Optional<Persona> ejecutar(Integer idPersona);
}
