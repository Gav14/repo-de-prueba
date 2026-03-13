package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.Alumno;
import ar.com.itecn1.model.Profesor;
import ar.com.itecn1.repository.ProfesorRepository;
import ar.com.itecn1.repository.impl.ProfesorRepositoryImpl;
import ar.com.itecn1.service.ProfesorService;

import java.util.List;


public class ProfesorServiceImpl implements ProfesorService{
    private final ProfesorRepository profesorRepository;

    public ProfesorServiceImpl() {this.profesorRepository = new ProfesorRepositoryImpl();}

    @Override
    public Profesor findByDni(String dni){return this.profesorRepository.findByDni(dni);}

    @Override
    public List<Profesor> findAll(){ return this.profesorRepository.findAll();}

    @Override
    public void save (Profesor profesor){this.profesorRepository.save(profesor);}

    @Override
    public void update(Profesor profesor){this.profesorRepository.update(profesor);}

    @Override
    public void delete(Profesor profesor){this.profesorRepository.delete(profesor);}

    public boolean validarFormatoDni(String dni) {
        if (dni == null) return false;
        if (dni.length() != 8) return false; // o 9 si corresponde
        for (int i = 0; i < dni.length(); i++) {
            char c = dni.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    public boolean validarCamposObligatorios(Profesor profesor) {
        // Validar nombre
        if (profesor.getNombre() == null || profesor.getNombre().trim().isEmpty()) {
            return false;
        }
        // Validar apellido
        if (profesor.getApellido() == null || profesor.getApellido().trim().isEmpty()) {
            return false;
        }
        // Validar teléfono
        if (profesor.getTelefono() == null || profesor.getTelefono().trim().isEmpty()) {
            return false;
        }
        // Validar email
        if (profesor.getEmail() == null || profesor.getEmail().trim().isEmpty()) {
            return false;
        }
        return true; // Todos los campos están completos
    }
}