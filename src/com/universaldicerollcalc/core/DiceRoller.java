package com.universaldicerollcalc.core;

import java.util.*;
import com.universaldicerollcalc.exception.*;
import com.universaldicerollcalc.model.*;

public interface DiceRoller {
	RollResult roll(int modifier); //For digital rolls (auto generates random dice)
	RollResult rollPhysical(List<Integer> physicalDice, int modifier) throws InvalidDiceException; //For physical rolls (accepts player's input based on their dice rolls)
	String getSystemName();
}