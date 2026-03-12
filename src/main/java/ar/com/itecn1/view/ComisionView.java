package ar.com.itecn1.view;

import ar.com.itecn1.controller.*;
import ar.com.itecn1.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ComisionView {

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
                default -> System.out.println("Ingrese una opción válida.");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n===== GESTIÓN DE COMISIÓN-MATERIA =====");
        System.out.println("1. Listar comisiones");
        System.out.println("2. Buscar comisión por código");
        System.out.println("3. Registrar comisión");
        System.out.println("4. Registrar una asistencia");
        System.out.println("5. Dar de baja comisión");
        System.out.println("6. Listar alumnos inscriptos de una comisión");
        System.out.println("7. Listar exámenes de una comisión");
        System.out.println("8. Listar asistencias de una comisión");
        System.out.println("9. Listar horarios");
        System.out.println("10. Actualizar comisión");
        System.out.println("0. Volver atrás");
        System.out.print("Seleccione opción: ");
    }

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Debe ingresar un número. Intente nuevamente: ");
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        return n;
    }

    private String leerString() {
        return scanner.nextLine().trim();
    }

    private boolean confirmarAccion(String mensaje) {
        int opcion;
        do {
            System.out.println(mensaje);
            System.out.println("1. Sí");
            System.out.println("2. No");
            opcion = leerEntero();
        } while (opcion < 1 || opcion > 2);
        return opcion == 1;
    }

    private int leerEnteroEnRango(int min, int max) {
        int num;
        do {
            num = leerEntero();
            if (num < min || num > max) {
                System.out.println("Ingrese un número entre " + min + " y " + max);
            }
        } while (num < min || num > max);
        return num;
    }

    private void mostrarComision(ComisionMateria c) {
        System.out.println("--------------------------------------------------");
        System.out.println("CÓDIGO: " + c.getCodigo());
        System.out.println("CARRERA: " + (c.getCarrera() != null ? c.getCarrera().getNombre() : "NO ASIGNADA"));
        System.out.println("MATERIA: " + (c.getMateria() != null ? c.getMateria().getNombre() : "NO ASIGNADA"));
        System.out.println("CUATRIMESTRE: " + (c.getCuatrimestre() != null ? c.getCuatrimestre().getNumero() : "NO ASIGNADO"));
        System.out.println("PROFESOR: " + (c.getProfesor() != null ? c.getProfesor().getNombre() + " " + c.getProfesor().getApellido() : "NO ASIGNADO"));
        System.out.println("ALUMNOS: " + (c.getAlumnosInscriptos() != null ? c.getAlumnosInscriptos().size() : 0));
        System.out.println("HORARIOS: " + (c.getHorarios() != null ? c.getHorarios().size() : 0));
        System.out.println("EXÁMENES: " + (c.getExamenes() != null ? c.getExamenes().size() : 0));
        System.out.println("ASISTENCIAS: " + (c.getAsistencias() != null ? c.getAsistencias().size() : 0));
        System.out.println("ESTADO: " + (c.isActivo() ? "ACTIVO" : "INACTIVO"));
        System.out.println("--------------------------------------------------");
    }

    private ComisionMateria buscarComisionPorCodigo() {
        System.out.print("Ingrese código de comisión: ");
        String codigo = scanner.nextLine();
        ComisionMateria com = comisionController.findByCode(codigo);
        if (com == null) {
            System.out.println("Comisión no encontrada.");
        }
        return com;
    }

    private void listarComisiones() {
        System.out.println("\n--- Listado de Comisiones ---");
        List<ComisionMateria> lista = comisionController.findAll();
        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay comisiones registradas.");
            return;
        }
        for (ComisionMateria c : lista) {
            mostrarComision(c);
        }
    }

    private void buscarComision() {
        System.out.print("\nIngrese el código de la comisión: ");
        String codigo = scanner.nextLine().trim();
        ComisionMateria c = comisionController.findByCode(codigo);
        if (c == null) {
            System.out.println("Comisión no encontrada.");
            return;
        }
        mostrarComision(c);
    }

    private void crearComision() {
        System.out.println("\n--- Registrar Comisión ---");
        System.out.print("Código de comisión (ej. AN-MAT101-2025-1): ");
        String codigo = scanner.nextLine().trim();

        if (codigo.isEmpty()) {
            System.out.println("Código vacío. Operación cancelada.");
            return;
        }
        if (comisionController.findByCode(codigo) != null) {
            System.out.println("Ya existe comisión con ese código.");
            return;
        }

        ComisionMateria nueva = new ComisionMateria();
        nueva.setCodigo(codigo);

        Carrera carrera = seleccionarCarrera();
        if (carrera == null) return;
        nueva.setCarrera(carrera);

        Materia materia = seleccionarMateria(carrera);
        if (materia == null) return;
        nueva.setMateria(materia);

        Profesor profesor = seleccionarProfesor();
        if (profesor == null) return;
        nueva.setProfesor(profesor);

        Cuatrimestre cuatrimestre = seleccionarCuatrimestre(materia);
        if (cuatrimestre == null) return;
        nueva.setCuatrimestre(cuatrimestre);

        nueva.setAlumnosInscriptos(new ArrayList<>());
        nueva.setExamenes(new ArrayList<>());
        nueva.setAsistencias(new ArrayList<>());
        nueva.setHorarios(new ArrayList<>());
        nueva.setActivo(true);

        System.out.println("\nVista previa de la comisión:");
        mostrarComision(nueva);

        if (confirmarAccion("¿Confirmar registro de la comisión?")) {
            String resultado = comisionController.registrarComision(nueva);
            if (resultado.startsWith("SUCCESS")) {
                System.out.println("✓ " + resultado.substring(8));
            } else {
                System.out.println("✗ " + resultado.substring(6));
            }
        } else {
            System.out.println("Registro cancelado.");
        }
    }

    private Carrera seleccionarCarrera() {
        List<Carrera> lista = carreraController.findAll();
        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay carreras registradas.");
            return null;
        }
        System.out.println("\n--- Seleccione Carrera ---");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i).getNombre() + " | Turno: " + lista.get(i).getTurno());
        }
        int op = seleccionarDeLista(lista.size());
        return lista.get(op - 1);
    }

    private Materia seleccionarMateria(Carrera carrera) {
        PlanEstudio plan = carrera.getPlanEstudio();
        if (plan == null || plan.getMaterias() == null || plan.getMaterias().isEmpty()) {
            System.out.println("La carrera seleccionada no tiene materias asignadas en su plan de estudio.");
            return null;
        }

        System.out.println("\n--- Seleccione la Materia (según el Plan de Estudio) ---");
        List<Materia> materiasPlan = plan.getMaterias();
        for (int i = 0; i < materiasPlan.size(); i++) {
            Materia mat = materiasPlan.get(i);
            System.out.println((i + 1) + ". " + mat.getCodigoMateria() + " | " + mat.getNombre());
        }

        System.out.println("Ingrese el número de la materia:");
        int opcionMateria = leerEnteroEnRango(1, materiasPlan.size());
        return materiasPlan.get(opcionMateria - 1);
    }

    private Profesor seleccionarProfesor() {
        List<Profesor> lista = profesorController.findAll();
        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay profesores registrados.");
            return null;
        }
        System.out.println("\n--- Seleccione Profesor ---");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i).getDni() + " | " + lista.get(i).getApellido() + " " + lista.get(i).getNombre());
        }
        int op = seleccionarDeLista(lista.size());
        return lista.get(op - 1);
    }

    private Cuatrimestre seleccionarCuatrimestre(Materia materia) {
        List<Cuatrimestre> lista = cuatrimestreController.findAll();
        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay cuatrimestres registrados.");
            return null;
        }

        String cuatrimestreMateria = materia.getCuatrimestre();

        for (Cuatrimestre c : lista) {
            if (c.getNumero().equals(cuatrimestreMateria)) {
                System.out.println("\n📌 Cuatrimestre asignado automáticamente: " +
                        c.getNumero() + "° - Año: " + c.getAnio());
                return c;
            }
        }

        System.out.println("Error: No hay cuatrimestre registrado para el " +
                cuatrimestreMateria + "° cuatrimestre.");
        return null;
    }

    private int seleccionarDeLista(int max) {
        int op;
        do {
            System.out.print("Seleccione opción (1-" + max + "): ");
            op = leerEntero();
        } while (op < 1 || op > max);
        return op;
    }

    private void eliminarComision() {
        System.out.print("\nIngrese el código de la comisión a eliminar: ");
        String codigo = scanner.nextLine().trim();
        ComisionMateria c = comisionController.findByCode(codigo);
        if (c == null) {
            System.out.println("Comisión no encontrada.");
            return;
        }
        mostrarComision(c);
        if (confirmarAccion("¿Confirmar eliminación?")) {
            comisionController.deleteComision(c);
            System.out.println("Comisión eliminada.");
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    private void actualizarComision() {
        System.out.println("\n--- Actualizar Comisión ---");
        ComisionMateria comision = buscarComisionPorCodigo();
        if (comision == null) return;

        boolean continuar = true;
        while (continuar) {
            System.out.println("1. Alumnos inscriptos");
            System.out.println("2. Exámenes");
            System.out.println("3. Eliminar una asistencia");
            System.out.println("4. Cambiar profesor");
            System.out.println("5. Modificar horarios");
            System.out.println("6. Ver comisión");
            System.out.println("0. Salir de actualización");
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
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void cambiarProfesor(ComisionMateria comision) {
        System.out.println("\n--- Cambiar Profesor ---");
        List<Profesor> profesores = this.profesorController.findAll();
        if(profesores.isEmpty()){
            System.out.println("No hay profesores registrados en el sistema.");
            return;
        }
        System.out.println("Profesores disponibles:");
        for (int i = 0; i < profesores.size(); i++) {
            Profesor p = profesores.get(i);
            System.out.println((i + 1) + ". " + p.getApellido() + ", " + p.getNombre());
        }
        System.out.print("Seleccione un profesor: ");
        int index = leerEntero() - 1;
        if (index < 0 || index >= profesores.size()) {
            System.out.println("Opción inválida.");
            return;
        }
        comision.setProfesor(profesores.get(index));
        comisionController.updateComision(comision);
        System.out.println("Profesor actualizado correctamente.");
    }

    private void modificarHorarios(ComisionMateria comision) {
        boolean seguir = true;
        while (seguir) {
            System.out.println("\n--- Modificar Horarios ---");
            System.out.println("1. Agregar horario");
            System.out.println("2. Eliminar horario");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            int opcion = leerEntero();
            switch (opcion) {
                case 1 -> agregarHorarioAComision(comision);
                case 2 -> eliminarHorarioDeComision(comision);
                case 0 -> seguir = false;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private Horario crearHorario() {
        System.out.println("\n--- Crear Horario ---");
        System.out.println("Seleccione el día:");
        Dia[] dias = Dia.values();
        for (int i = 0; i < dias.length; i++) {
            System.out.println((i + 1) + ". " + dias[i]);
        }
        int opcionDia = leerEntero() - 1;
        if (opcionDia < 0 || opcionDia >= dias.length) {
            System.out.println("Día inválido.");
            return null;
        }
        Dia dia = dias[opcionDia];

        System.out.print("Ingrese hora de inicio (HH:mm): ");
        String horaInicioStr = leerString();
        LocalTime inicio;
        try {
            inicio = LocalTime.parse(horaInicioStr);
        } catch (Exception e) {
            System.out.println("Formato de hora inválido. Use HH:mm (ej. 14:30)");
            return null;
        }

        System.out.print("Ingrese hora de fin (HH:mm): ");
        String horaFinStr = leerString();
        LocalTime fin;
        try {
            fin = LocalTime.parse(horaFinStr);
        } catch (Exception e) {
            System.out.println("Formato de hora inválido. Use HH:mm (ej. 16:30)");
            return null;
        }

        if (!fin.isAfter(inicio)) {
            System.out.println("La hora de fin debe ser posterior a la de inicio.");
            return null;
        }

        Modulo modulo = new Modulo();
        modulo.setCodigo("M" + inicio.toString().replace(":", "") + "-" + fin.toString().replace(":", ""));
        modulo.setInicio(inicio);
        modulo.setFin(fin);

        Horario horario = new Horario();
        horario.setDia(dia);
        horario.setModulo(modulo);
        horario.setDisponible(true);

        System.out.println("Horario creado: " + dia + " " + inicio + " - " + fin);
        return horario;
    }

    private void agregarHorarioAComision(ComisionMateria comision) {
        System.out.println("\n--- Agregar Horario ---");
        Horario nuevo = crearHorario();
        if (nuevo != null) {
            comision.getHorarios().add(nuevo);
            comisionController.updateComision(comision);
            System.out.println("Horario agregado correctamente.");
        }
    }

    private void eliminarHorarioDeComision(ComisionMateria comision) {
        List<Horario> horarios = comision.getHorarios();
        if (horarios.isEmpty()) {
            System.out.println("La comisión no tiene horarios.");
            return;
        }
        System.out.println("\n--- Horarios actuales ---");
        for (int i = 0; i < horarios.size(); i++) {
            Horario h = horarios.get(i);
            System.out.println((i + 1) + ". " + h.getDia() + " | De: " +
                    h.getModulo().getInicio() + " a " + h.getModulo().getFin());
        }
        System.out.print("Seleccione un horario para eliminar: ");
        int index = leerEntero() - 1;
        if (index < 0 || index >= horarios.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        horarios.remove(index);
        comisionController.updateComision(comision);
        System.out.println("Horario eliminado correctamente.");
    }

    private void actualizarAlumnos(ComisionMateria comision) {
        gestionarAlumnosInscriptos(comision.getAlumnosInscriptos(), comision);
        comisionController.updateComision(comision);
        System.out.println("Actualización de alumnos completada.");
    }

    private void actualizarExamenes(ComisionMateria comision) {
        gestionExamenes(comision.getExamenes(), comision);
        comisionController.updateComision(comision);
        System.out.println("Actualización de exámenes completada.");
    }

    private void eliminarAsistencia(ComisionMateria comision) {
        List<Asistencia> asistencias = comision.getAsistencias();
        if (asistencias == null || asistencias.isEmpty()) {
            System.out.println("No hay asistencias registradas en esta comisión.");
            return;
        }
        System.out.println("\n--- Asistencias registradas ---");
        for (int i = 0; i < asistencias.size(); i++) {
            Asistencia as = asistencias.get(i);
            Alumno al = as.getAlumno();
            System.out.println((i + 1) + ". " + al.getApellido() + ", " + al.getNombre() +
                    " | DNI: " + al.getDni() + " | Fecha: " + as.getFecha());
        }
        System.out.print("\nSeleccione número de asistencia a eliminar: ");
        int index = leerEntero() - 1;
        if (index < 0 || index >= asistencias.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        Asistencia eliminada = asistencias.remove(index);
        comisionController.updateComision(comision);
        System.out.println("Asistencia del alumno " + eliminada.getAlumno().getApellido() +
                " " + eliminada.getAlumno().getNombre() + " eliminada correctamente.");
    }

    private void registrarAsistencias() {
        System.out.println("\n--- Registrar Asistencia ---");
        System.out.print("Ingrese DNI del alumno: ");
        String dni = scanner.nextLine().trim();
        System.out.print("Ingrese código de la comisión: ");
        String codigoCom = scanner.nextLine().trim();

        if (!comisionController.puedeRegistrarAsistencia(codigoCom, dni)) {
            System.out.println("No se puede registrar la asistencia. Verifique:");
            System.out.println("- Que el alumno esté inscrito en la comisión");
            System.out.println("- Que no tenga asistencia registrada hoy");
            System.out.println("- Que haya clase en este horario");
            return;
        }

        Asistencia asistencia = new Asistencia();
        asistencia.setFecha(LocalDate.now());
        asistencia.setActivo(true);

        System.out.println("\nSeleccione asistencia:");
        System.out.println("1. Presente");
        System.out.println("2. Ausente");
        int opcion;
        do {
            opcion = leerEntero();
        } while (opcion < 1 || opcion > 2);
        asistencia.setPresente(opcion == 1);

        String resultado = comisionController.registrarAsistencia(codigoCom, dni, asistencia);
        if (resultado.startsWith("SUCCESS")) {
            System.out.println("✓ " + resultado.substring(8));
        } else {
            System.out.println("✗ " + resultado.substring(6));
        }
    }

    private void listarAlumnosInscriptos(ComisionMateria comision) {
        System.out.println("\n--- Alumnos inscriptos a la comisión " + comision.getCodigo() + " ---");
        List<AlumnoInscriptoMateria> lista = comision.getAlumnosInscriptos();
        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay alumnos inscriptos.");
            return;
        }
        for (AlumnoInscriptoMateria aim : lista) {
            Alumno alumno = aim.getAlumnoInscriptoCarrera().getAlumno();
            System.out.println("- " + alumno.getApellido() + ", " + alumno.getNombre() +
                    " | DNI: " + alumno.getDni());
        }
    }

    private void listarExamenes(ComisionMateria comision) {
        System.out.println("\n--- Exámenes de la comisión " + comision.getCodigo() + " ---");
        List<Examen> examenes = comision.getExamenes();
        if (examenes == null || examenes.isEmpty()) {
            System.out.println("No hay exámenes registrados.");
            return;
        }
        for (Examen ex : examenes) {
            System.out.println("- Fecha: " + ex.getFecha() + " | Tipo: " + ex.getTipo() +
                    " | Estado: " + (ex.isActivo() ? "ACTIVO" : "INACTIVO"));
        }
    }

    private void listarAsistencias(ComisionMateria comision) {
        System.out.println("\n--- Asistencias registradas en la comisión " + comision.getCodigo() + " ---");
        List<Asistencia> asistencias = comision.getAsistencias();
        if (asistencias == null || asistencias.isEmpty()) {
            System.out.println("No hay asistencias registradas.");
            return;
        }
        for (Asistencia as : asistencias) {
            Alumno alumno = as.getAlumno();
            System.out.println("- " + alumno.getApellido() + ", " + alumno.getNombre() +
                    " | DNI: " + alumno.getDni() + " | Fecha: " + as.getFecha() +
                    " | Presente: " + (as.isPresente() ? "Sí" : "No"));
        }
    }

    private void listarHorarios(ComisionMateria comision) {
        System.out.println("\n--- Horarios registrados en la comisión " + comision.getCodigo() + " ---");
        List<Horario> horarios = comision.getHorarios();
        if (horarios == null || horarios.isEmpty()) {
            System.out.println("No hay horarios registrados.");
            return;
        }
        for (Horario h : horarios) {
            System.out.println(" - " + h.getDia() + " | De: " +
                    h.getModulo().getInicio() + " a " + h.getModulo().getFin());
        }
    }

    private void gestionarAlumnosInscriptos(List<AlumnoInscriptoMateria> listaInscriptos, ComisionMateria comision) {
        boolean gestionando = true;
        while (gestionando) {
            System.out.println("\n--- Gestor de Alumnos Inscriptos ---");
            System.out.println("1. Inscribir un alumno");
            System.out.println("2. Dar de baja un alumno");
            System.out.println("3. Ver alumnos inscriptos");
            System.out.println("4. Terminar gestión");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> inscribirAlumno(listaInscriptos, comision);
                case "2" -> darDeBajaAlumno(listaInscriptos);
                case "3" -> verAlumnosInscriptos(listaInscriptos);
                case "4" -> gestionando = false;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void inscribirAlumno(List<AlumnoInscriptoMateria> listaInscriptos, ComisionMateria comision) {
        List<AlumnoInscriptoCarrera> inscriptosCarrera = alumnoInscriptoCarreraController.findAll();
        if (inscriptosCarrera == null || inscriptosCarrera.isEmpty()) {
            System.out.println("No hay alumnos inscriptos a la(s) carrera(s).");
            return;
        }

        System.out.println("\nAlumnos inscriptos en carreras:");
        boolean hayAlumnos = false;
        for (AlumnoInscriptoCarrera aic : inscriptosCarrera) {
            if (aic.getCarrera().getNombre().equals(comision.getCarrera().getNombre())) {
                System.out.println("DNI: " + aic.getAlumno().getDni() + " | " +
                        aic.getAlumno().getApellido() + " " + aic.getAlumno().getNombre());
                hayAlumnos = true;
            }
        }

        if (!hayAlumnos) {
            System.out.println("No hay alumnos inscriptos en la carrera " + comision.getCarrera().getNombre());
            return;
        }

        System.out.print("\nIngrese DNI del alumno a inscribir: ");
        String dni = scanner.nextLine().trim();

        AlumnoInscriptoCarrera aicSeleccionado = null;
        for (AlumnoInscriptoCarrera a : inscriptosCarrera) {
            if (a.getAlumno().getDni().equals(dni) &&
                    a.getCarrera().getNombre().equals(comision.getCarrera().getNombre())) {
                aicSeleccionado = a;
                break;
            }
        }

        if (aicSeleccionado == null) {
            System.out.println("No existe alumno inscripto a esa carrera con ese DNI.");
            return;
        }

        for (AlumnoInscriptoMateria a : listaInscriptos) {
            if (a.getAlumnoInscriptoCarrera().getAlumno().getDni().equals(dni)) {
                System.out.println("El alumno ya está inscripto en esta comisión.");
                return;
            }
        }

        AlumnoInscriptoMateria aim = new AlumnoInscriptoMateria();
        aim.setAlumnoInscriptoCarrera(aicSeleccionado);
        aim.setExamenes(new ArrayList<>());
        aim.setEstado(Estado.REGULAR);
        aim.setActivo(true);

        System.out.println("\nVista previa:");
        System.out.println("Alumno: " + aicSeleccionado.getAlumno().getApellido() + " " +
                aicSeleccionado.getAlumno().getNombre());
        System.out.println("DNI: " + aicSeleccionado.getAlumno().getDni());

        if (confirmarAccion("¿Confirmar inscripción?")) {
            String resultado = comisionController.inscribirAlumno(comision.getCodigo(), dni, aim);
            if (resultado.startsWith("SUCCESS")) {
                System.out.println("✓ " + resultado.substring(8));
            } else {
                System.out.println("✗ " + resultado.substring(6));
            }
        }
    }

    private void darDeBajaAlumno(List<AlumnoInscriptoMateria> listaInscriptos) {
        if (listaInscriptos.isEmpty()) {
            System.out.println("No hay alumnos inscriptos en la comisión.");
            return;
        }
        System.out.println("\nAlumnos inscriptos:");
        for (int i = 0; i < listaInscriptos.size(); i++) {
            Alumno a = listaInscriptos.get(i).getAlumnoInscriptoCarrera().getAlumno();
            System.out.println((i + 1) + ". " + a.getApellido() + ", " + a.getNombre() +
                    " | DNI: " + a.getDni());
        }
        System.out.print("\nSeleccione número del alumno a dar de baja: ");
        int index = leerEntero() - 1;
        if (index < 0 || index >= listaInscriptos.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        AlumnoInscriptoMateria eliminado = listaInscriptos.remove(index);
        eliminado.setActivo(false);
        System.out.println("Alumno dado de baja.");
    }

    private void verAlumnosInscriptos(List<AlumnoInscriptoMateria> listaInscriptos) {
        System.out.println("\nAlumnos inscriptos:");
        if (listaInscriptos.isEmpty()) {
            System.out.println("No hay alumnos inscriptos.");
        } else {
            for (AlumnoInscriptoMateria aim : listaInscriptos) {
                Alumno a = aim.getAlumnoInscriptoCarrera().getAlumno();
                System.out.println("- " + a.getApellido() + ", " + a.getNombre() +
                        " | DNI: " + a.getDni());
            }
        }
    }

    private void gestionExamenes(List<Examen> examenes, ComisionMateria comision) {
        boolean gestionando = true;
        while (gestionando) {
            System.out.println("\n--- Gestor de Exámenes ---");
            System.out.println("1. Crear examen");
            System.out.println("2. Cambiar nota");
            System.out.println("3. Eliminar examen");
            System.out.println("4. Ver exámenes");
            System.out.println("0. Terminar");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> crearExamen(examenes, comision);
                case "2" -> cambiarNota(examenes);
                case "3" -> eliminarExamen(examenes);
                case "4" -> verExamenes(examenes);
                case "0" -> gestionando = false;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void crearExamen(List<Examen> examenes, ComisionMateria comision) {
        if (comision.getAlumnosInscriptos().isEmpty()) {
            System.out.println("No hay alumnos inscriptos en la comisión.");
            return;
        }

        Examen nuevo = new Examen();
        List<Examen> todos = examenController.findAll();
        int nextId = (todos == null ? 0 : todos.size()) + 1;
        nuevo.setId(String.valueOf(nextId));

        System.out.println("\nTipos de examen:");
        Tipo[] tipos = Tipo.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }
        System.out.print("Seleccione tipo: ");
        int tipoIdx = leerEntero() - 1;
        if (tipoIdx < 0 || tipoIdx >= tipos.length) {
            System.out.println("Tipo inválido.");
            return;
        }
        nuevo.setTipo(tipos[tipoIdx]);

        System.out.print("Nota (0-10): ");
        double nota = leerEntero();
        if (nota < 0 || nota > 10) {
            System.out.println("Nota inválida.");
            return;
        }
        nuevo.setNota(nota);

        System.out.print("Fecha (AAAA-MM-DD): ");
        try {
            nuevo.setFecha(LocalDate.parse(scanner.nextLine().trim()));
        } catch (Exception e) {
            System.out.println("Fecha inválida.");
            return;
        }

        System.out.println("\nAlumnos inscriptos:");
        for (int i = 0; i < comision.getAlumnosInscriptos().size(); i++) {
            Alumno a = comision.getAlumnosInscriptos().get(i).getAlumnoInscriptoCarrera().getAlumno();
            System.out.println((i + 1) + ". " + a.getApellido() + ", " + a.getNombre() +
                    " | DNI: " + a.getDni());
        }
        System.out.print("Seleccione alumno: ");
        int alumnoIdx = leerEntero() - 1;
        if (alumnoIdx < 0 || alumnoIdx >= comision.getAlumnosInscriptos().size()) {
            System.out.println("Alumno inválido.");
            return;
        }

        AlumnoInscriptoMateria aim = comision.getAlumnosInscriptos().get(alumnoIdx);
        nuevo.setAlumno(aim.getAlumnoInscriptoCarrera().getAlumno());
        nuevo.setMateria(comision.getMateria());
        nuevo.setActivo(true);

        if (confirmarAccion("¿Confirmar creación?")) {
            examenController.createExamen(nuevo);
            aim.getExamenes().add(nuevo);
            examenes.add(nuevo);
            System.out.println("Examen creado.");
        }
    }

    private void cambiarNota(List<Examen> examenes) {
        if (examenes.isEmpty()) {
            System.out.println("No hay exámenes.");
            return;
        }
        System.out.println("\nExámenes:");
        for (int i = 0; i < examenes.size(); i++) {
            Examen e = examenes.get(i);
            System.out.println((i + 1) + ". " + e.getAlumno().getApellido() +
                    " | Nota: " + e.getNota());
        }
        System.out.print("Seleccione examen: ");
        int idx = leerEntero() - 1;
        if (idx < 0 || idx >= examenes.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        System.out.print("Nueva nota: ");
        double nota = leerEntero();
        if (nota < 0 || nota > 10) {
            System.out.println("Nota inválida.");
            return;
        }
        examenes.get(idx).setNota(nota);
        System.out.println("Nota actualizada.");
    }

    private void eliminarExamen(List<Examen> examenes) {
        if (examenes.isEmpty()) {
            System.out.println("No hay exámenes.");
            return;
        }
        System.out.println("\nExámenes:");
        for (int i = 0; i < examenes.size(); i++) {
            Examen e = examenes.get(i);
            System.out.println((i + 1) + ". " + e.getAlumno().getApellido());
        }
        System.out.print("Seleccione examen a eliminar: ");
        int idx = leerEntero() - 1;
        if (idx < 0 || idx >= examenes.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        examenes.remove(idx);
        System.out.println("Examen eliminado.");
    }

    private void verExamenes(List<Examen> examenes) {
        System.out.println("\nExámenes:");
        if (examenes.isEmpty()) {
            System.out.println("No hay exámenes.");
        } else {
            for (Examen e : examenes) {
                System.out.println("- " + e.getAlumno().getApellido() + ", " +
                        e.getAlumno().getNombre() + " | " +
                        e.getTipo() + " | Nota: " + e.getNota() +
                        " | Fecha: " + e.getFecha());
            }
        }
    }
}