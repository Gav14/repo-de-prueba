package ar.com.itecn1.controller;


import ar.com.itecn1.model.AlumnoInscriptoMateria;
import ar.com.itecn1.service.AlumnoInscriptoMateriaService;
import ar.com.itecn1.service.impl.AlumnoInscriptoMateriaServImpl;

import java.util.List;

public class AlumnoInscriptoMateriaController {
    private final AlumnoInscriptoMateriaService alumnoInscriptoMateriaService;


    public AlumnoInscriptoMateriaController(){this.alumnoInscriptoMateriaService = new AlumnoInscriptoMateriaServImpl();}

    public AlumnoInscriptoMateria findByAlumnoDni(String dni){return this.alumnoInscriptoMateriaService.findByAlumnoDni(dni);}

    public List<AlumnoInscriptoMateria> findAll(){return this.alumnoInscriptoMateriaService.findAll();}

    public void save(AlumnoInscriptoMateria alumnoInscriptoMateria){this.alumnoInscriptoMateriaService.save(alumnoInscriptoMateria);}

    public void update(AlumnoInscriptoMateria alumnoInscriptoMateria){this.alumnoInscriptoMateriaService.save(alumnoInscriptoMateria);}

    public void delete(AlumnoInscriptoMateria alumnoInscriptoMateria){this.alumnoInscriptoMateriaService.delete(alumnoInscriptoMateria);}

}
