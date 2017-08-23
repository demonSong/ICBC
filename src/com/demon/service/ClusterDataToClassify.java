package com.demon.service;

import com.demon.dao.ICBCFeature;
import com.demon.tools.DReader;
import com.demon.tools.DWriter;
import com.demon.tools.DataToCSV;

public class ClusterDataToClassify {
	
	private static final String NORMAL_INPUT = "data/cluster/cluster_normal_ICBCtrain.txt";
	private static final String NORMAL_OUTPUT = "data/classify/classify_normal_ICBCtrain.txt";
	
	private static final String ABNORMAL_INPUT = "data/process/feature/feature_abnormal_ICBCtrain.txt";
	private static final String ABNORMAL_OUTPUT = "data/classify/classify_abnormal_ICBCtrain.txt";
	
	public ClusterDataToClassify(){
		appendNormalLabel();
		appendAbnormalLable();
		
		new DataToCSV(new LoadFeatureSet(NORMAL_OUTPUT), NORMAL_OUTPUT, new ICBCFeature());
		new DataToCSV(new LoadFeatureSet(ABNORMAL_OUTPUT), ABNORMAL_OUTPUT, new ICBCFeature());
	}
	
	private void appendNormalLabel(){
		DReader reader = new DReader(NORMAL_INPUT);
		DWriter writer = new DWriter(NORMAL_OUTPUT);
		while (reader.hasNext()){
			ICBCFeature feature = new ICBCFeature(reader.next());
			if (feature.getLabel() == null) feature.setLabel("0"); // 设置为正常交易
			writer.println(feature.toStr());
		}
		writer.close();
	}
	
	private void appendAbnormalLable(){
		DReader reader = new DReader(ABNORMAL_INPUT);
		DWriter writer = new DWriter(ABNORMAL_OUTPUT);
		while (reader.hasNext()){
			ICBCFeature feature = new ICBCFeature(reader.next());
			if (feature.getLabel() == null) feature.setLabel("1"); // 设置为非正常交易
			writer.println(feature.toStr());
		}
		writer.close();
	}
	
	public static void main(String[] args) {
		new ClusterDataToClassify();
	}
}
