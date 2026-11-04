
package com.cea.infrastructure.adapter.output.postgres;

import com.cea.domain.municipio.Municipio;
import com.cea.domain.persona.FechaNacimiento;
import com.cea.domain.persona.Persona;

import java.time.LocalDate;

public class PersonaPostgresRegistroPrueba {

    public static void main(String[] args) {

        PersonaPostgresAdapter adaptador =
                new PersonaPostgresAdapter();

        try {
            Municipio municipio = new Municipio(
                    1,
                    1,
                    "COD050",
                    "Municipio con sufijo 050"
            );

            Persona persona = new Persona(
                    "Persona",
                    "Prueba PostgreSQL",
                    FechaNacimiento.crear(
                            LocalDate.of(2000, 1, 15)
                    ),
                    municipio
            );

            Persona registrada = adaptador.guardar(persona);

            System.out.println("=== REGISTRO EXITOSO ===");
            System.out.println(
                    "ID asignado: " + registrada.getIdPersona()
            );

            System.out.println(
                    "¿Existe en PostgreSQL? "
                            + adaptador.existePorId(
                            registrada.getIdPersona()
                    )
            );

            System.out.println(
                    "¿Se recupera correctamente? "
                            + adaptador.buscarPorId(
                            registrada.getIdPersona()
                    ).isPresent()
            );

        } catch (Exception e) {
            System.out.println("Error durante el registro:");
            e.printStackTrace();
        }
    }
}

