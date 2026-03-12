
package ar.com.itecn1.view;

import ar.com.itecn1.controller.*;
import ar.com.itecn1.model.*;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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

    // -------------------------
    // Menú principal
    // -------------------------
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

    // -------------------------
    // Utilitarios
    // -------------------------
    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Debe ingresar un número. Intente nuevamente: ");
        }
        int n = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer
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
        System.out.println("ALUMNOS: " + (c.getAlumnoInscriptos() != null ? c.getAlumnoInscriptos().size() : 0));
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


    // -------------------------
    // Listar y Buscar
    // -------------------------
    private void listarComisiones() {
        System.out.println("\n--- Listado de Comisiones ---");
        List<ComisionMateria> lista = comisionController.findAll();
        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay comisiones registradas.");
            return;
        }
        lista.forEach(this::mostrarComision);
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

    // -------------------------
    // Crear comisión (refactorizado)
    // -------------------------
    private void crearComision() {
        System.out.println("\n--- Registrar Comisión ---");
        System.out.print("Código de comisión (ej. AnLen1): ");
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

        // Carrera
        Carrera carrera = seleccionarCarrera();
        if (carrera == null) return;
        nueva.setCarrera(carrera);

        // Materia
        Materia materia = seleccionarMateria(carrera);
        if (materia == null) return;
        nueva.setMateria(materia);

        // Profesor
        Profesor profesor = seleccionarProfesor();
        if (profesor == null) return;
        nueva.setProfesor(profesor);

        // Cuatrimestre
        Cuatrimestre cuatrimestre = seleccionarCuatrimestre();
        if (cuatrimestre == null) return;
        nueva.setCuatrimestre(cuatrimestre);

        // Alumnos inscriptos
        List<AlumnoInscriptoMateria> alumnosInscriptos = new ArrayList<>();
        gestionarAlumnosInscriptos(alumnosInscriptos, nueva);
        nueva.setAlumnoInscriptos(alumnosInscriptos);

        // Exámenes
        List<Examen> examenes = new ArrayList<>();
        gestionExamenes(examenes, nueva);
        nueva.setExamenes(examenes);

        // Asistencias (inicialmente vacía)
        nueva.setAsistencias(new ArrayList<>());

        // Horarios
        List<Horario> horarios = new ArrayList<>();
        gestionarHorarios(horarios, nueva);
        nueva.setHorarios(horarios);

        // Vista previa
        System.out.println("\nVista previa de la comisión:");
        nueva.setActivo(true);
        mostrarComision(nueva);

        if (confirmarAccion("¿Confirmar registro de la comisión?")) {
            comisionController.createComision(nueva);
            System.out.println("Comisión registrada!");
        } else {
            System.out.println("Registro cancelado.");
        }
    }

    // -------------------------
    // Eliminar comisión
    // -------------------------
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

    // -------------------------
    // Actualizar comisión
    // --
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
                case 5 -> gestionarHorarios(comision.getHorarios(),comision);
