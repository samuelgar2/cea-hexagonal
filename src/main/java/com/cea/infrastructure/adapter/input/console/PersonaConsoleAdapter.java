
package com.cea.infrastructure.adapter.input.console;

import com.cea.application.port.input.ConsultarPersonaUseCase;
import com.cea.application.port.input.RegistrarPersonaUseCase;
import com.cea.domain.municipio.Municipio;
import com.cea.domain.persona.FechaNacimiento;
import com.cea.domain.persona.Persona;

import java.time.LocalDate;
import java.util.Scanner;

public class PersonaConsoleAdapter {

    private final RegistrarPersonaUseCase registrarPersona;
    private final ConsultarPersonaUseCase consultarPersona;
    private final Scanner scanner;

    public PersonaConsoleAdapter(
            RegistrarPersonaUseCase registrarPersona,
            ConsultarPersonaUseCase consultarPersona,
            Scanner scanner) {

        this.registrarPersona = registrarPersona;
        this.consultarPersona = consultarPersona;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion = 0;

        do {
            System.out.println("\n================================");
            System.out.println("          SISTEMA CEA");
            System.out.println("================================");
            System.out.println("1. Registrar persona");
            System.out.println("2. Consultar persona por ID");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrar();
                        break;
                    case 2:
                        consultar();
                        break;
                    case 3:
                        System.out.println("Cerrando Sistema CEA...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "Debe ingresar una opción numérica."
                );
            }
        } while (opcion != 3);
    }

    private void registrar() {
        try {
            System.out.println("\n--- REGISTRO DE PERSONA ---");

            System.out.print("Nombres: ");
            String nombres = scanner.nextLine();

            System.out.print("Apellidos: ");
            String apellidos = scanner.nextLine();

            System.out.print("Fecha de nacimiento (AAAA-MM-DD): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());

            Municipio municipio = new Municipio(
                    1, 1, "001", "Cartagena"
            );

            Persona persona = new Persona(
                    nombres,
                    apellidos,
                    FechaNacimiento.crear(fecha),
                    municipio
            );

            Persona registrada = registrarPersona.ejecutar(persona);

            System.out.println(
                    "\n¡Persona registrada correctamente!"
            );
            System.out.println(
                    "ID generado: " + registrada.getIdPersona()
            );

        } catch (Exception e) {
            System.out.println(
                    "\nNo fue posible registrar la persona: "
                            + e.getMessage()
            );
        }
    }

    private void consultar() {
        try {
            System.out.println("\n--- CONSULTA DE PERSONA ---");
            System.out.print("Ingrese el ID de la persona: ");

            Integer id = Integer.parseInt(scanner.nextLine());

            consultarPersona.ejecutar(id).ifPresentOrElse(
                    persona -> {
                        System.out.println("\n--- PERSONA ENCONTRADA ---");
                        System.out.println("ID: " + persona.getIdPersona());
                        System.out.println("Nombres: " + persona.getNombres());
                        System.out.println("Apellidos: " + persona.getApellidos());
                        System.out.println(
                                "Fecha de nacimiento: "
                                        + persona.getFechaNacimiento()
                        );
                    },
                    () -> System.out.println(
                            "\nNo se encontró una persona con ese ID."
                    )
            );

        } catch (NumberFormatException e) {
            System.out.println(
                    "\nEl ID debe ser un número entero."
            );
        } catch (Exception e) {
            System.out.println(
                    "\nNo fue posible realizar la consulta: "
                            + e.getMessage()
            );
        }
    }
}


