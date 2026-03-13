package ar.com.itecn1.view;

import ar.com.itecn1.controller.ModuloController;
import ar.com.itecn1.model.Modulo;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ModuloView {

    // Códigos de color
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    private final ModuloController moduloController;
    private final Scanner scanner;

    public ModuloView(ModuloController moduloController, Scanner scanner) {
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
                continue;
            }

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> listarModulos();
                case 2 -> buscarModulo();
                case 3 -> crearModulo();
                case 4 -> actualizarModulo();
                case 5 -> eliminarModulo();
                case 0 -> continuar = false;
                default -> System.out.println(RED + "Opción no válida" + RESET);
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n" + BLUE + BOLD + "MÓDULOS" + RESET);
        System.out.println("---------");
        System.out.println(CYAN + "1. Listar módulos" + RESET);
        System.out.println(CYAN + "2. Buscar módulo por código" + RESET);
        System.out.println(CYAN + "3. Registrar módulo" + RESET);
        System.out.println(CYAN + "4. Modificar módulo" + RESET);
        System.out.println(CYAN + "5. Eliminar módulo (dar de baja)" + RESET);
        System.out.println(YELLOW + "0. Volver" + RESET);
        System.out.print("\nSeleccione: ");
    }

    private void mostrarModulo(Modulo m) {
        String estado = m.isActivo() ? GREEN + "ACTIVO" + RESET : RED + "INACTIVO" + RESET;
        System.out.printf("│ %-8s │ %-8s │ %-8s │ %-8s │%n",
                m.getCodigo(),
                m.getInicio(),
                m.getFin(),
                estado.replaceAll("\u001B\\[[;\\d]*m", "")
        );
    }

    private void mostrarModuloColor(Modulo m) {
        String estado = m.isActivo() ? GREEN + "ACTIVO" + RESET : RED + "INACTIVO" + RESET;
        System.out.println("  Código: " + CYAN + m.getCodigo() + RESET);
        System.out.println("  Hora inicio: " + m.getInicio());
        System.out.println("  Hora fin: " + m.getFin());
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

    private LocalTime leerHora(String mensaje, boolean obligatorio) {
        while (true) {
            System.out.print(mensaje + (obligatorio ? " (HH:mm): " : " (HH:mm, ENTER para mantener): "));
            String input = scanner.nextLine().trim();

            if (!obligatorio && input.isEmpty()) {
                return null;
            }

            try {
                return LocalTime.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println(RED + "Formato de hora inválido. Use HH:mm (ej: 08:30)" + RESET);
            }
        }
    }

    private void listarModulos() {
        System.out.println("\n" + BLUE + BOLD + "LISTADO DE MÓDULOS" + RESET);
        System.out.println("===================");

        List<Modulo> lista = moduloController.findAll();

        if (lista.isEmpty()) {
            System.out.println("No hay módulos registrados.");
            pausa();
            return;
        }

        System.out.println("┌──────────┬──────────┬──────────┬──────────┐");
        System.out.println("│ CÓDIGO   │ INICIO   │ FIN      │ ESTADO   │");
        System.out.println("├──────────┼──────────┼──────────┼──────────┤");

        for (Modulo m : lista) {
            String estado = m.isActivo() ? "ACTIVO" : "INACTIVO";
            System.out.printf("│ %-8s │ %-8s │ %-8s │ %-8s │%n",
                    m.getCodigo(),
                    m.getInicio(),
                    m.getFin(),
                    estado
            );
        }
        System.out.println("└──────────┴──────────┴──────────┴──────────┘");
        pausa();
    }

    private void buscarModulo() {
        System.out.println("\n" + BLUE + BOLD + "BUSCAR MÓDULO" + RESET);
        System.out.println("==============");
        System.out.print("Ingrese el código del módulo: ");
        String codigo = scanner.nextLine();

        Modulo m = moduloController.findByCode(codigo);

        if (m == null) {
            System.out.println(RED + "Módulo no encontrado." + RESET);
        } else {
            System.out.println("\n" + GREEN + "Módulo encontrado:" + RESET);
            mostrarModuloColor(m);
        }
        pausa();
    }

    private void crearModulo() {
        System.out.println("\n" + BLUE + BOLD + "REGISTRAR MÓDULO" + RESET);
        System.out.println("=================");

        System.out.print("Código del módulo: ");
        String codigo = scanner.nextLine();

        if (moduloController.findByCode(codigo) != null) {
            System.out.println(RED + "Ya existe un módulo con ese código." + RESET);
            pausa();
            return;
        }

        LocalTime inicio = leerHora("Hora de inicio", true);
        LocalTime fin = leerHora("Hora de fin", true);

        Modulo nuevo = new Modulo(codigo, inicio, fin);

        System.out.println("\n" + CYAN + "Vista previa:" + RESET);
        mostrarModuloColor(nuevo);

        if (confirmarAccion("¿Confirmar registro?")) {
            moduloController.createModulo(nuevo);
            System.out.println(GREEN + "✓ Módulo registrado exitosamente!" + RESET);
        } else {
            System.out.println("Registro cancelado.");
        }
        pausa();
    }

    private void actualizarModulo() {
        System.out.println("\n" + BLUE + BOLD + "MODIFICAR MÓDULO" + RESET);
        System.out.println("=================");

        System.out.print("Ingrese el código del módulo: ");
        String codigo = scanner.nextLine();

        Modulo m = moduloController.findByCode(codigo);

        if (m == null) {
            System.out.println(RED + "Módulo no encontrado." + RESET);
            pausa();
            return;
        }

        System.out.println("\n" + CYAN + "Datos actuales:" + RESET);
        mostrarModuloColor(m);

        System.out.println("\n" + YELLOW + "NUEVOS DATOS (dejar en blanco para no cambiar):" + RESET);

        LocalTime nuevoInicio = leerHora("Nueva hora de inicio", false);
        LocalTime nuevoFin = leerHora("Nueva hora de fin", false);

        System.out.println("\n" + CYAN + "Vista previa:" + RESET);
        System.out.println("  Hora inicio: " + (nuevoInicio == null ? m.getInicio() : nuevoInicio));
        System.out.println("  Hora fin: " + (nuevoFin == null ? m.getFin() : nuevoFin));

        if (confirmarAccion("¿Confirmar cambios?")) {
            if (nuevoInicio != null) m.setInicio(nuevoInicio);
            if (nuevoFin != null) m.setFin(nuevoFin);

            moduloController.updateModulo(m);
            System.out.println(GREEN + "✓ Módulo modificado exitosamente!" + RESET);
        } else {
            System.out.println("Modificación cancelada.");
        }
        pausa();
    }

    private void eliminarModulo() {
        System.out.println("\n" + BLUE + BOLD + "ELIMINAR MÓDULO" + RESET);
        System.out.println("================");

        System.out.print("Ingrese el código del módulo: ");
        String codigo = scanner.nextLine();

        Modulo m = moduloController.findByCode(codigo);

        if (m == null) {
            System.out.println(RED + "Módulo no encontrado." + RESET);
            pausa();
            return;
        }

        System.out.println("\n" + CYAN + "Módulo a eliminar:" + RESET);
        mostrarModuloColor(m);

        if (confirmarAccion(RED + "¿Está seguro que desea ELIMINAR este módulo?" + RESET)) {
            moduloController.deleteModulo(m);
            System.out.println(GREEN + "✓ Módulo eliminado correctamente." + RESET);
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