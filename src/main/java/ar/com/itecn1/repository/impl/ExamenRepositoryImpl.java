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
//        Alumno mauro = this.alumnoRepository.findByDni("11111111");
//        Alumno lucas = this.alumnoRepository.findByDni("22222222");
//        Alumno luciano = this.alumnoRepository.findByDni("33333333");
//        Alumno maurog = this.alumnoRepository.findByDni("44444444");
//
//        Materia matematica1 = this.materiaRepository.findByCode("An25Mat1");
//
//        Examen examen1 = new Examen("1", Tipo.PARCIAL, LocalDate.now(), 9.00, mauro, matematica1);
//        Examen examen2 = new Examen("2", Tipo.PARCIAL, LocalDate.now(), 7.00, lucas, matematica1);
//        Examen examen3 = new Examen("3", Tipo.PARCIAL, LocalDate.now(), 9.50, luciano, matematica1);
//        Examen examen4 = new Examen("4", Tipo.PARCIAL, LocalDate.now(), 10.00, maurog, matematica1);
//
//        examenDb.add(examen1);
//        examenDb.add(examen2);
//        examenDb.add(examen3);
//        examenDb.add(examen4);
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
