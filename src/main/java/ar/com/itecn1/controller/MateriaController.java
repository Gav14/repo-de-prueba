package ar.com.itecn1.controller;

import ar.com.itecn1.model.Materia;
import ar.com.itecn1.service.MateriaService;
import ar.com.itecn1.service.impl.MateriaServiceImpl;
import java.util.List;
import java.util.Map;

public class MateriaController {
    private final MateriaService materiaService;

    public MateriaController() {
        this.materiaService = new MateriaServiceImpl();
    }

    public Materia findByCode(String codigoMateria) {
        return materiaService.findByCode(codigoMateria);
    }

    public List<Materia> findAll() {
        return materiaService.findAll();
    }

    public List<Materia> findMateriasByCuatrimestre(String cuatrimestre) {
        return materiaService.findMateriasByCuatrimestre(cuatrimestre);
    }

    public Map<String, List<Materia>> agruparCorrelativasPorCuatrimestre(Materia materia) {
        return materiaService.agruparCorrelativasPorCuatrimestre(materia);
    }

    public void save(Materia materia) {
        materiaService.save(materia);
    }

    public void update(Materia materia) {
        materiaService.update(materia);
    }

    public void delete(Materia materia) {
        materiaService.delete(materia);
    }

    public String agregarCorrelativa(String codigoMateria, String codigoCorrelativa) {
        return materiaService.agregarCorrelativa(codigoMateria, codigoCorrelativa);
    }

    public String quitarCorrelativa(String codigoMateria, String codigoCorrelativa) {
        return materiaService.quitarCorrelativa(codigoMateria, codigoCorrelativa);
    }

    public List<Materia> findMateriasDisponiblesParaCorrelativa(Materia materiaActual) {
        return materiaService.findMateriasDisponiblesParaCorrelativa(materiaActual);
    }
}