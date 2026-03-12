package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.Alumno;
import ar.com.itecn1.repository.AlumnoRepository;
import ar.com.itecn1.repository.impl.AlumnoRepositoryImpl;
import ar.com.itecn1.service.AlumnoService;

import java.util.List;

import static ar.com.itecn1.repository.impl.AlumnoRepositoryImpl.getInstancia;

public class AlumnoServiceImpl implements AlumnoService {
    private final AlumnoRepository alumnoRepository;

    public AlumnoServiceImpl() {
        this.alumnoRepository = AlumnoRepositoryImpl.getInstancia();
    }

    @Override
    public Alumno findByDni(String dni) {
        if (dni == null) {
            System.out.println("Error: El DNI no puede ser nulo");
            return null;
        }

        if (dni.length() != 8) {
            System.out.println("Error: El DNI debe tener exactamente 8 dígitos");
            return null;
        }

        for (int i = 0; i < dni.length(); i++) {
            char c = dni.charAt(i);
            if (c < '0' || c > '9') {
                System.out.println("Error: El DNI solo puede contener números");
                return null;
            }
        }

        return this.alumnoRepository.findByDni(dni);
    }

    @Override
    public List<Alumno> findAll() {
        return this.alumnoRepository.findAll();
    }

    @Override
    public void save(Alumno alumno) {
        this.alumnoRepository.save(alumno);
    }

    @Override
    public void update(Alumno alumno) {
        this.alumnoRepository.update(alumno);
    }

    @Override
    public void delete(Alumno alumno) {
        this.alumnoRepository.delete(alumno);
    }

    @Override
    public boolean validarFormatoDni(String dni) {
        if (dni == null) return false;
        if (dni.length() != 8) return false; // o 9 si corresponde
        for (int i = 0; i < dni.length(); i++) {
            char c = dni.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    public boolean validarCamposObligatorios(Alumno alumno) {
        // Validar nombre
        if (alumno.getNombre() == null || alumno.getNombre().trim().isEmpty()) {
            return false;
        }
        // Validar apellido
        if (alumno.getApellido() == null || alumno.getApellido().trim().isEmpty()) {
            return false;
        }
        // Validar teléfono
        if (alumno.getTelefono() == null || alumno.getTelefono().trim().isEmpty()) {
            return false;
        }
        // Validar email
        if (alumno.getEmail() == null || alumno.getEmail().trim().isEmpty()) {
            return false;
        }
        return true; // Todos los campos están completos
    }
}
