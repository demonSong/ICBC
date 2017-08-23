package com.demon.service;

import com.demon.tools.FileHelper;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSink;
import weka.core.converters.ConverterUtils.DataSource;

public class CSV2ARFF {
	
	public static void csv2Arff(String fileName){
		try {
			Instances data = DataSource.read(fileName);
			int index = fileName.indexOf(".");
			String file = fileName.substring(0, index) + ".arff";
			FileHelper.clearFile(file);
			DataSink.write(file, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
