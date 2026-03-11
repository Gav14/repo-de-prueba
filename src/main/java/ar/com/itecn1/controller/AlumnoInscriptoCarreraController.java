package ar.com.itecn1.controller;

import ar.com.itecn1.model.AlumnoInscriptoCarrera;
import ar.com.itecn1.service.AlumnoInscriptoCarreraService;
import ar.com.itecn1.service.impl.AlumnoInscriptoCarreraServImpl;

import java.util.List;

public class AlumnoInscriptoCarreraController {
    private final AlumnoInscriptoCarreraService alumnoInscriptoCarreraService;

    public AlumnoInscriptoCarreraController(){this.alumnoInscriptoCarreraService=new AlumnoInscriptoCarreraServImpl();}

    public AlumnoInscriptoCarrera findByDni(String dni){return this.alumnoInscriptoCarreraService.findByDni(dni);}

    public List<AlumnoInscriptoCarrera> findAll(){return this.alumnoInscriptoCarreraService.findAll();}

    public void save(AlumnoInscriptoCarrera alumnoInscriptoCarrera){this.alumnoInscriptoCarreraService.save(alumnoInscriptoCarrera);}

    public void update(AlumnoInscriptoCarrera alumnoInscriptoCarrera){this.alumnoInscriptoCarreraService.update(alumnoInscriptoCarrera);}

    public void delete(AlumnoInscriptoCarrera alumnoInscriptoCarrera){this.alumnoInscriptoCarreraService.delete(alumnoInscriptoCarrera);}



}
