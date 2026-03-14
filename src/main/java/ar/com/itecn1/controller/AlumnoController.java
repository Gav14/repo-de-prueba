package ar.com.itecn1.controller;

import ar.com.itecn1.model.Alumno;
import ar.com.itecn1.service.AlumnoService;
import ar.com.itecn1.service.impl.AlumnoServiceImpl;

import java.util.List;

public class AlumnoController {
    private final AlumnoService alumnoService;

    public AlumnoController() {
        this.alumnoService = new AlumnoServiceImpl();
    }

    public Alumno findByDni(String dni){
        return this.alumnoService.findByDni(dni);
    }

    public List<Alumno> findAll(){
        return this.alumnoService.findAll();
    }

    public void createAlumno(Alumno alumno){
        this.alumnoService.save(alumno);
    }

    public void updateAlumno(Alumno alumno){
        this.alumnoService.update(alumno);
    }

    public void deleteAlumno(Alumno alumno){
        this.alumnoService.delete(alumno);
    }

    public boolean validarDni(String dni) {
        return alumnoService.validarFormatoDni(dni);
    }

    public boolean validarCampos(Alumno alumno) {
        return alumnoService.validarCamposObligatorios(alumno);
    }

    public boolean validarNombre(String nombre) {
        return alumnoService.validarNombre(nombre);
    }

    public boolean validarApellido(String apellido) {
        return alumnoService.validarApellido(apellido);
    }

    // NUEVO: Método para validar teléfono
    public boolean validarTelefono(String telefono) {
        return alumnoService.validarTelefono(telefono);
    }

    public boolean validarEmail(String email) {
        return alumnoService.validarEmail(email);
    }

    public boolean validarAlumnoCompleto(Alumno alumno) {
        return alumnoService.validarAlumnoCompleto(alumno);
    }
}