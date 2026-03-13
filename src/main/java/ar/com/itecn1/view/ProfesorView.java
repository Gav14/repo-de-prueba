package ar.com.itecn1.view;

import ar.com.itecn1.controller.ProfesorController;
import ar.com.itecn1.model.Profesor;

import java.util.List;
import java.util.Scanner;

public class ProfesorView {

    // Códigos de color
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    private final ProfesorController profesorController;
    private final Scanner scanner;

    public ProfesorView(ProfesorController profesorController, Scanner scanner) {
        this.profesorController = profesorController;
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
                case 1 -> listarProfesores();
                case 2 -> buscarProfesor();
                case 3 -> crearProfesor();
                case 4 -> actualizarProfesor();
                case 5 -> eliminarProfesor();
                case 0 -> continuar = false;
                default -> System.out.println(RED + "Opción no válida" + RESET);
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n" + BLUE + BOLD + "PROFESORES" + RESET);
        System.out.println("-----------");
        System.out.println(CYAN + "1. Listar profesores" + RESET);
        System.out.println(CYAN + "2. Buscar profesor por DNI" + RESET);
        System.out.println(CYAN + "3. Registrar profesor" + RESET);
        System.out.println(CYAN + "4. Modificar profesor" + RESET);
        System.out.println(CYAN + "5. Eliminar profesor (dar de baja)" + RESET);
        System.out.println(YELLOW + "0. Volver" + RESET);
        System.out.print("\nSeleccione: ");
    }

    private void mostrarProfesor(Profesor profesor) {
        String estado = profesor.isActivo() ? GREEN + "ACTIVO" + RESET : RED + "INACTIVO" + RESET;
        System.out.printf("│ %-8s │ %-15s │ %-15s │ %-25s │ %-12s │ %-8s │%n",
                profesor.getDni(),
                profesor.getNombre(),
                profesor.getApellido(),
                profesor.getEmail(),
                profesor.getTelefono(),
                estado.replaceAll("\u001B\\[[;\\d]*m", "") // Sacar colores para formato
        );
    }

