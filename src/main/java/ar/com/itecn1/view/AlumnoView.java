package ar.com.itecn1.view;

import ar.com.itecn1.controller.AlumnoController;
import ar.com.itecn1.model.Alumno;

import java.util.List;
import java.util.Scanner;

public class AlumnoView {

    // Códigos de color
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    private final AlumnoController alumnoController;
    private final Scanner scanner;

    public AlumnoView(AlumnoController alumnoController, Scanner scanner) {
        this.alumnoController = alumnoController;
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
                case 1 -> listarAlumnos();
                case 2 -> buscarAlumno();
                case 3 -> crearAlumno();
                case 4 -> actualizarAlumno();
                case 5 -> eliminarAlumno();
                case 0 -> continuar = false;
                default -> System.out.println(RED + "Opción no válida" + RESET);
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n" + BLUE + BOLD + "ALUMNOS" + RESET);
        System.out.println("-------");
        System.out.println(CYAN + "1. Listar alumnos" + RESET);
        System.out.println(CYAN + "2. Buscar alumno por DNI" + RESET);
        System.out.println(CYAN + "3. Registrar alumno" + RESET);
        System.out.println(CYAN + "4. Modificar alumno" + RESET);
        System.out.println(CYAN + "5. Eliminar alumno (dar de baja)" + RESET);
        System.out.println(YELLOW + "0. Volver" + RESET);
        System.out.print("\nSeleccione: ");
    }
    private void mostrarAlumno(Alumno alumno) {
        String estado = alumno.isActivo() ? GREEN + "ACTIVO" + RESET : RED + "INACTIVO" + RESET;
        System.out.printf("│ %-8s │ %-15s │ %-15s │ %-25s │ %-12s │ %-8s │%n",
                alumno.getDni(),
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getEmail(),
                alumno.getTelefono(),
                estado.replaceAll("\u001B\\[[;\\d]*m", "") // Sacar colores para formato
        );
    }

    private void mostrarAlumnoColor(Alumno alumno) {
        String estado = alumno.isActivo() ? GREEN + "ACTIVO" + RESET : RED + "INACTIVO" + RESET;
        System.out.println("  DNI: " + CYAN + alumno.getDni() + RESET);
        System.out.println("  Nombre: " + alumno.getNombre() + " " + alumno.getApellido());
        System.out.println("  Email: " + alumno.getEmail());
        System.out.println("  Teléfono: " + alumno.getTelefono());
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

    private void listarAlumnos() {
        System.out.println("\n" + BLUE + BOLD + "LISTADO DE ALUMNOS" + RESET);
        System.out.println("===================");

        List<Alumno> alumnos = alumnoController.findAll();

        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
            return;
        }

        System.out.println("┌──────────┬─────────────────┬─────────────────┬───────────────────────────┬──────────────┬──────────┐");
        System.out.println("│ DNI      │ NOMBRE          │ APELLIDO        │ EMAIL                     │ TELÉFONO     │ ESTADO   │");
        System.out.println("├──────────┼─────────────────┼─────────────────┼───────────────────────────┼──────────────┼──────────┤");

        for (Alumno a : alumnos) {
            String estado = a.isActivo() ? "ACTIVO" : "INACTIVO";
            System.out.printf("│ %-8s │ %-15s │ %-15s │ %-25s │ %-12s │ %-8s │%n",
                    a.getDni(),
                    a.getNombre(),
                    a.getApellido(),
                    a.getEmail(),
                    a.getTelefono(),
                    estado
            );
        }
        System.out.println("└──────────┴─────────────────┴─────────────────┴───────────────────────────┴──────────────┴──────────┘");
        pausa();
    }

