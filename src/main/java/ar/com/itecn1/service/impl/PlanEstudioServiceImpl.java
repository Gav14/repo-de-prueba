package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.Materia;
import ar.com.itecn1.model.PlanEstudio;
import ar.com.itecn1.repository.MateriaRepository;
import ar.com.itecn1.repository.PlanEstudioRepository;
import ar.com.itecn1.repository.impl.MateriaRepositoryImpl;
import ar.com.itecn1.repository.impl.PlanEstudioRepositoryImpl;
import ar.com.itecn1.service.PlanEstudioService;

import java.util.List;

public class PlanEstudioServiceImpl implements PlanEstudioService {

    private final PlanEstudioRepository planEstudioRepository;
    private final MateriaRepository materiaRepository;

    public PlanEstudioServiceImpl() {
        this.planEstudioRepository = new PlanEstudioRepositoryImpl();
        this.materiaRepository = new MateriaRepositoryImpl();
    }

    @Override
    public PlanEstudio findByName(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del plan no puede estar vacío");
        }
        return planEstudioRepository.findByName(nombre.trim());
    }

    @Override
    public List<PlanEstudio> buscarPlanes(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar texto para buscar");
        }
        return planEstudioRepository.findByNombreSimilar(texto.trim());
    }

    @Override
    public List<PlanEstudio> findActivos() {
        return planEstudioRepository.findByActivo(true);
    }

    @Override
    public List<PlanEstudio> findInactivos() {
        return planEstudioRepository.findByActivo(false);
    }

    @Override
    public List<PlanEstudio> findPlanesPorAnio(int anio) {
        if (anio < 1900 || anio > 2100) {
            throw new IllegalArgumentException("Año inválido. Debe estar entre 1900 y 2100");
        }
        return planEstudioRepository.findByAnio(anio);
    }

    @Override
    public List<PlanEstudio> findAll() {
        return planEstudioRepository.findAll();
    }

    @Override
    public void save(PlanEstudio planEstudio) {
        validarPlanEstudio(planEstudio);

        PlanEstudio existente = planEstudioRepository.findByName(planEstudio.getNombre());

        if (existente != null) {
            if (!existente.isActivo()) {
                throw new IllegalArgumentException(
                        "Ya existe un plan INACTIVO con ese nombre. Use reactivarPlan()"
                );
            }
            throw new IllegalArgumentException("Ya existe un plan activo con ese nombre");
        }

        planEstudio.setActivo(true);
        planEstudioRepository.save(planEstudio);
    }

    @Override
    public void update(PlanEstudio planEstudio) {
        if (planEstudio == null || planEstudio.getNombre() == null) {
            throw new IllegalArgumentException("Plan de estudio inválido");
        }

        PlanEstudio existente = planEstudioRepository.findByName(planEstudio.getNombre());
        if (existente == null) {
            throw new IllegalArgumentException("No existe el plan que desea actualizar");
        }

        planEstudioRepository.update(planEstudio);
    }

    @Override
    public void delete(PlanEstudio planEstudio) {
        if (planEstudio == null || planEstudio.getNombre() == null) {
            throw new IllegalArgumentException("Plan de estudio inválido");
        }

        PlanEstudio existente = planEstudioRepository.findByName(planEstudio.getNombre());
        if (existente == null) {
            throw new IllegalArgumentException("No existe el plan que desea eliminar");
        }

        if (!existente.isActivo()) {
            throw new IllegalArgumentException("El plan ya está inactivo");
        }

        planEstudioRepository.delete(existente);
    }

    @Override
    public void reactivarPlan(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del plan no puede estar vacío");
        }

        PlanEstudio plan = planEstudioRepository.findByName(nombre.trim());
        if (plan == null) {
            throw new IllegalArgumentException("No existe un plan con ese nombre");
        }

        if (plan.isActivo()) {
            throw new IllegalArgumentException("El plan ya está activo");
        }

        plan.setActivo(true);
        planEstudioRepository.update(plan);
    }

    @Override
    public void agregarMateria(String nombrePlan, String codigoMateria) {
        PlanEstudio plan = planEstudioRepository.findByName(nombrePlan);
        if (plan == null) {
            throw new IllegalArgumentException("Plan no encontrado");
        }

        if (!plan.isActivo()) {
            throw new IllegalArgumentException("No se puede modificar un plan inactivo");
        }

        Materia materia = materiaRepository.findByCode(codigoMateria);
        if (materia == null) {
            throw new IllegalArgumentException("Materia no encontrada");
        }

        if (!materia.isActivo()) {
            throw new IllegalArgumentException("No se puede agregar una materia inactiva");
        }

        if (plan.getMaterias().contains(materia)) {
            throw new IllegalArgumentException("La materia ya está en el plan");
        }

        plan.getMaterias().add(materia);
        planEstudioRepository.update(plan);
    }

    @Override
    public void quitarMateria(String nombrePlan, String codigoMateria) {
        PlanEstudio plan = planEstudioRepository.findByName(nombrePlan);
        if (plan == null) {
            throw new IllegalArgumentException("Plan no encontrado");
        }

        if (!plan.isActivo()) {
            throw new IllegalArgumentException("No se puede modificar un plan inactivo");
        }

        Materia materia = materiaRepository.findByCode(codigoMateria);
        if (materia == null) {
            throw new IllegalArgumentException("Materia no encontrada");
        }

        if (!plan.getMaterias().remove(materia)) {
            throw new IllegalArgumentException("La materia no está en el plan");
        }

        planEstudioRepository.update(plan);
    }

    private void validarPlanEstudio(PlanEstudio plan) {
        if (plan == null) {
            throw new IllegalArgumentException("El plan no puede ser null");
        }
        if (plan.getNombre() == null || plan.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del plan es obligatorio");
        }
        if (plan.getTitulo() == null || plan.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del plan es obligatorio");
        }
        if (plan.getAnio() < 1900 || plan.getAnio() > 2100) {
            throw new IllegalArgumentException("El año debe ser válido (entre 1900 y 2100)");
        }
        if (plan.getDuracion() < 1 || plan.getDuracion() > 6) {
            throw new IllegalArgumentException("La duración debe ser entre 1 y 6 años");
        }
    }
}