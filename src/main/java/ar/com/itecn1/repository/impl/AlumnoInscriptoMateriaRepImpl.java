package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.AlumnoInscriptoMateria;
import ar.com.itecn1.repository.AlumnoInscriptoCarreraRepository;
import ar.com.itecn1.repository.AlumnoInscriptoMateriaRepository;


import java.util.ArrayList;
import java.util.List;

public class AlumnoInscriptoMateriaRepImpl implements AlumnoInscriptoMateriaRepository {

    private final List<AlumnoInscriptoMateria> inscriptosDb;
    private final AlumnoInscriptoCarreraRepository alumnoInscriptoCarreraRepository;

    public AlumnoInscriptoMateriaRepImpl() {
        this.inscriptosDb = new ArrayList<>();
        this.alumnoInscriptoCarreraRepository = new AlumnoInscriptoCarreraRepImpl();
        cargarDatos();
    }

    private void cargarDatos(){}

    @Override
    public AlumnoInscriptoMateria findByAlumnoDni(String dni){
       AlumnoInscriptoMateria inscripto = null;
       for(AlumnoInscriptoMateria aim: this.inscriptosDb){
           if(aim.getAlumnoInscriptoCarrera().getAlumno().getDni().equals(dni)){
               inscripto = aim;
               break;
           }
       }
       return inscripto;
   }

    @Override
    public List<AlumnoInscriptoMateria> findAll(){return this.inscriptosDb;}

    @Override
    public void save(AlumnoInscriptoMateria inscripto){
        for(AlumnoInscriptoMateria aim: this.inscriptosDb){
            if(aim.getAlumnoInscriptoCarrera().getAlumno().getDni().equals(inscripto.getAlumnoInscriptoCarrera().getAlumno().getDni())){
                return;
            }
        }
        this.inscriptosDb.add(inscripto);
    }

    @Override
    public void update(AlumnoInscriptoMateria inscripto){
        for(AlumnoInscriptoMateria aim: this.inscriptosDb){
            if(aim.getAlumnoInscriptoCarrera().getAlumno().getDni().equals(inscripto.getAlumnoInscriptoCarrera().getAlumno().getDni())){
                aim.setAlumnoInscriptoCarrera(inscripto.getAlumnoInscriptoCarrera());
                aim.setExamenes(inscripto.getExamenes());
                aim.setEstado(inscripto.getEstado());
                return;
            }
        }
    }

    @Override
    public void delete(AlumnoInscriptoMateria inscripto){
        for(AlumnoInscriptoMateria aim: this.inscriptosDb) {
            if (aim.getAlumnoInscriptoCarrera().getAlumno().getDni().equals(inscripto.getAlumnoInscriptoCarrera().getAlumno().getDni())) {
                aim.setActivo(false);
                return;
            }
        }
    }
}
