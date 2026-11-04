
package com.cea.infrastructure.adapter.output.postgres;

import com.cea.domain.persona.Persona;

import java.util.Optional;

public class PersonaPostgresAdapterPrueba {

    public static void main(String[] args) {

        PersonaPostgresAdapter adaptador =
                new PersonaPostgresAdapter();

        try {
            System.out.println("=== PRUEBA DEL ADAPTADOR POSTGRESQL ===");

            Optional<Persona> resultado =
                    adaptador.buscarPorId(1);

            if (resultado.isPresent()) {

                Persona persona = resultado.get();

                System.out.println("Persona encontrada:");
                System.out.println("ID: " + persona.getIdPersona());
                System.out.println("Nombres: " + persona.getNombres());
                System.out.println("Apellidos: " + persona.getApellidos());

                System.out.println(
                        "Fecha de nacimiento: "
                                + persona.getFechaNacimiento().getValor()
                );

                System.out.println(
                        "Municipio: "
                                + persona.getMunicipioNacimiento().getNombre()
                );

            } else {
                System.out.println("No se encontró la persona.");
            }

            System.out.println(
                    "¿Existe la persona con ID 1? "
                            + adaptador.existePorId(1)
            );

            System.out.println(
                    "¿Existe la persona con ID 999999? "
                            + adaptador.existePorId(999999)
            );

        } catch (Exception e) {
            System.out.println("Error durante la prueba:");
            e.printStackTrace();
        }
    }
}
