package ar.com.itecn1.controller;

import ar.com.itecn1.model.Materia;
import ar.com.itecn1.service.MateriaService;
import ar.com.itecn1.service.impl.MateriaServiceImpl;

import java.util.List;

public class MateriaController {
    private final MateriaService materiaService;

    public MateriaController(){this.materiaService = new MateriaServiceImpl();}

    public Materia findByCode(String codigoMateria){return this.materiaService.findByCode(codigoMateria);}

    public List<Materia> findAll(){return this.materiaService.findAll();}

    public void crearMateria(Materia materia){this.materiaService.save(materia);}

    public void editarMateria(Materia materia){this.materiaService.save(materia);}

    public void eliminarMateria(Materia materia){this.materiaService.delete(materia);}
}
