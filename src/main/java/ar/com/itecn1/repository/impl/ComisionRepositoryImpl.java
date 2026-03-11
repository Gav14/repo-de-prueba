package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.ComisionMateria;
import ar.com.itecn1.repository.ComisionRepository;

import java.util.ArrayList;
import java.util.List;

public class ComisionRepositoryImpl implements ComisionRepository {
    private final List<ComisionMateria> comisionMateriaDb;

    public ComisionRepositoryImpl() {
        this.comisionMateriaDb = new ArrayList<>();
        cargarDatos();
    }

    private void cargarDatos(){}

    @Override
    public ComisionMateria findByCode(String codigoComision) {
        ComisionMateria comision = null;
        for(ComisionMateria comisionResult: this.comisionMateriaDb){
            if(comisionResult.getCodigo().equals(codigoComision)){
                comision = comisionResult;
                break;
            }
        }
        return comision;
    }

    @Override
    public List<ComisionMateria> findAll() {
        return this.comisionMateriaDb;
    }

    @Override
    public void save(ComisionMateria comisionMateria) {
        for(ComisionMateria comisionResult: this.comisionMateriaDb){
            if(comisionResult.getCodigo().equals(comisionMateria.getCodigo())){
                return;
            }
        }
        this.comisionMateriaDb.add(comisionMateria);
    }

    @Override
    public void update(ComisionMateria comisionMateria) {
        for(ComisionMateria comisionResult: this.comisionMateriaDb){
            if (comisionResult.getCodigo().equals(comisionMateria.getCodigo())){
                comisionResult.setCarrera(comisionMateria.getCarrera());
                comisionResult.setMateria(comisionMateria.getMateria());
                comisionResult.setProfesor(comisionMateria.getProfesor());
                comisionResult.setCuatrimestre(comisionMateria.getCuatrimestre());
                comisionResult.setAsistencias(comisionMateria.getAsistencias());
                comisionResult.setExamenes(comisionMateria.getExamenes());
                comisionResult.setAlumnoInscriptos(comisionMateria.getAlumnoInscriptos());
                comisionResult.setHorarios(comisionMateria.getHorarios());
                return;
            }
        }
    }

    @Override
    public void delete(ComisionMateria comisionMateria) {
        for(ComisionMateria comisionResult: this.comisionMateriaDb){
            if (comisionResult.getCodigo().equals(comisionMateria.getCodigo())){
                comisionResult.setActivo(false);
                return;
            }
        }
    }
}