    private void buscarAlumno() {
        System.out.println("\n" + BLUE + BOLD + "BUSCAR ALUMNO" + RESET);
        System.out.println("==============");
        System.out.print("Ingrese el DNI: ");
        String dni = scanner.nextLine().trim();

        if (!alumnoController.validarDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Alumno alumno = alumnoController.findByDni(dni);

        if (alumno == null) {
            System.out.println(RED + "Alumno no encontrado." + RESET);
        } else {
            System.out.println("\n" + GREEN + "Alumno encontrado:" + RESET);
            mostrarAlumnoColor(alumno);
        }
        pausa();
    }

    private void crearAlumno() {
        System.out.println("\n" + BLUE + BOLD + "REGISTRAR ALUMNO" + RESET);
        System.out.println("=================");

        System.out.print("DNI: ");
        String dni = scanner.nextLine().trim();

        if (!alumnoController.validarDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        if (alumnoController.findByDni(dni) != null) {
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

        Alumno alumno = new Alumno(dni, nombre, apellido, telefono, email);

        System.out.println("\n" + CYAN + "Vista previa:" + RESET);
        mostrarAlumnoColor(alumno);

        if (confirmarAccion("¿Confirmar registro?")) {
            alumnoController.createAlumno(alumno);
            System.out.println(GREEN + "✓ Alumno registrado exitosamente!" + RESET);
        } else {
            System.out.println("Registro cancelado.");
        }
        pausa();
    }

    private void actualizarAlumno() {
        System.out.println("\n" + BLUE + BOLD + "MODIFICAR ALUMNO" + RESET);
        System.out.println("=================");

        System.out.print("Ingrese el DNI: ");
        String dni = scanner.nextLine().trim(); // Limpiar espacios

        if (!alumnoController.validarDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Alumno alumno = alumnoController.findByDni(dni);

        if (alumno == null) {
            System.out.println(RED + "Alumno no encontrado." + RESET);
            pausa();
            return;
        }

        System.out.println("\n" + CYAN + "Datos actuales:" + RESET);
        mostrarAlumnoColor(alumno);

        System.out.println("\n" + YELLOW + "NUEVOS DATOS (dejar en blanco para no cambiar):" + RESET);

        System.out.print("Nuevo nombre (" + alumno.getNombre() + "): ");
        String nuevoNombre = scanner.nextLine().trim();

        System.out.print("Nuevo apellido (" + alumno.getApellido() + "): ");
        String nuevoApellido = scanner.nextLine().trim();

        System.out.print("Nuevo teléfono (" + alumno.getTelefono() + "): ");
        String nuevoTelefono = scanner.nextLine().trim();

        System.out.print("Nuevo email (" + alumno.getEmail() + "): ");
        String nuevoEmail = scanner.nextLine().trim();

        System.out.println("\n" + CYAN + "Vista previa:" + RESET);
        System.out.println("  Nombre: " + (nuevoNombre.isBlank() ? alumno.getNombre() : nuevoNombre) +
                " " + (nuevoApellido.isBlank() ? alumno.getApellido() : nuevoApellido));
        System.out.println("  Teléfono: " + (nuevoTelefono.isBlank() ? alumno.getTelefono() : nuevoTelefono));
        System.out.println("  Email: " + (nuevoEmail.isBlank() ? alumno.getEmail() : nuevoEmail));

        if (confirmarAccion("¿Confirmar cambios?")) {
            if (!nuevoNombre.isBlank()) alumno.setNombre(nuevoNombre);
            if (!nuevoApellido.isBlank()) alumno.setApellido(nuevoApellido);
            if (!nuevoTelefono.isBlank()) alumno.setTelefono(nuevoTelefono);
            if (!nuevoEmail.isBlank()) alumno.setEmail(nuevoEmail);

            alumnoController.updateAlumno(alumno);
            System.out.println(GREEN + "✓ Alumno modificado exitosamente!" + RESET);
        } else {
            System.out.println("Modificación cancelada.");
        }
        pausa();
    }

    private void eliminarAlumno() {
        System.out.println("\n" + BLUE + BOLD + "ELIMINAR ALUMNO" + RESET);
        System.out.println("================");

        System.out.print("Ingrese el DNI: ");
        String dni = scanner.nextLine().trim();

        if (!alumnoController.validarDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Alumno alumno = alumnoController.findByDni(dni);

        if (alumno == null) {
            System.out.println(RED + "Alumno no encontrado." + RESET);
            pausa();
            return;
        }

        System.out.println("\n" + CYAN + "Alumno a eliminar:" + RESET);
        mostrarAlumnoColor(alumno);

        if (confirmarAccion(RED + "¿Está seguro que desea ELIMINAR este alumno?" + RESET)) {
            alumnoController.deleteAlumno(alumno);
            System.out.println(GREEN + "✓ Alumno eliminado correctamente." + RESET);
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
