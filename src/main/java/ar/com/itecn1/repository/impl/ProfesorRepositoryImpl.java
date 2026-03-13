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

        Profesor profesor1 = new Profesor("23145874", "Carlos", "Ramírez", "3764012456", "carlos.ramirez@gmail.com");
        Profesor profesor2 = new Profesor("23569874", "María", "Benítez", "3765124578", "maria.benitez@gmail.com");
        Profesor profesor3 = new Profesor("24125789", "Jorge", "Sosa", "3764013698", "jorge.sosa@gmail.com");
        Profesor profesor4 = new Profesor("24789563", "Ana", "Gutiérrez", "3765124789", "ana.gutierrez@gmail.com");
        Profesor profesor5 = new Profesor("25214789", "Ricardo", "Martínez", "3764011123", "ricardo.martinez@gmail.com");
        Profesor profesor6 = new Profesor("25874123", "Sofía", "Acosta", "3765123345", "sofia.acosta@gmail.com");
        Profesor profesor7 = new Profesor("26457891", "Hernán", "Cáceres", "3764015567", "hernan.caceres@gmail.com");
        Profesor profesor8 = new Profesor("26897451", "Laura", "Fernández", "3765127788", "laura.fernandez@gmail.com");
        Profesor profesor9 = new Profesor("27451236", "Diego", "López", "3764018899", "diego.lopez@gmail.com");
        Profesor profesor10 = new Profesor("27984512", "Paula", "Ríos", "3765129901", "paula.rios@gmail.com");
        Profesor profesor11 = new Profesor("28365124", "Fernando", "Pereyra", "3764013344", "fernando.pereyra@gmail.com");
        Profesor profesor12 = new Profesor("28974123", "Lucía", "Domínguez", "3765126677", "lucia.dominguez@gmail.com");
        Profesor profesor13 = new Profesor("29457812", "Matías", "Silva", "3764017788", "matias.silva@gmail.com");
        Profesor profesor14 = new Profesor("29874563", "Valeria", "Vega", "3765128899", "valeria.vega@gmail.com");
        Profesor profesor15 = new Profesor("30457891", "Gabriel", "Navarro", "3764012233", "gabriel.navarro@gmail.com");
        Profesor profesor16 = new Profesor("31245789", "Natalia", "Quiroga", "3765124455", "natalia.quiroga@gmail.com");
        Profesor profesor17 = new Profesor("32587412", "Sebastián", "Molina", "3764015566", "sebastian.molina@gmail.com");
        Profesor profesor18 = new Profesor("32974125", "Carolina", "Ortiz", "3765127789", "carolina.ortiz@gmail.com");

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
        this.profesorDb.add(profesor11);
        this.profesorDb.add(profesor12);
        this.profesorDb.add(profesor13);
        this.profesorDb.add(profesor14);
        this.profesorDb.add(profesor15);
        this.profesorDb.add(profesor16);
        this.profesorDb.add(profesor17);
        this.profesorDb.add(profesor18);
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
    public List<Profesor> findAll(){
        return this.profesorDb;
    }

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