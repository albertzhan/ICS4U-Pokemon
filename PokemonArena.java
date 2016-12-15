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

	private static void load(String inFile) throws IOException{
		Scanner pokemonFile = new Scanner(new BufferedReader(new FileReader(inFile)));
		int numPokemon = Integer.parseInt(pokemonFile.nextLine());
		for (int i = 0; i < numPokemon; ++i){
			Pokemon tmp = new Pokemon(pokemonFile.nextLine()+" ,");
			allPokemon.add(tmp);
		}
		pokemonFile.close();
	}
	private static void recharge(){
		myPokemon.get(0).unstun(); //unstuns at the end of the turn
		for (Pokemon alive : myPokemon) {
			alive.recharge(10);
		}
		for (Pokemon alive : opPokemon) {
			alive.recharge(10);
		}
	}
	private static void heal(){
		for (Pokemon alive : myPokemon) {
			alive.heal(20);
		}
	}
	private static void attack(Pokemon attacker,Pokemon defender){
		PokemonTools.displayBorderedUpText("Choose an Attack");
		PokemonTools.displayAttacks(attacker);
		PokemonTools.displayBorderedDownText(String.format("%d. Back",attacker.getNumMoves()+1));
		int myopt = PokemonTools.takeInt(1,attacker.getNumMoves()+1);
		if (myopt == attacker.getNumMoves()+1) {
			pickAction();
		}else{
			if(attacker.canUseMove(myopt-1)){
				System.out.println("Attacked");
				System.out.println(attacker.attack(defender,myopt-1));
			}else{
				System.out.println("NOT ENOUGH ENERGY");
				attack(attacker,defender); //allows them to reselect a move
			}
		}
	}
	private static void retreat(){
		PokemonTools.displayBorderedUpText("Which Pokemon would you like to swap out to?");
		for (int i = 1; i < myPokemon.size(); ++i) {
			PokemonTools.displayPokemonStats(i,myPokemon.get(i));
		}
		for (int i = 0; i < myGraveyard.size(); ++i) {
			System.out.print("-. ");
			PokemonTools.displayPokemonStats(myGraveyard.get(i));
		}
		PokemonTools.displayText(String.format("%d. Back",myPokemon.size()));
		PokemonTools.displayBorderedDownText("NoTextToDisplay");
		int myopt = PokemonTools.takeInt(1,myPokemon.size());
		if (myopt == myPokemon.size()) {
			pickAction();
		}else{//pop both out; 0 and selected. put 0 into end, and selected into 0
			Pokemon selected = myPokemon.get(myopt);
			PokemonTools.displayText(String.format("%s, I Choose You!",selected.getName()));
			Pokemon swapout = myPokemon.get(0);
			myPokemon.remove(myopt);
			myPokemon.remove(0);
			myPokemon.add(0,selected);
			myPokemon.add(myopt,swapout);
		}
	}
	private static void pass(){//does nothing
		return;
	}
	private static void enemyTurn(){
		//create list of priority attacks to use
		//go through and check if it can attack with the energy
		//else struggle
		//create arraylist of moves index, then shuffle
		// WHY DON'T I HAVE THIS PACKAGE int[] enemyOptions = IntStream.range(0, opPokemon.get(0).getNumMoves()).toArray();
		ArrayList<Integer> enemyOptions = new ArrayList<Integer>();
		for (int i = 0; i < opPokemon.get(0).getNumMoves(); i++) {
			enemyOptions.add(i);
		}
		Collections.shuffle(enemyOptions);
		for (Integer move : enemyOptions) {
			//check if can use
			if (opPokemon.get(0).canUseMove(move)) {
				//uses it and returns to stop method
				System.out.println(opPokemon.get(0).attack(myPokemon.get(0),move));
				return;
			}
		}
		//use struggle when no moves can be used
		opPokemon.get(0).struggle(myPokemon.get(0));
	}
	private static int battle(int first){//order of battle depends on first.
		PokemonTools.displayBattle(myPokemon.get(0),opPokemon.get(0));
		if(first == 1){
			enemyTurn();
			checkPlayerTurn();
		}
		if(pickAction()){
			return 0; //they want to quit
		}
		if(checkEnemyTurn()){
			return 1; //switch pokemon for enemy
		}
		if(first == 0){
			enemyTurn();
			checkPlayerTurn(); //nothing is done since the opPokemon doesn't switch
		}
		recharge();
		return 2; //nothing happens
	}
	private static boolean checkPlayerTurn(){
		if (myPokemon.get(0).getHp() <= 0) {
			Pokemon dead = myPokemon.get(0);
			myPokemon.remove(0);
			System.out.println("YOU DIED");
			dead.setHp(0);
			myGraveyard.add(dead);
			return true;
		}
		return false;
	}
	private static boolean checkEnemyTurn(){
		if (opPokemon.get(0).getHp() <= 0) {
			opPokemon.remove(0);
			System.out.println("THAT GUY FAINTED");
			return true;
		}
		return false;
	}
	private static void reset(){
		for (Pokemon alive : myPokemon) {
			alive.resetEnergy();
			alive.unstun();
			alive.undisable();
		}
	}
	private static void swapPokemon(int myopt){
		if (myopt == 0) {
			PokemonTools.displayText(String.format("%s, I Choose You!",myPokemon.get(myopt).getName()));
			return;
		}
		Pokemon selected = myPokemon.get(myopt);
		PokemonTools.displayText(String.format("%s, I Choose You!",selected.getName()));
		Pokemon swapout = myPokemon.get(0);
		myPokemon.remove(myopt);
		myPokemon.remove(0);
		myPokemon.add(0,selected);
		myPokemon.add(myopt,swapout);
	}
	private static void selectStarter(){
		System.out.println("choose who you want to start the battle with");
		PokemonTools.displayPokemon(myPokemon);
		int myopt = PokemonTools.takeInt(1,myPokemon.size())-1;
		swapPokemon(myopt);
	}
	private static boolean round(int battlenum){
		//determines if the opponent starts first in the round
		// Random rand = new Random();
		// PokemonTools.displayCenteredText(String.format("Battle %d",battlenum));
		// selectStarter();
		// if (rand.nextInt(2) == 1) {
		// 	PokemonTools.displayBattle(opPokemon.get(0),myPokemon.get(0));
		// 	enemyTurn();
		// 	if(checkPlayerTurn()){
		// 		return false;
		// 	}
		// }
		// while (true){
		// 	PokemonTools.displayBattle(myPokemon.get(0),opPokemon.get(0));
		// 	if(pickAction()){
		// 		return true;
		// 	}
		// 	if(checkEnemyTurn()){
		// 		break;
		// 	}
		// 	enemyTurn();
		// 	checkPlayerTurn();
		// 	recharge();
		// }
		// return false;
		Random rand = new Random();
		PokemonTools.displayCenteredText(String.format("Battle %d",battlenum));
		selectStarter();
		int first = rand.nextInt(2); //if first is 0 then player goes first, cpu if 1
		while (true){
			int result = battle(first);
			if(result == 0){
				return true; //quits
			}else if(result == 1){
				break;
			}
		}
		return false;
	}
	private static boolean pickAction(){
		PokemonTools.displayBorderedDownText(String.format("Pick an option\n1. Attack\n2. Retreat\n3. Pass\n4. Quit"));
		int myopt = PokemonTools.takeInt(1,4);
		if (myopt == 1){
			attack(myPokemon.get(0),opPokemon.get(0));
		}else if (myopt == 2){
			retreat();
		}else if(myopt == 3){
			pass();
		}else{
			return true;
		}
		return false;
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
		Random rand = new Random();
		while (allPokemon.size() > 0){
			int nextpoke = rand.nextInt(allPokemon.size());
			opPokemon.add(allPokemon.get(nextpoke));
			allPokemon.remove(nextpoke);
		}
	}

	public static void main(String[] args) throws IOException{
		//stages -> load pokemon, pick the pokemon
		//then enters fight stages, while loop
		load("pokemon.txt");
		pickPokemon();
		PokemonTools.loadTexts();
		int c = 1;
		while (myPokemon.size()!= 0 && opPokemon.size()!=0){
			if(round(c++)){
				break;
			} //takes care of the entire battle
			reset(); //heals all pokemon energy for 20 at the end of each battle
		}
		if (myPokemon.size() == 0) {
			System.out.println("YOU LOST");
		}else if (opPokemon.size() == 0) {
			System.out.println("YOU WON");
		}else{
			System.out.println("You fleed the battle...");
		}
	}
}