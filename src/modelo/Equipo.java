package modelo;

import java.io.Serializable;
import java.util.Objects;

/** Representa un equipo de la liga. */
public class Equipo implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String nombre;

    public Equipo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    @Override
    public boolean equals(Object o) {
        return o instanceof Equipo e && e.nombre.equalsIgnoreCase(nombre);
    }

    @Override
    public int hashCode() { return Objects.hash(nombre.toLowerCase()); }

    @Override
    public String toString() { return nombre; }
}
