package ar.com.itecn1.view;

import ar.com.itecn1.controller.ProfesorController;
import ar.com.itecn1.model.Profesor;

import java.util.List;
import java.util.Scanner;

public class ProfesorView {

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
                System.out.println("Debe ingresar un número.");
                scanner.next();
                continue;
            }

            int opcion = scanner.nextInt();
            pausa();

            switch (opcion) {
                case 1 -> listarProfesores();
                case 2 -> buscarProfesor();
                case 3 -> crearProfesor();
                case 4 -> actualizarProfesor();
                case 5 -> eliminarProfesor();
                case 6 -> continuar = false;
                default -> System.out.println("Ingrese una opción válida");
            }
        }
    }

    // ----------------------------------------------------------------
    // Métodos auxiliares reutilizables
    // ----------------------------------------------------------------

    private void mostrarMenu() {
        System.out.println("\nGESTIÓN DE PROFESORES");
        System.out.println("1. Listar profesores");
        System.out.println("2. Buscar profesor por DNI");
        System.out.println("3. Registrar profesor");
        System.out.println("4. Actualizar datos de un profesor");
        System.out.println("5. Dar de baja un profesor");
        System.out.println("6. Volver atrás");
        System.out.print("Seleccione una opción: ");
    }

    private void mostrarProfesor(Profesor profesor) {
        System.out.println(
            "DNI: " + profesor.getDni() +
            " | NOMBRE: " + profesor.getNombre() +
            " | APELLIDO: " + profesor.getApellido() +
            " | EMAIL: " + profesor.getEmail() +
            " | TELÉFONO: " + profesor.getTelefono() +
            " | ESTADO: " + (profesor.isActivo() ? "ACTIVO" : "INACTIVO")
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
        scanner.nextLine(); // Limpia buffer tras nextInt()
    }

    // ----------------------------------------------------------------
    // Funcionalidades
    // ----------------------------------------------------------------

    private void listarProfesores() {
        System.out.println("----------Listado de Profesores----------");
        List<Profesor> profesores = profesorController.findAll();

        if (profesores.isEmpty()) {
            System.out.println("No hay profesores registrados.");
            return;
        }

        profesores.forEach(this::mostrarProfesor);
    }

    private void buscarProfesor() {
        System.out.println("----------Buscar Profesor----------");
        System.out.print("Ingrese el DNI: ");

        String dni = scanner.nextLine();
        Profesor profesor = profesorController.findByDni(dni);

        if (profesor == null) {
            System.out.println("Profesor no encontrado.");
        } else {
            mostrarProfesor(profesor);
        }
    }

    private void crearProfesor() {
        System.out.println("----------Registrar Profesor----------");

        System.out.print("DNI: ");
        String dni = scanner.nextLine();

        if (profesorController.findByDni(dni) != null) {
            System.out.println("Ese DNI ya está registrado.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Profesor profesor = new Profesor(dni, nombre, apellido, telefono, email);

        System.out.println("\nVista previa:");
        mostrarProfesor(profesor);

        if (confirmarAccion() == 1) {
            profesorController.crearProfesor(profesor);
            System.out.println("Profesor registrado!");
        }
    }

    private void actualizarProfesor() {
        System.out.println("----------Actualizar Profesor----------");

        System.out.print("Ingrese el DNI del profesor: ");
        String dni = scanner.nextLine();

        Profesor profesor = profesorController.findByDni(dni);

        if (profesor == null) {
            System.out.println("Profesor no encontrado.");
            return;
        }

        System.out.println("Profesor encontrado:");
        mostrarProfesor(profesor);

        System.out.println("\nNuevos datos (dejar vacío para no cambiar):");

        System.out.print("Nuevo nombre (" + profesor.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (!nombre.isBlank()) profesor.setNombre(nombre);

        System.out.print("Nuevo apellido (" + profesor.getApellido() + "): ");
        String apellido = scanner.nextLine();
        if (!apellido.isBlank()) profesor.setApellido(apellido);

        System.out.print("Nuevo teléfono (" + profesor.getTelefono() + "): ");
        String telefono = scanner.nextLine();
        if (!telefono.isBlank()) profesor.setTelefono(telefono);

        System.out.print("Nuevo email (" + profesor.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isBlank()) profesor.setEmail(email);

        System.out.println("\nVista previa:");
        mostrarProfesor(profesor);

        if (confirmarAccion() == 1) {
            profesorController.editarProfesor(profesor);
            System.out.println("Profesor modificado!");
        }
    }

    private void eliminarProfesor() {
        System.out.println("----------Eliminar Profesor----------");

        System.out.print("Ingrese el DNI del profesor: ");
        String dni = scanner.nextLine();

        Profesor profesor = profesorController.findByDni(dni);

        if (profesor == null) {
            System.out.println("Profesor no encontrado.");
            return;
        }

        System.out.println("Profesor encontrado:");
        mostrarProfesor(profesor);

        if (confirmarAccion() == 1) {
            profesorController.eliminarProfesor(profesor);
            System.out.println("Profesor eliminado!");
        }
    }
}
