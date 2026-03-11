package ar.com.itecn1.view;

import ar.com.itecn1.controller.*;
import ar.com.itecn1.model.*;

import java.util.List;
import java.util.Scanner;

public class HorarioView {

    private final HorarioController horarioController;
    private final CarreraController carreraController;
    private final CuatrimestreController cuatrimestreController;
    private final ModuloController moduloController;
    private final Scanner scanner;

    public HorarioView(
            HorarioController horarioController,
            CarreraController carreraController,
            CuatrimestreController cuatrimestreController,
            ModuloController moduloController,
            Scanner scanner
    ) {
        this.horarioController = horarioController;
        this.carreraController = carreraController;
        this.cuatrimestreController = cuatrimestreController;
        this.moduloController = moduloController;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarHorarios();
                case 2 -> buscarHorario();
                case 3 -> crearHorario();
                case 4 -> actualizarHorario();
                case 5 -> eliminarHorario();
                case 0 -> continuar = false;
                default -> System.out.println("Ingrese una opción válida.");
            }
        }
    }

    // ============================================================
    // MENÚ
    // ============================================================

    private void mostrarMenu() {
        System.out.println("\nGESTIÓN DE HORARIOS");
        System.out.println("1. Listar horarios");
        System.out.println("2. Buscar horario por ID");
        System.out.println("3. Registrar horario");
        System.out.println("4. Actualizar horario");
        System.out.println("5. Dar de baja horario");
        System.out.println("0. Volver atrás");
    }

    // ============================================================
    // MÉTODOS UTILITARIOS
    // ============================================================

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Debe ingresar un número.");
        }
        int num = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer
        return num;
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

    // Mostrar horario
    private void mostrarHorario(Horario h) {
        System.out.println(
            "ID: " + h.getId() +
            " | DÍA: " + h.getDia() +
            " | CARRERA: " + (h.getCarrera() != null ? h.getCarrera().getNombre() : "NO ASIGNADA") +
            " | CUATRIMESTRE: " + (h.getCuatrimestre() != null ? h.getCuatrimestre().getNumero() : "NO ASIGNADO") +
            " | MÓDULO: " + (h.getModulo() != null ? h.getModulo().getCodigo() : "NO ASIGNADO") +
            " | ESTADO: " + (h.isActivo() ? "ACTIVO" : "INACTIVO")
        );
    }

    private Carrera seleccionarCarrera() {
        List<Carrera> carreras = carreraController.findAll();
        if (carreras.isEmpty()) {
            System.out.println("No hay carreras registradas.");
            return null;
        }

        System.out.println("CARRERAS DISPONIBLES:");
        carreras.forEach(c -> System.out.println("- " + c.getNombre()));

        System.out.print("Ingrese el nombre de la carrera: ");
        String nombre = scanner.nextLine().trim();

        Carrera encontrada = carreraController.findByName(nombre);
        if (encontrada == null) {
            System.out.println("Carrera inexistente.");
        }

        return encontrada;
    }

    private Cuatrimestre seleccionarCuatrimestre() {
        List<Cuatrimestre> cuatrimestres = cuatrimestreController.findAll();
        if (cuatrimestres.isEmpty()) {
            System.out.println("No hay cuatrimestres registrados.");
            return null;
        }

        System.out.println("\nCUATRIMESTRES DISPONIBLES:");
        cuatrimestres.forEach(c -> System.out.println(
            "NÚMERO: " + c.getNumero() +
            " | AÑO: " + c.getAnio() +
            " | INICIO: " + c.getInicio() +
            " | FIN: " + c.getFin() +
            " | ESTADO: " + (c.isActivo() ? "ACTIVO" : "INACTIVO")
        ));

        System.out.print("Ingrese el número del cuatrimestre: ");
        String numero = scanner.nextLine().trim();

        Cuatrimestre encontrado = cuatrimestreController.findByNumber(numero);
        if (encontrado == null) {
            System.out.println("Cuatrimestre inexistente.");
        }

        return encontrado;
    }

    private Modulo seleccionarModuloOpcional() {
        List<Modulo> modulos = moduloController.findAll();

        if (modulos.isEmpty()) {
            System.out.println("\nNo hay módulos registrados.");
            return null;
        }

        System.out.println("\nMÓDULOS DISPONIBLES:");
        modulos.forEach(m ->
                System.out.println("CÓDIGO: " + m.getCodigo() + " | INICIO: " + m.getInicio() + " | FIN: " + m.getFin())
        );

        System.out.print("Ingrese código del módulo (ENTER para no elegir ninguno): ");
        String codigo = scanner.nextLine().trim();

        if (codigo.isEmpty()) {
            return null;
        }

        Modulo encontrado = moduloController.findByCode(codigo);
        if (encontrado == null) {
            System.out.println("Módulo inexistente.");
        }

        return encontrado;
    }


    private Carrera leerCarreraOpcional(Carrera actual) {
        List<Carrera> carreras = carreraController.findAll();

        if (carreras.isEmpty()) {
            System.out.println("No hay carreras registradas.");
            return actual;
        }

        System.out.println("\nCarreras disponibles:");
        carreras.forEach(c -> System.out.println(" - " + c.getNombre()));

        System.out.print("Nueva carrera (ENTER para mantener): ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isEmpty()) return actual;

        Carrera c = carreraController.findByName(nombre);
        if (c == null) {
            System.out.println("Carrera inexistente. Se mantiene el valor actual.");
            return actual;
        }
        return c;
    }

    private Cuatrimestre leerCuatrimestreOpcional(Cuatrimestre actual) {
        List<Cuatrimestre> lista = cuatrimestreController.findAll();

        if (lista.isEmpty()) {
            System.out.println("No hay cuatrimestres registrados.");
            return actual;
        }

        System.out.println("\nCuatrimestres disponibles:");
        lista.forEach(c ->
                System.out.println("NÚMERO: " + c.getNumero() + " | INICIO: " + c.getInicio() + " | FIN: " + c.getFin())
        );

        System.out.print("Nuevo cuatrimestre (ENTER para mantener): ");
        String numero = scanner.nextLine().trim();

        if (numero.isEmpty()) return actual;

        Cuatrimestre c = cuatrimestreController.findByNumber(numero);
        if (c == null) {
            System.out.println("Cuatrimestre inexistente. Se mantiene el actual.");
            return actual;
        }
        return c;
    }

    private Modulo leerModuloOpcional(Modulo actual) {
        List<Modulo> modulos = moduloController.findAll();

        if (modulos.isEmpty()) {
            System.out.println("No hay módulos cargados.");
            return actual;
        }

        System.out.println("\nMódulos disponibles:");
        modulos.forEach(m ->
                System.out.println("CÓDIGO: " + m.getCodigo() + " | INICIO: " + m.getInicio() + " | FIN: " + m.getFin())
        );

        System.out.println("Módulo actual: " + actual.getCodigo());
        System.out.print("Nuevo módulo (ENTER para mantener): ");
        String codigo = scanner.nextLine().trim();

        if (codigo.isEmpty()) return actual;

        Modulo encontrado = moduloController.findByCode(codigo);
        if (encontrado == null) {
            System.out.println("Módulo inexistente. Se mantiene el actual.");
            return actual;
        }

        return encontrado;
    }

    private int generarIdHorario() {
        List<Horario> horarios = horarioController.findAll();

        // Si no hay horarios guardados → ID = 1
        if (horarios.isEmpty()) {
            return 1;
        }

        // Buscar el ID numérico más alto
        int maxId = horarios.stream()
                .mapToInt(h -> {
                    try {
                        return Integer.parseInt(h.getId());
                    } catch (NumberFormatException e) {
                        return 0; // si hubiera algún ID no numérico (no debería pasar)
                    }
                })
                .max()
                .orElse(0);

        return maxId + 1;
    }

    private Dia seleccionarDia() {
        System.out.println("\n----- Seleccionar día -----");

        Dia[] dias = Dia.values();

        // Mostrar lista numerada
        for (int i = 0; i < dias.length; i++) {
            System.out.println((i + 1) + ". " + dias[i]);
        }

        int opcion;
        do {
            System.out.print("Seleccione el día (1-" + dias.length + "): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Debe ingresar un número válido.");
                scanner.next(); // limpiar
            }
            opcion = scanner.nextInt();
        } while (opcion < 1 || opcion > dias.length);

        scanner.nextLine(); // limpiar buffer

        return dias[opcion - 1];
    }

    // ============================================================
    // CRUD
    // ============================================================

    private void listarHorarios() {
        System.out.println("---------- Listado de horarios ----------");

        List<Horario> lista = horarioController.findAll();

        if (lista.isEmpty()) {
            System.out.println("No hay horarios registrados.");
            return;
        }

        lista.forEach(this::mostrarHorario);
    }

    private void buscarHorario() {
        System.out.println("Ingrese ID del horario:");
        String id = scanner.nextLine();

        Horario h = horarioController.findById(id);

        if (h == null) {
            System.out.println("No encontrado.");
            return;
        }

        mostrarHorario(h);
    }

    private void crearHorario() {
        System.out.println("---------- Registrar horario ----------");

        int idGenerado = generarIdHorario();

        Horario horario = new Horario();
        horario.setId(String.valueOf(idGenerado));

        // 1. Carrera
        Carrera carrera = seleccionarCarrera();
        if (carrera == null) return;
        horario.setCarrera(carrera);

        // 2. Cuatrimestre
        Cuatrimestre cuatrimestre = seleccionarCuatrimestre();
        if (cuatrimestre == null) return;
        horario.setCuatrimestre(cuatrimestre);

        // 3. Día
        Dia diaSeleccionado = seleccionarDia();
        horario.setDia(diaSeleccionado);


        // 4. Módulo (opcional)
        Modulo modulo = seleccionarModuloOpcional();
        horario.setModulo(modulo);

        horario.setActivo(true);
        horario.setDisponible(true);

        System.out.println("\nVista previa:");
        mostrarHorario(horario);

        if (confirmarAccion("\n¿Confirmar registro?")) {
            horarioController.createHorario(horario);
            System.out.println("Horario creado con éxito.");
        }
    }

    private void actualizarHorario() {
        System.out.println("Ingrese ID del horario a actualizar:");
        String id = scanner.nextLine();

        Horario horario = horarioController.findById(id);
        if (horario == null) {
            System.out.println("Horario no encontrado.");
            return;
        }

        System.out.println("Horario encontrado:");
        mostrarHorario(horario);

        // Guardar valores originales
        Dia diaOriginal = horario.getDia();
        Carrera carreraOriginal = horario.getCarrera();
        Cuatrimestre cuatrimestreOriginal = horario.getCuatrimestre();
        Modulo moduloOriginal = horario.getModulo();

        System.out.println("\nNuevos datos:");

        Dia nuevoDia = seleccionarDia();
        horario.setDia(nuevoDia);

        horario.setCarrera(leerCarreraOpcional(horario.getCarrera()));
        horario.setCuatrimestre(leerCuatrimestreOpcional(horario.getCuatrimestre()));
        horario.setModulo(leerModuloOpcional(horario.getModulo()));

        System.out.println("\nVista previa:");
        mostrarHorario(horario);

        if (confirmarAccion("\n¿Guardar cambios?")) {
            horarioController.updateHorario(horario);
            System.out.println("Horario actualizado.");
        } else {
            horario.setDia(diaOriginal);
            horario.setCarrera(carreraOriginal);
            horario.setCuatrimestre(cuatrimestreOriginal);
            horario.setModulo(moduloOriginal);
            System.out.println("Actualización cancelada.");
        }
    }

    private void eliminarHorario() {
        System.out.println("Ingrese ID del horario:");
        String id = scanner.nextLine();

        Horario h = horarioController.findById(id);

        if (h == null) {
            System.out.println("Horario no encontrado.");
            return;
        }

        mostrarHorario(h);

        if (confirmarAccion("\n¿Confirmar baja lógica?")) {
            horarioController.deleteHorario(h);
            System.out.println("Horario dado de baja.");
        }
    }
}
