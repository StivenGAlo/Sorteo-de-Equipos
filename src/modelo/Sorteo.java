package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Sorteo {
    private static final Random RNG = new Random();

    public static void emparejar(List<Equipo> disponibles, List<Partido> partidos)
            throws SorteoInvalidoException {
        if (disponibles.isEmpty()) {
            return; // caso base
        }
        if (disponibles.size() % 2 != 0) {
            throw new SorteoInvalidoException(
                "Cantidad impar de equipos sin emparejar: " + disponibles.size());
        }
        Equipo local = disponibles.get(0);
        List<Equipo> candidatos = new ArrayList<>(disponibles.subList(1, disponibles.size()));
        Collections.shuffle(candidatos, RNG);

        for (Equipo rival : candidatos) {
            // Un equipo no puede ser competidor y oponente a la vez, ni repetir partido
            if (rival.equals(local) || yaJugaron(partidos, local, rival)) {
                continue;
            }
            Partido p = new Partido(local, rival);
            partidos.add(p);
            List<Equipo> resto = new ArrayList<>(disponibles);
            resto.remove(local);
            resto.remove(rival);
            try {
                emparejar(resto, partidos); // llamada recursiva
                return;
            } catch (SorteoInvalidoException e) {
                partidos.remove(p); // retroceso: probar otro oponente
            }
        }
        throw new SorteoInvalidoException("No existe oponente válido para el equipo " + local);
    }

    private static boolean yaJugaron(List<Partido> partidos, Equipo a, Equipo b) {
        for (Partido p : partidos) {
            if (p.enfrenta(a, b)) return true;
        }
        return false;
    }

    /** Simula el ganador de un partido al azar para avanzar de etapa. */
    public static Equipo definirGanador(Partido p) {
        Equipo g = RNG.nextBoolean() ? p.getLocal() : p.getVisitante();
        p.setGanador(g);
        return g;
    }
}
