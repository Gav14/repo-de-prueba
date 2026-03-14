package ar.com.itecn1.view;

import ar.com.itecn1.controller.*;
import java.util.Scanner;

public class InicioView {

    // Códigos de color ANSI (solo los básicos)
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String RED = "\u001B[31m";
    public static final String BOLD = "\u001B[1m";

    private final Scanner scanner = new Scanner(System.in);

    // --- CONTROLADORES ---
    private final AlumnoController alumnoController = new AlumnoController();
    private final ProfesorController profesorController = new ProfesorController();
    private final CarreraController carreraController = new CarreraController();
    private final CuatrimestreController cuatrimestreController = new CuatrimestreController();
    private final ModuloController moduloController = new ModuloController();
    private final ExamenController examenController = new ExamenController();
    private final MateriaController materiaController = new MateriaController();
    private final PlanEstudioController planEstudioController = new PlanEstudioController();
    private final HorarioController horarioController = new HorarioController();
    private final AlumnoInscriptoCarreraController alumnoInscriptoCarreraController = new AlumnoInscriptoCarreraController();
    private final AlumnoInscriptoMateriaController alumnoInscriptoMateriaController = new AlumnoInscriptoMateriaController();
    private final ComisionController comisionController = new ComisionController();

    // --- VISTAS ---
    private final AlumnoView alumnoView = new AlumnoView(alumnoController, scanner);
    private final ProfesorView profesorView = new ProfesorView(profesorController, scanner);
    private final CarreraView carreraView = new CarreraView(carreraController, planEstudioController, scanner);
    private final CuatrimestreView cuatrimestreView = new CuatrimestreView(cuatrimestreController, scanner);
    private final ModuloView moduloView = new ModuloView(moduloController, scanner);
    private final MateriaView materiaView = new MateriaView(materiaController, scanner);
    private final PlanEstudioView planEstudioView = new PlanEstudioView(planEstudioController, materiaController, scanner);
    private final HorarioView horarioView = new HorarioView(horarioController, carreraController, cuatrimestreController, moduloController, scanner);
    private final AlumnoInscriptoCarreraView alumnoInscriptoCarreraView = new AlumnoInscriptoCarreraView(
            alumnoInscriptoCarreraController, alumnoController, carreraController, planEstudioController, scanner
    );
    private final ComisionView comisionView = new ComisionView(
            comisionController, cuatrimestreController, profesorController,
            horarioController, examenController, alumnoInscriptoMateriaController,
            alumnoInscriptoCarreraController, carreraController, scanner
    );

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenuPrincipal();

