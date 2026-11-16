package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Equipo;
import modelo.GestorArchivo;
import modelo.NumeroEquiposInvalidoException;
import modelo.SorteoInvalidoException;
import modelo.Torneo;
import vista.VistaConsola;

public class ControladorTorneo {
    private static final String[] NOMBRES = {
        "Barcelona SC", "Liga de Quito", "Emelec", "Independiente del Valle",
        "El Nacional", "Aucas", "Delfín", "Universidad Católica",
        "Macará", "Técnico Universitario", "Orense", "Mushuc Runa",
        "Deportivo Cuenca", "Libertad", "Imbabura", "Guayaquil City"
    };

    private final VistaConsola vista;
    private Torneo torneo;

    public ControladorTorneo(VistaConsola vista) {
        this.vista = vista;
    }

    public void iniciar() {
        int op;
        do {
            op = vista.mostrarMenu();
            switch (op) {
                case 1 -> nuevoSorteo();
                case 2 -> vista.mostrarTorneo(torneo);
                case 3 -> guardar();
                case 4 -> cargar();
                case 0 -> vista.mostrarMensaje("Hasta luego.");
                default -> vista.mostrarError("Opción inválida.");
            }
        } while (op != 0);
    }

    private void nuevoSorteo() {
        try {
            List<Equipo> equipos = new ArrayList<>();
            for (String n : NOMBRES) equipos.add(new Equipo(n));
            torneo = new Torneo(equipos);
            torneo.realizarSorteo();
            vista.mostrarTorneo(torneo);
        } catch (NumeroEquiposInvalidoException | SorteoInvalidoException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    private void guardar() {
        if (torneo == null) {
            vista.mostrarError("No hay torneo para guardar.");
            return;
        }
        try {
            GestorArchivo.guardar(torneo, GestorArchivo.ARCHIVO);
            vista.mostrarMensaje("Torneo guardado en " + GestorArchivo.ARCHIVO);
        } catch (IOException e) {
            vista.mostrarError("No se pudo guardar: " + e.getMessage());
        }
    }

    private void cargar() {
        try {
            torneo = GestorArchivo.cargar(GestorArchivo.ARCHIVO);
            vista.mostrarMensaje("Torneo cargado correctamente.");
            vista.mostrarTorneo(torneo);
        } catch (IOException e) {
            vista.mostrarError("No se pudo leer el archivo: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            vista.mostrarError("Archivo con formato incompatible: " + e.getMessage());
        }
    }
}
