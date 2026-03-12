package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.Carrera;
import ar.com.itecn1.model.PlanEstudio;
import ar.com.itecn1.model.Turno;
import ar.com.itecn1.repository.CarreraRepository;
import ar.com.itecn1.repository.PlanEstudioRepository;

import java.util.ArrayList;
import java.util.List;

public class CarreraRepositoryImpl implements CarreraRepository {

    private final List<Carrera> carreras;
    private final PlanEstudioRepository planEstudioRepository;

    public CarreraRepositoryImpl() {
        this.carreras = new ArrayList<>();
        this.planEstudioRepository = new PlanEstudioRepositoryImpl();
        cargaDatos();
    }

    private void cargaDatos() {
        // Obtener los planes de estudio
        PlanEstudio planAnalista = planEstudioRepository.findByName("Analista");
        PlanEstudio planGastronomia = planEstudioRepository.findByName("Gastronomia");

        // Crear carreras
        Carrera carrera1 = new Carrera("Tecnicatura en Analisis de Sistemas", Turno.NOCHE);
        Carrera carrera2 = new Carrera("Tecnicatura en Gastronomia", Turno.MANANA);

        // Asignar planes a las carreras
        if (planAnalista != null) {
            carrera1.setPlanEstudio(planAnalista);
        }

        if (planGastronomia != null) {
            carrera2.setPlanEstudio(planGastronomia);
        }

        carreras.add(carrera1);
        carreras.add(carrera2);
    }

    @Override
    public Carrera findByName(String nombre) {
        for (Carrera carrera : carreras) {
            if (carrera.getNombre().equalsIgnoreCase(nombre)) {
                return carrera;
            }
        }
        return null;
    }

    @Override
    public List<Carrera> findByNombreSimilar(String texto) {
        List<Carrera> resultados = new ArrayList<>();
        for (Carrera carrera : carreras) {
            if (carrera.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                resultados.add(carrera);
            }
        }
        return resultados;
    }

    @Override
    public List<Carrera> findByActivo(boolean activo) {
        List<Carrera> resultados = new ArrayList<>();
        for (Carrera carrera : carreras) {
            if (carrera.isActivo() == activo) {
                resultados.add(carrera);
            }
        }
        return resultados;
    }

    @Override
    public List<Carrera> findByTurno(String turno) {
        List<Carrera> resultados = new ArrayList<>();
        for (Carrera carrera : carreras) {
            if (carrera.getTurno().name().equalsIgnoreCase(turno)) {
                resultados.add(carrera);
            }
        }
        return resultados;
    }

    @Override
    public List<Carrera> findAll() {
        return new ArrayList<>(carreras);
    }

    @Override
    public void save(Carrera carrera) {
        if (findByName(carrera.getNombre()) != null) {
            return;
        }
        carreras.add(carrera);
    }

    @Override
    public void update(Carrera carrera) {
        Carrera carreraExistente = findByName(carrera.getNombre());
        if (carreraExistente != null) {
            carreraExistente.setTurno(carrera.getTurno());
            carreraExistente.setPlanEstudio(carrera.getPlanEstudio());
            carreraExistente.setActivo(carrera.isActivo());
        }
    }

    @Override
    public void delete(Carrera carrera) {
        Carrera carreraExistente = findByName(carrera.getNombre());
        if (carreraExistente != null) {
            carreraExistente.setActivo(false);
        }
    }
}