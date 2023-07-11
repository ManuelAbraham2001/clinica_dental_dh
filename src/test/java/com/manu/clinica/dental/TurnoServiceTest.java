package com.manu.clinica.dental;

import com.manu.clinica.dental.Dto.OdontologoDTO;
import com.manu.clinica.dental.Dto.TurnoDTO;
import com.manu.clinica.dental.Dto.TurnoRequestDTO;
import com.manu.clinica.dental.Entity.*;
import com.manu.clinica.dental.Exceptions.*;
import com.manu.clinica.dental.Service.OdontologoService;
import com.manu.clinica.dental.Service.PacienteService;
import com.manu.clinica.dental.Service.TurnoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void crearTurnoTest() throws OdontologoAlreadyExistsException, ResourceNotFoundException, PacienteAlreadyExistsException, IllegalAccessException, TurnoAlreadyExistsException, ParseException, TurnoDateBeforeException {
        Odontologo odontologoAGuardar = new Odontologo();

        odontologoAGuardar.setNombre("Miguel");
        odontologoAGuardar.setApellido("Gomez");
        odontologoAGuardar.setMatricula("AB1245");

        Odontologo odontologoGuardado = odontologoService.crearOdontologo(odontologoAGuardar);

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
        pacienteAGuardar.setFechaIngreso(LocalDate.of(2023,Integer.parseInt("03"),7));
        pacienteAGuardar.setDireccion(domicilioPaciente);
        pacienteAGuardar.setEmail("pedrogomez@gmail.com");

        Paciente pacienteGuardado = pacienteService.crearPaciente(pacienteAGuardar);

        TurnoRequestDTO turnoRequestDTO = new TurnoRequestDTO();
        turnoRequestDTO.setMatricula(odontologoGuardado.getMatricula());
        turnoRequestDTO.setDocumento(pacienteGuardado.getDocumento());
        turnoRequestDTO.setHora(LocalTime.of(15, 30));
        turnoRequestDTO.setFecha(LocalDate.of(2023, Integer.parseInt("09"), 15));

        Turno turnoGuardado = turnoService.crearTurno(turnoRequestDTO);

        Long idOdontologoEsperado = 1L;
        String nombreOdontologoEsperado= "Miguel";

        Long idPacienteEsperado = 1L;
        String nombrePacienteEsperado = "Pedro";
        LocalDate fechaEsperada = LocalDate.of(2023, Integer.parseInt("09"),15);

        String nombreOdontologoObtenido = turnoGuardado.getOdontologo().getNombre();
        Long idOdontologoObtenido = turnoGuardado.getOdontologo().getId();
        String nombrePacienteObtenido = turnoGuardado.getPaciente().getNombre();
        Long idPacienteObtenido = turnoGuardado.getPaciente().getId();
        LocalDate fechaObtenida = turnoGuardado.getFecha();

        assertEquals(nombreOdontologoEsperado,nombreOdontologoObtenido);
        assertEquals(idOdontologoEsperado,idOdontologoObtenido);
        assertEquals(nombrePacienteEsperado,nombrePacienteObtenido);
        assertEquals(idPacienteEsperado,idPacienteObtenido);
        assertEquals(fechaEsperada,fechaObtenida);
    };

    @Test
    @Order(2)
    public void buscarTurnoPorIdTest() throws ResourceNotFoundException {
        Long idABuscar = 1L;
        Optional<Turno> turnoObtenido = turnoService.buscarTurnoPorId(idABuscar);

        assertNotNull(turnoObtenido.get());
    };

    @Test
    @Order(3)
    public void crearOtroTurnoTest() throws OdontologoAlreadyExistsException, ResourceNotFoundException, PacienteAlreadyExistsException, IllegalAccessException, TurnoAlreadyExistsException, ParseException, TurnoDateBeforeException {
        Odontologo odontologoAGuardar = new Odontologo();

        odontologoAGuardar.setNombre("German");
        odontologoAGuardar.setApellido("Garcia");
        odontologoAGuardar.setMatricula("ABC1245");

        Odontologo odontologoGuardado = odontologoService.crearOdontologo(odontologoAGuardar);

        Direccion domicilioPaciente = new Direccion();
        Provincia provincia = new Provincia("Buenos Aires");
        provincia.setId(1L);

        domicilioPaciente.setCalle("Brandsen");
        domicilioPaciente.setLocalidad("La Boca");
        domicilioPaciente.setNumero("502");
        domicilioPaciente.setProvincia(provincia);

        Paciente pacienteAGuardar = new Paciente();
        pacienteAGuardar.setNombre("Ramon");
        pacienteAGuardar.setApellido("Ramirez");
        pacienteAGuardar.setDocumento("45564349");
        pacienteAGuardar.setFechaIngreso(LocalDate.of(2023,Integer.parseInt("03"),7));
        pacienteAGuardar.setDireccion(domicilioPaciente);
        pacienteAGuardar.setEmail("ramonramirez@gmail.com");

        Paciente pacienteGuardado = pacienteService.crearPaciente(pacienteAGuardar);

        TurnoRequestDTO turnoRequestDTO = new TurnoRequestDTO();
        turnoRequestDTO.setMatricula(odontologoGuardado.getMatricula());
        turnoRequestDTO.setDocumento(pacienteGuardado.getDocumento());
        turnoRequestDTO.setHora(LocalTime.of(16, 30));
        turnoRequestDTO.setFecha(LocalDate.of(2023, 10, 15));

        Turno turnoGuardado = turnoService.crearTurno(turnoRequestDTO);

        Long idOdontologoEsperado = 2L;
        String nombreOdontologoEsperado= "German";

        Long idPacienteEsperado = 2L;
        String nombrePacienteEsperado = "Ramon";
        LocalDate fechaEsperada = LocalDate.of(2023,10,15);

        String nombreOdontologoObtenido = turnoGuardado.getOdontologo().getNombre();
        Long idOdontologoObtenido = turnoGuardado.getOdontologo().getId();
        String nombrePacienteObtenido = turnoGuardado.getPaciente().getNombre();
        Long idPacienteObtenido = turnoGuardado.getPaciente().getId();
        LocalDate fechaObtenida = turnoGuardado.getFecha();

        assertEquals(nombreOdontologoEsperado,nombreOdontologoObtenido);
        assertEquals(idOdontologoEsperado,idOdontologoObtenido);
        assertEquals(nombrePacienteEsperado,nombrePacienteObtenido);
        assertEquals(idPacienteEsperado,idPacienteObtenido);
        assertEquals(fechaEsperada,fechaObtenida);
    };

    @Test
    @Order(4)
    public void buscarTodosLosTurnosTest(){
        int valorEsperado = 2;
        int valorActual = turnoService.listarTodosLosTurnos().size();
        assertEquals(valorEsperado,valorActual);
    }

    @Test
    @Order(5)
    public void actualizarUnTurnoTest() throws TurnoAlreadyExistsException, ParseException, ResourceNotFoundException, TurnoDateBeforeException {

        TurnoRequestDTO turnoRequestDTO = new TurnoRequestDTO();
        turnoRequestDTO.setId(2L);
        turnoRequestDTO.setMatricula("ABC1245");
        turnoRequestDTO.setDocumento("45564349");
        turnoRequestDTO.setHora(LocalTime.of(12, 30));
        turnoRequestDTO.setFecha(LocalDate.of(2023, 12, 15));

        turnoService.actualizarTurno(turnoRequestDTO);

        Optional<Turno> turnoActualizado = turnoService.buscarTurnoPorId(2L);

        String nombreOdontologoEsperado = "German";
        String nombreOdontologoObtenido = turnoActualizado.get().getOdontologo().getNombre();

        String nombrePacienteEsperado = "Ramon";
        String nombrePacienteObtenido = turnoActualizado.get().getPaciente().getNombre();

        assertEquals(nombreOdontologoEsperado,nombreOdontologoObtenido);
        assertEquals(nombrePacienteEsperado,nombrePacienteObtenido);

    }

    @Test
    @Order(6)
    public void eliminarUnTurnoTest() throws ResourceNotFoundException {
        turnoService.eliminarTurno(1L);
        turnoService.eliminarTurno(2L);

        int cantDeTurnosEsperados = 0;
        int cantDeTurnosObtenidos = turnoService.listarTodosLosTurnos().size();

        assertEquals(cantDeTurnosEsperados,cantDeTurnosObtenidos);
    }

}
