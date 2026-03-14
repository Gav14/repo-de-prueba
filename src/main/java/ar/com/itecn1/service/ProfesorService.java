package ar.com.itecn1.service;

import ar.com.itecn1.model.Profesor;

import java.util.List;

public interface ProfesorService extends CRUDService<Profesor> {
    Profesor findByDni(String dni);
    boolean validarFormatoDni(String dni);
    boolean validarCamposObligatorios(Profesor profesor);
    boolean validarNombre(String nombre);
    boolean validarApellido(String apellido);
    boolean validarTelefono(String telefono);
    boolean validarEmail(String email);
    boolean validarProfesorCompleto(Profesor profesor);
}