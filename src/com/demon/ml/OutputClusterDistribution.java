package com.demon.ml;

import weka.clusterers.Clusterer;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;

public class OutputClusterDistribution {
	
	public static void main(String[] args) throws Exception {
		Instances test = DataSource.read("data/process/feature/feature_abnormal_ICBCtrain.arff");
		Clusterer cls = (Clusterer) SerializationHelper.read(
		"data/model/em.model");
		System.out.println("# - cluster - distribution");
	    for (int i = 0; i < test.numInstances(); i++) {
	      int cluster = cls.clusterInstance(test.instance(i));
	      double[] dist = cls.distributionForInstance(test.instance(i)); // 离某个聚类中心最近的用户
	      System.out.print((i+1));
	      System.out.print(" - ");
	      System.out.print(cluster);
	      System.out.print(" - ");
	      System.out.print(Utils.arrayToString(dist));
	      System.out.println();
	    }
	}
}

