package vista;

import java.util.Scanner;
import modelo.Etapa;
import modelo.Partido;
import modelo.Torneo;

public class VistaConsola {
    private final Scanner sc = new Scanner(System.in);

    public int mostrarMenu() {
        System.out.println("\n===== LIGA PROFESIONAL - SORTEO =====");
        System.out.println("1. Realizar nuevo sorteo");
        System.out.println("2. Mostrar sorteo actual");
        System.out.println("3. Guardar torneo (torneo.ser)");
        System.out.println("4. Cargar torneo (torneo.ser)");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void mostrarTorneo(Torneo t) {
        if (t == null || t.getEtapas().isEmpty()) {
            mostrarMensaje("Aún no hay un sorteo realizado.");
            return;
        }
        for (Etapa e : t.getEtapas()) {
            System.out.println("\n--- " + e.getNombre() + " ---");
            int i = 1;
            for (Partido p : e.getPartidos()) {
                System.out.println(i++ + ". " + p + "   (avanza: " + p.getGanador() + ")");
            }
        }
        System.out.println("\n*** CAMPEÓN: " + t.getCampeon() + " ***");
    }

    public void mostrarMensaje(String m) { System.out.println(m); }

    public void mostrarError(String m) { System.err.println("[ERROR] " + m); }
}
