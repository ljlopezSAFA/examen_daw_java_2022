package utilidades;

import modelos.Pokemon;
import modelos.TipoPokemon;

import java.util.*;
import java.util.stream.Collectors;

public class UtilidadesPokemon {
    public UtilidadesPokemon() {
    }

    public static List<Pokemon> obtenerPokemonConTipos1(List<Pokemon> pokemons, List<TipoPokemon> tipos) {

        List<Pokemon> solucion = new ArrayList<>();
        for(Pokemon p: pokemons){
            List<TipoPokemon> tiposPokemon = new ArrayList<>(p.getTipos());
            //RETAINALL
            if(tiposPokemon.retainAll(tipos)){
                solucion.add(p);
            }
        }
        return solucion;

    }

    public static List<Pokemon> obtenerPokemonConTipos(List<Pokemon> pokemons, List<TipoPokemon> tipos) {

        return pokemons
                .stream()
                .filter(p-> p.getTipos().retainAll(tipos))
                .collect(Collectors.toList());
    }


    public static Map<TipoPokemon, List<Pokemon>> obtenerPokemonPurosPorTipo(List<Pokemon> pokemons) {

        Map<TipoPokemon, List<Pokemon>> mapa = new HashMap<>();
        for(Pokemon p: pokemons){
            if (p.getTipos().size() == 1) {
                if(mapa.containsKey(p.getTipos().get(0))){
                    mapa.get(p.getTipos().get(0)).add(p);
                }else{
                    mapa.put(p.getTipos().get(0), new ArrayList<>(List.of(p)));
                }
            }
        }
        return mapa;

    }

    public static Map<TipoPokemon, List<Pokemon>> obtenerPokemonPurosPorTipo1(List<Pokemon> pokemons) {

        return pokemons
                .stream()
                .filter(p->p.getTipos().size() == 1)
                .collect(Collectors.groupingBy(p-> p.getTipos().get(0)));

    }

}
