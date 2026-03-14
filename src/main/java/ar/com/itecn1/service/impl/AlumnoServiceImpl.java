package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.Alumno;
import ar.com.itecn1.repository.AlumnoRepository;
import ar.com.itecn1.repository.impl.AlumnoRepositoryImpl;
import ar.com.itecn1.service.AlumnoService;

import java.util.List;

public class AlumnoServiceImpl implements AlumnoService {
    private final AlumnoRepository alumnoRepository;

    public AlumnoServiceImpl() {
        this.alumnoRepository = AlumnoRepositoryImpl.getInstancia();
    }

    @Override
    public Alumno findByDni(String dni) {
        if (!validarFormatoDni(dni)) {
            System.out.println("Error: El DNI debe tener exactamente 8 dígitos numéricos");
            return null;
        }
        return this.alumnoRepository.findByDni(dni);
    }

    @Override
    public List<Alumno> findAll() {
        return this.alumnoRepository.findAll();
    }

    @Override
    public void save(Alumno alumno) {
        if (validarAlumnoCompleto(alumno)) {
            this.alumnoRepository.save(alumno);
        } else {
            System.out.println("Error: No se pudo guardar el alumno debido a errores de validación");
        }
    }

    @Override
    public void update(Alumno alumno) {
        if (validarAlumnoCompleto(alumno)) {
            this.alumnoRepository.update(alumno);
        } else {
            System.out.println("Error: No se pudo actualizar el alumno debido a errores de validación");
        }
    }

    @Override
    public void delete(Alumno alumno) {
        this.alumnoRepository.delete(alumno);
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
    public boolean validarCamposObligatorios(Alumno alumno) {
        if (alumno == null) return false;

        // Validar nombre (no nulo, no vacío, sin números)
        if (!validarNombre(alumno.getNombre())) {
            System.out.println("Error: El nombre no puede estar vacío y no debe contener números");
            return false;
        }

        // Validar apellido (no nulo, no vacío, sin números)
        if (!validarApellido(alumno.getApellido())) {
            System.out.println("Error: El apellido no puede estar vacío y no debe contener números");
            return false;
        }

        // Validar teléfono (solo números)
        if (!validarTelefono(alumno.getTelefono())) {
            System.out.println("Error: El teléfono no puede estar vacío y debe contener solo números");
            return false;
        }

        // Validar email (formato básico)
        if (!validarEmail(alumno.getEmail())) {
            System.out.println("Error: El email no puede estar vacío y debe tener un formato válido");
            return false;
        }

        return true;
    }

    // Método adicional para validar nombre (sin números)
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

    // Método adicional para validar apellido (sin números)
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

    // NUEVO: Método para validar teléfono (solo números)
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

    // Método para validar email (formato básico)
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

    // Método que valida todo el alumno antes de guardar/actualizar
    public boolean validarAlumnoCompleto(Alumno alumno) {
        if (alumno == null) return false;

        // Validar DNI
        if (!validarFormatoDni(alumno.getDni())) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos");
            return false;
        }

        // Validar campos obligatorios (incluye validación de nombre, apellido, teléfono y email)
        return validarCamposObligatorios(alumno);
    }
}