package tfg;

import java.util.Collections;
import java.util.List;

public class Persona {

	private final int id;
	private final String nombre;
	private final List<Integer> listAfinidad;

	public Persona(int id, String nombre, List<Integer> listAfinidad) {
		this.id = id;
		this.nombre = nombre;
		this.listAfinidad = listAfinidad;
	}

	public double calcularTension(List<Persona> personasMesa) {
		if (personasMesa == null || personasMesa.size() <= 1) {
			return 0.0;
		}

		int tensionConMesa = 0;
		int tensionConGrupo = personasMesa.size() - 1;

		for (Persona persona : personasMesa) {
			if (persona != null && persona.id < listAfinidad.size()) {
				tensionConMesa += listAfinidad.get(persona.id);
			}
		}
		return (double) tensionConMesa / tensionConGrupo;
	}

	@Override // Sobreescribe el toString base de una clase Java para imprimirlo correctamente
	public String toString() {
		return nombre;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public List<Integer> getListAfinidad() {
		return Collections.unmodifiableList(listAfinidad);
	}
}
