package com.demon.ml;

import java.util.ArrayList;
import java.util.List;

import com.demon.dao.ICBCFeature;
import com.demon.tools.DWriter;

import weka.clusterers.Clusterer;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

public class OutputClusterDistribution {
	
	//对训练数据进行分类
	private DWriter writer;
	private List<Instance>[] records;
	
	public OutputClusterDistribution() throws Exception{
		Instances train = DataSource.read("data/process/feature/feature_normal_ICBCtrain.arff");
		Clusterer cls = (Clusterer) SerializationHelper.read(
				"data/model/em.model");
		int n = cls.numberOfClusters();
		records = new ArrayList[n];
		for (int i = 0; i < n; ++i) records[i] = new ArrayList<>();
		
		for (int i = 0; i < train.numInstances(); ++i){
			int cluster = cls.clusterInstance(train.instance(i));
			records[cluster].add(train.instance(i));
		}
		
		splitAndWrite(n);
	}
	
	private void splitAndWrite(int n){
		writer = new DWriter("data/cluster/cluster_normal_ICBCtrain.txt");
		for (int i = 0; i < n; ++i){
			String user = "000000000000" + String.valueOf(100 + i);
			for (Instance record : records[i]){
				ICBCFeature feature = new ICBCFeature();
				feature.setUser(user);
				feature.setTransAmount(record.toString(0));
				feature.setMaxMoney(record.toString(1));
				feature.setMinMoney(record.toString(2));
				feature.setAvgMoney(record.toString(3));
				feature.setIP(record.toString(4));
				writer.println(feature.toStr());
			}
		}
		writer.close();
	}
	
	
	
	public static void main(String[] args) throws Exception {
		OutputClusterDistribution ocd = new OutputClusterDistribution();
	}
}

