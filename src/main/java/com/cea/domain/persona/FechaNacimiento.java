package com.cea.domain.persona;

import com.cea.domain.exception.ExcepcionDominio;
import java.time.LocalDate;
import java.util.Objects;

public final class FechaNacimiento {

    private final LocalDate valor;

    private FechaNacimiento(LocalDate valor) {
        this.valor = valor;
    }

    public static FechaNacimiento crear(LocalDate fecha) {

        if (fecha == null) {
            throw new ExcepcionDominio(
                    "La fecha de nacimiento no puede ser nula."
            );
        }

        if (fecha.isAfter(LocalDate.now())) {
            throw new ExcepcionDominio(
                    "La fecha de nacimiento no puede ser futura."
            );
        }

        return new FechaNacimiento(fecha);
    }

    public LocalDate getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (!(objeto instanceof FechaNacimiento)) return false;

        FechaNacimiento otra = (FechaNacimiento) objeto;
        return valor.equals(otra.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}
