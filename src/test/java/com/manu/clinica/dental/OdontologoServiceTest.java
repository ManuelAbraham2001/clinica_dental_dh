package com.manu.clinica.dental;

import com.manu.clinica.dental.Entity.Odontologo;
import com.manu.clinica.dental.Exceptions.OdontologoAlreadyExistsException;
import com.manu.clinica.dental.Exceptions.ResourceNotFoundException;
import com.manu.clinica.dental.Service.OdontologoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class OdontologoServiceTest {
    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarOdontologoTest() throws OdontologoAlreadyExistsException, ResourceNotFoundException {
        Odontologo odontologoAGuardar = new Odontologo();

        odontologoAGuardar.setNombre("Miguel");
        odontologoAGuardar.setApellido("Gomez");
        odontologoAGuardar.setMatricula("AB1245");

        Odontologo odontologoGuardado = odontologoService.crearOdontologo(odontologoAGuardar);

        Long idEsperado = 1L;
        String nombreEsperado= "Miguel";
        String apellidoEsperado= "Gomez";
        String matriculaEsperada= "AB1245";

        assertEquals(idEsperado,odontologoGuardado.getId());
        assertEquals(nombreEsperado,odontologoGuardado.getNombre());
        assertEquals(apellidoEsperado,odontologoGuardado.getApellido());
        assertEquals(matriculaEsperada,odontologoGuardado.getMatricula());
    }

    @Test
    @Order(2)
    public void buscarOdontologoPorIdTest() throws ResourceNotFoundException {
        Long idABuscar = 1L;
        Odontologo odontologoBuscado = odontologoService.buscarOdontologoPorId(idABuscar);
        assertNotNull(odontologoBuscado);
    }

    @Test
    @Order(3)
    public void guardarUnSegundoOdontologoTest() throws OdontologoAlreadyExistsException, ResourceNotFoundException {
        Odontologo odontologoAGuardar = new Odontologo();

        odontologoAGuardar.setNombre("Dario");
        odontologoAGuardar.setApellido("Aguirre");
        odontologoAGuardar.setMatricula("AB2356");

        Odontologo odontologoGuardado = odontologoService.crearOdontologo(odontologoAGuardar);

        Long idEsperado = 2L;
        String nombreEsperado= "Dario";

        assertEquals(idEsperado,odontologoGuardado.getId());
        assertEquals(nombreEsperado,odontologoGuardado.getNombre());
    }

    @Test
    @Order(4)
    public void buscarTodosLosOdontologosTest(){
        int cantidadEsperadaDeOdontologos = 2;
        int cantidadActualDeOdontologos= odontologoService.listarTodosLosOdontologos().size();

        assertEquals(cantidadEsperadaDeOdontologos,cantidadActualDeOdontologos);
    }

    @Test
    @Order(5)
    public void actualizarOdontologoTest() throws OdontologoAlreadyExistsException, ResourceNotFoundException {
        Odontologo odontologoAActualizar = new Odontologo();

        odontologoAActualizar.setId(1L);
        odontologoAActualizar.setNombre("Juan");
        odontologoAActualizar.setApellido("Gomez");
        odontologoAActualizar.setMatricula("AB1289");

        odontologoService.actualizarOdontologo(odontologoAActualizar);

        String nombreEsperado = "Juan";
        String apellidoEsperado = "Gomez";

        Odontologo odontologoActualizado = odontologoService.buscarOdontologoPorId(odontologoAActualizar.getId());

        assertEquals(nombreEsperado,odontologoActualizado.getNombre());
        assertEquals(apellidoEsperado,odontologoActualizado.getApellido());
    }

    @Test
    @Order(6)
    public void eliminarOdontologoTest() throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(1L);
        odontologoService.eliminarOdontologo(2L);

        int cantEsperadaDeOdontologos = 0;
        int cantActualDeOdontologos = odontologoService.listarTodosLosOdontologos().size();

        assertEquals(cantEsperadaDeOdontologos,cantActualDeOdontologos);
    }
}
