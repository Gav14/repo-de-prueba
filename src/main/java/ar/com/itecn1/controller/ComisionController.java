package ar.com.itecn1.controller;

import ar.com.itecn1.model.ComisionMateria;
import ar.com.itecn1.service.ComisionService;
import ar.com.itecn1.service.impl.ComisionServiceImpl;

import java.util.List;

public class ComisionController {
    private final ComisionService comisionService;

    public ComisionController() {
        this.comisionService = new ComisionServiceImpl();
    }

    public List<ComisionMateria> findAll(){
        return this.comisionService.findAll();
    }

    public ComisionMateria findByCode(String codigoMateria){
        return this.comisionService.findByCode(codigoMateria);
    }

    public void createComision(ComisionMateria comisionMateria){
        this.comisionService.save(comisionMateria);
    }

    public void updateComsion(ComisionMateria comisionMateria){
        this.comisionService.update(comisionMateria);
    }

    public void deleteComision(ComisionMateria comisionMateria){
        this.comisionService.delete(comisionMateria);
    }
}
