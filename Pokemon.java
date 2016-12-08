//Pokemon
//class to create my Pokemon
import java.util.*;
public class Pokemon{
	private String name = "";
	private int ohp = 0;
	private int hp = 0;
	private int energy = 50;
	private String type = " ";
	private String weakness = " ";
	private String resistance = " ";
	private boolean stunned = false;
	private boolean disabled = false;
	private int nummoves = 0;
	ArrayList<Attack> attacks = new ArrayList<Attack>();
	public Pokemon(String pokemonString){
		String[] pokemonStats = pokemonString.split(",");
		name = pokemonStats[0];
		hp = Integer.parseInt(pokemonStats[1]);
		ohp += hp;
		for (int i = 2; i < 5; ++i) {
			if (!pokemonStats[i].equals(" ")){
				pokemonStats[i] = (char)(pokemonStats[i].charAt(0)-32) + pokemonStats[i].substring(1);
			}
		}
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
	public int getHp(){
		return hp;
	}
	public String getType(){
		//CHANGE FIRST ONE TO CAPITALS USING STRING SPLICING AND CHAR + 66
		return type;
	}
	public String getWeakness(){
		return weakness;
	}
	public String getResistance(){
		return resistance;
	}
	public String getStats(){
		return ""+hp + " " + energy + " " + (stunned? 1:0) +" "+ (disabled? 1:0);
	}
	public void attack(Pokemon otherPoke, int atknum){
		Attack currAttack = attacks.get(atknum);
	}
	public void heal(int amount){
		hp = (hp+amount)%ohp;
	}
}