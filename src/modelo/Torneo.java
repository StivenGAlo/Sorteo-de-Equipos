package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Torneo implements Serializable {
    private final List<Equipo> equipos;
    private final List<Etapa> etapas = new ArrayList<>();
    private Equipo campeon;

    public Torneo(List<Equipo> equipos) throws NumeroEquiposInvalidoException {
        validarCantidad(equipos.size());
        this.equipos = new ArrayList<>(equipos);
    }

    public static boolean esPotenciaDeDos(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    private static void validarCantidad(int n) throws NumeroEquiposInvalidoException {
        if (n < 2 || !esPotenciaDeDos(n)) {
            throw new NumeroEquiposInvalidoException(
                "El número de equipos (" + n + ") no es potencia de 2 (16, 8, 4, 2).");
        }
    }

    /** Sortea todas las etapas, desde octavos hasta la final. */
    public void realizarSorteo() throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        etapas.clear();
        campeon = null;
        sortearEtapa(new ArrayList<>(equipos));
    }

    /** Recursivo: sortea una etapa y se llama con los ganadores hasta la final. */
    private void sortearEtapa(List<Equipo> participantes)
            throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        validarCantidad(participantes.size());
        List<Partido> partidos = new ArrayList<>();
        Sorteo.emparejar(new ArrayList<>(participantes), partidos);
        etapas.add(new Etapa(nombreEtapa(participantes.size()), partidos));

        List<Equipo> ganadores = new ArrayList<>();
        for (Partido p : partidos) {
            ganadores.add(Sorteo.definirGanador(p));
        }
        if (ganadores.size() == 1) {
            campeon = ganadores.get(0);
        } else {
            sortearEtapa(ganadores);
        }
    }

    private static String nombreEtapa(int n) {
        return switch (n) {
            case 16 -> "OCTAVOS DE FINAL";
            case 8 -> "CUARTOS DE FINAL";
            case 4 -> "SEMIFINALES";
            case 2 -> "FINAL";
            default -> "RONDA DE " + n + " EQUIPOS";
        };
    }

    public List<Equipo> getEquipos() { return equipos; }
    public List<Etapa> getEtapas() { return etapas; }
    public Equipo getCampeon() { return campeon; }
}