            if (scanner.hasNextInt()) {
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1: menuPersonas(); break;
                    case 2: menuAcademico(); break;
                    case 3: menuCursada(); break;
                    case 4: menuConfiguracion(); break;
                    case 0:
                        continuar = false;
                        mostrarMensajeSalida();
                        break;
                    default:
                        System.out.println(RED + "Opción no válida" + RESET);
                        pausa();
                }
            } else {
                System.out.println(RED + "Debe ingresar un número" + RESET);
                scanner.next();
            }
        }
    }

    private void mostrarMenuPrincipal() {
        limpiarPantalla();

        System.out.println(BOLD + "╔═══════════════════════════════════════╗" + RESET);
        System.out.println(BOLD + " ║       SISTEMA DE GESTIÓN ACADÉMICA    ║" + RESET);
        System.out.println(BOLD + " ╠═══════════════════════════════════════╣" + RESET);

        System.out.println(" ║ " + BLUE   + "1. Personas" + RESET + "                           ║");
        System.out.println(" ║    Alumnos - Profesores               ║");

        System.out.println(" ║ " + GREEN  + "2. Estructura Académica" + RESET + "               ║");
        System.out.println(" ║    Carreras - Planes - Materias       ║");

        System.out.println(" ║ " + CYAN   + "3. Cursada e Inscripciones" + RESET + "            ║");
        System.out.println(" ║    Comisiones - Inscripciones         ║");

        System.out.println(" ║ " + PURPLE + "4. Configuración" + RESET + "                      ║");
        System.out.println(" ║    Cuatrimestres - Horarios           ║");

        System.out.println(" ║ " + YELLOW + "0. Salir" + RESET + "                              ║");

        System.out.println(BOLD + " ╚═══════════════════════════════════════╝" + RESET);

        System.out.print("\nSeleccione una opción: ");
    }


    private void menuPersonas() {
        while (true) {
            limpiarPantalla();
            System.out.println("\n" + BLUE + BOLD + "PERSONAS" + RESET);
            System.out.println("---------");
            System.out.println(CYAN + "1. Alumnos" + RESET);
            System.out.println(CYAN + "2. Profesores" + RESET);
            System.out.println(YELLOW + "0. Volver" + RESET);
            System.out.print("\nSeleccione: ");

            if (scanner.hasNextInt()) {
                int op = scanner.nextInt();
                scanner.nextLine();
                if (op == 1) alumnoView.iniciar();
                else if (op == 2) profesorView.iniciar();
                else if (op == 0) break;
                else System.out.println(RED + "Opción inválida" + RESET);
            } else {
                System.out.println(RED + "Número inválido" + RESET);
                scanner.next();
            }
        }
    }

    private void menuAcademico() {
        while (true) {
            limpiarPantalla();
            System.out.println("\n" + GREEN + BOLD + "ESTRUCTURA ACADÉMICA" + RESET);
            System.out.println("--------------------");
            System.out.println(CYAN + "1. Carreras" + RESET);
            System.out.println(CYAN + "2. Planes de Estudio" + RESET);
            System.out.println(CYAN + "3. Materias" + RESET);
            System.out.println(YELLOW + "0. Volver" + RESET);
            System.out.print("\nSeleccione: ");

            if (scanner.hasNextInt()) {
                int op = scanner.nextInt();
                scanner.nextLine();
                if (op == 1) carreraView.iniciar();
                else if (op == 2) planEstudioView.iniciar();
                else if (op == 3) materiaView.iniciar();
                else if (op == 0) break;
                else System.out.println(RED + "Opción inválida" + RESET);
            } else {
                System.out.println(RED + "Número inválido" + RESET);
                scanner.next();
            }
        }
    }

    private void menuCursada() {
        while (true) {
            limpiarPantalla();
            System.out.println("\n" + CYAN + BOLD + "CURSADA E INSCRIPCIONES" + RESET);
            System.out.println("------------------------");
            System.out.println(CYAN + "1. Comisiones" + RESET);
            System.out.println(CYAN + "2. Inscripciones a Carreras" + RESET);
            System.out.println(YELLOW + "0. Volver" + RESET);
            System.out.print("\nSeleccione: ");

            if (scanner.hasNextInt()) {
                int op = scanner.nextInt();
                scanner.nextLine();
                if (op == 1) comisionView.iniciar();
                else if (op == 2) alumnoInscriptoCarreraView.iniciar();
                else if (op == 0) break;
                else System.out.println(RED + "Opción inválida" + RESET);
            } else {
                System.out.println(RED + "Número inválido" + RESET);
                scanner.next();
            }
        }
    }

    private void menuConfiguracion() {
        while (true) {
            limpiarPantalla();
            System.out.println("\n" + PURPLE + BOLD + "CONFIGURACIÓN" + RESET);
            System.out.println("-------------");
            System.out.println(CYAN + "1. Cuatrimestres" + RESET);
            System.out.println(CYAN + "2. Horarios" + RESET);
            System.out.println(CYAN + "3. Módulos Horarios" + RESET);
            System.out.println(YELLOW + "0. Volver" + RESET);
            System.out.print("\nSeleccione: ");

            if (scanner.hasNextInt()) {
                int op = scanner.nextInt();
                scanner.nextLine();
                if (op == 1) cuatrimestreView.iniciar();
                else if (op == 2) horarioView.iniciar();
                else if (op == 3) moduloView.iniciar();
                else if (op == 0) break;
                else System.out.println(RED + "Opción inválida" + RESET);
            } else {
                System.out.println(RED + "Número inválido" + RESET);
                scanner.next();
            }
        }
    }

    private void limpiarPantalla() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 30; i++) System.out.println();
        }
    }

    private void mostrarMensajeSalida() {
        limpiarPantalla();
        System.out.println("\n" + GREEN + "¡Hasta pronto!" + RESET);
        System.out.println("Sistema de Gestión Académica");
        System.out.println();
    }

    private void pausa() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}