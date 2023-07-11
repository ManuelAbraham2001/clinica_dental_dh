package com.manu.clinica.dental;

import com.manu.clinica.dental.Entity.Direccion;
import com.manu.clinica.dental.Entity.Paciente;
import com.manu.clinica.dental.Entity.Provincia;
import com.manu.clinica.dental.Exceptions.PacienteAlreadyExistsException;
import com.manu.clinica.dental.Exceptions.ResourceNotFoundException;
import com.manu.clinica.dental.Service.PacienteService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    void guardarPaciente() throws PacienteAlreadyExistsException, IllegalAccessException {
        Direccion domicilioPaciente = new Direccion();
        Provincia provincia = new Provincia("Buenos Aires");
        provincia.setId(1L);

        domicilioPaciente.setCalle("Alvarado");
        domicilioPaciente.setLocalidad("Tigre");
        domicilioPaciente.setNumero("24");
        domicilioPaciente.setProvincia(provincia);

        Paciente pacienteAGuardar = new Paciente();
        pacienteAGuardar.setNombre("Pedro");
        pacienteAGuardar.setApellido("Gomez");
        pacienteAGuardar.setDocumento("45785649");
        pacienteAGuardar.setFechaIngreso(LocalDate.of(2023,03,7));
        pacienteAGuardar.setDireccion(domicilioPaciente);
        pacienteAGuardar.setEmail("pedrogomez@gmail.com");

        Paciente pacienteGuardado = pacienteService.crearPaciente(pacienteAGuardar);

        assertEquals(1L, pacienteGuardado.getId());

    }

    @Test
    @Order(2)
    void guardarOtroPaciente() throws PacienteAlreadyExistsException, IllegalAccessException {
        Direccion domicilioPaciente = new Direccion();
        Provincia provincia = new Provincia("Catamarca");
        provincia.setId(2L);

        domicilioPaciente.setCalle("Mitre");
        domicilioPaciente.setLocalidad("San Martin");
        domicilioPaciente.setNumero("3138");
        domicilioPaciente.setProvincia(provincia);

        Paciente pacienteAGuardar = new Paciente();
        pacienteAGuardar.setNombre("Pablo");
        pacienteAGuardar.setApellido("Lopez");
        pacienteAGuardar.setDocumento("35987465");
        pacienteAGuardar.setFechaIngreso(LocalDate.of(2023,03,7));
        pacienteAGuardar.setDireccion(domicilioPaciente);
        pacienteAGuardar.setEmail("pablolopez@gmail.com");

        Paciente pacienteGuardado = pacienteService.crearPaciente(pacienteAGuardar);

        assertEquals(2L, pacienteGuardado.getId());

    }

    @Test
    @Order(3)
    public void buscarPacientePorIdTest() throws ResourceNotFoundException {
        Long idABuscar=1L;
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(idABuscar);

        assertNotNull(pacienteBuscado.get());
    }

    @Test
    @Order(4)
    public void buscarTodosLosPacientesTest(){
        int cantidadEsperadaDePacientes = 2;
        int cantActualDePacientes = pacienteService.listarTodosLosPacientes().size();

        assertEquals(cantidadEsperadaDePacientes,cantActualDePacientes);
    }

    @Test
    @Order(5)
    void actualizarPaciente() throws PacienteAlreadyExistsException, IllegalAccessException, ResourceNotFoundException {
        Direccion domicilioPaciente = new Direccion();
        Provincia provincia = new Provincia("Buenos Aires");
        provincia.setId(1L);

        domicilioPaciente.setCalle("Ramon Carrillo");
        domicilioPaciente.setLocalidad("San Martin");
        domicilioPaciente.setNumero("2568");
        domicilioPaciente.setProvincia(provincia);

        Paciente pacienteAGuardar = new Paciente();
        pacienteAGuardar.setId(2L);
        pacienteAGuardar.setNombre("Pablo Miguel");
        pacienteAGuardar.setApellido("Lopez");
        pacienteAGuardar.setDocumento("35987465");
        pacienteAGuardar.setFechaIngreso(LocalDate.of(2023,03,7));
        pacienteAGuardar.setDireccion(domicilioPaciente);
        pacienteAGuardar.setEmail("pablolopez@gmail.com");

        pacienteService.actualizarPaciente(pacienteAGuardar);


        Optional<Paciente> pacienteActualizado = pacienteService.buscarPacientePorId(pacienteAGuardar.getId());
        assertNotNull(pacienteActualizado.get());
        assertEquals("Pablo Miguel", pacienteActualizado.get().getNombre());
        assertEquals("Lopez", pacienteActualizado.get().getApellido());
    }

    @Test
    @Order(6)
    public void eliminarPacientesTest() throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(1L);
        pacienteService.eliminarPaciente(2L);

        int cantEsperadaDePacientes = 0;
        int cantActualDePacientes = pacienteService.listarTodosLosPacientes().size();

        assertEquals(cantEsperadaDePacientes,cantActualDePacientes);
    }
}
