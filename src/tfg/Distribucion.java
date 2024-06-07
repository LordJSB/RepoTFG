package tfg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que se encarga de distribuir una lista de personas en varios grupos y 
 * generar informes en formato XML.
 */
public class Distribucion {

    /**
     * Distribuye una lista de personas en un numero especificado de grupos con un 
     * limite maximo de personas por grupo.
     * 
     * @param listaTotal Lista de personas a distribuir.
     * @param nGrupos Numero de grupos en los que se debe distribuir a las personas.
     * @param nLimite Limite maximo de personas por grupo.
     * @return La disposicion grupal con el menor rango de medias.
     */
    public static DisposicionGrupal distribuir(List<Persona> listaTotal, int nGrupos, int nLimite) {
        List<DisposicionGrupal> resultado = new ArrayList<>();
        DisposicionGrupal combiActual = new DisposicionGrupal();
        for (int i = 0; i < nGrupos; i++) {
            combiActual.addGrupo(new Grupo());
        }

        generaCombinaciones(listaTotal, 0, combiActual, resultado, nLimite);
        System.out.println("Todas las combinaciones posibles de " + nGrupos + " subgrupos con un maximo de "
                + nLimite + " numeros por subgrupo:");

        List<DisposicionGrupal> combiFiltrada = filtraCombinaciones(resultado, 0, 1); // Filtra las combinaciones con una media total entre 0 y 1.
        if (combiFiltrada.isEmpty()) {
            combiFiltrada = filtraCombinaciones(resultado, 1, 2); // Si no hay combinaciones en el rango anterior, filtra con una media total entre 1 y 2.
        }
        if (combiFiltrada.isEmpty()) {
            combiFiltrada = filtraCombinaciones(resultado, 2, 3); // Si no hay combinaciones en el rango anterior, filtra con una media total entre 2 y 3.
        }
        if (combiFiltrada.isEmpty()) {
            combiFiltrada = filtraCombinaciones(resultado, 3, 4); // Si no hay combinaciones en el rango anterior, filtra con una media total entre 3 y 4.
        }

        // Itera sobre todas las combinaciones generadas en 'resultado' y las imprime. PUEDES COMENTAR, ESTO ES SOLO PARA VER SI LO HACE TODO
        for (DisposicionGrupal combi : resultado) {
            System.out.print("[");
            for (Grupo group : combi.getListaGrupos()) {
                System.out.print("[");
                for (Persona p : group.getListaPersonas()) {
                    System.out.print(p + "-" + p.calcularTension(group.getListaPersonas()) + "|");
                }
                System.out.print("]");
            }
            System.out.print("]\n");
            System.out.println("Medias de los subgrupos: " + combi.getMedias());
            System.out.println("Media de las medias: " + combi.calculaMediaTotal());
            System.out.println("Rango de las medias: " + combi.calculaRango());
        }
        if (!combiFiltrada.isEmpty()) { // Si se encontraron combinaciones filtradas, obtiene y muestra la combinación con el menor rango de medias.
            DisposicionGrupal combiMinRango = getCombiMinRango(combiFiltrada);
            System.out.println("Combinación con el menor rango de medias: " + combiMinRango);
        } else {
            System.out.println("No se encontraron combinaciones filtradas."); // Si no, mensaje de error
        }
        return getCombiMinRango(combiFiltrada); // Devuelve la combinacion con el menor rango de medias.
    }

