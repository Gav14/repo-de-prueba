package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.Alumno;
import ar.com.itecn1.model.Examen;
import ar.com.itecn1.model.Materia;
import ar.com.itecn1.model.Tipo;
import ar.com.itecn1.repository.AlumnoRepository;
import ar.com.itecn1.repository.ExamenRepository;
import ar.com.itecn1.repository.MateriaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExamenRepositoryImpl implements ExamenRepository {
    private final List<Examen> examenDb;
    private final AlumnoRepository alumnoRepository;
    private final MateriaRepository materiaRepository;

    public ExamenRepositoryImpl() {
        this.examenDb = new ArrayList<>();
        this.alumnoRepository = AlumnoRepositoryImpl.getInstancia();
        this.materiaRepository = new MateriaRepositoryImpl();
        cargarDatos();
    }

    private void cargarDatos(){
    }

    @Override
    public Examen findById(String idExamen) {
        Examen examen = null;
        for(Examen examenResult: this.examenDb){
            if(examenResult.getId().equals(idExamen)){
                examen = examenResult;
                break;
            }
        }
        return examen;
    }

    @Override
    public List<Examen> findAll() {
        return this.examenDb;
    }

    @Override
    public void save(Examen examen) {
        for(Examen examenResult: this.examenDb){
            if(examenResult.getId().equals(examen.getId())){
                return;
            }
        }
        this.examenDb.add(examen);
    }

    @Override
    public void update(Examen examen) {
        for(Examen examenResult: this.examenDb){
            if(examenResult.getId().equals(examen.getId())){
                examenResult.setTipo(examen.getTipo());
                examenResult.setFecha(examen.getFecha());
                examenResult.setNota(examen.getNota());
                examenResult.setAlumno(examen.getAlumno());
                examenResult.setMateria(examen.getMateria());
            }
        }
    }

    @Override
    public void delete(Examen examen) {
        for(Examen examenResult: this.examenDb){
            if(examenResult.getId().equals(examen.getId())){
                examenResult.setActivo(false);
            }
        }
    }
}
