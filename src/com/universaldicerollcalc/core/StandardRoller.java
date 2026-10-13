package com.universaldicerollcalc.core;

import java.util.*;
import com.universaldicerollcalc.model.*;
import com.universaldicerollcalc.exception.*;

public class StandardRoller implements DiceRoller {
	
	private int diceCount;
	private int dieSides;
	
	public StandardRoller(int diceCount, int dieSides) throws InvalidDiceException {
		if(diceCount <= 0 || dieSides <= 1) {
			throw new InvalidDiceException("Your chosen dice configuration (" + diceCount + "d" + dieSides + ") does not exist in this reality :(");
		}
		this.diceCount = diceCount;
		this.dieSides = dieSides;
	}
	
	@Override
	public String getSystemName() {
		return "Standard (" + diceCount + "d" + dieSides + ")";
	}
	@Override
	public RollResult roll(int modifier) { //Override the roll method which returns a RollResult object
		Random rand = new Random(); //Set a random object
		List<Integer> dice = new ArrayList<>(); //Set an array named dice to hold the dice roll outcomes
		int sum = 0; //Set initial sum of dice roll to 0
		for(int i = 0; i < diceCount; i++) { //Set a for loop that iterates for how many dice there are via diceCount
			int r = rand.nextInt(dieSides) + 1; //Use the random object to generate a number that is based on the number of sides of the dice via dieSides
			dice.add(r); //Add outcome to the array using dice.add(outcome)
			sum += r; //Add outcome to the sum
		}
		int total = sum + modifier; //Add ability modifier to find actual total
		String summary1 = diceCount + "d" + dieSides + " have been rolled!\n"; 
		String summary2 = "With modifier of " + modifier + ", total is " + total + "\n";
		String fullSummary = summary1 + summary2;
		String systemName = "Standard d" + dieSides;
		return new RollResult(systemName, dice, total, fullSummary);
	}
	
	@Override
	public RollResult rollPhysical(List<Integer> physicalDice, int modifier) throws InvalidDiceException { 
		if(physicalDice == null || physicalDice.size() != diceCount) { //Check if number of dice rolled is valid and equal to diceCount
			throw new InvalidDiceException("Expected " + diceCount + " physical die roll(s), but got " + (physicalDice == null ? 0 : physicalDice.size()) + ".");
		}
		int sum = 0;
		for(int val : physicalDice) {
			if (val < 1 || val > dieSides) { //Check for any impossible rolls
				throw new InvalidDiceException("Enterred roll of " + val + " is impossible on a d" + dieSides + "!");
			}
			sum += val;
		}
		int total = sum + modifier;
		String summary1 = "[Physical Roll] " + diceCount + "d" + dieSides + " entered!\n";
		String summary2 = "With modifier of " + modifier + ", total is " + total + "\n";
		String fullSummary = summary1 + summary2;
		return new RollResult(getSystemName(), physicalDice, total, fullSummary);
	}
}
