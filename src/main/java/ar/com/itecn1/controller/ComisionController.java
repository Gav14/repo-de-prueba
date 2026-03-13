package ar.com.itecn1.controller;

import ar.com.itecn1.model.ComisionMateria;
import ar.com.itecn1.model.AlumnoInscriptoMateria;
import ar.com.itecn1.model.Asistencia;
import ar.com.itecn1.model.Examen;
import ar.com.itecn1.model.Tipo;
import ar.com.itecn1.service.ComisionService;
import ar.com.itecn1.service.impl.ComisionServiceImpl;

import java.util.List;

public class ComisionController {

    private final ComisionService comisionService;

    public ComisionController() {
        this.comisionService = new ComisionServiceImpl();
    }

    public ComisionMateria findByCode(String codigo) {
        return comisionService.findByCode(codigo);
    }

    public List<ComisionMateria> findAll() {
        return comisionService.findAll();
    }

    public List<ComisionMateria> findByCarrera(String nombreCarrera) {
        return comisionService.findByCarrera(nombreCarrera);
    }

    public List<ComisionMateria> findByMateria(String codigoMateria) {
        return comisionService.findByMateria(codigoMateria);
    }

    public List<ComisionMateria> findByCuatrimestre(String numeroCuatrimestre) {
        return comisionService.findByCuatrimestre(numeroCuatrimestre);
    }

    public List<ComisionMateria> findByProfesor(int dniProfesor) {
        return comisionService.findByProfesor(dniProfesor);
    }

    public String registrarComision(ComisionMateria comision) {
        return comisionService.registrarComision(comision);
    }

    public String inscribirAlumno(String codigoComision, String dniAlumno, AlumnoInscriptoMateria inscripcion) {
        return comisionService.inscribirAlumno(codigoComision, dniAlumno, inscripcion);
    }

    public String registrarAsistencia(String codigoComision, String dniAlumno, Asistencia asistencia) {
        return comisionService.registrarAsistencia(codigoComision, dniAlumno, asistencia);
    }

    public boolean puedeRegistrarAsistencia(String codigoComision, String dniAlumno) {
        return comisionService.puedeRegistrarAsistencia(codigoComision, dniAlumno);
    }

    public boolean puedeRendirExamen(String codigoComision, String dniAlumno, Tipo tipoExamen) {
        return comisionService.puedeRendirExamen(codigoComision, dniAlumno, tipoExamen);
    }

    public double calcularPorcentajeAsistencia(String codigoComision, String dniAlumno) {
        return comisionService.calcularPorcentajeAsistencia(codigoComision, dniAlumno);
    }

    public boolean tieneParcialAprobado(String codigoComision, String dniAlumno) {
        return comisionService.tieneParcialAprobado(codigoComision, dniAlumno);
    }

    public List<Examen> getExamenesAprobados(String codigoComision, String dniAlumno) {
        return comisionService.getExamenesAprobados(codigoComision, dniAlumno);
    }

    public String crearExamenConValidaciones(String codigoComision, Examen examen) {
        return comisionService.crearExamenConValidaciones(codigoComision, examen);
    }

    public void createComision(ComisionMateria comision) {
        comisionService.save(comision);
    }

    public void updateComision(ComisionMateria comision) {
        comisionService.update(comision);
    }

    public void deleteComision(ComisionMateria comision) {
        comisionService.delete(comision);
    }
}