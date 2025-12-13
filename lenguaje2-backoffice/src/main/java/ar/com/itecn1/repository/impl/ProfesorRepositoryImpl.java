package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.Profesor;
import ar.com.itecn1.repository.ProfesorRepository;

import java.util.ArrayList;
import java.util.List;

public class ProfesorRepositoryImpl implements ProfesorRepository {
    private final List<Profesor> profesorDb;

    public ProfesorRepositoryImpl() {
        this.profesorDb = new ArrayList<>();
        cargarDatos();
    }

    private void cargarDatos(){
        Profesor profesor1 = new Profesor("10101010", "Carlos", "Ramírez", "3764001001", "carlosramirez@gmail.com");
        Profesor profesor2 = new Profesor("20202020", "María", "Benítez", "3764002002", "mariabenitez@gmail.com");
        Profesor profesor3 = new Profesor("30303030", "Jorge", "Sosa", "3764003003", "jorgesosa@gmail.com");
        Profesor profesor4 = new Profesor("40404040", "Ana", "Gutiérrez", "3764004004", "anagutierrez@gmail.com");
        Profesor profesor5 = new Profesor("50505050", "Ricardo", "Martínez", "3764005005", "ricardomartinez@gmail.com");
        Profesor profesor6 = new Profesor("60606060", "Sofía", "Acosta", "3764006006", "sofiaacosta@gmail.com");
        Profesor profesor7 = new Profesor("70707070", "Hernán", "Cáceres", "3764007007", "hernancaceres@gmail.com");
        Profesor profesor8 = new Profesor("80808080", "Laura", "Fernández", "3764008008", "laurafernandez@gmail.com");
        Profesor profesor9 = new Profesor("90909090", "Diego", "López", "3764009009", "diegolopez@gmail.com");
        Profesor profesor10 = new Profesor("11223344", "Paula", "Ríos", "3764010010", "paularios@gmail.com");

        this.profesorDb.add(profesor1);
        this.profesorDb.add(profesor2);
        this.profesorDb.add(profesor3);
        this.profesorDb.add(profesor4);
        this.profesorDb.add(profesor5);
        this.profesorDb.add(profesor6);
        this.profesorDb.add(profesor7);
        this.profesorDb.add(profesor8);
        this.profesorDb.add(profesor9);
        this.profesorDb.add(profesor10);
    }


    @Override
    public Profesor findByDni(String dni){
        Profesor profesor = null;
        for(Profesor profesorResultado: this.profesorDb){
            if(profesorResultado.getDni().equals(dni)){
                profesor = profesorResultado;
                break;
            }
        }
        return profesor;
    }

    @Override
    public List<Profesor> findAll(){ return this.profesorDb; }

    @Override
    public void save(Profesor profesor){
        for(Profesor profesorResultado: this.profesorDb){
            if(profesorResultado.getDni().equals(profesor.getDni())){
                return;
            }
        }
        this.profesorDb.add(profesor);
    }

    @Override
    public void update (Profesor profesor){
        for(Profesor profesorResultado: this.profesorDb){
            if(profesorResultado.getDni().equals(profesor.getDni())){
                profesorResultado.setNombre(profesor.getNombre());
                profesorResultado.setApellido(profesor.getApellido());
                profesorResultado.setTelefono(profesor.getTelefono());
                profesorResultado.setEmail(profesor.getEmail());
                return;
            }
        }
    }

    @Override
    public void delete (Profesor profesor){
        for(Profesor profesorResult: this.profesorDb){
            if(profesorResult.getDni().equals(profesor.getDni())){
                profesorResult.setActivo(false);
                return;
            }
        }
    }



}
