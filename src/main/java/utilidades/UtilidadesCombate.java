package utilidades;

import modelos.*;

import java.util.*;
import java.util.stream.Collectors;

public class UtilidadesCombate {

//    private  static  Integer getPuntosEntrenador(Entrenador e){
//
//        int puntos = 0;
//        return puntos;
//    }



    public static Map<Entrenador, Integer>  repartirPokemon(List<Entrenador> entrenadores, List<Pokemon> pokemon){

        Map<Entrenador, Integer>  puntosEntrenadores = new HashMap<>();

        //pokemon = 8
        //entrenadores = 4




        if(pokemon.size() % entrenadores.size() == 0){

            int cuantosPorEntrenador = pokemon.size() / entrenadores.size();


            for(Entrenador e: entrenadores){
                e.getEquipoPokemon().addAll(pokemon.subList(0,cuantosPorEntrenador));
                pokemon.removeAll(pokemon.subList(0,cuantosPorEntrenador));
//                while(e.getEquipoPokemon().size() != 2){
//                    e.getEquipoPokemon().add(pokemon.get(0));
//                    pokemon.remove(pokemon.get(0));
//                }
            }




            for(Entrenador e:entrenadores){

                List<TipoPokemon> favoritos = new ArrayList<>(e.getTiposPreferidos());

                int puntos = 0;

                for(Pokemon p: e.getEquipoPokemon()){

                    List<TipoPokemon> tiposQueCoinciden = new ArrayList<>(p.getTipos());
                    tiposQueCoinciden.retainAll(favoritos);


                    puntos += tiposQueCoinciden.size();

                }

                puntosEntrenadores.put(e, puntos);

            }


        }


        return puntosEntrenadores;
    }





    public static void subirAlNivel(Pokemon pokemon , Integer nivel){

        pokemon.setNivel(nivel);

        pokemon.getStats().setAt(pokemon.getStats().getAt() + 2 * nivel);
        pokemon.getStats().setDf(pokemon.getStats().getDf() + 2 * nivel);
        pokemon.getStats().setPs(pokemon.getStats().getPs() + 2 * nivel);
        pokemon.getStats().setSpa(pokemon.getStats().getSpa() + 2 * nivel);
        pokemon.getStats().setSpdf(pokemon.getStats().getSpdf() + 2 * nivel);
        pokemon.getStats().setSpd(pokemon.getStats().getSpd() + 2 * nivel);
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

        Integer orden = pokemon.getLineaEvolutiva()
                .stream()
                .filter(l-> l.getPokemon().equals(pokemon))
                .findFirst()
                .get()
                .getOrden() ;


        LineaEvolutiva lineaEvolutivaSiguiente = pokemon.getLineaEvolutiva()
                .stream().filter(l-> l.getOrden() == orden+1).findFirst().orElse(null);


        return lineaEvolutivaSiguiente!=null && pokemon.getNivel() >= lineaEvolutivaSiguiente.getNivelParaEvolucionar();

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

        Integer orden = 0;

        for(LineaEvolutiva l : pokemon.getLineaEvolutiva()){
            if(l.getPokemon().equals(pokemon)){
                orden = l.getOrden();
            }
        }

//        boolean hayAlgunaLineaEvolutivaConOrdenSuperior = false;
//
//
//        List<LineaEvolutiva> lineas = new ArrayList<>(pokemon.getLineaEvolutiva());

        LineaEvolutiva siguienteLinea = null;
        //SIGUIENTE
        for(LineaEvolutiva l: pokemon.getLineaEvolutiva()){
            if(l.getOrden() == orden+1){
                siguienteLinea = l;
                break;
            }
        }


        return siguienteLinea!= null && pokemon.getNivel() >= siguienteLinea.getNivelParaEvolucionar();
    }








    /**
     *
     * @param pokemons
     * @param entrenadores
     * @return
     */
    public static Map<Entrenador, Integer> asignarEquipoPorTipos(List<Pokemon> pokemons, List<Entrenador> entrenadores) {
     
        Map<Entrenador, Integer> mapa = new HashMap<>();

        return mapa;



    }


    private  static  Integer getPuntosEntrenador(Entrenador e, Pokemon pokemon){
      
 
        return 0;
    }



    private  static  Entrenador dameElQueMenosTenga(List<Entrenador> entrenadors){
        return null;
    }


    public List<Movimiento> movimientosQuePuedeAprender1(Pokemon pokemon, List<Movimiento> movimientos){
            return new ArrayList<>();
    }

    public List<Movimiento> movimientosQuePuedeAprender(Pokemon pokemon, List<Movimiento> movimientos){

        List<Movimiento> movimientosFinales = new ArrayList<>();

        return movimientosFinales;

    }

    public static Pokemon obtenerEvolucionPosible(Pokemon pokemon){

            return  null;

    }




    public  static Map<Entrenador, Integer> coincidencias(List<Entrenador> entrenadors, List<TipoPokemon> tipos){

        Map<Entrenador, Integer > mapa = new HashMap<>();
      

        return mapa;

    }




}
