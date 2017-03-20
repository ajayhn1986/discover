package com.rzt.discover;

import java.util.*;

public class DCXRecord {

	private ArrayList<DCXMatch> matches = new ArrayList<DCXMatch>();
	private double oldScore = -1.0;
	private double newScore = -1.0;
	
	public String toString(List<String> headers)
	{
		StringBuffer sb = new StringBuffer(1024);
		boolean first = true;
		for (String s:headers)
		{
			sb.append((first?"":",")+s);
			first = false;
		}
		sb.append("\n");
		for (DCXMatch match:matches)
			sb.append(match.toString(headers));
		return sb.toString();
	}
	
	
	public void calculateScores()
	{
		for (DCXMatch match:matches)
		{
			double os = match.getScores().get("score");
			double ns = match.getScores().get("new_score");
			oldScore = Math.max(oldScore, os);
			newScore = Math.max(newScore, ns);
		}
	}
	
	
	
	public void addMatch(List<String> headers, List<String> vals)
	{
		matches.add(new DCXMatch(headers,vals));
	}

	public ArrayList<DCXMatch> getMatches() {
		return matches;
	}

	public void setMatches(ArrayList<DCXMatch> matches) {
		this.matches = matches;
	}



	public double getOldScore() {
		return oldScore;
	}



	public void setOldScore(double oldScore) {
		this.oldScore = oldScore;
	}



	public double getNewScore() {
		return newScore;
	}



	public void setNewScore(double newScore) {
		this.newScore = newScore;
	}
	
	
}
