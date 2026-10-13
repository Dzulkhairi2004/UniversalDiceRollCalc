package com.universaldicerollcalc.model;
import java.util.*;
public class RollResult {
	
	private String systemName;
	private List<Integer> rawDice;
	private int total;
	private String summaryText;
	
	public RollResult(String systemName, List<Integer> rawDice, int total, String summaryText) {
		this.systemName = systemName;
		this.rawDice = rawDice;
		this.total = total;
		this.summaryText = summaryText;
	}
	
	public String getSystemName() {
		return systemName;
	}
	
	public List<Integer> getRawDice() {
		return rawDice;
	}
	
	public int getTotal() {
		return total;
	}
	
	public String getSummaryText() {
		return summaryText;
	}
	
}
