package ar.com.itecn1.service;

import ar.com.itecn1.model.ComisionMateria;
import ar.com.itecn1.model.AlumnoInscriptoMateria;
import ar.com.itecn1.model.Asistencia;
import ar.com.itecn1.model.Examen;
import ar.com.itecn1.model.Tipo;
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

    // Métodos para validación de exámenes
    boolean puedeRendirExamen(String codigoComision, String dniAlumno, Tipo tipoExamen);
    double calcularPorcentajeAsistencia(String codigoComision, String dniAlumno);
    boolean tieneParcialAprobado(String codigoComision, String dniAlumno);
    List<Examen> getExamenesAprobados(String codigoComision, String dniAlumno);
    String crearExamenConValidaciones(String codigoComision, Examen examen);
}