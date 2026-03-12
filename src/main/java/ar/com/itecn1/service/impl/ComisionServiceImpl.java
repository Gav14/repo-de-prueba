package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.*;
import ar.com.itecn1.repository.ComisionRepository;
import ar.com.itecn1.repository.impl.ComisionRepositoryImpl;
import ar.com.itecn1.service.ComisionService;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

public class ComisionServiceImpl implements ComisionService {
    private final ComisionRepository comisionRepository;

    public ComisionServiceImpl() {
        this.comisionRepository = new ComisionRepositoryImpl();
    }

    @Override
    public String registrarComision(ComisionMateria comision) {
        if (comision.getMateria() == null || comision.getCuatrimestre() == null) {
            return "ERROR: Datos incompletos para registrar la comisión";
        }

        String cuatrimestreMateria = comision.getMateria().getCuatrimestre();
        String cuatrimestreComision = comision.getCuatrimestre().getNumero();

        if (!cuatrimestreMateria.equals(cuatrimestreComision)) {
            return "ERROR: La materia " + comision.getMateria().getNombre() +
                    " es de " + cuatrimestreMateria + "° cuatrimestre, " +
                    "no puede dictarse en el " + cuatrimestreComision + "° cuatrimestre";
        }

        boolean existeActiva = comisionRepository.existeComisionActivaParaMateriaEnCuatrimestre(
                comision.getMateria().getCodigoMateria(),
                cuatrimestreComision
        );

        if (existeActiva) {
            return "ERROR: Ya existe una comisión activa para " + comision.getMateria().getNombre() +
                    " en el " + cuatrimestreComision + "° cuatrimestre";
        }

        if (comision.getProfesor() == null) {
            return "ERROR: Debe seleccionar un profesor";
        }

        try {
            comisionRepository.save(comision);
            return "SUCCESS: Comisión registrada exitosamente";
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    @Override
    public String inscribirAlumno(String codigoComision, String dniAlumno, AlumnoInscriptoMateria inscripcion) {
        ComisionMateria comision = comisionRepository.findByCode(codigoComision);

        if (comision == null) {
            return "ERROR: Comisión no encontrada";
        }

        if (!comision.isActivo()) {
            return "ERROR: La comisión no está activa";
        }

        for (AlumnoInscriptoMateria a : comision.getAlumnosInscriptos()) {
            if (a.getAlumnoInscriptoCarrera().getAlumno().getDni().equals(dniAlumno)) {
                return "ERROR: El alumno ya está inscrito en esta comisión";
            }
        }

        comision.getAlumnosInscriptos().add(inscripcion);
        comisionRepository.update(comision);

        return "SUCCESS: Alumno inscrito correctamente";
    }

    @Override
    public boolean puedeRegistrarAsistencia(String codigoComision, String dniAlumno) {
        ComisionMateria comision = comisionRepository.findByCode(codigoComision);

        if (comision == null || !comision.isActivo()) {
            return false;
        }

        boolean alumnoInscripto = false;
        for (AlumnoInscriptoMateria a : comision.getAlumnosInscriptos()) {
            if (a.getAlumnoInscriptoCarrera().getAlumno().getDni().equals(dniAlumno)) {
                alumnoInscripto = true;
                break;
            }
        }

        if (!alumnoInscripto) {
            return false;
        }

        for (Asistencia a : comision.getAsistencias()) {
            if (a.getAlumno().getDni().equals(dniAlumno) &&
                    a.getFecha().equals(LocalDate.now())) {
                return false;
            }
        }

        String diaActual = LocalDate.now().getDayOfWeek()
                .getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
                .toUpperCase();

        String diaNormalizado = Normalizer
                .normalize(diaActual, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        LocalTime horaActual = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        for (Horario h : comision.getHorarios()) {
            if (h.getDia().name().equals(diaNormalizado)) {
                LocalTime inicio = h.getModulo().getInicio();
                LocalTime fin = h.getModulo().getFin();
                if (!horaActual.isBefore(inicio) && !horaActual.isAfter(fin)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String registrarAsistencia(String codigoComision, String dniAlumno, Asistencia asistencia) {
        if (!puedeRegistrarAsistencia(codigoComision, dniAlumno)) {
            return "ERROR: No se puede registrar la asistencia";
        }

        ComisionMateria comision = comisionRepository.findByCode(codigoComision);

        for (AlumnoInscriptoMateria a : comision.getAlumnosInscriptos()) {
            if (a.getAlumnoInscriptoCarrera().getAlumno().getDni().equals(dniAlumno)) {
                asistencia.setAlumno(a.getAlumnoInscriptoCarrera().getAlumno());
                break;
            }
        }

        comision.getAsistencias().add(asistencia);
        comisionRepository.update(comision);

        return "SUCCESS: Asistencia registrada";
    }

    @Override
    public ComisionMateria findByCode(String codigoComision) {
        return comisionRepository.findByCode(codigoComision);
    }

    @Override
    public List<ComisionMateria> findAll() {
        return comisionRepository.findAll();
    }

    @Override
    public List<ComisionMateria> findByCarrera(String nombreCarrera) {
        return comisionRepository.findByCarrera(nombreCarrera);
    }

    @Override
    public List<ComisionMateria> findByMateria(String codigoMateria) {
        return comisionRepository.findByMateria(codigoMateria);
    }

    @Override
    public List<ComisionMateria> findByCuatrimestre(String numeroCuatrimestre) {
        return comisionRepository.findByCuatrimestre(numeroCuatrimestre);
    }

    @Override
    public List<ComisionMateria> findByProfesor(int dniProfesor) {
        return comisionRepository.findByProfesor(dniProfesor);
    }

    @Override
    public void save(ComisionMateria comision) {
        comisionRepository.save(comision);
    }

    @Override
    public void update(ComisionMateria comision) {
        comisionRepository.update(comision);
    }

    @Override
    public void delete(ComisionMateria comision) {
        comisionRepository.delete(comision);
    }
}