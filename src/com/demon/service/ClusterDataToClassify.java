package com.demon.service;

import com.demon.dao.ICBCFeature;
import com.demon.tools.DReader;
import com.demon.tools.DWriter;
import com.demon.tools.DataToCSV;
import com.demon.tools.FileHelper;

public class ClusterDataToClassify {
	
	private final String NORMAL_INPUT;
	private final String NORMAL_OUTPUT;
	
	private final String ABNORMAL_INPUT;
	private final String ABNORMAL_OUTPUT;
	
	public ClusterDataToClassify(String dataSetName){
		NORMAL_INPUT = "data/cluster/cluster_normal_" + dataSetName + "train.txt";
		NORMAL_OUTPUT = "data/classify/classify_normal_" + dataSetName + "train.txt";
		
		ABNORMAL_INPUT = "data/process/feature/feature_abnormal_" + dataSetName + "train.txt";
		ABNORMAL_OUTPUT = "data/classify/classify_abnormal_" + dataSetName + "train.txt";
		
		appendNormalLabel();
		appendAbnormalLable();
		
		new DataToCSV(new LoadFeatureSet(NORMAL_OUTPUT), NORMAL_OUTPUT, new ICBCFeature());
		new DataToCSV(new LoadFeatureSet(ABNORMAL_OUTPUT), ABNORMAL_OUTPUT, new ICBCFeature());
	}
	
	private void appendNormalLabel(){
		FileHelper.clearFile(NORMAL_OUTPUT);
		DReader reader = new DReader(NORMAL_INPUT);
		DWriter writer = new DWriter(NORMAL_OUTPUT);
		while (reader.hasNext()){
			ICBCFeature feature = new ICBCFeature(reader.next());
			feature.setLabel("0"); // 设置为正常交易
			writer.println(feature.toStr());
		}
		writer.close();
	}
	
	private void appendAbnormalLable(){
		FileHelper.clearFile(ABNORMAL_OUTPUT);
		DReader reader = new DReader(ABNORMAL_INPUT);
		DWriter writer = new DWriter(ABNORMAL_OUTPUT);
		while (reader.hasNext()){
			ICBCFeature feature = new ICBCFeature(reader.next());
			feature.setLabel("1"); // 设置为非正常交易
			writer.println(feature.toStr());
		}
		writer.close();
	}
	
	public static void main(String[] args) {
		new ClusterDataToClassify("Demo");
	}
}
