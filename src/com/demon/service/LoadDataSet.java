package com.demon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.demon.dao.ICBCRecord;
import com.demon.tools.DReader;

public class LoadDataSet {
	
	private Map<String, List<ICBCRecord>> dataSet;
	
	public LoadDataSet(String fileName){
		dataSet = new HashMap<>();
		loadData(fileName);
	}
	
	
	private void loadData(String fileName){
		DReader reader = new DReader(fileName);
		while (reader.hasNext()){
			String data = reader.next();
			ICBCRecord record = new ICBCRecord(data);
			dataSet.computeIfAbsent(record.getUser(), k -> new ArrayList<>()).add(record);
		}
	}
	
	public Map<String, List<ICBCRecord>> getDataSet(){
		return this.dataSet;
	}
	
	public Set<String> getAllUsers(){
		return this.dataSet.keySet();
	}
	
	public static void main(String[] args) {
		LoadDataSet loader = new LoadDataSet("data/ICBCtrain.txt");
		int cnt = 0;
		for (String user : loader.getAllUsers()){
			for (ICBCRecord rec : loader.getDataSet().get(user)){
				System.out.println(++ cnt);
				System.out.println(rec);
			}
		}
	}
}
