package com.cea.application.service;

import com.cea.application.port.input.ConsultarPersonaUseCase;
import com.cea.application.port.output.PersonaRepositoryPort;
import com.cea.domain.persona.Persona;

import java.util.Optional;

public class ConsultarPersonaService implements ConsultarPersonaUseCase {

    private final PersonaRepositoryPort personaRepository;

    public ConsultarPersonaService(PersonaRepositoryPort personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public Optional<Persona> ejecutar(Integer idPersona) {
        if (idPersona == null || idPersona <= 0) {
            throw new IllegalArgumentException("El ID debe ser válido");
        }

        return personaRepository.buscarPorId(idPersona);
    }
}
