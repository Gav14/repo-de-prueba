package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.Materia;
import ar.com.itecn1.model.PlanEstudio;
import ar.com.itecn1.repository.MateriaRepository;
import ar.com.itecn1.repository.PlanEstudioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlanEstudioRepositoryImpl implements PlanEstudioRepository {

    private final List<PlanEstudio> planEstudiosDb;
    private final MateriaRepository materiaRepository;

    public PlanEstudioRepositoryImpl() {
        this.planEstudiosDb = new ArrayList<>();
        this.materiaRepository = new MateriaRepositoryImpl();
        cargarDatos();
    }

    private void cargarDatos() {
        PlanEstudio planAnalista2025 = new PlanEstudio("Analista", 2025, 3, "Tecnicatura en análisis de sistemas");
        PlanEstudio planGastronomia2024 = new PlanEstudio("Gastronomia", 2024, 2, "Tecnicatura en gastronomía");

        List<Materia> materiasAnalista = new ArrayList<>();

        Materia m1 = this.materiaRepository.findByCode("An25Mat1");
        Materia m2 = this.materiaRepository.findByCode("An25Mat2");
        Materia m3 = this.materiaRepository.findByCode("An25Mat3");
        Materia m4 = this.materiaRepository.findByCode("An25Ing1");
        Materia m5 = this.materiaRepository.findByCode("An25Ing2");

        if (m1 != null) materiasAnalista.add(m1);
        if (m2 != null) materiasAnalista.add(m2);
        if (m3 != null) materiasAnalista.add(m3);
        if (m4 != null) materiasAnalista.add(m4);
        if (m5 != null) materiasAnalista.add(m5);

        planAnalista2025.setMaterias(materiasAnalista);

        this.planEstudiosDb.add(planAnalista2025);
        this.planEstudiosDb.add(planGastronomia2024);
    }

    @Override
    public PlanEstudio findByName(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return null;

        return this.planEstudiosDb.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre.trim()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<PlanEstudio> findByNombreSimilar(String texto) {
        if (texto == null || texto.trim().isEmpty()) return new ArrayList<>();

        String textoBusqueda = texto.toLowerCase().trim();
        return this.planEstudiosDb.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(textoBusqueda))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanEstudio> findByActivo(boolean activo) {
        return this.planEstudiosDb.stream()
                .filter(p -> p.isActivo() == activo)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanEstudio> findByAnio(int anio) {
        return this.planEstudiosDb.stream()
                .filter(p -> p.getAnio() == anio)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanEstudio> findAll() {
        return new ArrayList<>(this.planEstudiosDb);
    }

    @Override
    public void save(PlanEstudio planEstudio) {
        if (findByName(planEstudio.getNombre()) != null) {
            throw new IllegalArgumentException("Ya existe un plan con ese nombre");
        }
        this.planEstudiosDb.add(planEstudio);
    }

    @Override
    public void update(PlanEstudio planEstudio) {
        PlanEstudio existente = findByName(planEstudio.getNombre());
        if (existente != null) {
            existente.setTitulo(planEstudio.getTitulo());
            existente.setAnio(planEstudio.getAnio());
            existente.setDuracion(planEstudio.getDuracion());
            existente.setMaterias(planEstudio.getMaterias());
            existente.setActivo(planEstudio.isActivo());
        }
    }

    @Override
    public void delete(PlanEstudio planEstudio) {
        PlanEstudio existente = findByName(planEstudio.getNombre());
        if (existente != null) {
            existente.setActivo(false);
        }
    }
}