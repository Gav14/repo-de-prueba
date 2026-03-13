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
import java.util.stream.Collectors;

public class ComisionServiceImpl implements ComisionService {
    private final ComisionRepository comisionRepository;

    // Constantes
    private static final double PORCENTAJE_ASISTENCIA_MINIMO = 70.0;
    private static final double NOTA_APROBACION = 6.0;
    private static final int CLASES_POR_CUATRIMESTRE = 16;

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
    public boolean puedeRendirExamen(String codigoComision, String dniAlumno, Tipo tipoExamen) {
        if (tipoExamen != Tipo.FINAL) {
            return true;
        }

        double porcentajeAsistencia = calcularPorcentajeAsistencia(codigoComision, dniAlumno);
        boolean tieneParcialAprobado = tieneParcialAprobado(codigoComision, dniAlumno);

        return porcentajeAsistencia >= PORCENTAJE_ASISTENCIA_MINIMO && tieneParcialAprobado;
    }

    @Override
    public double calcularPorcentajeAsistencia(String codigoComision, String dniAlumno) {
        ComisionMateria comision = comisionRepository.findByCode(codigoComision);

        if (comision == null) return 0.0;

        int totalClasesPosibles = comision.getHorarios().size() * CLASES_POR_CUATRIMESTRE;
        if (totalClasesPosibles == 0) return 0.0;

        long asistenciasAlumno = comision.getAsistencias().stream()
                .filter(a -> a.getAlumno().getDni().equals(dniAlumno))
                .filter(Asistencia::isPresente)
                .count();

        return (asistenciasAlumno * 100.0) / totalClasesPosibles;
    }

    @Override
    public boolean tieneParcialAprobado(String codigoComision, String dniAlumno) {
        List<Examen> examenesAprobados = getExamenesAprobados(codigoComision, dniAlumno);

        return examenesAprobados.stream()
                .anyMatch(e -> e.getTipo() == Tipo.PARCIAL);
    }

    @Override
    public List<Examen> getExamenesAprobados(String codigoComision, String dniAlumno) {
        ComisionMateria comision = comisionRepository.findByCode(codigoComision);

        if (comision == null) return List.of();

        return comision.getExamenes().stream()
                .filter(e -> e.getAlumno().getDni().equals(dniAlumno))
                .filter(e -> e.getNota() != null && e.getNota() >= NOTA_APROBACION)
                .collect(Collectors.toList());
    }

    @Override
    public String crearExamenConValidaciones(String codigoComision, Examen examen) {
        ComisionMateria comision = comisionRepository.findByCode(codigoComision);

        if (comision == null) {
            return "ERROR: Comisión no encontrada";
        }

        if (!comision.isActivo()) {
            return "ERROR: La comisión no está activa";
        }

        boolean alumnoInscripto = comision.getAlumnosInscriptos().stream()
                .anyMatch(a -> a.getAlumnoInscriptoCarrera().getAlumno().getDni()
                        .equals(examen.getAlumno().getDni()));

        if (!alumnoInscripto) {
            return "ERROR: El alumno no está inscrito en esta comisión";
        }

        if (examen.getTipo() == Tipo.FINAL) {
            double porcentajeAsistencia = calcularPorcentajeAsistencia(codigoComision,
                    examen.getAlumno().getDni());
            boolean tieneParcialAprobado = tieneParcialAprobado(codigoComision,
                    examen.getAlumno().getDni());

            if (porcentajeAsistencia < PORCENTAJE_ASISTENCIA_MINIMO) {
                return String.format("ERROR: Asistencia insuficiente (%.1f%%) para rendir examen final. Mínimo: %.1f%%",
                        porcentajeAsistencia, PORCENTAJE_ASISTENCIA_MINIMO);
            }

            if (!tieneParcialAprobado) {
                return "ERROR: El alumno no tiene ningún parcial aprobado (nota >= 6). No puede rendir examen final";
            }
        }

        examen.setActivo(true);
        comision.getExamenes().add(examen);
        comisionRepository.update(comision);

        return "SUCCESS: Examen registrado correctamente";
    }

    public void precargarAsistenciasEjemplo(ComisionMateria comision) {
        if (comision.getAlumnosInscriptos().isEmpty() || comision.getHorarios().isEmpty()) {
            return;
        }

        java.util.Random random = new java.util.Random();
        LocalDate fechaInicio = LocalDate.now().minusMonths(2);

        for (int i = 0; i < CLASES_POR_CUATRIMESTRE; i++) {
            LocalDate fechaClase = fechaInicio.plusDays(i * 3);

            for (AlumnoInscriptoMateria alumnoInscripto : comision.getAlumnosInscriptos()) {
                boolean presente = random.nextInt(100) < 85;

                Asistencia asistencia = new Asistencia();
                asistencia.setFecha(fechaClase);
                asistencia.setPresente(presente);
                asistencia.setAlumno(alumnoInscripto.getAlumnoInscriptoCarrera().getAlumno());
                asistencia.setActivo(true);

                comision.getAsistencias().add(asistencia);
            }
        }

        comisionRepository.update(comision);
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