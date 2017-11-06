package com.demon.newModel;

import java.util.ArrayList;
import java.util.List;

import com.demon.service.CSV2ARFF;
import com.demon.service.Evaluation;
import com.demon.tools.FileHelper;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

public class TrainClient {
	
	public static void main(String[] args) throws Exception {
		CSV2ARFF.csv2Arff("data/data_train.csv");
		CSV2ARFF.csv2Arff("data/data_test.csv");
		
		new Classifying();
		
		Classifier model;
		Instances dataSet = DataSource.read("data/data_test.arff");
		dataSet.setClassIndex(dataSet.numAttributes() - 1);
		
		List<Integer> real = new ArrayList<>();
		List<Integer> pred = new ArrayList<>();
		
		if (FileHelper.fileExist("data/sjh/model/" + "classifier" + ".model")){
			try {
				model = (Classifier) SerializationHelper.read("data/sjh/model/" + "classifier" + ".model");
				for (Instance test : dataSet){
					double p = model.classifyInstance(test);
					int pre = p > 0.5 ? 1 : 0;
					pred.add(pre);
					real.add(Integer.parseInt(test.toString(dataSet.numAttributes() - 1)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Evaluation eval = new Evaluation(real, pred);
		eval.printLog();
	}

}
