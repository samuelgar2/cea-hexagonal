package com.cea.application.service;

import com.cea.application.port.input.RegistrarPersonaUseCase;
import com.cea.application.port.output.PersonaRepositoryPort;
import com.cea.domain.persona.Persona;

public class RegistrarPersonaService implements RegistrarPersonaUseCase {

    private final PersonaRepositoryPort personaRepository;

    public RegistrarPersonaService(PersonaRepositoryPort personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public Persona ejecutar(Persona persona) {
        if (persona == null) {
            throw new IllegalArgumentException(
                    "La persona que se desea registrar no puede ser nula"
            );
        }

        return personaRepository.guardar(persona);
    }
}


