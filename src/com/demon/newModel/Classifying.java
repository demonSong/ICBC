package com.demon.newModel;

import com.demon.tools.FileHelper;

import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

public class Classifying {
	
		public Classifying(){
			try {
				
				Instances dataSet = DataSource.read("data/data_train.arff");
				dataSet.setClassIndex(dataSet.numAttributes() - 1);
					
				Classifier cls = new RandomForest();
				cls.buildClassifier(dataSet);
					
				String objFile = "data/sjh/model/classifier.model";
				FileHelper.clearFile(objFile);
				SerializationHelper.write(objFile, cls);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void main(String[] args) throws Exception {
			new Classifying();
		}
}
