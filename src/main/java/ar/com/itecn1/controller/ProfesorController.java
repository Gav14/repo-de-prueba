package ar.com.itecn1.controller;


import ar.com.itecn1.model.Profesor;
import ar.com.itecn1.service.ProfesorService;
import ar.com.itecn1.service.impl.ProfesorServiceImpl;

import java.util.List;


public class ProfesorController {
    private final ProfesorService profesorService;

    public ProfesorController() {this.profesorService = new ProfesorServiceImpl();}

    public Profesor findByDni(String dni){return this.profesorService.findByDni(dni);}

    public List<Profesor> findAll(){return this.profesorService.findAll();}

    public void crearProfesor(Profesor profesor){this.profesorService.save(profesor);}

    public void editarProfesor(Profesor profesor){this.profesorService.update(profesor);}

    public void eliminarProfesor(Profesor profesor){this.profesorService.delete(profesor);}

    public boolean validarFormatoDni(String dni) {
        return profesorService.validarFormatoDni(dni);
    }

    public boolean validarCampos(Profesor profesor) {
        return profesorService.validarCamposObligatorios(profesor);
    }
}
