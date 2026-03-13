package ar.com.itecn1.view;

import ar.com.itecn1.controller.*;
import ar.com.itecn1.model.*;

import java.util.List;
import java.util.Scanner;

public class HorarioView {

    // Códigos de color
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String PURPLE = "\u001B[35m";
    private static final String BOLD = "\u001B[1m";

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

            if (!scanner.hasNextInt()) {
                System.out.println(RED + "Debe ingresar un número." + RESET);
                scanner.next();
                scanner.nextLine();
                continue;
            }

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> listarHorarios();
                case 2 -> buscarHorario();
                case 3 -> crearHorario();
                case 4 -> actualizarHorario();
                case 5 -> eliminarHorario();
                case 0 -> continuar = false;
                default -> System.out.println(RED + "Opción no válida" + RESET);
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n" + BLUE + BOLD + "HORARIOS" + RESET);
        System.out.println("----------");
        System.out.println(CYAN + "1. Listar horarios" + RESET);
        System.out.println(CYAN + "2. Buscar horario por ID" + RESET);
        System.out.println(CYAN + "3. Registrar horario" + RESET);
        System.out.println(CYAN + "4. Modificar horario" + RESET);
        System.out.println(CYAN + "5. Eliminar horario (dar de baja)" + RESET);
        System.out.println(YELLOW + "0. Volver" + RESET);
        System.out.print("\nSeleccione: ");
    }

    private boolean confirmarAccion(String mensaje) {
        System.out.println("\n" + mensaje);
        System.out.println(YELLOW + "1. Sí" + RESET);
        System.out.println(YELLOW + "2. No" + RESET);
        System.out.print("Opción: ");

        int opcion;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println(RED + "Debe ingresar un número." + RESET);
                scanner.next();
                scanner.nextLine();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();
        } while (opcion < 1 || opcion > 2);

        return opcion == 1;
    }

    private void mostrarHorarioCompacto(Horario h) {
        String estado = h.isActivo() ? "ACTIVO" : "INACTIVO";
        String carrera = h.getCarrera() != null ? h.getCarrera().getNombre() : "NO ASIGNADA";
        String cuatrimestre = h.getCuatrimestre() != null ? h.getCuatrimestre().getNumero() : "NO ASIGNADO";
        String modulo = h.getModulo() != null ? h.getModulo().getCodigo() : "NO ASIGNADO";

        System.out.printf("│ %-4s │ %-9s │ %-20s │ %-6s │ %-8s │ %-8s │%n",
                h.getId(),
                h.getDia(),
                carrera.length() > 20 ? carrera.substring(0, 17) + "..." : carrera,
                cuatrimestre,
                modulo,
                estado
        );
    }

    private void mostrarHorarioDetallado(Horario h) {
        String estado = h.isActivo() ? GREEN + "ACTIVO" + RESET : RED + "INACTIVO" + RESET;
        String disponible = h.isDisponible() ? GREEN + "SÍ" + RESET : RED + "NO" + RESET;

        System.out.println("  " + BOLD + "ID:" + RESET + " " + CYAN + h.getId() + RESET);
        System.out.println("  " + BOLD + "Día:" + RESET + " " + h.getDia());
        System.out.println("  " + BOLD + "Carrera:" + RESET + " " + (h.getCarrera() != null ? h.getCarrera().getNombre() : "NO ASIGNADA"));
        System.out.println("  " + BOLD + "Cuatrimestre:" + RESET + " " + (h.getCuatrimestre() != null ?
                h.getCuatrimestre().getNumero() + " (" + h.getCuatrimestre().getAnio() + ")" : "NO ASIGNADO"));
        System.out.println("  " + BOLD + "Módulo:" + RESET + " " + (h.getModulo() != null ?
                h.getModulo().getCodigo() + " (" + h.getModulo().getInicio() + " - " + h.getModulo().getFin() + ")" : "NO ASIGNADO"));
        System.out.println("  " + BOLD + "Estado:" + RESET + " " + estado);
        System.out.println("  " + BOLD + "Disponible:" + RESET + " " + disponible);
    }

    private Dia seleccionarDia() {
        System.out.println("\n" + CYAN + "Seleccionar día:" + RESET);
        Dia[] dias = Dia.values();

        for (int i = 0; i < dias.length; i++) {
            System.out.println("  " + (i + 1) + ". " + dias[i]);
        }

        int opcion;
        do {
            System.out.print("Seleccione (1-" + dias.length + "): ");
            while (!scanner.hasNextInt()) {
                System.out.print(RED + "Debe ingresar un número: " + RESET);
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();
        } while (opcion < 1 || opcion > dias.length);

        return dias[opcion - 1];
    }

    private Carrera seleccionarCarrera(boolean obligatorio) {
        List<Carrera> carreras = carreraController.findAll();
        List<Carrera> activas = carreras.stream().filter(Carrera::isActivo).toList();

        if (activas.isEmpty()) {
            System.out.println(RED + "No hay carreras activas disponibles." + RESET);
            return null;
        }

        System.out.println("\n" + CYAN + "Carreras activas disponibles:" + RESET);
        for (int i = 0; i < activas.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + activas.get(i).getNombre());
        }

        if (!obligatorio) {
            System.out.println("  " + (activas.size() + 1) + ". Ninguna");
        }

        int opcion;
        do {
            System.out.print("Seleccione (1-" + (obligatorio ? activas.size() : activas.size() + 1) + "): ");
            while (!scanner.hasNextInt()) {
                System.out.print(RED + "Debe ingresar un número: " + RESET);
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();
        } while (opcion < 1 || opcion > (obligatorio ? activas.size() : activas.size() + 1));

        if (!obligatorio && opcion == activas.size() + 1) {
            return null;
        }

        return activas.get(opcion - 1);
    }

    private Cuatrimestre seleccionarCuatrimestre(boolean obligatorio) {
        List<Cuatrimestre> cuatrimestres = cuatrimestreController.findAll();
        List<Cuatrimestre> activos = cuatrimestres.stream().filter(Cuatrimestre::isActivo).toList();

        if (activos.isEmpty()) {
            System.out.println(RED + "No hay cuatrimestres activos disponibles." + RESET);
            return null;
        }

        System.out.println("\n" + CYAN + "Cuatrimestres activos disponibles:" + RESET);
        for (int i = 0; i < activos.size(); i++) {
            Cuatrimestre c = activos.get(i);
            System.out.println("  " + (i + 1) + ". " + c.getNumero() + " (" + c.getAnio() + ") - " +
                    c.getInicio() + " a " + c.getFin());
        }

        if (!obligatorio) {
            System.out.println("  " + (activos.size() + 1) + ". Ninguno");
        }

        int opcion;
        do {
            System.out.print("Seleccione (1-" + (obligatorio ? activos.size() : activos.size() + 1) + "): ");
            while (!scanner.hasNextInt()) {
                System.out.print(RED + "Debe ingresar un número: " + RESET);
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();
        } while (opcion < 1 || opcion > (obligatorio ? activos.size() : activos.size() + 1));

        if (!obligatorio && opcion == activos.size() + 1) {
            return null;
        }

        return activos.get(opcion - 1);
    }

    private Modulo seleccionarModulo(boolean obligatorio) {
        List<Modulo> modulos = moduloController.findAll();
        List<Modulo> activos = modulos.stream().filter(Modulo::isActivo).toList();

        if (activos.isEmpty()) {
            System.out.println(RED + "No hay módulos activos disponibles." + RESET);
            return null;
        }

        System.out.println("\n" + CYAN + "Módulos activos disponibles:" + RESET);
        for (int i = 0; i < activos.size(); i++) {
            Modulo m = activos.get(i);
            System.out.println("  " + (i + 1) + ". " + m.getCodigo() + " (" + m.getInicio() + " - " + m.getFin() + ")");
        }

        if (!obligatorio) {
            System.out.println("  " + (activos.size() + 1) + ". Ninguno");
        }

        int opcion;
        do {
            System.out.print("Seleccione (1-" + (obligatorio ? activos.size() : activos.size() + 1) + "): ");
            while (!scanner.hasNextInt()) {
                System.out.print(RED + "Debe ingresar un número: " + RESET);
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();
        } while (opcion < 1 || opcion > (obligatorio ? activos.size() : activos.size() + 1));

        if (!obligatorio && opcion == activos.size() + 1) {
            return null;
        }

        return activos.get(opcion - 1);
    }

    private String generarIdHorario() {
        List<Horario> horarios = horarioController.findAll();

        if (horarios.isEmpty()) {
            return "1";
        }

        int maxId = horarios.stream()
                .mapToInt(h -> {
                    try {
                        return Integer.parseInt(h.getId());
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);

        return String.valueOf(maxId + 1);
    }

    private void listarHorarios() {
        System.out.println("\n" + BLUE + BOLD + "LISTADO DE HORARIOS" + RESET);
        System.out.println("====================");

        List<Horario> lista = horarioController.findAll();

        if (lista.isEmpty()) {
            System.out.println("No hay horarios registrados.");
            pausa();
            return;
        }

        System.out.println("┌──────┬───────────┬──────────────────────┬────────┬──────────┬──────────┐");
        System.out.println("│ ID   │ DÍA       │ CARRERA               │ CUATR  │ MÓDULO   │ ESTADO   │");
        System.out.println("├──────┼───────────┼──────────────────────┼────────┼──────────┼──────────┤");

        for (Horario h : lista) {
            mostrarHorarioCompacto(h);
        }
        System.out.println("└──────┴───────────┴──────────────────────┴────────┴──────────┴──────────┘");
        pausa();
    }

    private void buscarHorario() {
        System.out.println("\n" + BLUE + BOLD + "BUSCAR HORARIO" + RESET);
        System.out.println("===============");
        System.out.print("Ingrese ID del horario: ");
        String id = scanner.nextLine();

        Horario h = horarioController.findById(id);

        if (h == null) {
            System.out.println(RED + "Horario no encontrado." + RESET);
        } else {
            System.out.println("\n" + GREEN + "Horario encontrado:" + RESET);
            mostrarHorarioDetallado(h);
        }
        pausa();
    }

    private void crearHorario() {
        System.out.println("\n" + BLUE + BOLD + "REGISTRAR HORARIO" + RESET);
        System.out.println("==================");

        String idGenerado = generarIdHorario();
        System.out.println("ID asignado: " + CYAN + idGenerado + RESET);

        Horario horario = new Horario();
        horario.setId(idGenerado);

        // Seleccionar día
        Dia dia = seleccionarDia();
        horario.setDia(dia);

        // Seleccionar carrera (obligatoria)
        Carrera carrera = seleccionarCarrera(true);
        if (carrera == null) {
            System.out.println(RED + "Debe seleccionar una carrera. Operación cancelada." + RESET);
            pausa();
            return;
        }
        horario.setCarrera(carrera);

        // Seleccionar cuatrimestre (obligatorio)
        Cuatrimestre cuatrimestre = seleccionarCuatrimestre(true);
        if (cuatrimestre == null) {
            System.out.println(RED + "Debe seleccionar un cuatrimestre. Operación cancelada." + RESET);
            pausa();
            return;
        }
        horario.setCuatrimestre(cuatrimestre);

        // Seleccionar módulo (opcional)
        Modulo modulo = seleccionarModulo(false);
        horario.setModulo(modulo);

        horario.setActivo(true);
        horario.setDisponible(true);

        System.out.println("\n" + CYAN + "Vista previa:" + RESET);
        mostrarHorarioDetallado(horario);

        if (confirmarAccion("¿Confirmar registro?")) {
            horarioController.createHorario(horario);
            System.out.println(GREEN + "✓ Horario creado con éxito." + RESET);
        } else {
            System.out.println("Registro cancelado.");
        }
        pausa();
    }

    private void actualizarHorario() {
        System.out.println("\n" + BLUE + BOLD + "MODIFICAR HORARIO" + RESET);
        System.out.println("==================");

        System.out.print("Ingrese ID del horario a actualizar: ");
        String id = scanner.nextLine();

        Horario horario = horarioController.findById(id);
        if (horario == null) {
            System.out.println(RED + "Horario no encontrado." + RESET);
            pausa();
            return;
        }

        System.out.println("\n" + CYAN + "Datos actuales:" + RESET);
        mostrarHorarioDetallado(horario);

        // Guardar valores originales
        Dia diaOriginal = horario.getDia();
        Carrera carreraOriginal = horario.getCarrera();
        Cuatrimestre cuatrimestreOriginal = horario.getCuatrimestre();
        Modulo moduloOriginal = horario.getModulo();

        System.out.println("\n" + YELLOW + "NUEVOS DATOS (dejar en blanco para no cambiar):" + RESET);

        // Nuevo día
        System.out.print("¿Desea cambiar el día? (S/N): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
            Dia nuevoDia = seleccionarDia();
            horario.setDia(nuevoDia);
        }

        // Nueva carrera
        System.out.print("¿Desea cambiar la carrera? (S/N): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
            Carrera nuevaCarrera = seleccionarCarrera(true);
            if (nuevaCarrera != null) {
                horario.setCarrera(nuevaCarrera);
            }
        }

        // Nuevo cuatrimestre
        System.out.print("¿Desea cambiar el cuatrimestre? (S/N): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
            Cuatrimestre nuevoCuatrimestre = seleccionarCuatrimestre(true);
            if (nuevoCuatrimestre != null) {
                horario.setCuatrimestre(nuevoCuatrimestre);
            }
        }

        // Nuevo módulo
        System.out.print("¿Desea cambiar el módulo? (S/N): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
            Modulo nuevoModulo = seleccionarModulo(false);
            horario.setModulo(nuevoModulo);
        }

        System.out.println("\n" + CYAN + "Vista previa de los cambios:" + RESET);
        mostrarHorarioDetallado(horario);

        if (confirmarAccion("¿Confirmar cambios?")) {
            horarioController.updateHorario(horario);
            System.out.println(GREEN + "✓ Horario actualizado." + RESET);
        } else {
            horario.setDia(diaOriginal);
            horario.setCarrera(carreraOriginal);
            horario.setCuatrimestre(cuatrimestreOriginal);
            horario.setModulo(moduloOriginal);
            System.out.println("Actualización cancelada.");
        }
        pausa();
    }

    private void eliminarHorario() {
        System.out.println("\n" + BLUE + BOLD + "ELIMINAR HORARIO" + RESET);
        System.out.println("=================");

        System.out.print("Ingrese ID del horario: ");
        String id = scanner.nextLine();

        Horario h = horarioController.findById(id);

        if (h == null) {
            System.out.println(RED + "Horario no encontrado." + RESET);
            pausa();
            return;
        }

        System.out.println("\n" + CYAN + "Horario a eliminar:" + RESET);
        mostrarHorarioDetallado(h);

        if (confirmarAccion(RED + "¿Está seguro que desea ELIMINAR (dar de baja) este horario?" + RESET)) {
            horarioController.deleteHorario(h);
            System.out.println(GREEN + "✓ Horario dado de baja correctamente." + RESET);
        } else {
            System.out.println("Eliminación cancelada.");
        }
        pausa();
    }

    private void pausa() {
        System.out.print("\n" + CYAN + "Presione ENTER para continuar..." + RESET);
        scanner.nextLine();
    }
}