package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.Profesor;
import ar.com.itecn1.repository.ProfesorRepository;
import ar.com.itecn1.repository.impl.ProfesorRepositoryImpl;
import ar.com.itecn1.service.ProfesorService;

import java.util.List;

public class ProfesorServiceImpl implements ProfesorService {
    private final ProfesorRepository profesorRepository;

    public ProfesorServiceImpl() {
        this.profesorRepository = new ProfesorRepositoryImpl();
    }

    @Override
    public Profesor findByDni(String dni) {
        if (!validarFormatoDni(dni)) {
            System.out.println("Error: El DNI debe tener exactamente 8 dígitos numéricos");
            return null;
        }
        return this.profesorRepository.findByDni(dni);
    }

    @Override
    public List<Profesor> findAll() {
        return this.profesorRepository.findAll();
    }

    @Override
    public void save(Profesor profesor) {
        if (validarProfesorCompleto(profesor)) {
            this.profesorRepository.save(profesor);
        } else {
            System.out.println("Error: No se pudo guardar el profesor debido a errores de validación");
        }
    }

    @Override
    public void update(Profesor profesor) {
        if (validarProfesorCompleto(profesor)) {
            this.profesorRepository.update(profesor);
        } else {
            System.out.println("Error: No se pudo actualizar el profesor debido a errores de validación");
        }
    }

    @Override
    public void delete(Profesor profesor) {
        this.profesorRepository.delete(profesor);
    }

    @Override
    public boolean validarFormatoDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) return false;
        if (dni.length() != 8) return false;

        for (int i = 0; i < dni.length(); i++) {
            char c = dni.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    @Override
    public boolean validarCamposObligatorios(Profesor profesor) {
        if (profesor == null) return false;

        // Validar nombre (no nulo, no vacío, sin números)
        if (!validarNombre(profesor.getNombre())) {
            System.out.println("Error: El nombre no puede estar vacío y no debe contener números");
            return false;
        }

        // Validar apellido (no nulo, no vacío, sin números)
        if (!validarApellido(profesor.getApellido())) {
            System.out.println("Error: El apellido no puede estar vacío y no debe contener números");
            return false;
        }

        // Validar teléfono (solo números)
        if (!validarTelefono(profesor.getTelefono())) {
            System.out.println("Error: El teléfono no puede estar vacío y debe contener solo números");
            return false;
        }

        // Validar email (formato básico)
        if (!validarEmail(profesor.getEmail())) {
            System.out.println("Error: El email no puede estar vacío y debe tener un formato válido");
            return false;
        }

        return true;
    }

    @Override
    public boolean validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return false;

        String nombreTrim = nombre.trim();

        // Verificar que no contenga números
        for (int i = 0; i < nombreTrim.length(); i++) {
            char c = nombreTrim.charAt(i);
            if (c >= '0' && c <= '9') {
                return false; // Encontró un número
            }
        }

        return true;
    }

    @Override
    public boolean validarApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) return false;

        String apellidoTrim = apellido.trim();

        // Verificar que no contenga números
        for (int i = 0; i < apellidoTrim.length(); i++) {
            char c = apellidoTrim.charAt(i);
            if (c >= '0' && c <= '9') {
                return false; // Encontró un número
            }
        }

        return true;
    }

    @Override
    public boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) return false;

        String telefonoTrim = telefono.trim();

        // Verificar que todos los caracteres sean números
        for (int i = 0; i < telefonoTrim.length(); i++) {
            char c = telefonoTrim.charAt(i);
            if (c < '0' || c > '9') {
                return false; // Encontró un caracter que no es número
            }
        }

        return true;
    }

    @Override
    public boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;

        String emailTrim = email.trim();

        // Validación básica: debe contener @ y .
        if (!emailTrim.contains("@") || !emailTrim.contains(".")) {
            return false;
        }

        // No debe tener espacios
        if (emailTrim.contains(" ")) {
            return false;
        }

        return true;
    }

    @Override
    public boolean validarProfesorCompleto(Profesor profesor) {
        if (profesor == null) return false;

        // Validar DNI
        if (!validarFormatoDni(profesor.getDni())) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos");
            return false;
        }

        // Validar campos obligatorios
        return validarCamposObligatorios(profesor);
    }
}