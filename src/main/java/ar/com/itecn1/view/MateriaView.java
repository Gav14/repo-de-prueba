package ar.com.itecn1.view;

import ar.com.itecn1.controller.MateriaController;
import ar.com.itecn1.model.Materia;

import java.util.List;
import java.util.Scanner;

public class MateriaView {

    private final MateriaController materiaController;
    private final Scanner scanner;

    public MateriaView(MateriaController materiaController, Scanner scanner) {
        this.materiaController = materiaController;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarMaterias();
                case 2 -> buscarMateria();
                case 3 -> crearMateria();
                case 4 -> actualizarMateria();
                case 5 -> eliminarMateria();
                case 0 -> continuar = false;
                default -> System.out.println("Ingrese una opción válida.");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\nGESTIÓN DE MATERIAS");
        System.out.println("1. Listar materias");
        System.out.println("2. Buscar materia por código");
        System.out.println("3. Registrar materia");
        System.out.println("4. Actualizar materia");
        System.out.println("5. Eliminar materia");
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
        scanner.nextLine();
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

    private void mostrarMateria(Materia m) {
        System.out.println(
            "CÓDIGO: " + m.getCodigoMateria() +
            " | MATERIA: " + m.getNombre() +
            " | AÑO: " + m.getAnio() +
            " | CORRELATIVAS: " + obtenerNombresCorrelativas(m)+
            " | ESTADO: " + (m.isActivo() ? "ACTIVO" : "INACTIVO")
        );
    }

    private String obtenerNombresCorrelativas(Materia m) {
        if (m.getCorrelativas() == null || m.getCorrelativas().isEmpty()) {
            return "NO POSEE";
        }

        return m.getCorrelativas().stream()
                .map(Materia::getNombre)
                .reduce((a, b) -> a + ", " + b)
                .orElse("NO POSEE");
    }


    private void listarMaterias() {
        System.out.println("---------- Listado de Materias ----------");
        List<Materia> materias = materiaController.findAll();

        if (materias.isEmpty()) {
            System.out.println("No hay materias registradas.");
            return;
        }

        materias.forEach(this::mostrarMateria);
    }

    private void buscarMateria() {
        System.out.println("---------- Buscar Materia ----------");
        System.out.println("Ingrese el código:");
        String codigo = scanner.nextLine();

        Materia materia = materiaController.findByCode(codigo);

        if (materia == null) {
            System.out.println("Materia no encontrada.");
            return;
        }

        mostrarMateria(materia);
    }

    private void crearMateria() {
        System.out.println("---------- Registrar Materia ----------");

        System.out.print("Código: ");
        String codigo = scanner.nextLine();

        if (materiaController.findByCode(codigo) != null) {
            System.out.println("Ya existe una materia con ese código.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Año: ");
        String anio = scanner.nextLine();

        Materia materia = new Materia(codigo, nombre, anio);

        System.out.println("\n--- Gestión de Correlativas ---");
        gestionarCorrelativas(materia);

        System.out.println("\nVista previa:");
        mostrarMateria(materia);

        if (confirmarAccion("¿Confirmar registro?")) {
            materiaController.crearMateria(materia);
            System.out.println("Materia registrada.");
        }
    }

    private void actualizarMateria() {
        System.out.println("---------- Actualizar Materia ----------");
        System.out.println("Ingrese el código de la materia:");
        String codigo = scanner.nextLine();

        Materia materia = materiaController.findByCode(codigo);

        if (materia == null) {
            System.out.println("Materia no encontrada.");
            return;
        }

        mostrarMateria(materia);

        // Guardar valores originales antes de cualquier modificación
        String nombreOriginal = materia.getNombre();
        String anioOriginal = materia.getAnio();
        List<Materia> correlativasOriginales = materia.getCorrelativas();

        System.out.println("Nuevos datos (presione Enter para dejar igual):");

        System.out.println("Nombre (" + materia.getNombre() + "):");
        String nuevoNombre = scanner.nextLine();
        if (!nuevoNombre.isEmpty()) materia.setNombre(nuevoNombre);

        System.out.println("Año (" + materia.getAnio() + "):");
        String nuevoAnio = scanner.nextLine();
        if (!nuevoAnio.isEmpty()) materia.setAnio(nuevoAnio);

        System.out.println("\n--- Gestión de Correlativas ---");
        gestionarCorrelativas(materia);

        System.out.println("\nVista previa:");
        mostrarMateria(materia);

        if (confirmarAccion("¿Guardar cambios?")) {
            materiaController.editarMateria(materia);
            System.out.println("Materia actualizada.");
        } else {
            // Restaurar valores originales
            materia.setNombre(nombreOriginal);
            materia.setAnio(anioOriginal);
            materia.setCorrelativas(correlativasOriginales); // ajustá el setter si es distinto
            System.out.println("Actualización cancelada.");
        }
    }

    private void eliminarMateria() {
        System.out.println("---------- Eliminar Materia ----------");

        System.out.println("Ingrese el código:");
        String codigo = scanner.nextLine();

        Materia materia = materiaController.findByCode(codigo);

        if (materia == null) {
            System.out.println("Materia no encontrada.");
            return;
        }

        mostrarMateria(materia);

        if (confirmarAccion("\n¿Confirmar eliminación?")) {
            materiaController.eliminarMateria(materia);
            System.out.println("Materia eliminada.");
        }
    }

    // ============================================================
    // GESTIÓN DE CORRELATIVAS
    // ============================================================

    private void gestionarCorrelativas(Materia materiaActual) {
        boolean gestionando = true;

        while (gestionando) {
            System.out.println("\nGestionar Correlativas");
            System.out.println("1. Agregar correlativa");
            System.out.println("2. Quitar correlativa");
            System.out.println("3. Ver correlativas");
            System.out.println("0. Terminar gestión de Correlativas");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> agregarCorrelativa(materiaActual);
                case "2" -> quitarCorrelativa(materiaActual);
                case "3" -> mostrarCorrelativas(materiaActual);
                case "0" -> gestionando = false;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void agregarCorrelativa(Materia materiaActual) {
        List<Materia> disponibles = materiaController.findAll();

        if (disponibles.isEmpty()) {
            System.out.println("No hay materias disponibles.");
            return;
        }

        System.out.println("\nMaterias disponibles:");
        disponibles.forEach(m -> System.out.println("CÓDIGO: "+m.getCodigoMateria() +" | MATERIA: " + m.getNombre()));

        System.out.println("\nCódigo a agregar:");
        String codigo = scanner.nextLine();

        Materia correlativa = materiaController.findByCode(codigo);

        if (correlativa == null) {
            System.out.println("Materia no encontrada.");
            return;
        }

        if (materiaActual == correlativa){
            System.out.println("No se puede agregar esta materia como correlativa.");
        }

        if (materiaActual.getCorrelativas().contains(correlativa)) {
            System.out.println("La materia ya es correlativa.");
            return;
        }

        materiaActual.getCorrelativas().add(correlativa);
        System.out.println("Agregada correctamente.");
    }

    private void quitarCorrelativa(Materia materiaActual) {
        if (materiaActual.getCorrelativas().isEmpty()) {
            System.out.println("No hay correlativas cargadas.");
            return;
        }

        mostrarCorrelativas(materiaActual);

        System.out.println("\nCódigo a quitar:");
        String codigo = scanner.nextLine();

        Materia correlativa = materiaController.findByCode(codigo);

        if (correlativa == null) {
            System.out.println("Materia no encontrada.");
            return;
        }

        if (materiaActual.getCorrelativas().remove(correlativa)) {
            System.out.println("Quitada correctamente.");
        } else {
            System.out.println("Esa materia no era correlativa.");
        }
    }

    private void mostrarCorrelativas(Materia materiaActual) {
        System.out.println("\nCorrelativas:");

        if (materiaActual.getCorrelativas().isEmpty()) {
            System.out.println("No posee.");
            return;
        }

        materiaActual.getCorrelativas().forEach(m -> System.out.println("MATERIA: " + m.getNombre()));
    }
}
