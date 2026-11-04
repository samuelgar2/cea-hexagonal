
package com.cea;

import com.cea.application.port.output.PersonaRepositoryPort;
import com.cea.application.service.ConsultarPersonaService;
import com.cea.application.service.RegistrarPersonaService;
import com.cea.infrastructure.adapter.input.console.PersonaConsoleAdapter;
import com.cea.infrastructure.adapter.output.postgres.PersonaPostgresAdapter;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        PersonaRepositoryPort repositorio =
                new PersonaPostgresAdapter();

        RegistrarPersonaService registrarServicio =
                new RegistrarPersonaService(repositorio);

        ConsultarPersonaService consultarServicio =
                new ConsultarPersonaService(repositorio);

        Scanner scanner = new Scanner(System.in);

        PersonaConsoleAdapter consola = new PersonaConsoleAdapter(
                registrarServicio,
                consultarServicio,
                scanner
        );

        consola.iniciar();

        scanner.close();
    }
}

