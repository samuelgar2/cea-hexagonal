
package com.cea.infrastructure.adapter.output.postgres;

import com.cea.application.port.output.PersonaRepositoryPort;
import com.cea.domain.municipio.Municipio;
import com.cea.domain.persona.FechaNacimiento;
import com.cea.domain.persona.Persona;
import com.cea.infrastructure.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public class PersonaPostgresAdapter implements PersonaRepositoryPort {

    @Override
    public Persona guardar(Persona persona) {

        if (persona == null) {
            throw new IllegalArgumentException(
                    "La persona no puede ser nula."
            );
        }

        if (persona.getIdPersona() != null) {
            throw new IllegalArgumentException(
                    "La persona ya tiene un identificador."
            );
        }

        String sql = """
                INSERT INTO public.persona (
                    id_persona,
                    nombres,
                    apellidos,
                    fecha_nacimiento,
                    id_municipio_nacimiento,
                    edad
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                // Evita asignaciones simultáneas del mismo identificador.
                try (PreparedStatement lock = connection.prepareStatement(
                        "LOCK TABLE public.persona IN EXCLUSIVE MODE")) {
                    lock.execute();
                }

                int siguienteId;

                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     "SELECT COALESCE(MAX(id_persona), 0) + 1 "
                                             + "FROM public.persona");
                     ResultSet result = statement.executeQuery()) {

                    result.next();
                    siguienteId = result.getInt(1);
                }

                if (siguienteId <= 0) {
                    throw new IllegalStateException(
                            "No se pudo generar un identificador válido."
                    );
                }

                int edad = Period.between(
                        persona.getFechaNacimiento().getValor(),
                        LocalDate.now()
                ).getYears();

                try (PreparedStatement statement =
                             connection.prepareStatement(sql)) {

                    statement.setInt(1, siguienteId);
                    statement.setString(2, persona.getNombres());
                    statement.setString(3, persona.getApellidos());
                    statement.setObject(
                            4,
                            persona.getFechaNacimiento().getValor()
                    );
                    statement.setInt(
                            5,
                            persona.getMunicipioNacimiento()
                                    .getIdMunicipio()
                    );
                    statement.setInt(6, edad);

                    statement.executeUpdate();
                }

                connection.commit();

                persona.asignarIdentificador(siguienteId);

                return persona;

            } catch (SQLException | RuntimeException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al guardar la persona en PostgreSQL.",
                    e
            );
        }
    }

    @Override
    public Optional<Persona> buscarPorId(Integer idPersona) {

        if (idPersona == null || idPersona <= 0) {
            return Optional.empty();
        }

        String sql = """
                SELECT
                    p.id_persona,
                    p.nombres,
                    p.apellidos,
                    p.fecha_nacimiento,
                    m.id_municipio,
                    m.id_provincia,
                    m.codigo_municipio,
                    m.nombre AS nombre_municipio
                FROM public.persona p
                INNER JOIN public.municipio m
                    ON p.id_municipio_nacimiento = m.id_municipio
                WHERE p.id_persona = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, idPersona);

            try (ResultSet result = statement.executeQuery()) {

                if (!result.next()) {
                    return Optional.empty();
                }

                Municipio municipio = new Municipio(
                        result.getInt("id_municipio"),
                        result.getInt("id_provincia"),
                        result.getString("codigo_municipio"),
                        result.getString("nombre_municipio")
                );

                FechaNacimiento fechaNacimiento =
                        FechaNacimiento.crear(
                                result.getDate("fecha_nacimiento")
                                        .toLocalDate()
                        );

                Persona persona = new Persona(
                        result.getString("nombres"),
                        result.getString("apellidos"),
                        fechaNacimiento,
                        municipio
                );

                persona.asignarIdentificador(
                        result.getInt("id_persona")
                );

                return Optional.of(persona);
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al buscar la persona en PostgreSQL.",
                    e
            );
        }
    }

    @Override
    public boolean existePorId(Integer idPersona) {

        if (idPersona == null || idPersona <= 0) {
            return false;
        }

        String sql = """
                SELECT EXISTS (
                    SELECT 1
                    FROM public.persona
                    WHERE id_persona = ?
                )
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, idPersona);

            try (ResultSet result = statement.executeQuery()) {
                result.next();
                return result.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al verificar la existencia de la persona.",
                    e
            );
        }
    }
}

