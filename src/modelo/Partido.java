package modelo;

import java.io.Serializable;

/** Enfrentamiento entre dos equipos dentro de una etapa. */
public class Partido implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Equipo local;
    private final Equipo visitante;
    private Equipo ganador;

    public Partido(Equipo local, Equipo visitante) {
        this.local = local;
        this.visitante = visitante;
    }

    public Equipo getLocal() { return local; }
    public Equipo getVisitante() { return visitante; }
    public Equipo getGanador() { return ganador; }
    public void setGanador(Equipo ganador) { this.ganador = ganador; }

    /** Indica si este partido enfrenta a los dos equipos dados (en cualquier orden). */
    public boolean enfrenta(Equipo a, Equipo b) {
        return (local.equals(a) && visitante.equals(b)) || (local.equals(b) && visitante.equals(a));
    }

    @Override
    public String toString() {
        return local + " vs " + visitante;
    }
}
