
package com.cea.domain.event;

import java.time.LocalDateTime;

public final class PersonaRegistrada {

    private final Integer idPersona;
    private final LocalDateTime fechaRegistro;

    public PersonaRegistrada(Integer idPersona) {
        if (idPersona == null || idPersona <= 0) {
            throw new IllegalArgumentException(
                    "El identificador de la persona debe ser válido."
            );
        }

        this.idPersona = idPersona;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
}

