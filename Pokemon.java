//Pokemon
//class to create my Pokemon
import java.util.*;
import java.io.*;
public class Pokemon{//class to create pokemon objects
	private String name = "";
	private String visual = "";
	private int oenergy = 50;
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
		resistance = pokemonStats[3];
		weakness = pokemonStats[4];
		nummoves = Integer.parseInt(pokemonStats[5]);
		for (int i = 0; i < nummoves; ++i) {//change all the typings so that they have capitals
			int spot = 6+4*i;
			Attack newAttack = new Attack(pokemonStats[spot],Integer.parseInt(pokemonStats[spot+1]),Integer.parseInt(pokemonStats[spot+2]),pokemonStats[spot+3]);
			attacks.add(newAttack);
		}
		try{//gets the visual of the pokemon, contained in the folder pokepack
			Scanner pokemonFile = new Scanner(new BufferedReader(new FileReader("pokepack/"+name+".txt")));
			pokemonFile.useDelimiter("\\Z");  
			visual = pokemonFile.next(); 
			pokemonFile.close();
		}catch(IOException e){
			visual = "NO DISPLAY AVAILABLE\n\n\n\n\n\n";
		}
	}
	public boolean canUseMove(int movenum){
		if (energy < attacks.get(movenum).cost) {
			return false;
		}
		return true;
	}
	public String struggle(Pokemon defender){//uses struggle on the defender
		System.out.println("I STRUGGLED!!!!!!!!!");
		return "";
	}
	public String attack(Pokemon defender,int movenum){//prechecked, it can attack
		//returns the attack description to be printed out
		Attack move = attacks.get(movenum);//move that is being used
		String attackDescription = String.format("%s attacks %s using %s",name,defender.getName(),move.getName());
		if (move.cost > energy) {
			return attackDescription + String.format(" but %s didn't have enough energy to attack!",name);
		}
		double multiplier = 1;
		if (type.equals(defender.resistance)){
			multiplier = multiplier/2;
			attackDescription += "\nAND IT WAS NOT VERY EFFECTIVE...";
		}
		if (type.equals(defender.weakness)) {
			multiplier = multiplier*2;
			attackDescription += "\nAND IT WAS SUPER EFFECTIVE...";
		}//check status
		if (stunned){
			return attackDescription + String.format(" but %s was stunned...",name);
		}
		energy -= move.cost;//subtracts energy cost
		int baseattack = move.damage - (disabled? 10:0);
		if (baseattack < 0) {
			baseattack = 0;
		}
		//calculating effects
		if (move.effects[0]) {
			move.stun(defender);
			attackDescription = attackDescription + String.format("\n%s got stunned!",defender,defender.getName());
		}if (move.effects[1]) {
			int timeshit = move.wildCard();
			if (timeshit == 0) {
				attackDescription += String.format("\n%s missed! ",name);
			}else{
				attackDescription += String.format("\n%s hit! ",name);
			}
			baseattack *= timeshit;
		}if (move.effects[2]) {
			int timeshit = move.wildStorm();
			baseattack *= timeshit;
			if (timeshit == 0) {
				attackDescription = attackDescription + String.format("\n%s missed!",name);
			}else if (timeshit == 1){
				attackDescription = attackDescription + String.format("\n%s hit 1 time!",name);
			}else{
			attackDescription = attackDescription + String.format("\n%s hit %d times ",name,timeshit);				
			}
		}if (move.effects[3]) {
			move.disable(defender);
			attackDescription = attackDescription + String.format("\n%s got disabled! %s now does 10 less damage.",defender,defender.getName());
		}if (move.effects[4]) {
			recharge(20);
			attackDescription = attackDescription + String.format("\n%s recovered 20 energy!",name);
		}
		float damage = Math.round(baseattack*multiplier);
		int rdamage = (int)damage;//java hates me and possible loss of double to int conversion
		defender.hp -= rdamage;
		return attackDescription + String.format("\n%s dealt ",name) + rdamage + " damage";
	}	
	public String getName(){
		return name;
	}
	public String getVisual(){//returns the ASCII'd pokemon
		return visual;
	}
	public String toString(){
		return name;
	}
	public int getHp(){
		return hp;
	}
	public int getPercentHp(){
		return Math.round(100*hp/(ohp));
	}
	public void setHp(int amount){
		hp = 0;
	}
	public String getType(){
		return type;
	}
	public String getWeakness(){
		return weakness;
	}
	public String getResistance(){
		return resistance;
	}
	public ArrayList<Attack> getMoves(){
		return attacks;
	}
	public int getNumMoves(){
		return nummoves;
	}
	public int getEnergy(){
		return energy;
	}
	public int getPercentEnergy(){
		return Math.round(100*energy/oenergy);
	}
	public String getStats(){//returns string of stats and 2 booleans of stunned disabled
		return ""+hp + " " + energy + " " + (stunned? 1:0) +" "+ (disabled? 1:0);
	}
	public void recharge(int amount){//allows pokemon to heal energy
		energy = energy+amount;
		if (energy > oenergy){
			energy = oenergy;
		}else{
		}
	}
	public void heal(int amount){//allows pokemon to heal
		hp = hp + amount;
		if (hp > ohp) {
			hp = ohp;
		}else{
		}
	}
	public void resetEnergy(){//let's the energy all get reset to 50
		energy = 50;
	}
	public void unstun(){//allows them to unstun
		stunned = false;
	}
	public void undisable(){//let's pokemon not get disabled
		disabled = false;
	}
	class Attack{//subclass of Pokemon, since only pokemon will attack
		String name = "";
		int cost = 0;
		int damage = 0;
		//stun, wild card, wild storm, disable, recharge
		boolean [] effects = new boolean[5];

		public Attack(String name, int cost, int damage, String effect){
			this.name = name;
			this.cost = cost;
			this.damage = damage;
			if (effect.equals("stun")) {//just in case newer moves can contain multiple effects
				//or more effects, it's easier to change.
				this.effects[0] = true;
			}else if (effect.contains("wild card")) {
				this.effects[1] = true;
			}else if (effect.contains("wild storm")){
				this.effects[2] = true;
			}else if (effect.contains("disable")) {
				this.effects[3] = true;
			}else if (effect.contains("recharge")){
				this.effects[4] = true;
			}
		}
		public String getName(){
			return name;
		}
		public void stun(Pokemon defender){
			defender.stunned = true;
		}
		public int wildCard(){//50%chance to hit, returning the multiplier
			Random rand = new Random();
			return rand.nextInt(2);
		}
		public int wildStorm(){//recursion of 50% chance to hit, returning the multiplier
			Random rand = new Random();
			int myopt = rand.nextInt(2);
			return myopt == 1? myopt + wildStorm():0;
		}
		public void disable(Pokemon defender){
			defender.disabled = true;
		}
	}
}