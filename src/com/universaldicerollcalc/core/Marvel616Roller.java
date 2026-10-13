package com.universaldicerollcalc.core;
import com.universaldicerollcalc.exception.*;
import com.universaldicerollcalc.model.*;
import java.util.*;
public class Marvel616Roller implements DiceRoller {
	
	@Override
	public RollResult roll(int modifier) {
		return roll(modifier, 1);
	}
	
	public RollResult roll(int modifier, int rank) {
		Random rand = new Random();
		ArrayList<Integer> dice = new ArrayList<Integer>();
		int die1 = rand.nextInt(6) + 1;
		dice.add(die1);
		int die2 = rand.nextInt(6) + 1;
		dice.add(die2);
		int marvelDieroll = rand.nextInt(6) + 1;
		int marvelDievalue;
		boolean fantasticTracker = false;
		boolean ultimateTracker = false;
		if(marvelDieroll == 1) {
			marvelDievalue = 6;
			fantasticTracker = true;
			if(die1 == 6 && die2 == 6) {
				ultimateTracker = true;
			}
		}
		else {
			marvelDievalue = marvelDieroll;
		}
		dice.add(marvelDieroll);
		int totalScore = die1 + die2 + marvelDievalue + modifier;
		int calculatedDamage = marvelDievalue * rank;
		String scoreSummary = "You Got A: " + totalScore + "\n";
		String fantasticBonus = "Not this time :(\n";
		if(ultimateTracker) {
			fantasticBonus = "YOU ARE A WINNER! ULTIMATE FANTASTIC SUCCESS!\n";
		}
		else if(fantasticTracker) {
			fantasticBonus = "YOU ROLLED A MARVEL! FANTASTIC RESULT!\n";
		}
		String damageSummary = "Damage Dealt: " + calculatedDamage + "\n";  
		String fullSummary = scoreSummary + fantasticBonus + damageSummary;
		String m616 = "Marvel Multiverse d616";
		return new RollResult(m616, dice, totalScore, fullSummary);
	}
	
	@Override
	public String getSystemName() {
		return "Marvel Multiverse d616";
	}
	
	@Override
	public RollResult rollPhysical(List<Integer> physicalDice, int modifier) throws InvalidDiceException {
		return rollPhysical(physicalDice, modifier, 1);
	}
	
	public RollResult rollPhysical(List<Integer> physicalDice, int modifier, int rank) throws InvalidDiceException {
		if(physicalDice == null || physicalDice.size() != 3) { //Check that number of dice is exactly 3 (2 regular + 1 marvel)
			throw new InvalidDiceException("Marvel 616 requires exactly 3 physical dice (2 regular d6 + 1 Marvel d6).");
		}
		for(int val : physicalDice) {
			if(val < 1 || val > 6) { //Check for impossible rolls
				throw new InvalidDiceException("Entered roll of " + val + " is impossible on a d6!");
			}
		}
		int die1 = physicalDice.get(0);
		int die2 = physicalDice.get(1);
		int marvelDieroll = physicalDice.get(2);
		int marvelDievalue;
		boolean fantasticTracker = false;
		boolean ultimateTracker = false;
		if(marvelDieroll == 1) {
			marvelDievalue = 6;
			fantasticTracker = true;
			if(die1 == 6 && die2 == 6) {
				ultimateTracker = true;
			}
		}
		else {
			marvelDievalue = marvelDieroll;
		}
		int totalScore = die1 + die2 + marvelDievalue + modifier;
		int calculatedDamage = marvelDievalue * rank;
		String scoreSummary = "[Physical Roll] You Got A: " + totalScore + "\n";
		String fantasticBonus = "Not this time :(\n";
		if(ultimateTracker) {
			fantasticBonus = "YOU ARE A WINNER! ULTIMATE FANTASTIC SUCCESS!\n";
		}
		else if(fantasticTracker) {
			fantasticBonus = "YOU ROLLED A MARVEL! FANTASTIC RESULT!\n";
		}
		String damageSummary = "Damage Dealt: " + calculatedDamage + "\n";  
		String fullSummary = scoreSummary + fantasticBonus + damageSummary;
		return new RollResult(getSystemName(), physicalDice, totalScore, fullSummary);
	}
}

