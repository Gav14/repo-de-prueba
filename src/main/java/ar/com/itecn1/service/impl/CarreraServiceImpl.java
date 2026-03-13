package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.Carrera;
import ar.com.itecn1.repository.CarreraRepository;
import ar.com.itecn1.repository.impl.CarreraRepositoryImpl;
import ar.com.itecn1.service.CarreraService;

import java.util.List;

public class CarreraServiceImpl implements CarreraService {

    private final CarreraRepository carreraRepository;

    public CarreraServiceImpl(){
        this.carreraRepository = new CarreraRepositoryImpl();
    }

    @Override
    public Carrera findByName(String nombre) {
        if(nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre de la carrera no puede estar vacío");
        }
        return carreraRepository.findByName(nombre.trim());
    }

    @Override
    public List<Carrera> findAll() {
        return carreraRepository.findAll();
    }

    @Override
    public List<Carrera> buscarCarreras(String texto){
        if(texto == null || texto.trim().isEmpty()){
            throw new IllegalArgumentException("Debe ingresar texto para buscar");
        }
        return carreraRepository.findByNombreSimilar(texto.trim());
    }

    @Override
    public void save(Carrera carrera) {
        if(carrera == null){
            throw new IllegalArgumentException("La carrera no puede ser null");
        }
        if(carrera.getNombre() == null || carrera.getNombre().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre de la carrera es obligatorio");
        }
        if(carrera.getTurno() == null){
            throw new IllegalArgumentException("El turno de la carrera es obligatorio");
        }

        String nombreNormalizado = carrera.getNombre().trim();
        carrera.setNombre(nombreNormalizado);

        Carrera existente = carreraRepository.findByName(nombreNormalizado);

        if(existente != null){
            if(!existente.isActivo()){
                // Reactivar carrera existente
                existente.setActivo(true);
                existente.setTurno(carrera.getTurno());
                existente.setPlanEstudio(carrera.getPlanEstudio());
                carreraRepository.update(existente);
                return;
            }
            throw new IllegalArgumentException("La carrera ya existe");
        }
        carreraRepository.save(carrera);
    }

    @Override
    public void update(Carrera carrera) {
        if(carrera == null || carrera.getNombre() == null){
            throw new IllegalArgumentException("Carrera inválida");
        }

        Carrera existente = carreraRepository.findByName(carrera.getNombre());
        if(existente == null){
            throw new IllegalArgumentException("No existe la carrera que desea actualizar");
        }

        carreraRepository.update(carrera);
    }

    @Override
    public void delete(Carrera carrera) {
        if(carrera == null || carrera.getNombre() == null){
            throw new IllegalArgumentException("Carrera inválida");
        }

        Carrera existente = carreraRepository.findByName(carrera.getNombre());
        if(existente == null){
            throw new IllegalArgumentException("No existe la carrera que desea eliminar");
        }

        carreraRepository.delete(existente);
    }

    // ✅ NUEVO MÉTODO - Lógica de negocio para reactivar
    @Override
    public void reactivarCarrera(String nombre) {
        if(nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre de la carrera no puede estar vacío");
        }

        Carrera carrera = carreraRepository.findByName(nombre.trim());
        if(carrera == null){
            throw new IllegalArgumentException("No existe una carrera con ese nombre");
        }

        if(carrera.isActivo()){
            throw new IllegalArgumentException("La carrera ya está activa");
        }

        carrera.setActivo(true);
        carreraRepository.update(carrera);
    }
}