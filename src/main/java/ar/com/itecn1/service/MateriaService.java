package ar.com.itecn1.service;

import ar.com.itecn1.model.Materia;
import java.util.List;
import java.util.Map;

public interface MateriaService extends CRUDService<Materia> {
    Materia findByCode(String codigoMateria);
    String agregarCorrelativa(String codigoMateria, String codigoCorrelativa);
    String quitarCorrelativa(String codigoMateria, String codigoCorrelativa);
    List<Materia> findMateriasDisponiblesParaCorrelativa(Materia materiaActual);
    List<Materia> findMateriasByCuatrimestre(String cuatrimestre);
    Map<String, List<Materia>> agruparCorrelativasPorCuatrimestre(Materia materia);
}