package ar.com.itecn1.controller;

import ar.com.itecn1.model.PlanEstudio;
import ar.com.itecn1.service.PlanEstudioService;
import ar.com.itecn1.service.impl.PlanEstudioServiceImpl;

import java.util.List;

public class PlanEstudioController {
    private final PlanEstudioService planEstudioService;

    public PlanEstudioController(){this.planEstudioService = new PlanEstudioServiceImpl();}

    public PlanEstudio findByName(String nombre){return this.planEstudioService.findByName(nombre);}

    public List<PlanEstudio> findAll(){return this.planEstudioService.findAll();}

    public void crearPlanEstudio(PlanEstudio planEstudio){this.planEstudioService.save(planEstudio);}

    public void editarPlanEstudio(PlanEstudio planEstudio){this.planEstudioService.save(planEstudio);}

    public void eliminarPlanEstudio(PlanEstudio planEstudio){this.planEstudioService.delete(planEstudio);}

}
