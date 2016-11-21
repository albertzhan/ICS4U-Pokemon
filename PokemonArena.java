//Pokemon Arena
//Albert Zhan
//main class to run

import java.util.*;
import java.io.*;

public class PokemonArena{
	private static ArrayList<Pokemon> allPokemon = new ArrayList<Pokemon>();


	private static ArrayList<Pokemon> load() throws IOException{
		Scanner pokemonFile = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));
		return allPokemon;
	}
	private static boolean start(){
		//takes care of starting the game,
		return false;
	}
	private static boolean fight(ArrayList<Pokemon> player, ArrayList<Pokemon> enemy){
		return true;
	}
	public static void main(String[] args) {
		//takes care of starting the run, and restarting
		while (true){
			if (!start()){
				break;
			}
		}
	}
}