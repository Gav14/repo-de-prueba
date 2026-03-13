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
        this.alumnoRepository = AlumnoRepositoryImpl.getInstancia();
        this.planEstudioRepository = new PlanEstudioRepositoryImpl();
        this.carreraRepository = new CarreraRepositoryImpl();
        cargarDatos();
    }

    private void cargarDatos(){

        Carrera carreraAnalista = carreraRepository.findByName("Tecnicatura en Analisis de Sistemas");

        PlanEstudio planAnalista = planEstudioRepository.findByName("Analista");

        Carrera carreraGastronomia = carreraRepository.findByName("Tecnicatura en Gastronomia");

        PlanEstudio planGastronomia = planEstudioRepository.findByName("Gastronomia");

        Alumno alumno1 = alumnoRepository.findByDni("43563697");
        Alumno alumno2 = alumnoRepository.findByDni("47426591");
        Alumno alumno3 = alumnoRepository.findByDni("44231908");
        Alumno alumno4 = alumnoRepository.findByDni("46023942");
        Alumno alumno5 = alumnoRepository.findByDni("48472019");
        Alumno alumno6 = alumnoRepository.findByDni("33125487");
        Alumno alumno7 = alumnoRepository.findByDni("34789621");
        Alumno alumno8 = alumnoRepository.findByDni("35641278");
        Alumno alumno9 = alumnoRepository.findByDni("36874125");
        Alumno alumno10 = alumnoRepository.findByDni("37254169");
        Alumno alumno11 = alumnoRepository.findByDni("38451236");
        Alumno alumno12 = alumnoRepository.findByDni("39547128");
        Alumno alumno13 = alumnoRepository.findByDni("40125896");
        Alumno alumno14 = alumnoRepository.findByDni("41254789");
        Alumno alumno15 = alumnoRepository.findByDni("42365124");
        Alumno alumno16 = alumnoRepository.findByDni("43251478");
        Alumno alumno17 = alumnoRepository.findByDni("44521478");
        Alumno alumno18 = alumnoRepository.findByDni("32987451");
        Alumno alumno19 = alumnoRepository.findByDni("33478521");
        Alumno alumno20 = alumnoRepository.findByDni("34871236");
        Alumno alumno21 = alumnoRepository.findByDni("35987412");
        Alumno alumno22 = alumnoRepository.findByDni("36451278");
        Alumno alumno23 = alumnoRepository.findByDni("37854123");
        Alumno alumno24 = alumnoRepository.findByDni("38974512");
        Alumno alumno25 = alumnoRepository.findByDni("39784512");
        Alumno alumno26 = alumnoRepository.findByDni("40214587");
        Alumno alumno27 = alumnoRepository.findByDni("41578412");
        Alumno alumno28 = alumnoRepository.findByDni("42874512");
        Alumno alumno29 = alumnoRepository.findByDni("43984512");
        Alumno alumno30 = alumnoRepository.findByDni("44781235");
        Alumno alumno31 = alumnoRepository.findByDni("33214587");
        Alumno alumno32 = alumnoRepository.findByDni("34659874");
        Alumno alumno33 = alumnoRepository.findByDni("47426591");

        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno1, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno2, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno3, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno4, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno5, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno6, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno7, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno8, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno9, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno10, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno11, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno12, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno13, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno14, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno15, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraAnalista, alumno16, planAnalista));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno17, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno18, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno19, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno20, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno21, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno22, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno23, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno24, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno25, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno26, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno27, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno28, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno29, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno30, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno31, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno32, planGastronomia));
        this.inscriptosDb.add(new AlumnoInscriptoCarrera("2025", carreraGastronomia, alumno33, planGastronomia));
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
            if(aic.getAlumno().getDni().equals(inscripto.getAlumno().getDni())
                    && aic.getCarrera().getNombre().equals(inscripto.getCarrera().getNombre())){
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

        for(AlumnoInscriptoCarrera aic: this.inscriptosDb){
            if(aic.getAlumno().getDni().equals(inscripto.getAlumno().getDni())
                    && aic.getCarrera().getNombre().equalsIgnoreCase(inscripto.getCarrera().getNombre())){
                aic.setActivo(false);
                return;
            }
        }
    }
}