    private void mostrarProfesorColor(Profesor profesor) {
        String estado = profesor.isActivo() ? GREEN + "ACTIVO" + RESET : RED + "INACTIVO" + RESET;
        System.out.println("  DNI: " + CYAN + profesor.getDni() + RESET);
        System.out.println("  Nombre: " + profesor.getNombre() + " " + profesor.getApellido());
        System.out.println("  Email: " + profesor.getEmail());
        System.out.println("  Teléfono: " + profesor.getTelefono());
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

    private void listarProfesores() {
        System.out.println("\n" + BLUE + BOLD + "LISTADO DE PROFESORES" + RESET);
        System.out.println("======================");

        List<Profesor> profesores = profesorController.findAll();

        if (profesores.isEmpty()) {
            System.out.println("No hay profesores registrados.");
            pausa();
            return;
        }

        System.out.println("┌──────────┬─────────────────┬─────────────────┬───────────────────────────┬──────────────┬──────────┐");
        System.out.println("│ DNI      │ NOMBRE          │ APELLIDO        │ EMAIL                     │ TELÉFONO     │ ESTADO   │");
        System.out.println("├──────────┼─────────────────┼─────────────────┼───────────────────────────┼──────────────┼──────────┤");

        for (Profesor p : profesores) {
            String estado = p.isActivo() ? "ACTIVO" : "INACTIVO";
            System.out.printf("│ %-8s │ %-15s │ %-15s │ %-25s │ %-12s │ %-8s │%n",
                    p.getDni(),
                    p.getNombre(),
                    p.getApellido(),
                    p.getEmail(),
                    p.getTelefono(),
                    estado
            );
        }
        System.out.println("└──────────┴─────────────────┴─────────────────┴───────────────────────────┴──────────────┴──────────┘");
        pausa();
    }

    private void buscarProfesor() {
        System.out.println("\n" + BLUE + BOLD + "BUSCAR PROFESOR" + RESET);
        System.out.println("================");
        System.out.print("Ingrese el DNI: ");
        String dni = scanner.nextLine();

        Profesor profesor = profesorController.findByDni(dni);

        Profesor profesor = profesorController.findByDni(dni);
        
        if (profesor == null) {
            System.out.println(RED + "Profesor no encontrado." + RESET);
        } else {
            System.out.println("\n" + GREEN + "Profesor encontrado:" + RESET);
            mostrarProfesorColor(profesor);
        }
        pausa();
    }

    private void crearProfesor() {
        System.out.println("\n" + BLUE + BOLD + "REGISTRAR PROFESOR" + RESET);
        System.out.println("===================");

        System.out.print("DNI: ");
        String dni = scanner.nextLine();

        // ✅ VALIDACIÓN DE FORMATO (8 dígitos numéricos)
        if (!profesorController.validarFormatoDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine(); // Pausa para que el usuario vea el mensaje
            return;             // Sale del método y vuelve al menú
        }

        if (profesorController.findByDni(dni) != null) {
            System.out.println(RED + "Ese DNI ya está registrado." + RESET);
            pausa();
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        Profesor profesor = new Profesor(dni, nombre, apellido, telefono, email);

        System.out.println("\n" + CYAN + "Vista previa:" + RESET);
        mostrarProfesorColor(profesor);

        if (confirmarAccion("¿Confirmar registro?")) {
            profesorController.crearProfesor(profesor);
            System.out.println(GREEN + "✓ Profesor registrado exitosamente!" + RESET);
        } else {
            System.out.println("Registro cancelado.");
        }
        pausa();
    }

    private void actualizarProfesor() {
        System.out.println("\n" + BLUE + BOLD + "MODIFICAR PROFESOR" + RESET);
        System.out.println("===================");

        System.out.print("Ingrese el DNI: ");
        String dni = scanner.nextLine();

        if (!profesorController.validarFormatoDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Profesor profesor = profesorController.findByDni(dni);
        if (profesor == null) {
            System.out.println(RED + "Profesor no encontrado." + RESET);
            pausa();
            return;
        }

        System.out.println("\n" + CYAN + "Datos actuales:" + RESET);
        mostrarProfesorColor(profesor);

        System.out.println("\n" + YELLOW + "NUEVOS DATOS (dejar en blanco para no cambiar):" + RESET);

        System.out.print("Nuevo nombre (" + profesor.getNombre() + "): ");
        String nuevoNombre = scanner.nextLine().trim();
        System.out.print("Nuevo apellido (" + profesor.getApellido() + "): ");
        String nuevoApellido = scanner.nextLine().trim();
        System.out.print("Nuevo teléfono (" + profesor.getTelefono() + "): ");
        String nuevoTelefono = scanner.nextLine().trim();
        System.out.print("Nuevo email (" + profesor.getEmail() + "): ");
        String nuevoEmail = scanner.nextLine().trim();

        System.out.println("\n" + CYAN + "Vista previa:" + RESET);
        System.out.println("  Nombre: " + (nuevoNombre.isBlank() ? profesor.getNombre() : nuevoNombre) +
                " " + (nuevoApellido.isBlank() ? profesor.getApellido() : nuevoApellido));
        System.out.println("  Teléfono: " + (nuevoTelefono.isBlank() ? profesor.getTelefono() : nuevoTelefono));
        System.out.println("  Email: " + (nuevoEmail.isBlank() ? profesor.getEmail() : nuevoEmail));

        if (confirmarAccion("¿Confirmar cambios?")) {
            if (!nuevoNombre.isBlank()) profesor.setNombre(nuevoNombre);
            if (!nuevoApellido.isBlank()) profesor.setApellido(nuevoApellido);
            if (!nuevoTelefono.isBlank()) profesor.setTelefono(nuevoTelefono);
            if (!nuevoEmail.isBlank()) profesor.setEmail(nuevoEmail);

            profesorController.editarProfesor(profesor);
            System.out.println(GREEN + "✓ Profesor modificado exitosamente!" + RESET);
        } else {
            System.out.println("Modificación cancelada.");
        }
        pausa();
    }

    private void eliminarProfesor() {
        System.out.println("\n" + BLUE + BOLD + "ELIMINAR PROFESOR" + RESET);
        System.out.println("==================");

        System.out.print("Ingrese el DNI: ");
        String dni = scanner.nextLine();

        if (!profesorController.validarFormatoDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Profesor profesor = profesorController.findByDni(dni);
        if (profesor == null) {
            System.out.println(RED + "Profesor no encontrado." + RESET);
            pausa();
            return;
        }

        System.out.println("\n" + CYAN + "Profesor a eliminar:" + RESET);
        mostrarProfesorColor(profesor);

        if (confirmarAccion(RED + "¿Está seguro que desea ELIMINAR este profesor?" + RESET)) {
            profesorController.eliminarProfesor(profesor);
            System.out.println(GREEN + "✓ Profesor eliminado correctamente." + RESET);
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