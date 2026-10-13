package com.universaldicerollcalc.core;
import com.universaldicerollcalc.exception.*;
public class RollerFactory {
	public static DiceRoller getRoller(String systemType, int diceCount, int sides) throws InvalidSystemException, InvalidDiceException {
		if(systemType.equalsIgnoreCase("Standard")) {
			return new StandardRoller(diceCount, sides);
		}
		else if(systemType.equalsIgnoreCase("Marvel")) {
			return new Marvel616Roller();
		}
		else {
			throw new InvalidSystemException("Unsupported game system: '" + systemType + "'. Current supported systems are 'Standard' and 'Marvel'.");
		}
	}
}
