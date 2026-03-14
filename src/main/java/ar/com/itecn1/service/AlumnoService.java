package ar.com.itecn1.service;

import ar.com.itecn1.model.Alumno;

import java.util.List;

public interface AlumnoService extends CRUDService<Alumno> {
    Alumno findByDni(String dni);
    boolean validarFormatoDni(String dni);
    boolean validarCamposObligatorios(Alumno alumno);
    boolean validarNombre(String nombre);
    boolean validarApellido(String apellido);
    boolean validarTelefono(String telefono); // NUEVO
    boolean validarEmail(String email);
    boolean validarAlumnoCompleto(Alumno alumno);
}