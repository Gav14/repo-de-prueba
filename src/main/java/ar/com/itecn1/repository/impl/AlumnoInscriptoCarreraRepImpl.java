package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.*;
import ar.com.itecn1.repository.AlumnoInscriptoCarreraRepository;
import ar.com.itecn1.repository.AlumnoRepository;
import ar.com.itecn1.repository.CarreraRepository;
import ar.com.itecn1.repository.PlanEstudioRepository;

import java.util.ArrayList;
import java.util.List;

public class AlumnoInscriptoCarreraRepImpl implements AlumnoInscriptoCarreraRepository {
    private final List<AlumnoInscriptoCarrera> inscriptosDb;
    private final AlumnoRepository alumnoRepository;
    private final PlanEstudioRepository planEstudioRepository;
    private final CarreraRepository carreraRepository;

    public AlumnoInscriptoCarreraRepImpl() {
        this.inscriptosDb = new ArrayList<>();
        this.alumnoRepository = new AlumnoRepositoryImpl();
        this.planEstudioRepository = new PlanEstudioRepositoryImpl();
        this.carreraRepository = new CarreraRepositoryImpl();
        cargarDatos();
    }

    private void cargarDatos(){
        Carrera carreraAnalista = carreraRepository.findByName("Tecnicatura en analisis de sistemas");
        Carrera carreraGastronomia = carreraRepository.findByName("Tecnicatura en gastronomia");
        Alumno alumnoMauro = this.alumnoRepository.findByDni("11111111");
        Alumno alumnoLucas = this.alumnoRepository.findByDni("22222222");
        Alumno alumnoLuciano = this.alumnoRepository.findByDni("33333333");
        Alumno alumnoMauroG = this.alumnoRepository.findByDni("44444444");

        PlanEstudio planAnalista = planEstudioRepository.findByName("Analista");
        PlanEstudio planGastronomia = this.planEstudioRepository.findByName("Gastronomia");


        AlumnoInscriptoCarrera inscripto1 = new AlumnoInscriptoCarrera("2025",carreraAnalista, alumnoMauro, planAnalista);
        AlumnoInscriptoCarrera inscripto2 = new AlumnoInscriptoCarrera("2025",carreraAnalista, alumnoLucas, planAnalista);
        AlumnoInscriptoCarrera inscripto3 = new AlumnoInscriptoCarrera("2025",carreraAnalista, alumnoLuciano, planAnalista);
        AlumnoInscriptoCarrera inscripto4 = new AlumnoInscriptoCarrera("2025",carreraAnalista, alumnoMauroG, planAnalista);
        AlumnoInscriptoCarrera inscripto5 = new AlumnoInscriptoCarrera("2025",carreraGastronomia,alumnoLucas,planAnalista);

        this.inscriptosDb.add(inscripto1);
        this.inscriptosDb.add(inscripto2);
        this.inscriptosDb.add(inscripto3);
        this.inscriptosDb.add(inscripto4);
        this.inscriptosDb.add(inscripto5);

    }

    @Override
    public AlumnoInscriptoCarrera findByDni(String dni) {
        AlumnoInscriptoCarrera inscripto = null;
        for(AlumnoInscriptoCarrera aic: this.inscriptosDb){
            if(aic.getAlumno().getDni().equals(dni)){
                inscripto = aic;
                break;
            }
        }
        return inscripto;
    }

    @Override
    public List<AlumnoInscriptoCarrera> findAll() {
        return this.inscriptosDb;
    }

    @Override
    public void save(AlumnoInscriptoCarrera inscripto) {
        for(AlumnoInscriptoCarrera aic: this.inscriptosDb){
            if(aic.getAlumno().getDni().equals(inscripto.getAlumno().getDni()) && aic.getCarrera().getNombre().equals(inscripto.getCarrera().getNombre()) ){
                return;
            }
        }
        this.inscriptosDb.add(inscripto);
    }

    @Override
    public void update(AlumnoInscriptoCarrera inscripto) {
        for(AlumnoInscriptoCarrera aic: this.inscriptosDb){
            if(aic.getAlumno().getDni().equals(inscripto.getAlumno().getDni())){
                aic.setCarrera(inscripto.getCarrera());
                aic.setAlumno(inscripto.getAlumno());
                aic.setAnioIngreso(inscripto.getAnioIngreso());
                aic.setPlanEstudio(inscripto.getPlanEstudio());
                return;
            }
        }
    }

    @Override
    public void delete(AlumnoInscriptoCarrera inscripto) {
        for(AlumnoInscriptoCarrera aic: this.inscriptosDb) {
            if(aic.getAlumno().getDni().equals(inscripto.getAlumno().getDni())
            && aic.getCarrera().getNombre().equalsIgnoreCase(inscripto.getCarrera().getNombre())){
                aic.setActivo(false);
                return;
            }
        }
    }
}
