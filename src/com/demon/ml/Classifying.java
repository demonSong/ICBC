package com.demon.ml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demon.tools.FileHelper;

import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class Classifying {
	
	//建立群体分类器 和 相似群体分类器
	
	Map<String, List<Instance>> users;
	Map<String, Classifier> classifiers;
	
	public Classifying(String dataSetName){
		try {
			users = new HashMap<>();
			classifiers = new HashMap<>();
			
			Instances normal = DataSource.read("data/classify/classify_normal_" + dataSetName + "train.arff");
			Instances abnormal = DataSource.read("data/classify/classify_abnormal_" + dataSetName + "train.arff");
			
			// 个体分类
			for (int i = 0; i < normal.numInstances(); ++i){
				String user = normal.instance(i).toString(0);
				users.computeIfAbsent(user, k -> new ArrayList<>()).add(normal.instance(i));
			}
			
			for (String key : users.keySet()){
				Instances data = new Instances(abnormal);
				for (Instance instance : users.get(key)){
					data.add(instance);
				}
				
				Remove filter = new Remove();
				filter.setAttributeIndices("1"); // 移除用户ID
				filter.setInputFormat(data);
				Instances train = Filter.useFilter(data, filter);
				train.setClassIndex(train.numAttributes() - 1);
				
				Classifier cls = new RandomForest();
				cls.buildClassifier(train);
				
				classifiers.put(key, cls);
			}
			
			String objFile = "data/model/classifier.model";
			FileHelper.clearFile(objFile);
			SerializationHelper.write(objFile, classifiers);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new Classifying("Demo");
	}
	
}
