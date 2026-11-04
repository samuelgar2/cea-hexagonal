
package com.cea.infrastructure.adapter.output.memory;

import com.cea.application.port.output.PersonaRepositoryPort;
import com.cea.domain.persona.Persona;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PersonaInMemoryAdapter implements PersonaRepositoryPort {

    private final Map<Integer, Persona> personas = new HashMap<>();
    private int siguienteId = 1;

    @Override
    public synchronized Persona guardar(Persona persona) {

        if (persona == null) {
            throw new IllegalArgumentException(
                    "La persona no puede ser nula."
            );
        }

        if (persona.getIdPersona() == null) {
            persona.asignarIdentificador(siguienteId++);
        }

        personas.put(persona.getIdPersona(), persona);

        return persona;
    }

    @Override
    public Optional<Persona> buscarPorId(Integer idPersona) {
        return Optional.ofNullable(personas.get(idPersona));
    }

    @Override
    public boolean existePorId(Integer idPersona) {
        return personas.containsKey(idPersona);
    }
}


