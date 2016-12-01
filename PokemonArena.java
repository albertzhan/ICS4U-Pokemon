//Pokemon Arena
//Albert Zhan
//main class to run

import java.util.*;
import java.io.*;
public class PokemonArena{

	private static ArrayList<Pokemon> allPokemon = new ArrayList<Pokemon>();
	private static ArrayList<Pokemon> myPokemon = new ArrayList<Pokemon>();
	private static ArrayList<Pokemon> opPokemon = new ArrayList<Pokemon>();

	private static ArrayList<Pokemon> load() throws IOException{
		Scanner pokemonFile = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));
		int numPokemon = Integer.parseInt(pokemonFile.nextLine());
		for (int i = 0; i < numPokemon; ++i){
			Pokemon tmp = new Pokemon(pokemonFile.nextLine()+" ,");
			allPokemon.add(tmp);
		}
		return allPokemon;
	}
	private static void attack(){
		int e;
	}
	private static void retreat(){
		int e;
	}
	private static void pass(){
		int e;
	}
	private static boolean battle(ArrayList<Pokemon> player, ArrayList<Pokemon> enemy){
		return true;
	}
	private static void startRound(){
		pickOption();
	}
	private static void pickOption(){
		int myopt = PokemonTools.takeInt(1,3);
		System.out.printf("Pick an option\n1. Attack\n2. Retreat\n3. Pass");
		if (myopt == 0){
			attack();
		}else if (myopt == 1){
			retreat();
		}else{
			pass();
		}
	}
	private static void pickPokemon(){
		System.out.println("HELLO! Welcome to Pokemon Arena! Choose 4 pokemon");
		for (int i = 0; i < 4; ++i) {
			PokemonTools.displayPokemon(allPokemon);
			System.out.printf("Please select a Pokemon to add to you team! %d/4 \n",i+1);
			int poke = PokemonTools.takeInt(1,allPokemon.size())-1;
			myPokemon.add(allPokemon.get(poke));
			allPokemon.remove(poke);
		}
		for (Pokemon other : allPokemon) {
			opPokemon.add(other);
		}
	}

	public static void main(String[] args) throws IOException{
		//stages -> load pokemon, pick the pokemon
		//then enters fight stages, while loop
		//once fight ends, boolean win or lose determines what to print
		load();
		pickPokemon();
		while (myPokemon.size()!= 0 && opPokemon.size()!=0){
			startRound(); //takes care of the entire turn
		}
	}
}