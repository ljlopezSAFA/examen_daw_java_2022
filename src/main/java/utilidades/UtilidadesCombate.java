package utilidades;

import modelos.*;

import java.util.*;
import java.util.stream.Collectors;

public class UtilidadesCombate {

private  static  Integer getPuntosEntrenador(Entrenador e){

        int puntos = 0;
        for(Pokemon p : e.getEquipoPokemon()){
            List<TipoPokemon> tipoFavoritos = new ArrayList<>(e.getTiposPreferidos());
            tipoFavoritos.retainAll(p.getTipos());
            puntos+= tipoFavoritos.size();
        }
        return puntos;
    }

    public static Map<Entrenador, Integer>   repartirPokemon(List<Entrenador> entrenadores, List<Pokemon> pokemon){


        Map<Entrenador, Integer>  puntosEntrenadores = new HashMap<>();

        //Comprobar que se cumplen las condiciones
        if(entrenadores.size() % 2 == 0 && pokemon.size()%2 == 0 && pokemon.size() % entrenadores.size() == 0 ){


            //REPARTIR LOS POKEMON

            //pokemon -> 20
            //entrenadores -> 4
            // numPokemonEntrenador = 20/4 = 5
            int numPokemonPorEntrenador = pokemon.size() / entrenadores.size();

            int indiceListaInicial = 0;
            for(Entrenador entrenador : entrenadores) {
                entrenador.setEquipoPokemon(pokemon.subList(indiceListaInicial, indiceListaInicial + numPokemonPorEntrenador));
                indiceListaInicial += numPokemonPorEntrenador;
                puntosEntrenadores.put(entrenador, getPuntosEntrenador(entrenador));
            }
        }

        return puntosEntrenadores;
    }






    public static void subirAlNivel(Pokemon pokemon , Integer nivel){
    }


    /**
     *
     * 1- Que en su linea evolutiva haya un pokemon con un orden superior
     * 2- Que alguno de esos tenga el nivel menor o igual al nivel de mi pokemon
     *
     * @param pokemon
     * @return
     */
    public static boolean puedeEvolucionar1(Pokemon pokemon){

        LineaEvolutiva lineaEvolutivaMiPokemon = null;
        boolean puedeEvolucionar = false;



        //Sacar cual es la la linea evolutiva de mi pokemon
        for(LineaEvolutiva lineaEvolutiva1 : pokemon.getLineaEvolutiva()){
            if(lineaEvolutiva1.getPokemon().equals(pokemon)){
                lineaEvolutivaMiPokemon = lineaEvolutiva1;
            }
        }


        //Ver si hay alguna con orden superior
        for(LineaEvolutiva lineaEvolutiva: pokemon.getLineaEvolutiva()){
            if(lineaEvolutiva.getOrden() > lineaEvolutivaMiPokemon.getOrden() &&
                    (lineaEvolutiva.getNivelParaEvolucionar() <= pokemon.getNivel() &&  !lineaEvolutiva.getPokemon().equals(pokemon)) ){
                puedeEvolucionar = true;
                break;
            }
        }



        return puedeEvolucionar;

    }


    /**
     *
     * 1- Que en su linea evolutiva haya un pokemon con un orden superior
     * 2- Que alguno de esos tenga el nivel menor o igual al nivel de mi pokemon
     *
     * @param pokemon
     * @return
     */
    public static boolean puedeEvolucionar(Pokemon pokemon){

        LineaEvolutiva lineaEvolutivaMiPokemon = pokemon.getLineaEvolutiva()
                .stream()
                .filter(l-> l.getPokemon().equals(pokemon))
                .findFirst().orElse(null);


        return pokemon.getLineaEvolutiva()
                .stream().anyMatch(l -> l.getOrden() > lineaEvolutivaMiPokemon.getOrden() &&
                        l.getNivelParaEvolucionar() <= pokemon.getNivel() &&
                        !l.getPokemon().equals(pokemon));

    }


    /**
     *
     * @param pokemons
     * @param entrenadores
     * @return
     */
    public static Map<Entrenador, Integer> asignarEquipoPorTipos(List<Pokemon> pokemons, List<Entrenador> entrenadores) {
        for (Pokemon pokemon : pokemons) {
            Entrenador favorito = null;

            for (Entrenador entrenador : entrenadores) {
                if (favorito == null) {
                    favorito = entrenador;
                } else {
                    Integer puntosFavorito = getPuntosEntrenador(favorito, pokemon);
                    Integer puntosEntrenador = getPuntosEntrenador(entrenador, pokemon);
                    if (puntosFavorito < puntosEntrenador) {
                        favorito = entrenador;
                    } else if (puntosFavorito.equals(puntosEntrenador)) {
                        favorito = dameElQueMenosTenga(new ArrayList<>(List.of(favorito, entrenador)));
                    }
                }
            }
            favorito.getEquipoPokemon().add(pokemon);
        }

        Map<Entrenador, Integer> mapa = new HashMap<>();

        for (Entrenador e : entrenadores) {
            mapa.put(e, e.getEquipoPokemon().size());
        }


        return mapa;



    }


    private  static  Integer getPuntosEntrenador(Entrenador e, Pokemon pokemon){
        List<TipoPokemon> tipoFavoritos = new ArrayList<>(e.getTiposPreferidos());
        tipoFavoritos.retainAll(pokemon.getTipos());
        return tipoFavoritos.size();
    }



    private  static  Entrenador dameElQueMenosTenga(List<Entrenador> entrenadors){
        entrenadors.sort(Comparator.comparing(e-> e.getEquipoPokemon().size()));
        return entrenadors.get(0);
    }


    public List<Movimiento> movimientosQuePuedeAprender1(Pokemon pokemon, List<Movimiento> movimientos){
        return movimientos
                .stream()
                .filter(movimiento -> pokemon.getTipos().contains(movimiento.getTipo()))
                .collect(Collectors.toList());
    }

    public List<Movimiento> movimientosQuePuedeAprender(Pokemon pokemon, List<Movimiento> movimientos){

        List<Movimiento> movimientosFinales = new ArrayList<>();

        for(Movimiento movimiento: movimientos){
            if(pokemon.getTipos().contains(movimiento.getTipo())){
                movimientosFinales.add(movimiento);
            }
        }
        return movimientosFinales;

    }

    public static Pokemon obtenerEvolucionPosible(Pokemon pokemon){

        if(puedeEvolucionar(pokemon)){

            //Sacamos la linea evolutiva de mi pokemon
            LineaEvolutiva lineaEvolutivaMiPokemno = pokemon.getLineaEvolutiva()
                    .stream()
                    .filter(l-> l.getPokemon().equals(pokemon))
                    .findFirst()
                    .get();

            //Sacar la linea evolutiva de la evolución (la que tenga el orden = orden +1)
            LineaEvolutiva lineaEvolutivaEvolucion = pokemon.getLineaEvolutiva()
                    .stream()
                    .filter(l-> l.getOrden().equals(lineaEvolutivaMiPokemno.getOrden() + 1))
                    .collect(Collectors.toList()).get(0);

            return lineaEvolutivaEvolucion.getPokemon();

        }else{
            return  null;
        }



    }




    public  static Map<Entrenador, Integer> coincidencias(List<Entrenador> entrenadors, List<TipoPokemon> tipos){

        Map<Entrenador, Integer > mapa = new HashMap<>();

        for(Entrenador e: entrenadors){
            List<TipoPokemon> tipoFavoritos = new ArrayList<>(e.getTiposPreferidos());
            tipoFavoritos.retainAll(tipos);
            mapa.put(e,tipoFavoritos.size());
        }

        return mapa;

    }




}