    /**
     * Genera todas las combinaciones posibles de grupos a partir de una lista de 
     * personas y las agrega al resultado.
     * 
     * @param tamanho Lista de personas a distribuir.
     * @param siAparece Indice de la persona actual en la lista.
     * @param combiActual Combinacion actual de grupos.
     * @param resultadoCombi Lista de combinaciones resultantes.
     * @param nLimite Limite maximo de personas por grupo.
     */
    private static void generaCombinaciones(List<Persona> tamanho, int siAparece, DisposicionGrupal combiActual,
                                            List<DisposicionGrupal> resultadoCombi, int nLimite) {
        if (siAparece == tamanho.size()) {  // De normal se ve
            boolean todasUnaVez = true;
            for (Persona persona : tamanho) { // Mira si cada persona aparece solo 1 vez
                int apariciones = 0;
                for (Grupo grupo : combiActual.getListaGrupos()) {
                    if (grupo.getListaPersonas().contains(persona)) {
                        apariciones++;
                    }
                }
                if (apariciones != 1) { // Si una persona aparece mas o menos de una vez, no vale.
                    todasUnaVez = false;
                    break;
                }
            }
            if (todasUnaVez) { // Si todas las personas aparecen solo una vez, combinacion valida.
                DisposicionGrupal combi = new DisposicionGrupal();
                for (Grupo g : combiActual.getListaGrupos()) {
                    combi.addGrupo(g);
                }
                resultadoCombi.add(combi);
            }
            return;
        }

        Persona currentNumber = tamanho.get(siAparece);
        for (int i = 0; i < combiActual.getListaGrupos().size(); i++) { // Intentar agregar la persona actual a cada uno de los grupos disponibles.
            if (combiActual.getGrupo(i).getListaPersonas().size() < nLimite) { // Solo agregar si el grupo no ha alcanzado el limite maximo de personas
                combiActual.getGrupo(i).addPersona(currentNumber);
                generaCombinaciones(tamanho, siAparece + 1, combiActual, resultadoCombi, nLimite); // Siguiente persona en la lista, iterativo
                combiActual.getGrupo(i).getListaPersonas().remove(combiActual.getGrupo(i).getListaPersonas().size() - 1); // Eliminar la persona agregada despues de la recursion para probar otra combinacion.
            }
        }
    }

    /**
     * Filtra las combinaciones de grupos según un rango de medias especificado.
     * 
     * @param combinations Lista de combinaciones a filtrar.
     * @param minRange Rango mínimo de la media total.
     * @param maxRange Rango máximo de la media total.
     * @return Lista de combinaciones filtradas que cumplen con el rango especificado.
     */
    private static List<DisposicionGrupal> filtraCombinaciones(List<DisposicionGrupal> combinations, double minRange, double maxRange) {
        List<DisposicionGrupal> combiFiltrada = new ArrayList<>();
        for (DisposicionGrupal combi : combinations) {
            double mediaTotal = combi.calculaMediaTotal();
            if (mediaTotal >= minRange && mediaTotal < maxRange) {
                combiFiltrada.add(combi);
            }
        }
        return combiFiltrada;
    }

    /**
     * Obtiene la combinación de grupos con el menor rango de medias.
     * 
     * @param combinaciones Lista de combinaciones a evaluar.
     * @return La combinación de grupos con el menor rango de medias.
     */
    private static DisposicionGrupal getCombiMinRango(List<DisposicionGrupal> combinaciones) {
        DisposicionGrupal combiMinRango = null;
        double minRange = Double.MAX_VALUE;
        for (DisposicionGrupal comb : combinaciones) {
            double range = comb.calculaRango();
            if (range < minRange) {
                minRange = range;
                combiMinRango = comb;
            }
        }
        return combiMinRango;
    }

    /**
     * Crea un archivo XML con la disposición de los grupos y el informe especificado.
     * 
     * @param grupos Lista de grupos a incluir en el informe.
     * @param informe Informe que contiene información adicional del autor y participantes.
     */
    public static void crearArchivoXML(List<Grupo> grupos, Informe informe) {
        try {
            String nombreArchivo = "informe " + informe.getNombre() + ".xml";
            File file = new File(nombreArchivo);
            FileWriter writer = new FileWriter(file);

            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<informe>\n");
            writer.write("  <autor>" + informe.getNombre() + "</autor>\n");
            writer.write("  <participantes>" + informe.getValor() + "</participantes>\n");
            writer.write("  <grupos>\n");

            for (Grupo grupo : grupos) {
                writer.write("    <grupo>\n");
                for (Persona persona : grupo.getListaPersonas()) {
                    writer.write("      <participante>\n");
                    writer.write("        <nombre>" + persona.getNombre() + "</nombre>\n");
                    writer.write("      </participante>\n");
                }
                writer.write("    </grupo>\n");
            }

            writer.write("  </grupos>\n");
            writer.write("</informe>");

            writer.close();
            System.out.println("El archivo XML se ha creado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al crear el archivo XML: " + e.getMessage());
        }
    }
}
