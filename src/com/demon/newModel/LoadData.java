package com.demon.newModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.demon.tools.DReader;

public class LoadData {
	
	Map<String, List<DataRecord>> dataSet;
	
	LoadData(String fileName){
		dataSet = new HashMap<>();
		load(fileName);
	}
	
	void load(String fileName){
		DReader reader = new DReader(fileName);
		while (reader.hasNext()){
			String data = reader.next();
			DataRecord record = new DataRecord(data);
			dataSet.computeIfAbsent(record.user, k -> new ArrayList<>()).add(record);
		}
	}
	
	Set<String> getAllUsers(){
		return dataSet.keySet();
	}
	
	Map<String, List<DataRecord>> getDataSet(){
		return dataSet;
	}
	
	public static void main(String[] args) {
		LoadData load = new LoadData("data/data_train.txt");
	}
}
