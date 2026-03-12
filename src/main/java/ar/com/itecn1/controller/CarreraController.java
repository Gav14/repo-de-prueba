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
        return carreraService.findByName(nombre);
    }

    public List<Carrera> findAll(){
        return carreraService.findAll();
    }

    public List<Carrera> buscarCarreras(String texto){
        return carreraService.buscarCarreras(texto);
    }

    public void createCarrera(Carrera carrera){
        carreraService.save(carrera);
    }

    public void updateCarrera(Carrera carrera){
        carreraService.update(carrera);
    }

    public void deleteCarrera(Carrera carrera){
        carreraService.delete(carrera);
    }

    // ✅ NUEVO MÉTODO - Solo pasa el mensaje
    public void reactivarCarrera(String nombre){
        carreraService.reactivarCarrera(nombre);
    }
}