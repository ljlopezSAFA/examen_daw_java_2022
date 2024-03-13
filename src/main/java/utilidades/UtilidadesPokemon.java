package utilidades;

import modelos.Pokemon;
import modelos.TipoPokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UtilidadesPokemon {
 public UtilidadesPokemon() {
    }

    public static List<Pokemon> obtenerPokemonConTipos(List<Pokemon> pokemons, List<TipoPokemon> tipos){

        List<Pokemon> coincedentesTipos = new ArrayList<>();

        for(Pokemon pokemon : pokemons){
            List<TipoPokemon> tipoPokemons = new ArrayList<>(pokemon.getTipos());
            tipoPokemons.retainAll(tipos);

            if(tipoPokemons.size() >0){
                coincedentesTipos.add(pokemon);
            }
        }

        return coincedentesTipos;

    }

    public static List<Pokemon> obtenerPokemonConTipos1(List<Pokemon> pokemons, List<TipoPokemon> tipos){

        return pokemons
                .stream()
                .filter(p-> p.getTipos().retainAll(tipos))
                .collect(Collectors.toList());
    }



    public static Map<TipoPokemon, List<Pokemon>> obtenerPokemonPurosPorTipo(List<Pokemon> pokemons){

        Map<TipoPokemon, List<Pokemon>> mapa = new HashMap<>();

        for(Pokemon pokemon : pokemons){

            //Comprobar que el tipo es unico
            if(pokemon.getTipos().size() == 1 ){
                TipoPokemon tipo = pokemon.getTipos().get(0);

                //Comprobar si el tipo está como clave en el mapa
                if(mapa.containsKey(tipo)){
                    mapa.get(tipo).add(pokemon);
                }else{
                    mapa.put(tipo, new ArrayList<>(List.of(pokemon)));
                }
            }
        }

        return mapa;

    }

    public static Map<TipoPokemon, List<Pokemon>> obtenerPokemonPurosPorTipo1(List<Pokemon> pokemons){

        return pokemons
                .stream()
                .filter(p-> p.getTipos().size() ==1)
                .collect(Collectors.groupingBy(p-> p.getTipos().get(0)));

    }

}
