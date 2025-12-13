package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.Carrera;
import ar.com.itecn1.model.Turno;
import ar.com.itecn1.repository.CarreraRepository;
import ar.com.itecn1.repository.PlanEstudioRepository;

import java.util.ArrayList;
import java.util.List;

public class CarreraRepositoryImpl implements CarreraRepository {
    private final  List<Carrera> carreras;
    private final PlanEstudioRepository planEstudioRepository;

    public CarreraRepositoryImpl(){
        this.carreras = new ArrayList<>();
        this.planEstudioRepository = new PlanEstudioRepositoryImpl();
        cargaDatos();
    }

    private void cargaDatos(){
        Carrera carrera1 = new Carrera("Tecnicatura en analisis de sistemas", Turno.NOCHE);
        Carrera carrera2 = new Carrera("Tecnicatura en gastronomia",Turno.MANANA);

        //agrega el plan a la carrera
        carrera1.setPlanEstudio(this.planEstudioRepository.findByName("Analista"));

        this.carreras.add(carrera1);
        this.carreras.add(carrera2);
    }


    @Override
    public Carrera findByName(String nombre) {
       Carrera carrea = null;
       for(Carrera carreraResult: this.carreras){
           if(carreraResult.getNombre().equals(nombre)){
               carrea = carreraResult;
               break;
           }
       }
       return carrea;
    }

    @Override
    public List<Carrera> findAll() {
       return this.carreras;
    }

    @Override
    public void save(Carrera carrera) {
        for(Carrera carreraResult : this.carreras ){
           if(carreraResult.getNombre().equals(carrera.getNombre())){
                return;
            }
        }
        this.carreras.add(carrera);

    }

    @Override
    public void update(Carrera carrera) {
        for(Carrera carreraResult : this.carreras){
            if(carreraResult.getNombre().equals(carrera.getNombre())){
                carreraResult.setTurno(carrera.getTurno());
                carreraResult.setPlanEstudio(carrera.getPlanEstudio());
                return;
            }
        }
    }

    @Override
    public void delete(Carrera carrera) {
        for(Carrera carreraResult: this.carreras){
            if(carreraResult.getNombre().equals(carrera.getNombre())){
                carreraResult.setActivo(false);
                return;
            }
        }
    }
}


