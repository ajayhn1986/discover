package com.rzt.discover;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import com.rzt.utils.file.CSVParse;
import com.rzt.utils.file.FileUtils;

/**
 * Created by macbook on 3/4/17.
 */
public class Update {

	public static int[] PERCENTILES = {0,1119811,1179857,1239928,1299998,2060086,2120169,2180125,2240073};
	
    public static void main(String[] args) throws Exception
    {
    	HashMap<String,DCXRecord> recs = new HashMap<String,DCXRecord>();
    	String filePath = "/Data/discover/update0319.csv";
    	String outFilePath = "/Data/discover/updateOut";
    	ArrayList<String> arr = FileUtils.getContentsAsList(new File(filePath));
    	ArrayList<List<String>> data  = new ArrayList<List<String>>();
    	List<String> headers = CSVParse.parseLine(arr.get(0));
    	int hdrSize = headers.size();
    	int dis = 0;
    	for (int i=1; i<arr.size(); i++)
    	{
    		List<String> l = CSVParse.parseLine(arr.get(i));
    		if (l.size() == hdrSize)
    		{
    			String key = l.get(1);
    			Integer k = Integer.parseInt(key.trim());
    			if (k <= PERCENTILES[5])						// CHANGE THIS TO DO ANALYSIS...
    			{
	    			DCXRecord rec = recs.get(key);
	    			if (rec == null) {rec = new DCXRecord(); recs.put(key, rec);}
	    			try
	    			{
	    				if (rec != null)
	    					rec.addMatch(headers, l);
	    			} catch(Exception e)
	    			{
	    				System.out.println(arr.get(i));
	    			}
    			}
    		} else
    		{
    			dis ++;
    		}
    		if (i % 100000 == 0) System.out.println(i);
    	}
    	for (DCXRecord rec:recs.values()) rec.calculateScores();
    	HashMap<String,Integer> hm = new HashMap<String,Integer>();
		File f = new File(outFilePath);
		if (f.exists()) deleteFolder(f, true);
   		BufferedWriter bw;
   	    for (String s:recs.keySet())
    	{
    		DCXRecord rec = recs.get(s);
    		int opc = (int) ((rec.getOldScore()*1.0)/10.0);
    		int npc = (int) ((rec.getNewScore()*1.0)/10.0);
    		String key = opc +"-" + npc;
    		String outDir = outFilePath+"/"+key;
    		f = new File(outDir);
    		if (!f.exists()) f.mkdirs();
    		try
    		{
    			bw = new BufferedWriter(new FileWriter(outDir+"/"+s+".txt", false));
    			bw.write(rec.toString(headers));
    			bw.close();
    		} catch (Exception e)
    		{
    			e.printStackTrace();
    		}

    		Integer ct = hm.get(key);
    		if (ct == null) ct = 0;
    		hm.put(key, ct + 1);
    	}
    	StringBuffer sb = new StringBuffer(1024);
    	sb.append("OLD_SCORE,NEW_SCORE,COUNT\n");
    	for (String s:hm.keySet())
    	{
    		String[] ss = s.split("-");
    		sb.append(ss[0]).append(",").append(ss[1]).append(",").append(""+hm.get(s)).append("\n");
    	}
		bw = new BufferedWriter(new FileWriter(outFilePath+"/summary.txt", false));
		bw.write(sb.toString());
		bw.close();
    }
    
    
	public static void deleteFolder(File folder,boolean deleteContentsOnly) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f,false);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    if (!deleteContentsOnly) folder.delete();
	}


}
