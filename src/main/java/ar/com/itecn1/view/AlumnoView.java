package ar.com.itecn1.view;

import ar.com.itecn1.controller.AlumnoController;
import ar.com.itecn1.model.Alumno;

import java.util.List;
import java.util.Scanner;

public class AlumnoView {
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
                System.out.println("Debe ingresar un número.");
                scanner.next();
                continue;
            }

            int opcion = scanner.nextInt();
            pausa();

            switch (opcion) {
                case 1 -> listarAlumnos();
                case 2 -> buscarAlumno();
                case 3 -> crearAlumno();
                case 4 -> actualizarAlumno();
                case 5 -> eliminarAlumno();
                case 0 -> continuar = false;
                default -> System.out.println("Ingrese una opción válida");
            }
        }
    }

    // -----------------------------
    // Métodos auxiliares
    // -----------------------------

    private void mostrarMenu() {
        System.out.println("\nGESTIÓN DE ALUMNOS");
        System.out.println("1. Listar alumnos");
        System.out.println("2. Buscar alumno por DNI");
        System.out.println("3. Registrar alumno");
        System.out.println("4. Actualizar datos de un alumno");
        System.out.println("5. Dar de baja un alumno");
        System.out.println("0. Volver atrás");
        System.out.print("Seleccione una opción: ");
    }

    private void mostrarAlumno(Alumno alumno) {
        System.out.println(
            "DNI: " + alumno.getDni() +
            " | NOMBRE: " + alumno.getNombre() +
            " | APELLIDO: " + alumno.getApellido() +
            " | EMAIL: " + alumno.getEmail() +
            " | TELÉFONO: " + alumno.getTelefono() +
            " | ESTADO: " + (alumno.isActivo() ? "ACTIVO" : "INACTIVO")
        );
    }

    private int confirmarAccion() {
        int opcion = 0;
        while (opcion != 1 && opcion != 2) {
            System.out.println("\nConfirmar:");
            System.out.println("1. Sí");
            System.out.println("2. No");
            System.out.print("Opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
            } else {
                scanner.next();
            }
        }
        pausa();
        return opcion;
    }

    private void pausa() {
        scanner.nextLine(); // limpia buffer luego de nextInt()
    }

    // -----------------------------
    // Funcionalidades
    // -----------------------------

    private void listarAlumnos() {
        System.out.println("----------Listado de Alumnos----------");
        List<Alumno> alumnos = alumnoController.findAll();

        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
            return;
        }

        alumnos.forEach(this::mostrarAlumno);
    }

    private void buscarAlumno() {
        System.out.println("----------Buscar alumno----------");
        System.out.print("Ingrese el DNI: ");
        String dni = scanner.nextLine();

        if (!alumnoController.validarDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Alumno alumno = alumnoController.findByDni(dni);

        if (alumno == null) {
            System.out.println("Alumno no encontrado.");
        } else {
            mostrarAlumno(alumno);
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    private void crearAlumno() {
        System.out.println("----------Registrar alumno----------");

        System.out.print("DNI: ");
        String dni = scanner.nextLine().trim();

        if (!alumnoController.validarDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        if (alumnoController.findByDni(dni) != null) {
            System.out.println("Ese DNI ya está registrado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
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


        // Validar campos obligatorios
        if (!alumnoController.validarCampos(alumno)) {
            System.out.println("Error: Todos los campos son obligatorios. No se permiten valores vacíos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("\nVista previa:");
        mostrarAlumno(alumno);

        if (confirmarAccion() == 1) {
            alumnoController.createAlumno(alumno);
            System.out.println("Alumno registrado!");
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    private void actualizarAlumno() {
        ystem.out.println("----------Actualizar alumno----------");

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
            System.out.println("Alumno no encontrado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("Alumno encontrado:");
        mostrarAlumno(alumno);

        System.out.println("\nNUEVOS DATOS (dejar en blanco para no cambiar):");

        System.out.print("Nuevo nombre (" + alumno.getNombre() + "): ");
        String nuevoNombre = scanner.nextLine().trim();

        System.out.print("Nuevo apellido (" + alumno.getApellido() + "): ");
        String nuevoApellido = scanner.nextLine().trim();

        System.out.print("Nuevo teléfono (" + alumno.getTelefono() + "): ");
        String nuevoTelefono = scanner.nextLine().trim();

        System.out.print("Nuevo email (" + alumno.getEmail() + "): ");
        String nuevoEmail = scanner.nextLine().trim();

        // Vista previa
        System.out.println("\nVista previa:");
        System.out.println("Nombre: "   + (nuevoNombre.isBlank()    ? alumno.getNombre()    : nuevoNombre));
        System.out.println("Apellido: " + (nuevoApellido.isBlank()  ? alumno.getApellido()  : nuevoApellido));
        System.out.println("Teléfono: " + (nuevoTelefono.isBlank()  ? alumno.getTelefono()  : nuevoTelefono));
        System.out.println("Email: "    + (nuevoEmail.isBlank()     ? alumno.getEmail()     : nuevoEmail));

        if (confirmarAccion() == 1) {
            // Solo actualizar si el campo no está en blanco (después de trim)
            if (!nuevoNombre.isBlank())    alumno.setNombre(nuevoNombre);
            if (!nuevoApellido.isBlank())  alumno.setApellido(nuevoApellido);
            if (!nuevoTelefono.isBlank())  alumno.setTelefono(nuevoTelefono);
            if (!nuevoEmail.isBlank())     alumno.setEmail(nuevoEmail);

            alumnoController.updateAlumno(alumno);
            System.out.println("Alumno modificado!");
        } else {
            System.out.println("Actualización cancelada.");
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    private void eliminarAlumno() {
        System.out.println("----------Eliminar alumno----------");

        System.out.print("Ingrese el DNI: ");
        String dni = scanner.nextLine();

        if (!alumnoController.validarDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Alumno alumno = alumnoController.findByDni(dni);

        if (alumno == null) {
            System.out.println("Alumno no encontrado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("Alumno encontrado:");
        mostrarAlumno(alumno);

        if (confirmarAccion() == 1) {
            alumnoController.deleteAlumno(alumno);
            System.out.println("Alumno eliminado!");
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    public boolean validarFormatoDni(String dni) {
        if (dni == null) return false;
        if (dni.length() != 8) return false; // Cambia a 9 si es necesario
        for (int i = 0; i < dni.length(); i++) {
            char c = dni.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }
}
