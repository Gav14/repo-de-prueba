package ar.com.itecn1.view;

import ar.com.itecn1.controller.CuatrimestreController;
import ar.com.itecn1.model.Cuatrimestre;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class CuatrimestreView {

    // Códigos de color
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    private final CuatrimestreController cuatrimestreController;
    private final Scanner scanner;

    public CuatrimestreView(CuatrimestreController cuatrimestreController, Scanner scanner) {
        this.cuatrimestreController = cuatrimestreController;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();

            if (!scanner.hasNextInt()) {
                System.out.println(RED + "Debe ingresar un número." + RESET);
                scanner.next();
                continue;
            }

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> listarCuatrimestres();
                case 2 -> buscarCuatrimestre();
                case 3 -> crearCuatrimestre();
                case 4 -> actualizarCuatrimestre();
                case 5 -> eliminarCuatrimestre();
                case 0 -> continuar = false;
                default -> System.out.println(RED + "Opción no válida" + RESET);
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n" + BLUE + BOLD + "CUATRIMESTRES" + RESET);
        System.out.println("---------------");
        System.out.println(CYAN + "1. Listar cuatrimestres" + RESET);
        System.out.println(CYAN + "2. Buscar cuatrimestre por número" + RESET);
        System.out.println(CYAN + "3. Registrar cuatrimestre" + RESET);
        System.out.println(CYAN + "4. Modificar cuatrimestre" + RESET);
        System.out.println(CYAN + "5. Eliminar cuatrimestre" + RESET);
        System.out.println(YELLOW + "0. Volver" + RESET);
        System.out.print("\nSeleccione: ");
    }

    private void mostrarCuatrimestre(Cuatrimestre c) {
        String estado = c.isActivo() ? GREEN + "ACTIVO" + RESET : RED + "INACTIVO" + RESET;
        System.out.printf("│ %-8s │ %-6s │ %-12s │ %-12s │ %-8s │%n",
                c.getNumero(),
                c.getAnio(),
                c.getInicio(),
                c.getFin(),
                estado.replaceAll("\u001B\\[[;\\d]*m", "")
        );
    }

    private void mostrarCuatrimestreColor(Cuatrimestre c) {
        String estado = c.isActivo() ? GREEN + "ACTIVO" + RESET : RED + "INACTIVO" + RESET;
        System.out.println("  Número: " + CYAN + c.getNumero() + RESET);
        System.out.println("  Año: " + c.getAnio());
        System.out.println("  Fecha inicio: " + c.getInicio());
        System.out.println("  Fecha fin: " + c.getFin());
        System.out.println("  Estado: " + estado);
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
            }
            opcion = scanner.nextInt();
            scanner.nextLine();
        } while (opcion < 1 || opcion > 2);

        return opcion == 1;
    }

    private LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println(RED + "Formato de fecha inválido. Use YYYY-MM-DD" + RESET);
            }
        }
    }

    private void listarCuatrimestres() {
        System.out.println("\n" + BLUE + BOLD + "LISTADO DE CUATRIMESTRES" + RESET);
        System.out.println("=========================");

        List<Cuatrimestre> lista = cuatrimestreController.findAll();

        if (lista.isEmpty()) {
            System.out.println("No hay cuatrimestres registrados.");
            pausa();
            return;
        }

        System.out.println("┌──────────┬────────┬──────────────┬──────────────┬──────────┐");
        System.out.println("│ NÚMERO   │ AÑO    │ FECHA INICIO │ FECHA FIN    │ ESTADO   │");
        System.out.println("├──────────┼────────┼──────────────┼──────────────┼──────────┤");

        for (Cuatrimestre c : lista) {
            String estado = c.isActivo() ? "ACTIVO" : "INACTIVO";
            System.out.printf("│ %-8s │ %-6s │ %-12s │ %-12s │ %-8s │%n",
                    c.getNumero(),
                    c.getAnio(),
                    c.getInicio(),
                    c.getFin(),
                    estado
            );
        }
        System.out.println("└──────────┴────────┴──────────────┴──────────────┴──────────┘");
        pausa();
    }

    private void buscarCuatrimestre() {
        System.out.println("\n" + BLUE + BOLD + "BUSCAR CUATRIMESTRE" + RESET);
        System.out.println("=====================");
        System.out.print("Ingrese el número del cuatrimestre: ");
        String numero = scanner.nextLine();

        Cuatrimestre c = cuatrimestreController.findByNumber(numero);

        if (c == null) {
            System.out.println(RED + "Cuatrimestre no encontrado." + RESET);
        } else {
            System.out.println("\n" + GREEN + "Cuatrimestre encontrado:" + RESET);
            mostrarCuatrimestreColor(c);
        }
        pausa();
    }

    private void crearCuatrimestre() {
        System.out.println("\n" + BLUE + BOLD + "REGISTRAR CUATRIMESTRE" + RESET);
        System.out.println("========================");

        System.out.print("Número: ");
        String numero = scanner.nextLine();

        if (cuatrimestreController.findByNumber(numero) != null) {
            System.out.println(RED + "Ya existe un cuatrimestre con ese número." + RESET);
            pausa();
            return;
        }

        System.out.print("Año: ");
        String anio = scanner.nextLine();

        LocalDate inicio = leerFecha("Fecha de inicio (YYYY-MM-DD): ");
        LocalDate fin = leerFecha("Fecha de fin (YYYY-MM-DD): ");

        Cuatrimestre nuevo = new Cuatrimestre(numero, inicio, fin, anio);

        System.out.println("\n" + CYAN + "Vista previa:" + RESET);
        mostrarCuatrimestreColor(nuevo);

        if (confirmarAccion("¿Confirmar registro?")) {
            cuatrimestreController.createCuatrimestre(nuevo);
            System.out.println(GREEN + "✓ Cuatrimestre registrado exitosamente!" + RESET);
        } else {
            System.out.println("Registro cancelado.");
        }
        pausa();
    }

    private void actualizarCuatrimestre() {
        System.out.println("\n" + BLUE + BOLD + "MODIFICAR CUATRIMESTRE" + RESET);
        System.out.println("========================");

        System.out.print("Ingrese el número del cuatrimestre: ");
        String numero = scanner.nextLine();

        Cuatrimestre c = cuatrimestreController.findByNumber(numero);

        if (c == null) {
            System.out.println(RED + "Cuatrimestre no encontrado." + RESET);
            pausa();
            return;
        }

        System.out.println("\n" + CYAN + "Datos actuales:" + RESET);
        mostrarCuatrimestreColor(c);

        System.out.println("\n" + YELLOW + "NUEVOS DATOS (dejar en blanco para no cambiar):" + RESET);

        System.out.print("Nuevo año (" + c.getAnio() + "): ");
        String nuevoAnio = scanner.nextLine();

        System.out.print("Nueva fecha inicio (" + c.getInicio() + "): ");
        String nuevoInicio = scanner.nextLine();

        System.out.print("Nueva fecha fin (" + c.getFin() + "): ");
        String nuevoFin = scanner.nextLine();

        System.out.println("\n" + CYAN + "Vista previa:" + RESET);
        System.out.println("  Año: " + (nuevoAnio.isBlank() ? c.getAnio() : nuevoAnio));
        System.out.println("  Fecha inicio: " + (nuevoInicio.isBlank() ? c.getInicio() : nuevoInicio));
        System.out.println("  Fecha fin: " + (nuevoFin.isBlank() ? c.getFin() : nuevoFin));

        if (confirmarAccion("¿Confirmar cambios?")) {
            if (!nuevoAnio.isBlank()) c.setAnio(nuevoAnio);
            if (!nuevoInicio.isBlank()) {
                try {
                    c.setInicio(LocalDate.parse(nuevoInicio));
                } catch (DateTimeParseException e) {
                    System.out.println(RED + "Formato de fecha inválido. No se actualizó la fecha de inicio." + RESET);
                }
            }
            if (!nuevoFin.isBlank()) {
                try {
                    c.setFin(LocalDate.parse(nuevoFin));
                } catch (DateTimeParseException e) {
                    System.out.println(RED + "Formato de fecha inválido. No se actualizó la fecha de fin." + RESET);
                }
            }

            cuatrimestreController.updateCuatrimestre(c);
            System.out.println(GREEN + "✓ Cuatrimestre modificado exitosamente!" + RESET);
        } else {
            System.out.println("Modificación cancelada.");
        }
        pausa();
    }

    private void eliminarCuatrimestre() {
        System.out.println("\n" + BLUE + BOLD + "ELIMINAR CUATRIMESTRE" + RESET);
        System.out.println("=======================");

        System.out.print("Ingrese el número del cuatrimestre: ");
        String numero = scanner.nextLine();

        Cuatrimestre c = cuatrimestreController.findByNumber(numero);

        if (c == null) {
            System.out.println(RED + "Cuatrimestre no encontrado." + RESET);
            pausa();
            return;
        }

        System.out.println("\n" + CYAN + "Cuatrimestre a eliminar:" + RESET);
        mostrarCuatrimestreColor(c);

        if (confirmarAccion(RED + "¿Está seguro que desea ELIMINAR este cuatrimestre?" + RESET)) {
            cuatrimestreController.deleteCuatrimestre(c);
            System.out.println(GREEN + "✓ Cuatrimestre eliminado correctamente." + RESET);
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