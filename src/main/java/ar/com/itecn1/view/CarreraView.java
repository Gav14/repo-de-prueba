package ar.com.itecn1.view;

import ar.com.itecn1.controller.CarreraController;
import ar.com.itecn1.controller.PlanEstudioController;
import ar.com.itecn1.model.Carrera;
import ar.com.itecn1.model.PlanEstudio;
import ar.com.itecn1.model.Turno;

import java.util.List;
import java.util.Scanner;

public class CarreraView {

    private final CarreraController carreraController;
    private final PlanEstudioController planEstudioController;
    private final Scanner scanner;

    public CarreraView(CarreraController carreraController, PlanEstudioController planEstudioController, Scanner scanner) {
        this.carreraController = carreraController;
        this.planEstudioController = planEstudioController;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            try {
                mostrarMenu();

                if (!scanner.hasNextInt()) {
                    System.out.println("Debe ingresar un número.");
                    scanner.next();
                    continue;
                }

                int opcion = scanner.nextInt();
                pausa();

                switch (opcion) {
                    case 1 -> listarCarreras();
                    case 2 -> buscarCarrera();
                    case 3 -> registrarCarrera();
                    case 4 -> modificarCarrera();
                    case 5 -> eliminarCarrera();
                    case 6 -> reactivarCarrera();
                    case 0 -> continuar = false;
                    default -> System.out.println("Opción no válida. Por favor ingrese un número del 0 al 6.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("❌ Error inesperado: " + e.getMessage());
            }
        }
    }

    // ----------------------------------------------------
    // MÉTODOS AUXILIARES
    // ----------------------------------------------------

    private void mostrarMenu() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║      GESTIÓN DE CARRERAS       ║");
        System.out.println("╠════════════════════════════════╣");
        System.out.println("║ 1. Listar todas las carreras   ║");
        System.out.println("║ 2. Buscar carrera              ║");
        System.out.println("║ 3. Registrar nueva carrera     ║");
        System.out.println("║ 4. Modificar datos de una      ║");
        System.out.println("║    carrera                     ║");
        System.out.println("║ 5. Eliminar carrera            ║");
        System.out.println("║    (dar de baja)               ║");
        System.out.println("║ 6. Reactivar carrera           ║");
        System.out.println("║ 0. Volver al menú anterior     ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
    }

    private void pausa() {
        scanner.nextLine();
    }

    private int confirmarAccion() {
        int opcion = 0;

        while (opcion != 1 && opcion != 2) {
            System.out.println("\n┌─────────────────────────┐");
            System.out.println("│     CONFIRMAR ACCIÓN     │");
            System.out.println("├─────────────────────────┤");
            System.out.println("│ 1. Sí, confirmar         │");
            System.out.println("│ 2. No, cancelar          │");
            System.out.println("└─────────────────────────┘");
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

    private void mostrarCarrera(Carrera carrera) {
        String planInfo = (carrera.getPlanEstudio() != null) ?
                carrera.getPlanEstudio().getNombre() : "NO POSEE";

        String estado = carrera.isActivo() ? "ACTIVA" : "INACTIVA";

        System.out.printf("│ • %-35s │ %-8s │ %-15s │ %-8s │%n",
                carrera.getNombre(),
                carrera.getTurno(),
                planInfo.length() > 15 ? planInfo.substring(0, 12) + "..." : planInfo,
                estado
        );
    }

    private void mostrarPlan(PlanEstudio planEstudio) {
        String materiasInfo = (planEstudio.getMaterias() != null && !planEstudio.getMaterias().isEmpty()) ?
                planEstudio.getMaterias().stream().map(m -> m.getNombre()).reduce((a, b) -> a + ", " + b).orElse("NO POSEE") :
                "NO POSEE";

        System.out.println(
                "NOMBRE: " + planEstudio.getNombre() +
                        " | TÍTULO: " + planEstudio.getTitulo() +
                        " | MATERIAS: " + materiasInfo +
                        " | ESTADO: " + (planEstudio.isActivo() ? "ACTIVO" : "INACTIVO")
        );
    }

    private void listarCarreras() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                         LISTADO DE CARRERAS                                  │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────────┤");

        List<Carrera> carreras = carreraController.findAll();

        if (carreras.isEmpty()) {
            System.out.println("│                No hay carreras registradas.                                │");
        } else {
            System.out.println("│ CARRERA                          │ TURNO   │ PLAN DE ESTUDIO  │ ESTADO   │");
            System.out.println("├──────────────────────────────────┼─────────┼──────────────────┼──────────┤");
            carreras.forEach(this::mostrarCarrera);
        }

        System.out.println("└─────────────────────────────────────────────────────────────────────────────┘");
    }

    private void buscarCarrera() {
        System.out.println("\n🔍 BUSCAR CARRERA");
        System.out.print("Ingrese el nombre o parte del nombre: ");
        String texto = scanner.nextLine();

        try {
            List<Carrera> resultados = carreraController.buscarCarreras(texto);

            if (resultados.isEmpty()) {
                System.out.println("❌ No se encontraron carreras que coincidan con: \"" + texto + "\"");
            } else {
                System.out.println("\n✅ Se encontraron " + resultados.size() + " carrera(s):");
                System.out.println("┌─────────────────────────────────────────────────────────────────────────────┐");
                System.out.println("│ CARRERA                          │ TURNO   │ PLAN DE ESTUDIO  │ ESTADO   │");
                System.out.println("├──────────────────────────────────┼─────────┼──────────────────┼──────────┤");
                resultados.forEach(this::mostrarCarrera);
                System.out.println("└─────────────────────────────────────────────────────────────────────────────┘");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void registrarCarrera() {
        System.out.println("\n📝 REGISTRAR NUEVA CARRERA");
        System.out.print("Nombre de la carrera: ");
        String nombre = scanner.nextLine();

        if (nombre.trim().isEmpty()) {
            System.out.println("❌ El nombre no puede estar vacío.");
            return;
        }

        try {
            // Verificar si ya existe una carrera con ese nombre exacto
            Carrera existente = carreraController.findByName(nombre);

            if (existente != null) {
                if (existente.isActivo()) {
                    System.out.println("❌ Ya existe una carrera ACTIVA con ese nombre.");
                    return;
                } else {
                    System.out.println("⚠️  Ya existe una carrera INACTIVA con ese nombre.");
                    System.out.println("¿Desea reactivarla en lugar de crear una nueva?");
                    if (confirmarAccion() == 1) {
                        carreraController.reactivarCarrera(nombre);
                        System.out.println("✅ Carrera reactivada exitosamente!");
                    }
                    return;
                }
            }

            // Si hay carreras similares pero no exactas, mostrar advertencia
            List<Carrera> similares = carreraController.buscarCarreras(nombre);
            if (!similares.isEmpty()) {
                System.out.println("\n⚠️  Carreras con nombres similares encontradas:");
                similares.forEach(this::mostrarCarrera);
                System.out.println("\n¿Desea continuar con el registro de \"" + nombre + "\"?");
                if (confirmarAccion() != 1) {
                    System.out.println("Registro cancelado.");
                    return;
                }
            }

            Turno turno = seleccionarTurno();
            Carrera carrera = new Carrera(nombre, turno);

            System.out.println("\n📚 Plan de estudio (opcional)");
            System.out.print("¿Desea asignar un plan de estudio? (S/N): ");
            String respuesta = scanner.nextLine();

            if (respuesta.equalsIgnoreCase("S")) {
                gestionarPlanEstudio(carrera);
            }

            System.out.println("\n📋 Vista previa de la carrera a registrar:");
            mostrarCarrera(carrera);

            System.out.println("\n¿Confirma el registro de esta carrera?");
            if (confirmarAccion() == 1) {
                carreraController.createCarrera(carrera);
                System.out.println("✅ ¡Carrera registrada exitosamente!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void modificarCarrera() {
        System.out.println("\n✏️  MODIFICAR CARRERA");
        System.out.print("Ingrese el nombre o parte del nombre de la carrera: ");
        String texto = scanner.nextLine();

        try {
            List<Carrera> resultados = carreraController.buscarCarreras(texto);

            // Filtrar solo carreras activas para modificar
            List<Carrera> activas = resultados.stream().filter(Carrera::isActivo).toList();

            if (activas.isEmpty()) {
                System.out.println("❌ No se encontraron carreras ACTIVAS que coincidan con: \"" + texto + "\"");
                if (!resultados.isEmpty()) {
                    System.out.println("(Las carreras encontradas están INACTIVAS. Use la opción 6 para reactivarlas)");
                }
                return;
            }

            Carrera carreraSeleccionada = (activas.size() == 1) ?
                    activas.get(0) : seleccionarCarreraDeLista(activas, "modificar");

            if (carreraSeleccionada == null) return;

            System.out.println("\n🔄 Modificando carrera: " + carreraSeleccionada.getNombre());

            // Guardar valores originales
            Turno turnoOriginal = carreraSeleccionada.getTurno();
            PlanEstudio planEstudioOriginal = carreraSeleccionada.getPlanEstudio();

            // Modificar turno
            System.out.println("\n⏰ Turno actual: " + carreraSeleccionada.getTurno());
            System.out.print("¿Desea cambiar el turno? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                Turno nuevoTurno = seleccionarTurno();
                carreraSeleccionada.setTurno(nuevoTurno);
            }

            // Modificar plan de estudio
            System.out.println("\n📚 Plan de estudio actual: " +
                    (carreraSeleccionada.getPlanEstudio() != null ?
                            carreraSeleccionada.getPlanEstudio().getNombre() : "NO POSEE"));
            System.out.print("¿Desea modificar el plan de estudio? (S/N): ");

            if (scanner.nextLine().equalsIgnoreCase("S")) {
                gestionarPlanEstudio(carreraSeleccionada);
            }

            // Mostrar vista previa
            System.out.println("\n📋 Vista previa de los cambios:");
            mostrarCarrera(carreraSeleccionada);

            System.out.println("\n¿Confirma los cambios?");
            if (confirmarAccion() == 1) {
                carreraController.updateCarrera(carreraSeleccionada);
                System.out.println("✅ ¡Carrera modificada exitosamente!");
            } else {
                // Restaurar valores originales
                carreraSeleccionada.setTurno(turnoOriginal);
                carreraSeleccionada.setPlanEstudio(planEstudioOriginal);
                System.out.println("Modificación cancelada.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void eliminarCarrera() {
        System.out.println("\n🗑️  ELIMINAR CARRERA (DAR DE BAJA)");
        System.out.print("Ingrese el nombre o parte del nombre de la carrera: ");
        String texto = scanner.nextLine();

        try {
            List<Carrera> resultados = carreraController.buscarCarreras(texto);
            List<Carrera> activas = resultados.stream().filter(Carrera::isActivo).toList();

            if (activas.isEmpty()) {
                System.out.println("❌ No se encontraron carreras ACTIVAS para eliminar.");
                if (!resultados.isEmpty()) {
                    System.out.println("(Las carreras encontradas ya están INACTIVAS)");
                }
                return;
            }

            Carrera carreraSeleccionada = (activas.size() == 1) ?
                    activas.get(0) : seleccionarCarreraDeLista(activas, "eliminar");

            if (carreraSeleccionada == null) return;

            System.out.println("\n⚠️  ADVERTENCIA: Esta acción dará de baja la carrera:");
            mostrarCarrera(carreraSeleccionada);
            System.out.println("\n¿Está seguro que desea ELIMINAR (dar de baja) esta carrera?");

            if (confirmarAccion() == 1) {
                carreraController.deleteCarrera(carreraSeleccionada);
                System.out.println("✅ Carrera dada de baja correctamente (estado: INACTIVA)");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void reactivarCarrera() {
        System.out.println("\n🔄 REACTIVAR CARRERA");
        System.out.print("Ingrese el nombre o parte del nombre de la carrera: ");
        String texto = scanner.nextLine();

        try {
            List<Carrera> resultados = carreraController.buscarCarreras(texto);
            List<Carrera> inactivas = resultados.stream().filter(c -> !c.isActivo()).toList();

            if (inactivas.isEmpty()) {
                System.out.println("❌ No se encontraron carreras INACTIVAS para reactivar.");
                if (!resultados.isEmpty()) {
                    System.out.println("(Las carreras encontradas ya están ACTIVAS)");
                }
                return;
            }

            Carrera carreraSeleccionada = (inactivas.size() == 1) ?
                    inactivas.get(0) : seleccionarCarreraDeLista(inactivas, "reactivar");

            if (carreraSeleccionada == null) return;

            System.out.println("\n🔄 Carrera seleccionada para reactivar:");
            mostrarCarrera(carreraSeleccionada);

            System.out.println("\n¿Confirma la reactivación de esta carrera?");
            if (confirmarAccion() == 1) {
                carreraController.reactivarCarrera(carreraSeleccionada.getNombre());
                System.out.println("✅ ¡Carrera reactivada exitosamente!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // Método auxiliar para seleccionar carrera de una lista
    private Carrera seleccionarCarreraDeLista(List<Carrera> carreras, String accion) {
        System.out.println("\n📋 Se encontraron las siguientes carreras:");
        for (int i = 0; i < carreras.size(); i++) {
            System.out.print((i + 1) + ". ");
            mostrarCarrera(carreras.get(i));
        }

        System.out.print("\nSeleccione el número de la carrera que desea " + accion + " (0 para cancelar): ");

        int seleccion = -1;
        if (scanner.hasNextInt()) {
            seleccion = scanner.nextInt();
        }
        pausa();

        if (seleccion == 0) {
            System.out.println("Operación cancelada.");
            return null;
        }

        if (seleccion < 1 || seleccion > carreras.size()) {
            System.out.println("❌ Opción no válida.");
            return null;
        }

        return carreras.get(seleccion - 1);
    }

    private Turno seleccionarTurno() {
        int opcion = 0;

        System.out.println("\n⏰ Seleccione turno:");
        System.out.println("1. MAÑANA");
        System.out.println("2. TARDE");
        System.out.println("3. NOCHE");

        while (opcion < 1 || opcion > 3) {
            System.out.print("Opción: ");
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
            } else {
                scanner.next();
            }
        }
        pausa();

        return switch (opcion) {
            case 1 -> Turno.MANANA;
            case 2 -> Turno.TARDE;
            case 3 -> Turno.NOCHE;
            default -> Turno.MANANA;
        };
    }

    // ----------------------------------------------------
    // GESTIÓN PLAN DE ESTUDIO
    // ----------------------------------------------------
    private void gestionarPlanEstudio(Carrera carrera) {
        boolean gestionando = true;

        while (gestionando) {
            System.out.println("\n┌─────────────────────────┐");
            System.out.println("│   GESTIÓN PLAN DE ESTUDIO │");
            System.out.println("├─────────────────────────┤");
            System.out.println("│ 1. Asignar plan         │");
            System.out.println("│ 2. Quitar plan          │");
            System.out.println("│ 3. Ver plan actual      │");
            System.out.println("│ 4. Terminar             │");
            System.out.println("└─────────────────────────┘");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> asignarPlanEstudio(carrera);
                case "2" -> quitarPlanEstudio(carrera);
                case "3" -> {
                    if (carrera.getPlanEstudio() == null) {
                        System.out.println("📌 La carrera no tiene plan asignado.");
                    } else {
                        System.out.println("\n📚 Plan actual:");
                        mostrarPlan(carrera.getPlanEstudio());
                    }
                }
                case "4" -> gestionando = false;
                default -> System.out.println("❌ Opción no válida.");
            }
        }
    }

    private void asignarPlanEstudio(Carrera carrera) {
        try {
            List<PlanEstudio> planes = planEstudioController.findAll();
            List<PlanEstudio> activos = planes.stream().filter(PlanEstudio::isActivo).toList();

            if (activos.isEmpty()) {
                System.out.println("❌ No hay planes de estudio activos disponibles.");
                return;
            }

            System.out.println("\n📚 Planes de estudio activos disponibles:");
            for (int i = 0; i < activos.size(); i++) {
                System.out.print((i + 1) + ". ");
                System.out.println(activos.get(i).getNombre() + " - " + activos.get(i).getTitulo());
            }

            System.out.print("\nSeleccione el número del plan (0 para cancelar): ");

            int seleccion = -1;
            if (scanner.hasNextInt()) {
                seleccion = scanner.nextInt();
            }
            pausa();

            if (seleccion == 0) return;

            if (seleccion < 1 || seleccion > activos.size()) {
                System.out.println("❌ Opción no válida.");
                return;
            }

            carrera.setPlanEstudio(activos.get(seleccion - 1));
            System.out.println("✅ Plan asignado correctamente.");

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void quitarPlanEstudio(Carrera carrera) {
        if (carrera.getPlanEstudio() == null) {
            System.out.println("❌ La carrera no tiene plan de estudio asignado.");
            return;
        }

        System.out.println("\n📚 Plan actual: " + carrera.getPlanEstudio().getNombre());
        System.out.print("¿Está seguro que desea quitar el plan de estudio? (S/N): ");

        if (scanner.nextLine().equalsIgnoreCase("S")) {
            carrera.setPlanEstudio(null);
            System.out.println("✅ Plan de estudio eliminado.");
        }
    }
}