package ar.com.itecn1.service;

import ar.com.itecn1.model.ComisionMateria;
import ar.com.itecn1.model.AlumnoInscriptoMateria;
import ar.com.itecn1.model.Asistencia;
import java.util.List;

public interface ComisionService extends CRUDService<ComisionMateria> {
    ComisionMateria findByCode(String codigoComision);
    List<ComisionMateria> findByCarrera(String nombreCarrera);
    List<ComisionMateria> findByMateria(String codigoMateria);
    List<ComisionMateria> findByCuatrimestre(String numeroCuatrimestre);
    List<ComisionMateria> findByProfesor(int dniProfesor);

    String registrarComision(ComisionMateria comision);
    String inscribirAlumno(String codigoComision, String dniAlumno, AlumnoInscriptoMateria inscripcion);
    String registrarAsistencia(String codigoComision, String dniAlumno, Asistencia asistencia);
    boolean puedeRegistrarAsistencia(String codigoComision, String dniAlumno);
}