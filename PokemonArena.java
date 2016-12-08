//Pokemon Arena
//Albert Zhan
//main class to run

import java.util.*;
import java.io.*;
public class PokemonArena{

	private static ArrayList<Pokemon> allPokemon = new ArrayList<Pokemon>();
	//the pokemon fighting will be the first item in each arraylist.
	private static ArrayList<Pokemon> myPokemon = new ArrayList<Pokemon>();
	private static ArrayList<Pokemon> opPokemon = new ArrayList<Pokemon>();
	private static ArrayList<Pokemon> myGraveyard = new ArrayList<Pokemon>();

	private static ArrayList<Pokemon> load(String inFile) throws IOException{
		Scanner pokemonFile = new Scanner(new BufferedReader(new FileReader(inFile)));
		int numPokemon = Integer.parseInt(pokemonFile.nextLine());
		for (int i = 0; i < numPokemon; ++i){
			Pokemon tmp = new Pokemon(pokemonFile.nextLine()+" ,");
			allPokemon.add(tmp);
		}
		return allPokemon;
	}
	private static void heal(){
		for (Pokemon alive : myPokemon) {
			alive.heal(20);
		}
	}
	private static void attack(){
		int e;
	}
	private static void retreat(){
		PokemonTools.displayBorderedUpText("Which Pokemon would you like to swap out to?");
		for (int i = 1; i < myPokemon.size(); ++i) {
			PokemonTools.displayPokemonStats(i,myPokemon.get(i));
		}
		for (int i = 0; i < myGraveyard.size(); ++i) {
			PokemonTools.displayPokemonStats(myGraveyard.get(i));
		}
		PokemonTools.displayText(String.format("%d. Back",myPokemon.size()));
		PokemonTools.displayBorderedDownText("NoTextToDisplay");
		int myopt = PokemonTools.takeInt(1,myPokemon.size());
		if (myopt == myPokemon.size()) {
			pickAction();
		}else{//pop both out; 0 and selected. put 0 into end, and selected into 0
			Pokemon selected = myPokemon.get(myopt);
			Pokemon swapout = myPokemon.get(0);
			myPokemon.remove(myopt);
			myPokemon.remove(0);
			myPokemon.add(0,selected);
			myPokemon.add(myopt,swapout);
		}
	}
	private static void pass(){//does nothing
		int e;
	}
	private static void enemyTurn(){
		int e;
	}
	private static boolean battle(ArrayList<Pokemon> player, ArrayList<Pokemon> enemy){
		return true;
	}
	private static void checkPlayerTurn(){
		int e;
	}
	private static boolean checkEnemyTurn(){
		return false;
	}
	private static void round(){
		//determines if the opponent starts first in the round
		Random rand = new Random();
		if (rand.nextInt(1) == 1) {
			PokemonTools.displayBattle(opPokemon.get(0),myPokemon.get(0));
			enemyTurn();
			checkPlayerTurn();
		}
		while (true){
			PokemonTools.displayBattle(myPokemon.get(0),opPokemon.get(0));
			pickAction();
			if(checkEnemyTurn()){
				break;
			}
			enemyTurn();
			checkPlayerTurn();		
		}
	}
	private static void pickAction(){
		PokemonTools.displayBorderedDownText(String.format("Pick an option\n1. Attack\n2. Retreat\n3. Pass"));
		int myopt = PokemonTools.takeInt(1,3);
		if (myopt == 1){
			attack();
		}else if (myopt == 2){
			retreat();
		}else{
			pass();
		}
	}
	private static void pickPokemon(){
		PokemonTools.displayText("HELLO! Welcome to Pokemon Arena! Choose 4 pokemon");
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
		load("pokemon.txt");
		pickPokemon();
		myPokemon.remove(0);
		Pokemon test = new Pokemon("Diglett,30,earth,leaf,electric,2,Dig,10,10, ,Mud Slap,20,30, ");
		myGraveyard.add(test);
		while (myPokemon.size()!= 0 && opPokemon.size()!=0){
			round(); //takes care of the entire turn
			heal(); //heals all pokemon for 20
		}
	}
}