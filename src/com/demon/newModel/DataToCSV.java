package com.demon.newModel;

import java.io.FileWriter;
import java.io.IOException;

import com.demon.dao.Instance;
import com.demon.tools.FileHelper;
import com.opencsv.CSVWriter;

public class DataToCSV {
	
	private CSVWriter writer;
	
	public DataToCSV(LoadData dataSet, String fileName, Instance type){
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
	
	private void writeContent(LoadData dataSet){
		for (String user : dataSet.getAllUsers()){
			for (DataRecord record : dataSet.getDataSet().get(user)){
				writer.writeNext(record.toContent());
			}
		}
	}
	
	public static void main(String[] args) {
		new DataToCSV(new LoadData("data/data_train.txt"), "data/data_train.txt", new DataRecord());
		new DataToCSV(new LoadData("data/data_test.txt"), "data/data_test.txt", new DataRecord());
	}

}
