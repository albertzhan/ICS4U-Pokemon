//PokemonTools
//tools that will help me make my arena
import java.util.*;
import java.io.*;
public class PokemonTools{
	private static String errorMessage = "Oops, please enter a valid number!";
	//static class with static methods to be used in pokemon arena

	public static int takeInt(int lower, int higher){
		//lower higher are inclusive, i.e. 1,3 -> 1,2,3 are all valid
		//my version to take in input, to make sure it doesn't crash each time
		Scanner kb = new Scanner(System.in);
		String toInt = kb.nextLine();
		try{
			int myout = Integer.parseInt(toInt);
			if (lower <= myout && myout <= higher) {
				return myout;
			}
			System.out.println(errorMessage);
			return takeInt(lower,higher);
		}catch(NumberFormatException e){
			System.out.println(errorMessage);
			return takeInt(lower,higher);
		}
	}

	public static void displayPokemon(ArrayList<Pokemon> pokeToSee){
		for (int i = 1; i < pokeToSee.size()+1; ++i) {
			System.out.printf(i+". %s\n",pokeToSee.get(i-1).getName());
		}
	}
}

class Attack{
	String name = "";
	int cost = 0;
	int damage = 0;
	String effect = "";
	public Attack(String name, int cost, int damage, String effect){
		this.name = name;
		this.cost = cost;
		this.damage = damage;
		this.effect = effect;
	}
}

class AttackEffect{
	int hi = 0;
	public AttackEffect(int hi){
		this.hi = hi;
	}
}

