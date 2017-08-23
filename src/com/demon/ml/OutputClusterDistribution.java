package com.demon.ml;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.compressors.FileNameUtil;

import com.demon.dao.ICBCFeature;
import com.demon.tools.DWriter;
import com.demon.tools.FileHelper;

import weka.clusterers.Clusterer;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class OutputClusterDistribution {
	
	//对训练数据进行分类
	private DWriter writer;
	private List<Instance>[] records;
	private String dataSetName;
	
	public OutputClusterDistribution(String dataSetName){
		try {
			this.dataSetName = dataSetName;
			String fileName = "data/process/feature/feature_normal_" + dataSetName + "train.arff";
			Instances data = DataSource.read(fileName);
			
			Remove filter = new Remove();
			int[] remove = { 0, data.numAttributes() - 1 };
			filter.setAttributeIndicesArray(remove); // 删除标签 和 用户ID
			filter.setInputFormat(data);
			Instances train = Filter.useFilter(data, filter);
			
			Clusterer cls = (Clusterer) SerializationHelper.read(
					"data/model/cluster.model");
			
			int n = cls.numberOfClusters();
			records = new ArrayList[n];
			for (int i = 0; i < n; ++i) records[i] = new ArrayList<>();
			
			for (int i = 0; i < train.numInstances(); ++i){
				int cluster = cls.clusterInstance(train.instance(i));
				records[cluster].add(train.instance(i));
			}
			
			splitAndWrite(n);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void splitAndWrite(int n){
		String fileName = "data/cluster/cluster_normal_" + dataSetName + "train.txt";
		FileHelper.clearFile(fileName);
		writer = new DWriter(fileName);
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
		OutputClusterDistribution ocd = new OutputClusterDistribution("Demo");
	}
}

