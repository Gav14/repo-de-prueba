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
                case 0 -> continuar = false;
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
        System.out.println("0. Volver atrás");
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
        String dni = scanner.nextLine().trim(); // ← agregar .trim()

        // Validar formato
        if (!profesorController.validarFormatoDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Profesor profesor = profesorController.findByDni(dni);
        
        if (profesor == null) {
            System.out.println("Profesor no encontrado.");
        } else {
            mostrarProfesor(profesor);
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();

    }

    private void crearProfesor() {
        System.out.println("----------Registrar Profesor----------");

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

        Profesor profesor = new Profesor(dni, nombre, apellido, telefono, email);

        // Validar formato
        if (!profesorController.validarFormatoDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        // Validar campos obligatorios
        if (!profesorController.validarCampos(profesor)) {
            System.out.println("Error: Todos los campos son obligatorios. No se permiten valores vacíos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("\nVista previa:");
        mostrarProfesor(profesor);

        if (confirmarAccion() == 1) {
            profesorController.crearProfesor(profesor);
            System.out.println("Profesor registrado!");
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    private void actualizarProfesor() {
        System.out.println("----------Actualizar Profesor----------");

        System.out.print("Ingrese el DNI del profesor: ");
        String dni = scanner.nextLine().trim();

        if (!profesorController.validarFormatoDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Profesor profesor = profesorController.findByDni(dni);
        if (profesor == null) {
            System.out.println("Profesor no encontrado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("Profesor encontrado:");
        mostrarProfesor(profesor);

        System.out.println("\nNuevos datos (dejar vacío para no cambiar):");
        System.out.print("Nuevo nombre (" + profesor.getNombre() + "): ");
        String nuevoNombre = scanner.nextLine().trim();
        System.out.print("Nuevo apellido (" + profesor.getApellido() + "): ");
        String nuevoApellido = scanner.nextLine().trim();
        System.out.print("Nuevo teléfono (" + profesor.getTelefono() + "): ");
        String nuevoTelefono = scanner.nextLine().trim();
        System.out.print("Nuevo email (" + profesor.getEmail() + "): ");
        String nuevoEmail = scanner.nextLine().trim();

        System.out.println("\nVista previa:");
        System.out.println("Nombre: "   + (nuevoNombre.isBlank()   ? profesor.getNombre()    : nuevoNombre));
        System.out.println("Apellido: " + (nuevoApellido.isBlank() ? profesor.getApellido()  : nuevoApellido));
        System.out.println("Teléfono: " + (nuevoTelefono.isBlank() ? profesor.getTelefono()  : nuevoTelefono));
        System.out.println("Email: "    + (nuevoEmail.isBlank()    ? profesor.getEmail()     : nuevoEmail));

        if (confirmarAccion() == 1) {
            if (!nuevoNombre.isBlank())   profesor.setNombre(nuevoNombre);
            if (!nuevoApellido.isBlank()) profesor.setApellido(nuevoApellido);
            if (!nuevoTelefono.isBlank()) profesor.setTelefono(nuevoTelefono);
            if (!nuevoEmail.isBlank())    profesor.setEmail(nuevoEmail);

            profesorController.editarProfesor(profesor);
            System.out.println("Profesor modificado!");
        } else {
            System.out.println("Actualización cancelada.");
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    private void eliminarProfesor() {
        System.out.println("----------Eliminar Profesor----------");

        System.out.print("Ingrese el DNI del profesor: ");
        String dni = scanner.nextLine().trim();

        if (!profesorController.validarFormatoDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Profesor profesor = profesorController.findByDni(dni);
        if (profesor == null) {
            System.out.println("Profesor no encontrado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("Profesor encontrado:");
        mostrarProfesor(profesor);

        if (confirmarAccion() == 1) {
            profesorController.eliminarProfesor(profesor);
            System.out.println("Profesor eliminado!");
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }
}
