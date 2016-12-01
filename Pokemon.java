//Pokemon
//class to create my Pokemon
import java.util.*;
public class Pokemon{
	private String name = "";
	private int hp = 0;
	private int energy = 50;
	private String type = "";
	private String weakness = "";
	private String resistance = "";
	private boolean stunned = false;
	private boolean disabled = false;
	private int nummoves = 0;
	ArrayList<Attack> attacks = new ArrayList<Attack>();
	public Pokemon(String pokemonString){
		String[] pokemonStats = pokemonString.split(",");
		name = pokemonStats[0];
		hp = Integer.parseInt(pokemonStats[1]);
		type = pokemonStats[2];
		weakness = pokemonStats[3];
		resistance = pokemonStats[4];
		nummoves = Integer.parseInt(pokemonStats[5]);
		for (int i = 0; i < nummoves; ++i) {
			int spot = 6+4*i;
			Attack newAttack = new Attack(pokemonStats[spot],Integer.parseInt(pokemonStats[spot+1]),Integer.parseInt(pokemonStats[spot+2]),pokemonStats[spot+3]);
			attacks.add(newAttack);
		}
	}
	public String getName(){
		return name;
	}
	public void attack(Pokemon otherPoke, int atknum){
		Attack currAttack = attacks.get(atknum);
	}
}