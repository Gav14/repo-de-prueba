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
        // Crear planes de estudio
        PlanEstudio planAnalista2025 = new PlanEstudio("Analista", 2025, 3, "Tecnicatura en análisis de sistemas");
        PlanEstudio planGastronomia2024 = new PlanEstudio("Gastronomia", 2024, 2, "Tecnicatura en gastronomía");

        // ============================================================
        // ASIGNAR MATERIAS AL PLAN ANALISTA
        // ============================================================
        List<Materia> materiasAnalista = new ArrayList<>();

        // Materias de 1° Cuatrimestre
        agregarSiExiste(materiasAnalista, "THT-101"); // Taller de Herramientas
        agregarSiExiste(materiasAnalista, "MAT-101"); // Matemática I
        agregarSiExiste(materiasAnalista, "ICC-101"); // Introducción a la Computación
        agregarSiExiste(materiasAnalista, "IPR-101"); // Introducción a la Programación
        agregarSiExiste(materiasAnalista, "ING-101"); // Inglés I
        agregarSiExiste(materiasAnalista, "TDS-101"); // Teoría de Sistemas
        agregarSiExiste(materiasAnalista, "FDE-101"); // Fundamentos de Electrónica

        // 2° Cuatrimestre
        agregarSiExiste(materiasAnalista, "MAT-201"); // Matemática II
        agregarSiExiste(materiasAnalista, "SOP-201"); // Sistemas Operativos
        agregarSiExiste(materiasAnalista, "LEN-201"); // Lenguaje I
        agregarSiExiste(materiasAnalista, "ING-201"); // Inglés II
        agregarSiExiste(materiasAnalista, "ECY-201"); // Elementos de Contabilidad

        // 3° Cuatrimestre
        agregarSiExiste(materiasAnalista, "MAT-301"); // Matemática III
        agregarSiExiste(materiasAnalista, "LEN-301"); // Lenguaje II
        agregarSiExiste(materiasAnalista, "BDD-301"); // Base de Datos
        agregarSiExiste(materiasAnalista, "ADS-301"); // Análisis de Sistemas
        agregarSiExiste(materiasAnalista, "IEC-301"); // Introducción a la Economía

        // 4° Cuatrimestre
        agregarSiExiste(materiasAnalista, "EST-401"); // Estadísticas
        agregarSiExiste(materiasAnalista, "EDD-401"); // Estructura de Datos
        agregarSiExiste(materiasAnalista, "POO-401"); // Programación Orientada a Objetos
        agregarSiExiste(materiasAnalista, "REI-401"); // Redes e Internet
        agregarSiExiste(materiasAnalista, "PAS-401"); // Pasantías I

        // 5° Cuatrimestre
        agregarSiExiste(materiasAnalista, "TSI-501"); // Taller de Seguridad Informática
        agregarSiExiste(materiasAnalista, "RRH-501"); // Recursos Humanos
        agregarSiExiste(materiasAnalista, "TRD-501"); // Taller de Redes
        agregarSiExiste(materiasAnalista, "TS1-501"); // Taller de Sistemas I
        agregarSiExiste(materiasAnalista, "OYM-501"); // Organización y Métodos
        agregarSiExiste(materiasAnalista, "TPW-501"); // Taller de Página Web

        // 6° Cuatrimestre
        agregarSiExiste(materiasAnalista, "ADP-601"); // Administración de la Producción
        agregarSiExiste(materiasAnalista, "ISW-601"); // Ingeniería en Software
        agregarSiExiste(materiasAnalista, "TS2-601"); // Taller de Sistemas II
        agregarSiExiste(materiasAnalista, "MKT-601"); // Marketing
        agregarSiExiste(materiasAnalista, "FIN-601"); // Finanzas
        agregarSiExiste(materiasAnalista, "EYD-601"); // Ética y Deontología
        agregarSiExiste(materiasAnalista, "PAS-601"); // Pasantías II

        // Materias nuevas
        agregarSiExiste(materiasAnalista, "PHY");     // Phyton I
        agregarSiExiste(materiasAnalista, "NODE.JS"); // Node
        agregarSiExiste(materiasAnalista, "JS");      // JavaScript

        planAnalista2025.setMaterias(materiasAnalista);

        // ============================================================
        // CREAR Y ASIGNAR MATERIAS AL PLAN GASTRONOMIA
        // ============================================================
        List<Materia> materiasGastronomia = new ArrayList<>();

        // Crear materias de gastronomía
        crearMateriaGastronomia("GAS-101", "Introducción a la Gastronomía", "1", materiasGastronomia);
        crearMateriaGastronomia("COC-101", "Técnicas Culinarias Básicas", "1", materiasGastronomia);
        crearMateriaGastronomia("HIG-101", "Higiene y Manipulación de Alimentos", "1", materiasGastronomia);
        crearMateriaGastronomia("NUT-101", "Nutrición Básica", "1", materiasGastronomia);

        crearMateriaGastronomia("PAN-201", "Panadería y Pastelería", "2", materiasGastronomia);
        crearMateriaGastronomia("COC-201", "Cocina Regional", "2", materiasGastronomia);
        crearMateriaGastronomia("BEB-201", "Bebidas y Coctelería", "2", materiasGastronomia);
        crearMateriaGastronomia("GAS-201", "Gastronomía Argentina", "2", materiasGastronomia);

        crearMateriaGastronomia("COC-301", "Cocina Internacional", "3", materiasGastronomia);
        crearMateriaGastronomia("PAR-301", "Parilla y Asados", "3", materiasGastronomia);
        crearMateriaGastronomia("POS-301", "Postres y Repostería", "3", materiasGastronomia);
        crearMateriaGastronomia("GAS-301", "Gestión Gastronómica", "3", materiasGastronomia);
        crearMateriaGastronomia("ECO-301", "Economía Aplicada", "3", materiasGastronomia);

        crearMateriaGastronomia("EVE-401", "Eventos y Catering", "4", materiasGastronomia);
        crearMateriaGastronomia("COC-401", "Alta Cocina", "4", materiasGastronomia);
        crearMateriaGastronomia("ENO-401", "Enología", "4", materiasGastronomia);
        crearMateriaGastronomia("GAS-401", "Marketing Gastronómico", "4", materiasGastronomia);

        crearMateriaGastronomia("EMP-501", "Emprendimientos Gastronómicos", "5", materiasGastronomia);
        crearMateriaGastronomia("COC-501", "Cocina Molecular", "5", materiasGastronomia);
        crearMateriaGastronomia("GAS-501", "Legislación Alimentaria", "5", materiasGastronomia);
        crearMateriaGastronomia("NEG-501", "Gestión de Restaurantes", "5", materiasGastronomia);

        crearMateriaGastronomia("GAS-601", "Proyecto Final Gastronómico", "6", materiasGastronomia);
        crearMateriaGastronomia("COC-601", "Cocina de Autor", "6", materiasGastronomia);
        crearMateriaGastronomia("GAS-602", "Sommelier", "6", materiasGastronomia);
        crearMateriaGastronomia("GAS-603", "Gestión de Calidad", "6", materiasGastronomia);

        // Agregar materias comunes
        agregarSiExiste(materiasGastronomia, "MAT-101"); // Matemática I
        agregarSiExiste(materiasGastronomia, "ING-101"); // Inglés I
        agregarSiExiste(materiasGastronomia, "ING-201"); // Inglés II
        agregarSiExiste(materiasGastronomia, "ECY-201"); // Elementos de Contabilidad
        agregarSiExiste(materiasGastronomia, "IEC-301"); // Introducción a la Economía
        agregarSiExiste(materiasGastronomia, "RRH-501"); // Recursos Humanos
        agregarSiExiste(materiasGastronomia, "MKT-601"); // Marketing
        agregarSiExiste(materiasGastronomia, "EYD-601"); // Ética y Deontología

        planGastronomia2024.setMaterias(materiasGastronomia);

        // Agregar los planes a la base de datos
        this.planEstudiosDb.add(planAnalista2025);
        this.planEstudiosDb.add(planGastronomia2024);
    }

    private void agregarSiExiste(List<Materia> lista, String codigo) {
        Materia materia = this.materiaRepository.findByCode(codigo);
        if (materia != null && !lista.contains(materia)) {
            lista.add(materia);
        }
    }

    private void crearMateriaGastronomia(String codigo, String nombre, String cuatrimestre, List<Materia> lista) {
        Materia existente = this.materiaRepository.findByCode(codigo);
        if (existente == null) {
            Materia nuevaMateria = new Materia(codigo, nombre, cuatrimestre);
            this.materiaRepository.save(nuevaMateria);
            lista.add(nuevaMateria);
        } else {
            lista.add(existente);
        }
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