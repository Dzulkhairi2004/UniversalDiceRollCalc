package com.universaldicerollcalc.app;
import java.util.*;
import com.universaldicerollcalc.core.*;
import com.universaldicerollcalc.exception.*;
import com.universaldicerollcalc.model.*;

public class MainApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean running = true;
		System.out.println("WELCOME TO THE UNIVERSAL DICE ROLLER!");
		while(running) {
			System.out.println("\nSelect a Game System:");
			System.out.println("1. Standard (d4, d6, d8, d10, d12, d20, d100)");
			System.out.println("2. Marvel Multiverse d616");
			System.out.println("3. Dungeons & Dimensions");
			System.out.println("4. Exit");
			System.out.print("Enter choice (1-4): ");
			
			String choice = sc.nextLine().trim();
			
			if(choice.equals("4")) {
				System.out.println("\nThank you for rolling! Goodbye!");
				running = false;
				break;
			}
			
			String systemName = "";
			if(choice.equals("1")) {
				systemName = "Standard";
			}
			else if(choice.equals("2")) {
				systemName = "Marvel";
			}
			else if(choice.equals("3")) {
				systemName = "Dimensions";
			}
			else {
				System.out.println("\n[!] Invalid choice! Please select 1, 2, 3 or 4.");
				continue;
			}
			
			System.out.println("\nSelect Rolling Mode:");
			System.out.println("1. Digital (In-built Generator)");
			System.out.println("2. Physical (Manual Dice Entry)");
			System.out.print("Enter mode choice: ");
			String modeChoice = sc.nextLine().trim();
			
			if(!modeChoice.equals("1") && !modeChoice.equals("2")) {
				System.out.println("\n[!] Invalid mode! Returning to main menu.");
				continue;
			}
			try {
				int diceCount = 0;
				int dieSides = 0;
				
				if(systemName.equalsIgnoreCase("Standard")) {
					System.out.print("Enter number of dice: ");
					diceCount = Integer.parseInt(sc.nextLine().trim());
					System.out.print("Enter sides per die: ");
					dieSides = Integer.parseInt(sc.nextLine().trim());
				}
				DiceRoller roller = RollerFactory.getRoller(systemName, diceCount, dieSides);
				System.out.print("Enter stat/modifier bonus: ");
				int modifier = Integer.parseInt(sc.nextLine().trim());
				RollResult result;
				
				if(modeChoice.equals("1")) {
					result = roller.roll(modifier);
				}
				else {
					List<Integer> physicalDice = new ArrayList<>();
					
					if(systemName.equalsIgnoreCase("Standard")) {
						for(int i = 1; i <= diceCount; i++) {
							System.out.print("Enter value for Die #" + i + ": ");
							int val = Integer.parseInt(sc.nextLine().trim());
							physicalDice.add(val);
						}
					}
					else if(systemName.equalsIgnoreCase("Marvel")) {
						System.out.print("Enter Regular Die #1 value: ");
						physicalDice.add(Integer.parseInt(sc.nextLine().trim()));
						System.out.print("Enter Regular Die #2 value: ");
						physicalDice.add(Integer.parseInt(sc.nextLine().trim()));
						System.out.print("Enter Marvel Die value: ");
						physicalDice.add(Integer.parseInt(sc.nextLine().trim()));
					}
					else if(systemName.equalsIgnoreCase("Dimensions")) {
					    System.out.print("Enter Regular Action Die #1 value: ");
					    physicalDice.add(Integer.parseInt(sc.nextLine().trim()));
					    System.out.print("Enter Regular Action Die #2 value: ");
					    physicalDice.add(Integer.parseInt(sc.nextLine().trim()));
					    System.out.print("Enter Bonus Action Die value: ");
					    physicalDice.add(Integer.parseInt(sc.nextLine().trim()));
					    System.out.print("Enter Damage Die value (1d6, or 0 if attack missed): ");
					    physicalDice.add(Integer.parseInt(sc.nextLine().trim()));
					}
					result = roller.rollPhysical(physicalDice, modifier);
				}
				System.out.println("\n--- ROLL RESULT ---");
				result.printBreakdown();
			} catch(NumberFormatException e) {
				System.out.println("\n[!] Input Error: Please enter valid integer numbers!");
			} catch(InvalidSystemException e) {
				System.out.println("\nSystem Error: " + e.getMessage());
			} catch(InvalidDiceException e) {
				System.out.println("\nDice Error: " + e.getMessage());
			}
		}
		sc.close();
	}
}