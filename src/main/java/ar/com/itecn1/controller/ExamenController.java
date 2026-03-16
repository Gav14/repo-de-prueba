package ar.com.itecn1.controller;

import ar.com.itecn1.model.Examen;
import ar.com.itecn1.service.ExamenService;
import ar.com.itecn1.service.impl.ExamenServiceImpl;

import java.util.List;

public class ExamenController {
    private final ExamenService examenService;

    public ExamenController() {
        this.examenService = new ExamenServiceImpl();
    }

    public List<Examen> findAll(){
        return this.examenService.findAll();
    }

    public Examen findById(String idExamen){
        return this.examenService.findById(idExamen);
    }

}
