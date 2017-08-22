package com.demon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.demon.dao.ICBCFeature;
import com.demon.dao.Instance;
import com.demon.tools.DReader;

public class LoadFeatureSet implements LoadSet{
	
	private Map<String, List<Instance>> dataSet;
	
	public LoadFeatureSet(String fileName){
		dataSet = new HashMap<>();
		loadData(fileName);
	}
	
	
	private void loadData(String fileName){
		DReader reader = new DReader(fileName);
		while (reader.hasNext()){
			String data = reader.next();
			ICBCFeature record = new ICBCFeature(data);
			dataSet.computeIfAbsent(record.getUser(), k -> new ArrayList<>()).add(record);
		}
	}
	
	public Map<String, List<Instance>> getDataSet(){
		return this.dataSet;
	}
	
	public Set<String> getAllUsers(){
		return this.dataSet.keySet();
	}
	
	public static void main(String[] args) {
		LoadFeatureSet loader = new LoadFeatureSet("data/process/feature/feature_normal_ICBCtrain.txt");
		int cnt = 0;
		for (String user : loader.getAllUsers()){
			for (Instance rec : loader.getDataSet().get(user)){
				System.out.println(++ cnt);
				System.out.println(rec.toStr());
			}
		}
	}
}