//                case 5 -> modificarHorarios(comision);
                case 6 -> mostrarComision(comision);
                case 0 -> continuar = false;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // cambiar profesor
    private void cambiarProfesor(ComisionMateria comision) {
        System.out.println("\n--- Cambiar Profesor ---");

        if (comision.getProfesor() == null) {
            System.out.println("No hay profesor asignado.");
            return;
        }

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

        System.out.println("Profesor actualizado correctamente.");
    }

    //modificar horario
    private void modificarHorarios(ComisionMateria comision) {
        boolean seguir = true;

        while (seguir) {
            System.out.println("\n--- Modificar Horarios ---");
            System.out.println("1. Agregar horario");
            System.out.println("2. Eliminar horario");
            System.out.println("4. Volver");
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

        // Selección del día
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

        // Ingreso de hora inicio
        System.out.print("Ingrese hora de inicio (HH:mm): ");
        String horaInicioStr = leerString();
        LocalTime inicio;

        try {
            inicio = LocalTime.parse(horaInicioStr);
        } catch (Exception e) {
            System.out.println("Formato de hora inválido.");
            return null;
        }

        // Ingreso de hora fin
        System.out.print("Ingrese hora de fin (HH:mm): ");
        String horaFinStr = leerString();
        LocalTime fin;

        try {
            fin = LocalTime.parse(horaFinStr);
        } catch (Exception e) {
            System.out.println("Formato de hora inválido.");
            return null;
        }

        if (!fin.isAfter(inicio)) {
            System.out.println("La hora de fin debe ser posterior a la de inicio.");
            return null;
        }

        // Crear módulo
        Modulo modulo = new Modulo();
        modulo.setCodigo("M" + inicio + "-" + fin);
        modulo.setInicio(inicio);
        modulo.setFin(fin);

        // Crear horario
        Horario horario = new Horario();
        horario.setDia(dia);
        horario.setModulo(modulo);
        horario.setDisponible(true);

        System.out.println("Horario creado: " + dia + " " + inicio + " - " + fin);

        return horario;
    }

    private void agregarHorarioAComision(ComisionMateria comision) {
        System.out.println("\n--- Agregar Horario ---");

        Horario nuevo = crearHorario(); // reutilizás tu método existente

        if (nuevo != null) {
            comision.getHorarios().add(nuevo);
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
            System.out.println((i + 1) + ". " +
                    h.getDia() + " | " +
                    h.getModulo().getCodigo() + " (" +
                    h.getModulo().getInicio() + " - " +
                    h.getModulo().getFin() + ")");
        }

        System.out.print("Seleccione un horario para eliminar: ");
        int index = leerEntero() - 1;

        if (index < 0 || index >= horarios.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        horarios.remove(index);
        System.out.println("Horario eliminado correctamente.");
    }


    // actualizar alumnos
    private void actualizarAlumnos(ComisionMateria comision) {
        gestionarAlumnosInscriptos(comision.getAlumnoInscriptos(), comision);
        System.out.println("Actualización de alumnos completada.");
    }

    // actualizar examenes
    private void actualizarExamenes(ComisionMateria comision) {
        gestionExamenes(comision.getExamenes(), comision);
        System.out.println("Actualización de exámenes completada.");
    }

    // eliminar asistencia
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
            System.out.println((i + 1) + ". " +
                    al.getApellido() + ", " + al.getNombre() +
                    " | DNI: " + al.getDni() +
                    " | Fecha: " + as.getFecha());
        }

        System.out.print("\nSeleccione número de asistencia a eliminar: ");
        int index = leerEntero() - 1;

        if (index < 0 || index >= asistencias.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        Asistencia eliminada = asistencias.remove(index);

        System.out.println("Asistencia del alumno " +
                eliminada.getAlumno().getApellido() + " " +
                eliminada.getAlumno().getNombre() +
                " eliminada correctamente.");
    }


    // -------------------------
    // Registrar asistencias (refactorizado y con validaciones)
    // -------------------------
    private void registrarAsistencias() {
        System.out.println("\n--- Registrar Asistencia ---");
        System.out.print("Ingrese DNI del alumno: ");
        String dni = scanner.nextLine().trim();

        AlumnoInscriptoMateria alumnoMat = alumnoInscriptoMateriaController.findByAlumnoDni(dni);
        if (alumnoMat == null) {
            System.out.println("Alumno no registrado en ninguna comisión.");
            return;
        }

        System.out.print("Ingrese código de la comisión: ");
        String codigoCom = scanner.nextLine().trim();

        ComisionMateria comision = comisionController.findByCode(codigoCom);
        if (comision == null) {
            System.out.println("Comisión no encontrada.");
            return;
        }

        // verificar que alumno está en la comisión
        boolean inscrito = comision.getAlumnoInscriptos().stream()
                .anyMatch(a -> a.getAlumnoInscriptoCarrera().getAlumno().getDni().equals(dni));

        if (!inscrito) {
            System.out.println("El alumno no está inscripto en esta comisión.");
            return;
        }

        // verificar si ya tiene asistencia para hoy en esta comisión
        boolean yaAsistenciaHoy = comision.getAsistencias().stream()
                .anyMatch(a -> a.getAlumno().getDni().equals(dni) && a.getFecha().equals(LocalDate.now()));

        if (yaAsistenciaHoy) {
            System.out.println("El alumno ya posee asistencia registrada para hoy en esta comisión.");
            return;
        }

        // determinar día actual y hora actual
        String diaActual = LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES")).toUpperCase();
        String diaNormalizado = Normalizer
                .normalize(diaActual, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        LocalTime horaActual = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        // buscar horario de la comisión que coincida con día y hora
        Optional<Horario> horarioCoincidente = comision.getHorarios().stream()
                .filter(h -> h.getDia().toString().equals(diaNormalizado))
                .filter(h -> {
                    LocalTime inicio = h.getModulo().getInicio();
                    LocalTime fin = h.getModulo().getFin();
                    return !horaActual.isBefore(inicio) && !horaActual.isAfter(fin);
                })
                .findFirst();

        if (horarioCoincidente.isEmpty()) {
            System.out.println("Hoy no se dicta la materia en esta comisión o no hay horario coincidente.");
            return;
        }

        Horario horario = horarioCoincidente.get();

        // Asignar asistencia
        Asistencia asistencia = new Asistencia();
        asistencia.setFecha(LocalDate.now());
        asistencia.setHorario(horario);
        asistencia.setAlumno(alumnoMat.getAlumnoInscriptoCarrera().getAlumno());
        asistencia.setActivo(true);

        System.out.println("\nSeleccione asistencia:");
        System.out.println("1. Presente");
        System.out.println("2. Ausente");
        int opcion;
        do {
            opcion = leerEntero();
        } while (opcion < 1 || opcion > 2);

        if (opcion == 1) asistencia.setPresente(true);
        if (opcion == 2) asistencia.setPresente(false);

        comision.getAsistencias().add(asistencia);
        if (asistencia.isPresente()) {
            System.out.println("Asistencia registrada: PRESENTE.");
        } else {
            System.out.println("Asistencia registrada: AUSENTE.");
        }
    }

    private void listarAlumnosInscriptos(ComisionMateria comision) {
        System.out.println("\n--- Alumnos inscriptos a la comisión " + comision.getCodigo() + " ---");

        List<AlumnoInscriptoMateria> lista = comision.getAlumnoInscriptos();

        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay alumnos inscriptos.");
            return;
        }

        for (AlumnoInscriptoMateria aim : lista) {
            Alumno alumno = aim.getAlumnoInscriptoCarrera().getAlumno();
            System.out.println(
                "- " + alumno.getApellido() + ", " + alumno.getNombre() +
                " | DNI: " + alumno.getDni()
            );
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
            System.out.println("- Fecha: " + ex.getFecha() +
                    " | Tipo: " + ex.getTipo() +
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

            System.out.println("- " +
                    alumno.getApellido() + ", " + alumno.getNombre() +
                    " | DNI: " + alumno.getDni() +
                    " | Fecha: " + as.getFecha() +
                    " | Presente: " + (as.isPresente() ? "Sí" : "No")
            );
        }
    }

    private void listarHorarios(ComisionMateria comision){
        System.out.println("\n--- Horarios registrados en la comisión " + comision.getCodigo() + " ---");

        List<Horario> horarios = comision.getHorarios();

        if (horarios == null || horarios.isEmpty()) {
            System.out.println("No hay asistencias registradas.");
            return;
        }

        horarios.forEach(h -> System.out.println(" - ID: " + h.getId() + " | " + h.getDia() + " | De: " + h.getModulo().getInicio() + " a " + h.getModulo().getFin()));
    }

    // -------------------------
    // Gestión Alumnos Inscriptos
    // -------------------------
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
                case "2" -> darBajaAlumno(listaInscriptos);
                case "3" -> mostrarAlumnosInscriptos(listaInscriptos);
                case "4" -> {
                    gestionando = false;
                    System.out.println("Gestión finalizada.");
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void inscribirAlumno(List<AlumnoInscriptoMateria> listaInscriptos, ComisionMateria comision) {
        System.out.println("\n--- Inscribir Alumno ---");

        // Mostrar alumnos inscriptos a carreras (disponibles)
        List<AlumnoInscriptoCarrera> inscriptosCarrera = alumnoInscriptoCarreraController.findAll();
        if (inscriptosCarrera == null || inscriptosCarrera.isEmpty()) {
            System.out.println("No hay alumnos inscriptos a ninguna carrera.");
            return;
        }

        System.out.println("\nAlumnos inscriptos en carreras:");
        inscriptosCarrera.forEach(aic -> System.out.println(
                "DNI: " + aic.getAlumno().getDni() + " | " +
                        aic.getAlumno().getApellido() + " " + aic.getAlumno().getNombre() + " | " +
                        "Carrera: " + aic.getCarrera().getNombre() + " | Año: " + aic.getAnioIngreso()
        ));

        System.out.print("\nIngrese DNI del alumno a inscribir en la comisión: ");
        String dni = scanner.nextLine().trim();

        // Validar formato DNI (usando el controlador de alumno si existe, o el de profesor, pero deberías tener un método genérico)
        // Suponiendo que tienes un método validarDni en algún controlador (por ejemplo, alumnoController)
        // Como no lo tenemos en esta vista, usamos una validación simple
        if (dni.length() != 8 || !dni.matches("\\d+")) {
            System.out.println("Error: DNI inválido. Debe tener 8 dígitos numéricos.");
            return;
        }

        // Buscar el AlumnoInscriptoCarrera por DNI
        AlumnoInscriptoCarrera aic = alumnoInscriptoCarreraController.findByDni(dni);
        if (aic == null) {
            System.out.println("No existe alumno inscripto a carrera con ese DNI.");
            return;
        }

        // Verificar si el alumno ya está inscripto en esta comisión
        boolean yaInscripto = listaInscriptos.stream()
                .anyMatch(aim -> aim.getAlumnoInscriptoCarrera().getAlumno().getDni().equals(dni));
        if (yaInscripto) {
            System.out.println("El alumno ya está inscripto en esta comisión.");
            return;
        }

        // Crear AlumnoInscriptoMateria
        AlumnoInscriptoMateria aim = new AlumnoInscriptoMateria();
        aim.setAlumnoInscriptoCarrera(aic);
        aim.setExamenes(new ArrayList<>());
        aim.setEstado(Estado.REGULAR);
        aim.setActivo(true);

        // Vista previa
        System.out.println("\nVista previa inscripción a la comisión:");
        System.out.println("Alumno: " + aic.getAlumno().getApellido() + " " + aic.getAlumno().getNombre());
        System.out.println("DNI: " + aic.getAlumno().getDni());
        System.out.println("Carrera: " + aic.getCarrera().getNombre());
        System.out.println("Año ingreso: " + aic.getAnioIngreso());

        if (confirmarAccion("¿Confirmar inscripción del alumno a la comisión?")) {
            alumnoInscriptoMateriaController.save(aim); // persistir
            listaInscriptos.add(aim);
            System.out.println("Alumno inscrito a la comisión.");
        } else {
            System.out.println("Inscripción cancelada.");
        }
    }

    private void darBajaAlumno(List<AlumnoInscriptoMateria> listaInscriptos) {
        System.out.println("\n--- Dar de baja Alumno ---");

        if (listaInscriptos.isEmpty()) {
            System.out.println("No hay alumnos inscriptos en la comisión.");
            return;
        }

        System.out.println("\nAlumnos inscriptos en la comisión:");
        listaInscriptos.forEach(aim -> System.out.println(
                "DNI: " + aim.getAlumnoInscriptoCarrera().getAlumno().getDni() + " | " +
                        aim.getAlumnoInscriptoCarrera().getAlumno().getApellido() + " " +
                        aim.getAlumnoInscriptoCarrera().getAlumno().getNombre()
        ));

        System.out.print("\nIngrese DNI del alumno a dar de baja: ");
        String dni = scanner.nextLine().trim();

        // Validación simple
        if (dni.length() != 8 || !dni.matches("\\d+")) {
            System.out.println("Error: DNI inválido.");
            return;
        }

        Optional<AlumnoInscriptoMateria> opt = listaInscriptos.stream()
                .filter(a -> a.getAlumnoInscriptoCarrera().getAlumno().getDni().equals(dni))
                .findFirst();

        if (opt.isPresent()) {
            if (confirmarAccion("¿Confirmar baja del alumno en la comisión?")) {
                listaInscriptos.remove(opt.get());
                // Si tuvieras baja lógica: alumnoInscriptoMateriaController.delete(opt.get());
                System.out.println("Alumno dado de baja de la comisión.");
            } else {
                System.out.println("Operación cancelada.");
            }
        } else {
            System.out.println("Alumno no encontrado en la comisión.");
        }
    }

    private void mostrarAlumnosInscriptos(List<AlumnoInscriptoMateria> listaInscriptos) {
        System.out.println("\n--- Alumnos inscriptos en la comisión ---");
        if (listaInscriptos.isEmpty()) {
            System.out.println("No hay alumnos inscriptos.");
            return;
        }
        listaInscriptos.forEach(aim -> {
            Alumno a = aim.getAlumnoInscriptoCarrera().getAlumno();
            System.out.println(
                    "DNI: " + a.getDni() + " | " +
                            a.getApellido() + " " + a.getNombre() + " | " +
                            "Carrera: " + aim.getAlumnoInscriptoCarrera().getCarrera().getNombre() + " | " +
                            "Año ingreso: " + aim.getAlumnoInscriptoCarrera().getAnioIngreso()
            );
        });
    }

    // -------------------------
    // Gestión Horarios
    // -------------------------
    private void gestionarHorarios(List<Horario> horarios, ComisionMateria comision) {
        boolean gestionando = true;

        while (gestionando) {
            System.out.println("\n--- Gestor de Horarios ---");
            System.out.println("1. Seleccionar horarios");
            System.out.println("2. Quitar horario");
            System.out.println("3. Ver horarios seleccionados");
            System.out.println("0. Terminar gestión");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> {
                    // mostrar horarios disponibles filtrados por carrera + cuatrimestre
                    List<Horario> disponibles = horarioController.findAll().stream()
                            .filter(h -> h.getCarrera() != null && comision.getCarrera() != null
                                    && h.getCarrera().getNombre().equals(comision.getCarrera().getNombre()))
                            .filter(h -> h.getCuatrimestre() != null && comision.getCuatrimestre() != null
                                    && h.getCuatrimestre().getNumero().equals(comision.getCuatrimestre().getNumero()))
                            .collect(Collectors.toList());

                    if (disponibles.isEmpty()) {
                        System.out.println("No hay horarios disponibles para la carrera/cuatrimestre de la comisión.");
                        break;
                    }

                    // agrupar por día y mostrar
                    Map<Dia, List<Horario>> porDia = disponibles.stream()
                            .collect(Collectors.groupingBy(Horario::getDia, LinkedHashMap::new, Collectors.toList()));

                    porDia.forEach((dia, list) -> {
                        System.out.println("\n" + dia);
                        list.forEach(h -> {
                            String estado = h.isDisponible() ? "DISPONIBLE" : "OCUPADO";
                            System.out.println(" - ID: " + h.getId() + " | De: " + h.getModulo().getInicio() + " a " + h.getModulo().getFin() + " | " + estado);
                        });
                    });

                    boolean seguir = true;
                    while (seguir) {
                        System.out.print("\nIngrese ID del horario a agregar: ");
                        String id = scanner.nextLine().trim();
                        Horario h = horarioController.findById(id);
                        if (h == null) {
                            System.out.println("Horario inexistente.");
                        } else if (!h.isDisponible()) {
                            System.out.println("Horario ocupado.");
                        } else {
                            horarios.add(h);
                            h.setDisponible(false);
                            System.out.println("Horario agregado.");
                        }

                        System.out.println("\n¿Seguir agregando módulos?");
                        System.out.println("1. Si");
                        System.out.println("2. No");
                        int opt = leerEntero();
                        if (opt == 2) seguir = false;
                    }
                }
                case "2" -> {
                    if (horarios.isEmpty()) {
                        System.out.println("No hay horarios agregados.");
                        break;
                    }
                    System.out.println("\nHorarios agregados:");
                    horarios.forEach(h -> System.out.println(" - ID: " + h.getId() + " | " + h.getDia() + " | De: " + h.getModulo().getInicio() + " a " + h.getModulo().getFin()));

                    System.out.print("\nIngrese ID del horario a quitar: ");
                    String idQ = scanner.nextLine().trim();
                    Optional<Horario> opt = horarios.stream().filter(h -> h.getId().equals(idQ)).findFirst();
                    if (opt.isPresent()) {
                        Horario toRemove = opt.get();
                        horarios.remove(toRemove);
                        toRemove.setDisponible(true);
                        System.out.println("Horario removido.");
                    } else {
                        System.out.println("Horario no encontrado en la lista.");
                    }
                }
                case "3" -> {
                    System.out.println("\nHorarios agregados:");
                    if (horarios.isEmpty()) System.out.println("No hay horarios.");
                    else horarios.forEach(h -> System.out.println(" - ID: " + h.getId() + " | " + h.getDia() + " | De: " + h.getModulo().getInicio() + " a " + h.getModulo().getFin()));
                }
                case "0" -> gestionando = false;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // -------------------------
    // Gestión Exámenes
    // -------------------------
    private void gestionExamenes(List<Examen> examenes, ComisionMateria comision) {
        boolean gestionando = true;

        while (gestionando) {
            System.out.println("\n--- Gestor de Exámenes ---");
            System.out.println("1. Crear examen");
            System.out.println("2. Cambiar nota a un examen");
            System.out.println("3. Eliminar examen");
            System.out.println("4. Ver exámenes");
            System.out.println("0. Terminar gestión");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> {
                    Examen nuevo = new Examen();
                    // generar ID simple incremental local
                    List<Examen> todos = examenController.findAll();
                    int nextId = (todos == null ? 0 : todos.size()) + 1;
                    nuevo.setId(String.valueOf(nextId));

                    // seleccionar tipo
                    Tipo tipo = seleccionarTipoExamen();
                    if (tipo == null) {
                        System.out.println("Tipo inválido. Cancelando creación.");
                        break;
                    }
                    nuevo.setTipo(tipo);

                    // nota (opcional, por defecto 0.00)
                    System.out.print("Ingrese nota (Enter para 0): ");
                    String notaStr = scanner.nextLine().trim();
                    double nota = 0.0;
                    if (!notaStr.isBlank()) {
                        try {
                            nota = Double.parseDouble(notaStr);
                        } catch (NumberFormatException e) {
                            System.out.println("Nota inválida, se usará 0.");
                        }
                    }
                    nuevo.setNota(nota);

                    // fecha
                    System.out.print("Ingrese fecha del examen (AAAA-MM-DD): ");
                    try {
                        LocalDate fecha = LocalDate.parse(scanner.nextLine().trim());
                        nuevo.setFecha(fecha);
                    } catch (Exception e) {
                        System.out.println("Fecha inválida. Cancelado.");
                        break;
                    }

                    // seleccionar alumno (de los inscriptos)
                    if (comision.getAlumnoInscriptos() == null || comision.getAlumnoInscriptos().isEmpty()) {
                        System.out.println("No hay alumnos inscriptos en la comisión.");
                        break;
                    }
                    System.out.println("\nAlumnos inscriptos:");
                    comision.getAlumnoInscriptos().forEach(a -> System.out.println(" - " + a.getAlumnoInscriptoCarrera().getAlumno().getDni() + " | " + a.getAlumnoInscriptoCarrera().getAlumno().getApellido() + " " + a.getAlumnoInscriptoCarrera().getAlumno().getNombre()));

                    System.out.print("Ingrese DNI del alumno para el examen: ");
                    String dni = scanner.nextLine().trim();
                    AlumnoInscriptoMateria aim = alumnoInscriptoMateriaController.findByAlumnoDni(dni);
                    if (aim == null) {
                        System.out.println("Alumno no pertenece a la cursada. Cancelado.");
                        break;
                    }
                    nuevo.setAlumno(aim.getAlumnoInscriptoCarrera().getAlumno());
                    nuevo.setMateria(comision.getMateria());
                    nuevo.setActivo(true);

                    // vista previa
                    System.out.println("\nVista previa examen:");
                    System.out.println("ID: " + nuevo.getId());
                    System.out.println("Tipo: " + nuevo.getTipo());
                    System.out.println("Nota: " + nuevo.getNota());
                    System.out.println("Fecha: " + nuevo.getFecha());
                    System.out.println("Alumno: " + nuevo.getAlumno().getApellido() + " " + nuevo.getAlumno().getNombre());
                    System.out.println("Materia: " + nuevo.getMateria().getNombre());

                    if (confirmarAccion("¿Confirmar creación de examen?")) {
                        // persistir
                        examenController.createExamen(nuevo);
                        // agregar a alumnoInscriptoMateria y a lista de la comisión
                        aim.getExamenes().add(nuevo);
                        examenes.add(nuevo);
                        System.out.println("Examen registrado.");
                    }
                }
                case "2" -> {
                    if (examenes.isEmpty()) {
                        System.out.println("No hay exámenes para modificar.");
                        break;
                    }
                    System.out.println("\nExámenes:");
                    examenes.forEach(e -> System.out.println(" - ID: " + e.getId() + " | Nota: " + e.getNota() + " | Alumno: " + e.getAlumno().getApellido()));

                    System.out.print("Ingrese ID del examen a modificar: ");
                    String id = scanner.nextLine().trim();
                    Examen ex = examenController.findById(id);
                    if (ex == null) {
                        System.out.println("Examen no encontrado.");
                        break;
                    }

                    System.out.print("Ingrese nueva nota (Enter para cancelar): ");
                    String notaN = scanner.nextLine().trim();
                    if (notaN.isBlank()) {
                        System.out.println("No se modificó la nota.");
                        break;
                    }
                    try {
                        double nuevaNota = Double.parseDouble(notaN);
                        if (nuevaNota > 0 && nuevaNota <= 10) {
                            ex.setNota(nuevaNota);
                            System.out.println("Nota actualizada.");
                        } else {
                            System.out.println("Nota fuera de rango.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Nota inválida.");
                    }
                }
                case "3" -> {
                    if (examenes.isEmpty()) {
                        System.out.println("No hay exámenes para eliminar.");
                        break;
                    }
                    System.out.println("\nExámenes:");
                    examenes.forEach(e -> System.out.println(" - ID: " + e.getId() + " | Alumno: " + e.getAlumno().getApellido() + " | Nota: " + e.getNota()));

                    System.out.print("Ingrese ID del examen a eliminar: ");
                    String idE = scanner.nextLine().trim();
                    Examen exE = examenController.findById(idE);
                    if (exE != null) {
                        examenes.remove(exE);
                        System.out.println("Examen eliminado de la lista.");
                        // si tu controlador maneja borrado: examenController.delete(exE);
                    } else {
                        System.out.println("Examen inexistente.");
                    }
                }
                case "4" -> {
                    System.out.println("\nExámenes actuales:");
                    if (examenes.isEmpty()) System.out.println("No hay exámenes.");
                    else examenes.forEach(e -> System.out.println(" - ID: " + e.getId() + " | Tipo: " + e.getTipo() + " | Nota: " + e.getNota() + " | Fecha: " + e.getFecha() + " | ALUMNO: "+ e.getAlumno().getNombre() + " "+ e.getAlumno().getApellido()));
                }
                case "0" -> gestionando = false;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // -------------------------
    // Selecciones reutilizables
    // -------------------------
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
            System.out.println((i + 1) + ". " + mat.getCodigoMateria() + " | "+ mat.getNombre());
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

    private Cuatrimestre seleccionarCuatrimestre() {
        List<Cuatrimestre> lista = cuatrimestreController.findAll();
        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay cuatrimestres registrados.");
            return null;
        }
        System.out.println("\n--- Seleccione Cuatrimestre ---");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i).getNumero() + " | Año: " + lista.get(i).getAnio());
        }
        int op = seleccionarDeLista(lista.size());
        return lista.get(op - 1);
    }

    private Tipo seleccionarTipoExamen() {
        Tipo[] tipos = Tipo.values();
        System.out.println("\n--- Seleccione Tipo de Examen ---");
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }
        int op = seleccionarDeLista(tipos.length);
        return tipos[op - 1];
    }

    // -------------------------
    // Helpers
    // -------------------------
    private int seleccionarDeLista(int max) {
        int op;
        do {
            System.out.print("Seleccione opción (1-" + max + "): ");
            op = leerEntero();
        } while (op < 1 || op > max);
        return op;
    }
}
