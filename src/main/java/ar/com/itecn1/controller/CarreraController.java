package ar.com.itecn1.controller;

import ar.com.itecn1.model.Carrera;
import ar.com.itecn1.service.CarreraService;
import ar.com.itecn1.service.impl.CarreraServiceImpl;

import java.util.List;

public class CarreraController {
    private final CarreraService carreraService;

    public CarreraController(){
        this.carreraService = new CarreraServiceImpl();
    }

    public Carrera findByName(String nombre){
        return this.carreraService.findByName(nombre);
    }

    public List<Carrera> findAll(){
        return this.carreraService.findAll();
    }

    public void createCarrera(Carrera carrera){
        this.carreraService.save(carrera);
    }

    public void updateCarrrera(Carrera carrera){
        this.carreraService.update(carrera);
    }

    public void  deleteCarrera(Carrera carrera){
        this.carreraService.delete(carrera);
    }

}


