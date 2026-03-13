package ar.com.itecn1.view;

import ar.com.itecn1.controller.*;
import ar.com.itecn1.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ComisionView {

    // Códigos de color
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    private final ComisionController comisionController;
    private final CuatrimestreController cuatrimestreController;
    private final ProfesorController profesorController;
    private final HorarioController horarioController;
    private final ExamenController examenController;
    private final AlumnoInscriptoMateriaController alumnoInscriptoMateriaController;
    private final AlumnoInscriptoCarreraController alumnoInscriptoCarreraController;
    private final CarreraController carreraController;
    private final Scanner scanner;

    public ComisionView(
            ComisionController comisionController,
            CuatrimestreController cuatrimestreController,
            ProfesorController profesorController,
            HorarioController horarioController,
            ExamenController examenController,
            AlumnoInscriptoMateriaController alumnoInscriptoMateriaController,
            AlumnoInscriptoCarreraController alumnoInscriptoCarreraController,
            CarreraController carreraController,
            Scanner scanner
    ) {
        this.comisionController = comisionController;
        this.cuatrimestreController = cuatrimestreController;
        this.profesorController = profesorController;
        this.horarioController = horarioController;
        this.examenController = examenController;
        this.alumnoInscriptoMateriaController = alumnoInscriptoMateriaController;
        this.alumnoInscriptoCarreraController = alumnoInscriptoCarreraController;
        this.carreraController = carreraController;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarComisiones();
                case 2 -> buscarComision();
                case 3 -> crearComision();
                case 4 -> registrarAsistencias();
                case 5 -> eliminarComision();
                case 6 -> {
                    ComisionMateria com = buscarComisionPorCodigo();
                    if (com != null) listarAlumnosInscriptos(com);
                }
                case 7 -> {
                    ComisionMateria com = buscarComisionPorCodigo();
                    if (com != null) listarExamenes(com);
                }
                case 8 -> {
                    ComisionMateria com = buscarComisionPorCodigo();
                    if (com != null) listarAsistencias(com);
                }
                case 9 -> {
                    ComisionMateria com = buscarComisionPorCodigo();
                    if (com != null) listarHorarios(com);
                }
                case 10 -> actualizarComision();
                case 0 -> continuar = false;
                default -> System.out.println(RED + "Opción no válida" + RESET);
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n" + BLUE + BOLD + "COMISIONES" + RESET);
        System.out.println("----------");
        System.out.println(CYAN + "1. Listar comisiones" + RESET);
        System.out.println(CYAN + "2. Buscar comisión por código" + RESET);
        System.out.println(CYAN + "3. Registrar comisión" + RESET);
        System.out.println(CYAN + "4. Registrar asistencia" + RESET);
        System.out.println(CYAN + "5. Dar de baja comisión" + RESET);
        System.out.println(CYAN + "6. Listar alumnos inscriptos" + RESET);
        System.out.println(CYAN + "7. Listar exámenes" + RESET);
        System.out.println(CYAN + "8. Listar asistencias" + RESET);
        System.out.println(CYAN + "9. Listar horarios" + RESET);
        System.out.println(CYAN + "10. Actualizar comisión" + RESET);
        System.out.println(YELLOW + "0. Volver" + RESET);
        System.out.print("\nSeleccione: ");
    }

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            System.out.println(RED + "Debe ingresar un número." + RESET);
            scanner.next();
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        return n;
    }

    private int leerEnteroEnRango(int min, int max) {
        int num;
        do {
            num = leerEntero();
            if (num < min || num > max) {
                System.out.println(RED + "Ingrese un número entre " + min + " y " + max + RESET);
            }
        } while (num < min || num > max);
        return num;
    }

    private boolean confirmarAccion(String mensaje) {
        System.out.println("\n" + mensaje);
        System.out.println(YELLOW + "1. Sí" + RESET);
        System.out.println(YELLOW + "2. No" + RESET);
        System.out.print("Opción: ");

        int opcion = leerEnteroEnRango(1, 2);
        return opcion == 1;
    }

    private void mostrarComision(ComisionMateria c) {
        String estado = c.isActivo() ? GREEN + "ACTIVO" + RESET : RED + "INACTIVO" + RESET;
        System.out.println("┌────────────────────────────────────────────────────────────────┐");
        System.out.println("  " + CYAN + "CÓDIGO:" + RESET + " " + c.getCodigo());
        System.out.println("  " + CYAN + "CARRERA:" + RESET + " " + (c.getCarrera() != null ? c.getCarrera().getNombre() : "NO ASIGNADA"));
        System.out.println("  " + CYAN + "MATERIA:" + RESET + " " + (c.getMateria() != null ? c.getMateria().getNombre() : "NO ASIGNADA"));
        System.out.println("  " + CYAN + "CUATRIMESTRE:" + RESET + " " + (c.getCuatrimestre() != null ? c.getCuatrimestre().getNumero() : "NO ASIGNADO"));
        System.out.println("  " + CYAN + "PROFESOR:" + RESET + " " + (c.getProfesor() != null ? c.getProfesor().getNombre() + " " + c.getProfesor().getApellido() : "NO ASIGNADO"));
        System.out.println("  " + CYAN + "ALUMNOS:" + RESET + " " + (c.getAlumnosInscriptos() != null ? c.getAlumnosInscriptos().size() : 0));
        System.out.println("  " + CYAN + "HORARIOS:" + RESET + " " + (c.getHorarios() != null ? c.getHorarios().size() : 0));
        System.out.println("  " + CYAN + "EXÁMENES:" + RESET + " " + (c.getExamenes() != null ? c.getExamenes().size() : 0));
        System.out.println("  " + CYAN + "ASISTENCIAS:" + RESET + " " + (c.getAsistencias() != null ? c.getAsistencias().size() : 0));
        System.out.println("  " + CYAN + "ESTADO:" + RESET + " " + estado);
        System.out.println("└────────────────────────────────────────────────────────────────┘");
    }

    private ComisionMateria buscarComisionPorCodigo() {
        System.out.print("\nIngrese código de comisión: ");
        String codigo = scanner.nextLine().trim();
        ComisionMateria com = comisionController.findByCode(codigo);
        if (com == null) {
            System.out.println(RED + "Comisión no encontrada." + RESET);
        }
        return com;
    }

    private void listarComisiones() {
        System.out.println("\n" + BLUE + BOLD + "LISTADO DE COMISIONES" + RESET);
        System.out.println("======================");

        List<ComisionMateria> lista = comisionController.findAll();
        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay comisiones registradas.");
        } else {
            for (ComisionMateria c : lista) {
                mostrarComision(c);
            }
        }
        pausa();
    }

    private void buscarComision() {
        System.out.println("\n" + BLUE + BOLD + "BUSCAR COMISIÓN" + RESET);
        System.out.println("================");

        ComisionMateria c = buscarComisionPorCodigo();
        if (c != null) {
            mostrarComision(c);
        }
        pausa();
    }

    private void crearComision() {
        System.out.println("\n" + BLUE + BOLD + "REGISTRAR COMISIÓN" + RESET);
        System.out.println("===================");

        System.out.print("Código de comisión (ej. AN-MAT101-2025-1): ");
        String codigo = scanner.nextLine().trim();

        if (codigo.isEmpty()) {
            System.out.println(RED + "Código vacío. Operación cancelada." + RESET);
            pausa();
            return;
        }

        if (comisionController.findByCode(codigo) != null) {
            System.out.println(RED + "Ya existe una comisión con ese código." + RESET);
            pausa();
            return;
        }

        ComisionMateria nueva = new ComisionMateria();
        nueva.setCodigo(codigo);

        Carrera carrera = seleccionarCarrera();
        if (carrera == null) return;

        Materia materia = seleccionarMateria(carrera);
        if (materia == null) return;

        Profesor profesor = seleccionarProfesor();
        if (profesor == null) return;

        Cuatrimestre cuatrimestre = seleccionarCuatrimestre(materia);
        if (cuatrimestre == null) return;

        nueva.setCarrera(carrera);
        nueva.setMateria(materia);
        nueva.setProfesor(profesor);
        nueva.setCuatrimestre(cuatrimestre);
        nueva.setAlumnosInscriptos(new ArrayList<>());
        nueva.setExamenes(new ArrayList<>());
        nueva.setAsistencias(new ArrayList<>());
        nueva.setHorarios(new ArrayList<>());
        nueva.setActivo(true);

        System.out.println("\n" + CYAN + "VISTA PREVIA:" + RESET);
        mostrarComision(nueva);

        if (confirmarAccion("¿Confirmar registro?")) {
            String resultado = comisionController.registrarComision(nueva);
            if (resultado.startsWith("SUCCESS")) {
                System.out.println(GREEN + "✓ Comisión registrada exitosamente!" + RESET);
            } else {
                System.out.println(RED + "✗ " + resultado.substring(6) + RESET);
            }
        }
        pausa();
    }

    private Carrera seleccionarCarrera() {
        List<Carrera> lista = carreraController.findAll();
        if (lista == null || lista.isEmpty()) {
            System.out.println(RED + "No hay carreras registradas." + RESET);
            pausa();
            return null;
        }

        System.out.println("\n" + CYAN + "CARRERAS DISPONIBLES:" + RESET);
        for (int i = 0; i < lista.size(); i++) {
            Carrera c = lista.get(i);
            System.out.println((i + 1) + ". " + c.getNombre() + " (" + c.getTurno() + ")");
        }

        int op = leerEnteroEnRango(1, lista.size());
        return lista.get(op - 1);
    }

    private Materia seleccionarMateria(Carrera carrera) {
        PlanEstudio plan = carrera.getPlanEstudio();
        if (plan == null || plan.getMaterias() == null || plan.getMaterias().isEmpty()) {
            System.out.println(RED + "La carrera no tiene materias en su plan de estudio." + RESET);
            pausa();
            return null;
        }

        System.out.println("\n" + CYAN + "MATERIAS DEL PLAN " + plan.getNombre() + ":" + RESET);
        List<Materia> materias = plan.getMaterias();
        for (int i = 0; i < materias.size(); i++) {
            Materia m = materias.get(i);
            System.out.println((i + 1) + ". " + m.getCodigoMateria() + " - " + m.getNombre());
        }

        int op = leerEnteroEnRango(1, materias.size());
        return materias.get(op - 1);
    }

    private Profesor seleccionarProfesor() {
        List<Profesor> lista = profesorController.findAll();
        if (lista == null || lista.isEmpty()) {
            System.out.println(RED + "No hay profesores registrados." + RESET);
            pausa();
            return null;
        }

        System.out.println("\n" + CYAN + "PROFESORES DISPONIBLES:" + RESET);
        for (int i = 0; i < lista.size(); i++) {
            Profesor p = lista.get(i);
            System.out.println((i + 1) + ". " + p.getApellido() + ", " + p.getNombre());
        }

        int op = leerEnteroEnRango(1, lista.size());
        return lista.get(op - 1);
    }

    private Cuatrimestre seleccionarCuatrimestre(Materia materia) {
        List<Cuatrimestre> lista = cuatrimestreController.findAll();
        if (lista == null || lista.isEmpty()) {
            System.out.println(RED + "No hay cuatrimestres registrados." + RESET);
            pausa();
            return null;
        }

        String cuatrimestreMateria = materia.getCuatrimestre();

        for (Cuatrimestre c : lista) {
            if (c.getNumero().equals(cuatrimestreMateria)) {
                System.out.println("\n" + GREEN + "✓ Cuatrimestre asignado: " + c.getNumero() + "° - Año " + c.getAnio() + RESET);
                return c;
            }
        }

        System.out.println(RED + "Error: No hay cuatrimestre para el " + cuatrimestreMateria + "° cuatrimestre." + RESET);
        pausa();
        return null;
    }

    private void eliminarComision() {
        System.out.println("\n" + BLUE + BOLD + "ELIMINAR COMISIÓN" + RESET);
        System.out.println("==================");

        ComisionMateria c = buscarComisionPorCodigo();
        if (c == null) {
            pausa();
            return;
        }

        mostrarComision(c);

        if (confirmarAccion(RED + "¿Está seguro de eliminar esta comisión?" + RESET)) {
            comisionController.deleteComision(c);
            System.out.println(GREEN + "✓ Comisión eliminada correctamente." + RESET);
        }
        pausa();
    }

    private void actualizarComision() {
        System.out.println("\n" + BLUE + BOLD + "ACTUALIZAR COMISIÓN" + RESET);
        System.out.println("====================");

        ComisionMateria comision = buscarComisionPorCodigo();
        if (comision == null) {
            pausa();
            return;
        }

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n" + CYAN + "OPCIONES DE ACTUALIZACIÓN:" + RESET);
            System.out.println("1. Alumnos inscriptos");
            System.out.println("2. Exámenes");
            System.out.println("3. Eliminar asistencia");
            System.out.println("4. Cambiar profesor");
            System.out.println("5. Modificar horarios");
            System.out.println("6. Ver comisión");
            System.out.println(YELLOW + "0. Volver" + RESET);
            System.out.print("Opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> actualizarAlumnos(comision);
                case 2 -> actualizarExamenes(comision);
                case 3 -> eliminarAsistencia(comision);
                case 4 -> cambiarProfesor(comision);
                case 5 -> modificarHorarios(comision);
                case 6 -> mostrarComision(comision);
                case 0 -> continuar = false;
                default -> System.out.println(RED + "Opción no válida." + RESET);
            }
        }
    }

    private void cambiarProfesor(ComisionMateria comision) {
        List<Profesor> profesores = profesorController.findAll();
        if (profesores.isEmpty()) {
            System.out.println(RED + "No hay profesores registrados." + RESET);
            return;
        }

        System.out.println("\n" + CYAN + "PROFESORES DISPONIBLES:" + RESET);
        for (int i = 0; i < profesores.size(); i++) {
            Profesor p = profesores.get(i);
            System.out.println((i + 1) + ". " + p.getApellido() + ", " + p.getNombre());
        }

        System.out.print("Seleccione (0 para cancelar): ");
        int seleccion = leerEntero();

        if (seleccion > 0 && seleccion <= profesores.size()) {
            comision.setProfesor(profesores.get(seleccion - 1));
            comisionController.updateComision(comision);
            System.out.println(GREEN + "✓ Profesor actualizado." + RESET);
        }
    }

    private void modificarHorarios(ComisionMateria comision) {
        boolean seguir = true;
        while (seguir) {
            System.out.println("\n" + CYAN + "GESTIÓN DE HORARIOS:" + RESET);
            System.out.println("1. Agregar horario");
            System.out.println("2. Eliminar horario");
            System.out.println(YELLOW + "0. Volver" + RESET);
            System.out.print("Opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> agregarHorario(comision);
                case 2 -> eliminarHorario(comision);
                case 0 -> seguir = false;
                default -> System.out.println(RED + "Opción no válida." + RESET);
            }
        }
    }

    private void agregarHorario(ComisionMateria comision) {
        System.out.println("\n" + CYAN + "NUEVO HORARIO:" + RESET);

        System.out.println("Días:");
        Dia[] dias = Dia.values();
        for (int i = 0; i < dias.length; i++) {
            System.out.println((i + 1) + ". " + dias[i]);
        }
        System.out.print("Seleccione día: ");
        int diaIdx = leerEnteroEnRango(1, dias.length) - 1;
        Dia dia = dias[diaIdx];

        System.out.print("Hora inicio (HH:mm): ");
        String horaInicioStr = scanner.nextLine();
        LocalTime inicio;
        try {
            inicio = LocalTime.parse(horaInicioStr);
        } catch (Exception e) {
            System.out.println(RED + "Formato de hora inválido. Use HH:mm" + RESET);
            return;
        }

        System.out.print("Hora fin (HH:mm): ");
        String horaFinStr = scanner.nextLine();
        LocalTime fin;
        try {
            fin = LocalTime.parse(horaFinStr);
        } catch (Exception e) {
            System.out.println(RED + "Formato de hora inválido. Use HH:mm" + RESET);
            return;
        }

        if (!fin.isAfter(inicio)) {
            System.out.println(RED + "La hora fin debe ser posterior a inicio." + RESET);
            return;
        }

        Modulo modulo = new Modulo();
        modulo.setCodigo("M" + inicio.toString().replace(":", "") + "-" + fin.toString().replace(":", ""));
        modulo.setInicio(inicio);
        modulo.setFin(fin);

        Horario horario = new Horario();
        horario.setDia(dia);
        horario.setModulo(modulo);
        horario.setDisponible(true);

        comision.getHorarios().add(horario);
        comisionController.updateComision(comision);
        System.out.println(GREEN + "✓ Horario agregado." + RESET);
    }

    private void eliminarHorario(ComisionMateria comision) {
        List<Horario> horarios = comision.getHorarios();
        if (horarios.isEmpty()) {
            System.out.println(YELLOW + "No hay horarios para eliminar." + RESET);
            return;
        }

        System.out.println("\n" + CYAN + "HORARIOS ACTUALES:" + RESET);
        for (int i = 0; i < horarios.size(); i++) {
            Horario h = horarios.get(i);
            System.out.println((i + 1) + ". " + h.getDia() + " " + h.getModulo().getInicio() + " - " + h.getModulo().getFin());
        }

        System.out.print("Seleccione horario a eliminar (0 para cancelar): ");
        int seleccion = leerEntero();

        if (seleccion > 0 && seleccion <= horarios.size()) {
            horarios.remove(seleccion - 1);
            comisionController.updateComision(comision);
            System.out.println(GREEN + "✓ Horario eliminado." + RESET);
        }
    }

    private void actualizarAlumnos(ComisionMateria comision) {
        gestionarAlumnosInscriptos(comision.getAlumnosInscriptos(), comision);
        comisionController.updateComision(comision);
        System.out.println(GREEN + "✓ Actualización de alumnos completada." + RESET);
    }

    private void actualizarExamenes(ComisionMateria comision) {
        gestionExamenes(comision.getExamenes(), comision);
        comisionController.updateComision(comision);
        System.out.println(GREEN + "✓ Actualización de exámenes completada." + RESET);
    }

    private void eliminarAsistencia(ComisionMateria comision) {
        List<Asistencia> asistencias = comision.getAsistencias();
        if (asistencias == null || asistencias.isEmpty()) {
            System.out.println(YELLOW + "No hay asistencias registradas." + RESET);
            return;
        }

        System.out.println("\n" + CYAN + "ASISTENCIAS REGISTRADAS:" + RESET);
        for (int i = 0; i < asistencias.size(); i++) {
            Asistencia a = asistencias.get(i);
            Alumno al = a.getAlumno();
            System.out.println((i + 1) + ". " + al.getApellido() + ", " + al.getNombre() +
                    " | Fecha: " + a.getFecha() + " | " + (a.isPresente() ? "Presente" : "Ausente"));
        }

        System.out.print("Seleccione asistencia a eliminar (0 para cancelar): ");
        int seleccion = leerEntero();

        if (seleccion > 0 && seleccion <= asistencias.size()) {
            asistencias.remove(seleccion - 1);
            comisionController.updateComision(comision);
            System.out.println(GREEN + "✓ Asistencia eliminada." + RESET);
        }
    }

    private void registrarAsistencias() {
        System.out.println("\n" + BLUE + BOLD + "REGISTRAR ASISTENCIA" + RESET);
        System.out.println("=====================");

        System.out.print("DNI del alumno: ");
        String dni = scanner.nextLine().trim();

        System.out.print("Código de comisión: ");
        String codigo = scanner.nextLine().trim();

        if (!comisionController.puedeRegistrarAsistencia(codigo, dni)) {
            System.out.println(RED + "No se puede registrar la asistencia. Verifique:" + RESET);
            System.out.println("- Que el alumno esté inscrito");
            System.out.println("- Que no tenga asistencia hoy");
            System.out.println("- Que haya clase en este horario");
            pausa();
            return;
        }

        System.out.println("\n" + CYAN + "TIPO DE ASISTENCIA:" + RESET);
        System.out.println("1. Presente");
        System.out.println("2. Ausente");
        System.out.print("Seleccione: ");

        int tipo = leerEnteroEnRango(1, 2);

        Asistencia asistencia = new Asistencia();
        asistencia.setFecha(LocalDate.now());
        asistencia.setPresente(tipo == 1);
        asistencia.setActivo(true);

        String resultado = comisionController.registrarAsistencia(codigo, dni, asistencia);
        if (resultado.startsWith("SUCCESS")) {
            System.out.println(GREEN + "✓ Asistencia registrada." + RESET);
        } else {
            System.out.println(RED + "✗ " + resultado.substring(6) + RESET);
        }
        pausa();
    }

    private void listarAlumnosInscriptos(ComisionMateria comision) {
        System.out.println("\n" + BLUE + BOLD + "ALUMNOS INSCRIPTOS - " + comision.getCodigo() + RESET);
        System.out.println("================================");

        List<AlumnoInscriptoMateria> lista = comision.getAlumnosInscriptos();
        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay alumnos inscriptos.");
        } else {
            for (int i = 0; i < lista.size(); i++) {
                Alumno a = lista.get(i).getAlumnoInscriptoCarrera().getAlumno();
                System.out.println((i + 1) + ". " + a.getApellido() + ", " + a.getNombre() + " (DNI: " + a.getDni() + ")");
            }
        }
        pausa();
    }

    private void listarExamenes(ComisionMateria comision) {
        System.out.println("\n" + BLUE + BOLD + "EXÁMENES - " + comision.getCodigo() + RESET);
        System.out.println("======================");

        List<Examen> examenes = comision.getExamenes();
        if (examenes == null || examenes.isEmpty()) {
            System.out.println("No hay exámenes registrados.");
        } else {
            for (Examen e : examenes) {
                String estado = e.isActivo() ? "ACTIVO" : "INACTIVO";
                System.out.println("  • " + e.getAlumno().getApellido() + ": " + e.getTipo() +
                        " - Nota: " + e.getNota() + " - Fecha: " + e.getFecha() + " (" + estado + ")");
            }
        }
        pausa();
    }

    private void listarAsistencias(ComisionMateria comision) {
        System.out.println("\n" + BLUE + BOLD + "ASISTENCIAS - " + comision.getCodigo() + RESET);
        System.out.println("========================");

        List<Asistencia> asistencias = comision.getAsistencias();
        if (asistencias == null || asistencias.isEmpty()) {
            System.out.println("No hay asistencias registradas.");
        } else {
            for (Asistencia a : asistencias) {
                String presente = a.isPresente() ? GREEN + "Presente" + RESET : RED + "Ausente" + RESET;
                System.out.println("  • " + a.getAlumno().getApellido() + " - " + a.getFecha() + " - " + presente);
            }
        }
        pausa();
    }

    private void listarHorarios(ComisionMateria comision) {
        System.out.println("\n" + BLUE + BOLD + "HORARIOS - " + comision.getCodigo() + RESET);
        System.out.println("======================");

        List<Horario> horarios = comision.getHorarios();
        if (horarios == null || horarios.isEmpty()) {
            System.out.println("No hay horarios registrados.");
        } else {
            for (Horario h : horarios) {
                System.out.println("  • " + h.getDia() + " " + h.getModulo().getInicio() + " - " + h.getModulo().getFin());
            }
        }
        pausa();
    }

    private void gestionarAlumnosInscriptos(List<AlumnoInscriptoMateria> listaInscriptos, ComisionMateria comision) {
        boolean gestionando = true;

        while (gestionando) {
            System.out.println("\n" + CYAN + "GESTIÓN DE ALUMNOS:" + RESET);
            System.out.println("1. Inscribir alumno");
            System.out.println("2. Dar de baja alumno");
            System.out.println("3. Ver alumnos inscriptos");
            System.out.println(YELLOW + "4. Terminar" + RESET);
            System.out.print("Opción: ");

            int opcion = leerEnteroEnRango(1, 4);

            switch (opcion) {
                case 1 -> inscribirAlumno(listaInscriptos, comision);
                case 2 -> darDeBajaAlumno(listaInscriptos);
                case 3 -> verAlumnosInscriptos(listaInscriptos);
                case 4 -> gestionando = false;
            }
        }
    }

    private void inscribirAlumno(List<AlumnoInscriptoMateria> listaInscriptos, ComisionMateria comision) {
        List<AlumnoInscriptoCarrera> inscriptosCarrera = alumnoInscriptoCarreraController.findAll();
        if (inscriptosCarrera == null || inscriptosCarrera.isEmpty()) {
            System.out.println(RED + "No hay alumnos inscriptos a carreras." + RESET);
            return;
        }

        System.out.println("\n" + CYAN + "ALUMNOS DISPONIBLES:" + RESET);
        List<AlumnoInscriptoCarrera> disponibles = new ArrayList<>();

        for (AlumnoInscriptoCarrera aic : inscriptosCarrera) {
            if (aic.getCarrera().getNombre().equals(comision.getCarrera().getNombre())) {
                disponibles.add(aic);
                System.out.println(disponibles.size() + ". " + aic.getAlumno().getApellido() + ", " +
                        aic.getAlumno().getNombre() + " (DNI: " + aic.getAlumno().getDni() + ")");
            }
        }

        if (disponibles.isEmpty()) {
            System.out.println(YELLOW + "No hay alumnos disponibles para esta carrera." + RESET);
            return;
        }

        System.out.print("Seleccione (0 para cancelar): ");
        int seleccion = leerEntero();

        if (seleccion > 0 && seleccion <= disponibles.size()) {
            AlumnoInscriptoCarrera aic = disponibles.get(seleccion - 1);

            // Verificar si ya está inscripto
            boolean yaInscripto = listaInscriptos.stream()
                    .anyMatch(a -> a.getAlumnoInscriptoCarrera().getAlumno().getDni()
                            .equals(aic.getAlumno().getDni()));

            if (yaInscripto) {
                System.out.println(RED + "El alumno ya está inscripto en esta comisión." + RESET);
                return;
            }

            AlumnoInscriptoMateria aim = new AlumnoInscriptoMateria();
            aim.setAlumnoInscriptoCarrera(aic);
            aim.setExamenes(new ArrayList<>());
            aim.setEstado(Estado.REGULAR);
            aim.setActivo(true);

            System.out.println("\n" + CYAN + "VISTA PREVIA:" + RESET);
            System.out.println("  Alumno: " + aic.getAlumno().getApellido() + ", " + aic.getAlumno().getNombre());
            System.out.println("  DNI: " + aic.getAlumno().getDni());

            if (confirmarAccion("¿Confirmar inscripción?")) {
                String resultado = comisionController.inscribirAlumno(comision.getCodigo(),
                        aic.getAlumno().getDni(), aim);
                if (resultado.startsWith("SUCCESS")) {
                    System.out.println(GREEN + "✓ Alumno inscrito." + RESET);
                    listaInscriptos.add(aim);
                } else {
                    System.out.println(RED + "✗ " + resultado.substring(6) + RESET);
                }
            }
        }
    }

    private void darDeBajaAlumno(List<AlumnoInscriptoMateria> listaInscriptos) {
        if (listaInscriptos.isEmpty()) {
            System.out.println(YELLOW + "No hay alumnos inscriptos." + RESET);
            return;
        }

        System.out.println("\n" + CYAN + "ALUMNOS INSCRIPTOS:" + RESET);
        for (int i = 0; i < listaInscriptos.size(); i++) {
            Alumno a = listaInscriptos.get(i).getAlumnoInscriptoCarrera().getAlumno();
            System.out.println((i + 1) + ". " + a.getApellido() + ", " + a.getNombre());
        }

        System.out.print("Seleccione (0 para cancelar): ");
        int seleccion = leerEntero();

        if (seleccion > 0 && seleccion <= listaInscriptos.size()) {
            AlumnoInscriptoMateria eliminado = listaInscriptos.remove(seleccion - 1);
            eliminado.setActivo(false);
            System.out.println(GREEN + "✓ Alumno dado de baja." + RESET);
        }
    }

    private void verAlumnosInscriptos(List<AlumnoInscriptoMateria> listaInscriptos) {
        if (listaInscriptos.isEmpty()) {
            System.out.println(YELLOW + "No hay alumnos inscriptos." + RESET);
        } else {
            System.out.println("\n" + CYAN + "ALUMNOS INSCRIPTOS:" + RESET);
            for (AlumnoInscriptoMateria aim : listaInscriptos) {
                Alumno a = aim.getAlumnoInscriptoCarrera().getAlumno();
                System.out.println("  • " + a.getApellido() + ", " + a.getNombre() + " (DNI: " + a.getDni() + ")");
            }
        }
    }

    private void gestionExamenes(List<Examen> examenes, ComisionMateria comision) {
        boolean gestionando = true;

        while (gestionando) {
            System.out.println("\n" + CYAN + "GESTIÓN DE EXÁMENES:" + RESET);
            System.out.println("1. Crear examen");
            System.out.println("2. Cambiar nota");
            System.out.println("3. Eliminar examen");
            System.out.println("4. Ver exámenes");
            System.out.println(YELLOW + "0. Terminar" + RESET);
            System.out.print("Opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> crearExamen(examenes, comision);
                case 2 -> cambiarNota(examenes);
                case 3 -> eliminarExamen(examenes);
                case 4 -> verExamenes(examenes);
                case 0 -> gestionando = false;
                default -> System.out.println(RED + "Opción no válida." + RESET);
            }
        }
    }

    private void crearExamen(List<Examen> examenes, ComisionMateria comision) {
        if (comision.getAlumnosInscriptos().isEmpty()) {
            System.out.println(RED + "No hay alumnos inscriptos en la comisión." + RESET);
            return;
        }

        Examen nuevo = new Examen();
        List<Examen> todos = examenController.findAll();
        int nextId = (todos == null ? 0 : todos.size()) + 1;
        nuevo.setId(String.valueOf(nextId));

        System.out.println("\n" + CYAN + "TIPOS DE EXAMEN:" + RESET);
        Tipo[] tipos = Tipo.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }
        System.out.print("Seleccione: ");
        int tipoIdx = leerEnteroEnRango(1, tipos.length) - 1;
        Tipo tipo = tipos[tipoIdx];
        nuevo.setTipo(tipo);

        System.out.print("Nota (0-10): ");
        double nota = scanner.nextDouble();
        scanner.nextLine();
        if (nota < 0 || nota > 10) {
            System.out.println(RED + "Nota inválida." + RESET);
            return;
        }
        nuevo.setNota(nota);

        System.out.print("Fecha (AAAA-MM-DD): ");
        try {
            nuevo.setFecha(LocalDate.parse(scanner.nextLine()));
        } catch (Exception e) {
            System.out.println(RED + "Fecha inválida." + RESET);
            return;
        }

        System.out.println("\n" + CYAN + "ALUMNOS INSCRIPTOS:" + RESET);
        List<AlumnoInscriptoMateria> alumnos = comision.getAlumnosInscriptos();
        for (int i = 0; i < alumnos.size(); i++) {
            Alumno a = alumnos.get(i).getAlumnoInscriptoCarrera().getAlumno();
            System.out.println((i + 1) + ". " + a.getApellido() + ", " + a.getNombre());
        }

        System.out.print("Seleccione alumno: ");
        int alumnoIdx = leerEnteroEnRango(1, alumnos.size()) - 1;

        AlumnoInscriptoMateria aim = alumnos.get(alumnoIdx);
        nuevo.setAlumno(aim.getAlumnoInscriptoCarrera().getAlumno());
        nuevo.setMateria(comision.getMateria());

        // Validar requisitos para examen final
        if (tipo == Tipo.FINAL) {
            double asistencia = comisionController.calcularPorcentajeAsistencia(
                    comision.getCodigo(), nuevo.getAlumno().getDni());
            boolean tieneParcial = comisionController.tieneParcialAprobado(
                    comision.getCodigo(), nuevo.getAlumno().getDni());

            System.out.println("\n" + YELLOW + "REQUISITOS:" + RESET);
            System.out.printf("  Asistencia: %.1f%% (mínimo 70%%) %s%n",
                    asistencia, asistencia >= 70 ? GREEN + "✅" + RESET : RED + "❌" + RESET);
            System.out.println("  Parcial aprobado: " + (tieneParcial ? GREEN + "✅" + RESET : RED + "❌" + RESET));

            if (asistencia < 70 || !tieneParcial) {
                if (!confirmarAccion(RED + "¿Crear examen de todas formas?" + RESET)) {
                    return;
                }
            }
        }

        if (confirmarAccion("¿Confirmar creación?")) {
            String resultado = comisionController.crearExamenConValidaciones(comision.getCodigo(), nuevo);

            if (resultado.startsWith("SUCCESS")) {
                System.out.println(GREEN + "✓ Examen creado." + RESET);
                aim.getExamenes().add(nuevo);
                examenes.add(nuevo);
            } else {
                System.out.println(RED + "✗ " + resultado.substring(6) + RESET);
            }
        }
    }

    private void cambiarNota(List<Examen> examenes) {
        if (examenes.isEmpty()) {
            System.out.println(YELLOW + "No hay exámenes." + RESET);
            return;
        }

        System.out.println("\n" + CYAN + "EXÁMENES:" + RESET);
        for (int i = 0; i < examenes.size(); i++) {
            Examen e = examenes.get(i);
            System.out.println((i + 1) + ". " + e.getAlumno().getApellido() + " - Nota: " + e.getNota());
        }

        System.out.print("Seleccione examen (0 para cancelar): ");
        int seleccion = leerEntero();

        if (seleccion > 0 && seleccion <= examenes.size()) {
            System.out.print("Nueva nota: ");
            double nuevaNota = scanner.nextDouble();
            scanner.nextLine();

            if (nuevaNota >= 0 && nuevaNota <= 10) {
                examenes.get(seleccion - 1).setNota(nuevaNota);
                System.out.println(GREEN + "✓ Nota actualizada." + RESET);
            } else {
                System.out.println(RED + "Nota inválida." + RESET);
            }
        }
    }

    private void eliminarExamen(List<Examen> examenes) {
        if (examenes.isEmpty()) {
            System.out.println(YELLOW + "No hay exámenes." + RESET);
            return;
        }

        System.out.println("\n" + CYAN + "EXÁMENES:" + RESET);
        for (int i = 0; i < examenes.size(); i++) {
            Examen e = examenes.get(i);
            System.out.println((i + 1) + ". " + e.getAlumno().getApellido() + " - " + e.getTipo());
        }

        System.out.print("Seleccione examen a eliminar (0 para cancelar): ");
        int seleccion = leerEntero();

        if (seleccion > 0 && seleccion <= examenes.size()) {
            examenes.remove(seleccion - 1);
            System.out.println(GREEN + "✓ Examen eliminado." + RESET);
        }
    }

    private void verExamenes(List<Examen> examenes) {
        if (examenes.isEmpty()) {
            System.out.println(YELLOW + "No hay exámenes." + RESET);
        } else {
            System.out.println("\n" + CYAN + "EXÁMENES:" + RESET);
            for (Examen e : examenes) {
                System.out.println("  • " + e.getAlumno().getApellido() + ", " + e.getAlumno().getNombre() +
                        " | " + e.getTipo() + " | Nota: " + e.getNota() + " | Fecha: " + e.getFecha());
            }
        }
    }

    private void pausa() {
        System.out.print("\n" + CYAN + "Presione ENTER para continuar..." + RESET);
        scanner.nextLine();
    }
}