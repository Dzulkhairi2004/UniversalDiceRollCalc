package com.universaldicerollcalc.core;
import java.util.*;
import com.universaldicerollcalc.model.*;
import com.universaldicerollcalc.exception.*;
public class DimensionsRoller implements DiceRoller {
	
	private int damageDiceCount;
	private int damageDieSides;
	
	public DimensionsRoller() {
		this.damageDiceCount = 1;
		this.damageDieSides = 6;
	}
	
	public DimensionsRoller(int damageDiceCount, int damageDieSides) {
		this.damageDiceCount = Math.max(1, damageDiceCount);
		this.damageDieSides = Math.max(2, damageDieSides);
	}
	
	@Override
	public String getSystemName() {
		return "Dungeons & Dimensions";
	}
	
	@Override
	public RollResult roll(int modifier) {
		Random rand = new Random();
		List<Integer> dice = new ArrayList<>();
		
		int die1 = rand.nextInt(6) + 1;
        int die2 = rand.nextInt(6) + 1;
        int bonusDieRoll = rand.nextInt(6) + 1;
        
        dice.add(die1);
        dice.add(die2);
        dice.add(bonusDieRoll);

        int bonusDieValue;
        boolean fantasticTracker = false;
        boolean ultimateTracker = false;

        if (bonusDieRoll == 1) {
            bonusDieValue = 6;
            fantasticTracker = true;
            if (die1 == 6 && die2 == 6) {
                ultimateTracker = true;
            }
        }
        else {
        	bonusDieValue = bonusDieRoll;
        }
        
        int totalScore = die1 + die2 + bonusDieValue + modifier;
        
        List<Integer> damageDice = new ArrayList<>();
        int rawDamageSum = 0;
        for (int i = 0; i < damageDiceCount; i++) {
            int dRoll = rand.nextInt(damageDieSides) + 1;
            damageDice.add(dRoll); // Track damage dice inside rawDice list
            rawDamageSum += dRoll;
        }
        
        int baseDamage = rawDamageSum + modifier;
        int finalDamage = baseDamage;
        int multiplier = 1;

        if (ultimateTracker) {
            multiplier = 3;
            finalDamage = baseDamage * 3;
        }
        else if(fantasticTracker) {
        	multiplier = 2;
        	finalDamage = baseDamage * 2;
        }
        
        String scoreSummary = "You Got A " + totalScore + "\n";
        String fantasticBonus = "Fantastic Status : Not this time :(\n";
        
        if (ultimateTracker) {
            fantasticBonus = "Fantastic Status : WE HAVE A WINNER! ULTIMATE FANTASTIC SUCCESS! (3x Damage)\n";
        }
        else if (fantasticTracker) {
            fantasticBonus = "Fantastic Status : YOU ROLLED A BONUS 1! FANTASTIC RESULT! (2x Damage)\n";
        }
        
        String damageRollText = "Damage Roll      : " + damageDice + "\n";
        String damageSummary = "Total Damage Dealt: " + finalDamage + 
                " (Base: " + baseDamage + " [" + damageDiceCount + "d" + damageDieSides + " + " + modifier + "]" + 
                (multiplier > 1 ? " x" + multiplier : "") + ")";
        
        String fullSummary = scoreSummary + fantasticBonus + damageRollText + damageSummary;

        return new RollResult(getSystemName(), dice, totalScore, fullSummary);
	}
	
	@Override
	public RollResult rollPhysical(List<Integer> physicalDice, int modifier) throws InvalidDiceException {
	    // Requires 3 action dice (2 regular + 1 bonus) + damageDiceCount physical dice
	    int expectedDice = 3 + damageDiceCount;
	    if (physicalDice == null || physicalDice.size() != expectedDice) {
	        throw new InvalidDiceException("Dimensions System requires exactly " + expectedDice + 
	            " physical dice (2 regular d6 + 1 bonus d6 + " + damageDiceCount + " damage d" + damageDieSides + ").");
	    }

	    // Split physical input into action dice and damage dice lists
	    List<Integer> actionDice = physicalDice.subList(0, 3);
	    List<Integer> damageDice = physicalDice.subList(3, expectedDice);

	    // Validate Action Dice
	    for (int val : actionDice) {
	        if (val < 1 || val > 6) {
	            throw new InvalidDiceException("Entered roll of " + val + " is impossible on a d6 action die!");
	        }
	    }

	    // Validate Damage Dice (Allows 0 if attack missed)
	    for (int val : damageDice) {
	        if (val < 0 || val > damageDieSides) {
	            throw new InvalidDiceException("Entered damage roll of " + val + " is invalid! (Enter 1-6, or 0 if attack missed).");
	        }
	    }

	    int die1 = actionDice.get(0);
	    int die2 = actionDice.get(1);
	    int bonusDieRoll = actionDice.get(2);

	    int bonusDieValue;
	    boolean fantasticTracker = false;
	    boolean ultimateTracker = false;

	    if (bonusDieRoll == 1) {
	        bonusDieValue = 6;
	        fantasticTracker = true;
	        if (die1 == 6 && die2 == 6) {
	            ultimateTracker = true;
	        }
	    } else {
	        bonusDieValue = bonusDieRoll;
	    }

	    int totalScore = die1 + die2 + bonusDieValue + modifier;

	    int rawDamageSum = 0;
	    for (int val : damageDice) {
	        rawDamageSum += val;
	    }

	    int multiplier = 1;
	    if (ultimateTracker) {
	        multiplier = 3;
	    } 
	    else if (fantasticTracker) {
	        multiplier = 2;
	    }

	    int baseDamage = (rawDamageSum == 0) ? 0 : (rawDamageSum + modifier);
	    int finalDamage = baseDamage * multiplier;

	    String scoreSummary = "[Physical Roll] You Got A " + totalScore + "\n";
	    String fantasticBonus = "Fantastic Status : Not this time :(\n";

	    if (ultimateTracker) {
	        fantasticBonus = "Fantastic Status : WE HAVE A WINNER! ULTIMATE FANTASTIC SUCCESS! (3x Damage)\n";
	    } 
	    else if (fantasticTracker) {
	        fantasticBonus = "Fantastic Status : YOU ROLLED A BONUS 1! FANTASTIC RESULT! (2x Damage)\n";
	    }

	    String damageRollText = "Damage Roll      : " + (rawDamageSum == 0 ? "[No Damage / Missed]" : damageDice) + "\n";
	    String damageSummary  = "Total Damage Dealt: " + finalDamage + 
	                            (rawDamageSum == 0 ? " (Attack missed or no damage rolled)" : 
	                            " (Base: " + baseDamage + " [" + damageDiceCount + "d" + damageDieSides + " + " + modifier + "]" + 
	                            (multiplier > 1 ? " x" + multiplier : "") + ")");

	    String fullSummary = scoreSummary + fantasticBonus + damageRollText + damageSummary;

	    // Pass ONLY actionDice (first 3 dice) so Individual Rolls displays [die1, die2, bonusDie]
	    return new RollResult(getSystemName(), actionDice, totalScore, fullSummary);
	}
}
