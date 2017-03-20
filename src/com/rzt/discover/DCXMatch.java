package com.rzt.discover;

import java.util.*;

public class DCXMatch {

	private HashMap<String,String> vars = new HashMap<String,String>();
	private HashMap<String,Double> scores = new HashMap<String,Double>();

	
	
	public String toString(List<String> headers)
	{
		StringBuffer sb = new StringBuffer(1024);
		boolean first = true;
		for (String s:headers)
		{
			sb.append((first?"":",")+getVal(s));
			first = false;
		}
		sb.append("\n");
		return sb.toString();
	}
	
	private String getVal(String key)
	{
		if (key.toUpperCase().contains("SCORE"))
		{
			return ""+scores.get(key);
		} else
		{
			return "\""+vars.get(key)+"\"";
		}
	}
	
	public DCXMatch(List<String> headers, List<String> vals)
	{
		for (int i=0; i<headers.size(); i++)
		{
			String key = headers.get(i);
			String val = vals.get(i);
			if (key.toUpperCase().contains("SCORE"))
			{
				if (val.trim().equals("null")) scores.put(key, 0.0);
				else
				scores.put(key, Double.parseDouble(val));
			}
			else
				vars.put(key, val);
		}
	}
	
	public HashMap<String, String> getVars() {
		return vars;
	}
	public void setVars(HashMap<String, String> vars) {
		this.vars = vars;
	}
	public HashMap<String, Double> getScores() {
		return scores;
	}
	public void setScores(HashMap<String, Double> scores) {
		this.scores = scores;
	}
	
	
	
}
