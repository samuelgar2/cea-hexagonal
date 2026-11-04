
package com.cea.domain.persona;

import com.cea.domain.exception.ExcepcionDominio;
import com.cea.domain.municipio.Municipio;
import com.cea.domain.event.PersonaRegistrada;

import java.util.Objects;

public class Persona {

    private Integer idPersona;
    private String nombres;
    private String apellidos;
    private final FechaNacimiento fechaNacimiento;
    private final Municipio municipioNacimiento;
    private PersonaRegistrada eventoRegistro;

    public Persona(String nombres, String apellidos,
                   FechaNacimiento fechaNacimiento,
                   Municipio municipioNacimiento) {

        if (nombres == null || nombres.isBlank()) {
            throw new ExcepcionDominio(
                    "Los nombres de la persona son obligatorios."
            );
        }

        if (apellidos == null || apellidos.isBlank()) {
            throw new ExcepcionDominio(
                    "Los apellidos de la persona son obligatorios."
            );
        }

        if (fechaNacimiento == null) {
            throw new ExcepcionDominio(
                    "La fecha de nacimiento es obligatoria."
            );
        }

        if (municipioNacimiento == null) {
            throw new ExcepcionDominio(
                    "El municipio de nacimiento es obligatorio."
            );
        }

        this.idPersona = null;
        this.nombres = nombres.trim();
        this.apellidos = apellidos.trim();
        this.fechaNacimiento = fechaNacimiento;
        this.municipioNacimiento = municipioNacimiento;
        this.eventoRegistro = null;
    }

    public void asignarIdentificador(Integer idPersona) {
        if (idPersona == null || idPersona <= 0) {
            throw new ExcepcionDominio(
                    "El identificador debe ser válido."
            );
        }

        if (this.idPersona != null) {
            throw new ExcepcionDominio(
                    "La persona ya tiene un identificador asignado."
            );
        }

        this.idPersona = idPersona;
        this.eventoRegistro = new PersonaRegistrada(idPersona);
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public FechaNacimiento getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Municipio getMunicipioNacimiento() {
        return municipioNacimiento;
    }

    public PersonaRegistrada getEventoRegistro() {
        return eventoRegistro;
    }

    public void actualizarNombres(String nombres) {
        if (nombres == null || nombres.isBlank()) {
            throw new ExcepcionDominio(
                    "Los nombres no pueden estar vacíos."
            );
        }

        this.nombres = nombres.trim();
    }

    public void actualizarApellidos(String apellidos) {
        if (apellidos == null || apellidos.isBlank()) {
            throw new ExcepcionDominio(
                    "Los apellidos no pueden estar vacíos."
            );
        }

        this.apellidos = apellidos.trim();
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Persona)) {
            return false;
        }

        Persona persona = (Persona) objeto;

        return idPersona != null
                && idPersona.equals(persona.idPersona);
    }

    @Override
    public int hashCode() {
        return idPersona == null
                ? System.identityHashCode(this)
                : Objects.hash(idPersona);
    }
}


