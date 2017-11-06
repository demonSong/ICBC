package com.demon.tools;

import java.io.FileWriter;
import java.io.IOException;

import com.demon.dao.ICBCRecord;
import com.demon.dao.Instance;
import com.demon.service.LoadDataSet;
import com.demon.service.LoadSet;
import com.opencsv.CSVWriter;

public class DataToCSV {
	
	private CSVWriter writer;
	
	public DataToCSV(LoadSet dataSet, String fileName, Instance type){
		int index = fileName.indexOf(".");
		String newFileName = fileName.substring(0, index);
		try {
			FileHelper.clearFile(newFileName + ".csv"); 
			writer = new CSVWriter(new FileWriter(newFileName + ".csv"));
			writeHead(type);
			writeContent(dataSet);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeHead(Instance type){
		String[] heads = type.toHead();
		writer.writeNext(heads);
	}
	
	private void writeContent(LoadSet dataSet){
		for (String user : dataSet.getAllUsers()){
			for (Instance record : dataSet.getDataSet().get(user)){
				writer.writeNext(record.toContent());
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		DataToCSV toCSV = new DataToCSV(new LoadDataSet("data/process/normal_ICBCtrain.txt"), "data/process/normal_ICBCtrain.txt", new ICBCRecord());
	}
}
