//PokemonTools
//tools that will help me make my arena
import java.util.*;
import java.io.*;
public class PokemonTools{
	private static int boxsize = 114;
	private static String border = new String(new char[boxsize]).replace('\0', '=');
	private static String errorMessage = "Oops, please enter a valid number!";
	//static class with static methods to be used in pokemon arena
	public static int takeInt(int lower, int higher){
		//lower higher are inclusive, i.e. 1,3 -> 1,2,3 are all valid
		//my version to take int input, to make sure it doesn't crash each time
		Scanner kb = new Scanner(System.in);
		String toInt = kb.nextLine().trim();
		try{
			int myout = Integer.parseInt(toInt);
			if (lower <= myout && myout <= higher) {
				return myout;
			}
			System.out.println(errorMessage);
			return takeInt(lower,higher);
		}catch(NumberFormatException e){
			displayText(errorMessage);
			return takeInt(lower,higher);//recursively calls until something valid is given
		}
	}
	public static void displayPokemonStats(Pokemon pokeToSee){
		String [] pokeStats = pokeToSee.getStats().split("\\s+");
		//MAKE BARS FOR HP AND ENERGY
		String toDisplay = String.format("HP: %s   ~~~   Energy: %s",pokeStats[0],pokeStats[1]);
		displayText(String.format("%-12s   ~~~   ",pokeToSee.getName()) + toDisplay);
	}
	public static void displayPokemonStats(int counter, Pokemon pokeToSee){
		System.out.print(counter + ". ");
		displayPokemonStats(pokeToSee);
	}
	public static void displayPokemon(ArrayList<Pokemon> pokeToSee){
		String toDisplay = "";
		toDisplay += border + "\n";
		for (int i = 1; i < pokeToSee.size()+1; i++) {
			Pokemon currPoke = pokeToSee.get(i-1);
			toDisplay = toDisplay + String.format("%2d. %-12s   ~~~   HP: %-3d   ~~~   Type: %-9s   ~~~   Weakness: %-9s   ~~~   Resistance: %-9s",i,currPoke.getName(),currPoke.getHp(),currPoke.getType(),currPoke.getWeakness(),currPoke.getResistance())+"\n";
		}
		toDisplay+= border;
		displayText(toDisplay);
	}
	public static void displayBattle(Pokemon myPoke, Pokemon opPoke){
		System.out.println(border);
		displayPokemonStats(myPoke);
		displayCenteredText("vs");
		displayPokemonStats(opPoke);
		System.out.println(border);
	}
	public static void displayBorderedUpText(String toDisplay){
		System.out.println(border);//displays the top border
		if (!toDisplay.equals("NoTextToDisplay")) {
			//allows me to pass in something so that it doesn't give a new line
			displayText(toDisplay);
		}
	}
	public static void displayBorderedDownText(String toDisplay){
		if (!toDisplay.equals("NoTextToDisplay")) {
			//allows me to pass in something so that it doesn't give a new line
			displayText(toDisplay);		
		}
		System.out.println(border);//displaying the bottom border
	}
	public static void displayAttacks(Pokemon attacker){
		ArrayList<Pokemon.Attack> attacks = attacker.getMoves();
		for (int i = 0; i < attacks.size(); ++i) {
			System.out.printf("%d. %s\n",i+1,attacks.get(i).getName());
		}
	}
	public static void displayText(String toDisplay){
		//a println statement that diffrentiates test printing and actual game printing
		System.out.println(toDisplay);
	}
	public static void displayCenteredText(String toDisplay){
		//the width is fixed 114
		System.out.println(toDisplay);
	}
}


