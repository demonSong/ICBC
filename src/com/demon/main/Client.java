package com.demon.main;

import com.demon.dao.ICBCRecord;
import com.demon.ml.Classifying;
import com.demon.ml.Clustering;
import com.demon.ml.OutputClusterDistribution;
import com.demon.service.CSV2ARFF;
import com.demon.service.ClusterDataToClassify;
import com.demon.service.DataDivider;
import com.demon.service.FeatureExtract;
import com.demon.service.LoadDataSet;
import com.demon.service.RandomData;
import com.demon.tools.DataToCSV;
import com.demon.tools.FileHelper;

import weka.core.SerializationHelper;

public class Client {
	
	private ICBCPredict model;
	
	public Client(String dataSetName){
		
		// 0.加载模型
		if (FileHelper.fileExist("data/model" + dataSetName + ".obj")){
			try {
				model = (ICBCPredict) SerializationHelper.read("data/model" + dataSetName + ".obj");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		
		// 1.仿真数据
		if (!FileHelper.fileExist("data/" + dataSetName + "train.txt")){
			System.err.println("注意：数据集不存在喔，请在data文件夹下添加" + dataSetName + "train.txt文件");
			System.out.println("字段要求请参看README...");
			System.out.println("生成仿真数据...");
			long s = System.currentTimeMillis();
			RandomData.persistData("data/" + dataSetName, 35000, false);
			System.out.println("仿真数据生成成功..." + (System.currentTimeMillis() - s) + "ms");
		}
		
		// 2.加载数据
		long s = System.currentTimeMillis();
		System.out.println("加载数据集...");
		if (!FileHelper.fileExist("data/" + dataSetName + "test.txt")){
			System.err.println("注意：系统没有检测到测试集，系统将从train.txt中随机抽取作为测试集");
			new DataDivider(dataSetName, false);
		}
		else{
			System.out.println("加载测试集...");
			new DataDivider(dataSetName, true);
		}
		System.out.println("数据加载成功..." + (System.currentTimeMillis() - s) + "ms");
		
		// 3.数据集转CSV，方便分析
		s = System.currentTimeMillis();
		System.out.println("文本数据转CSV...");
		new DataToCSV(new LoadDataSet("data/process/normal_" + dataSetName + "train.txt"), 
				"data/process/normal_" + dataSetName + "train.txt", new ICBCRecord());
		new DataToCSV(new LoadDataSet("data/process/abnormal_" + dataSetName + "train.txt"), 
				"data/process/abnormal_" + dataSetName + "train.txt", new ICBCRecord());
		System.out.println("CSV文件生成成功..." + (System.currentTimeMillis() - s) + "ms");
		
		// 4.特征提取
		s = System.currentTimeMillis();
		System.out.println("特征提取工程...");
		new FeatureExtract("data/process/normal_" + dataSetName + "train.txt");
		new FeatureExtract("data/process/abnormal_" + dataSetName + "train.txt");
		System.out.println("特征提取成功..." + (System.currentTimeMillis() - s) + "ms");
		
		// 5.特征数据集转CSV
		s = System.currentTimeMillis();
		System.out.println("arff格式数据生成...");
		CSV2ARFF.csv2Arff("data/process/feature/feature_normal_" + dataSetName + "train.csv");
		CSV2ARFF.csv2Arff("data/process/feature/feature_abnormal_" + dataSetName + "train.csv");
		System.out.println("arff格式数据生成成功..." + (System.currentTimeMillis() - s) + "ms");
		
		// 6.1 模型训练 : 所有正常交易进行聚类
		s = System.currentTimeMillis();
		System.out.println("聚类中...");
		new Clustering("data/process/feature/feature_normal_" + dataSetName + "train.arff");
		System.out.println("聚类完成..." + (System.currentTimeMillis() - s) + "ms");
		
		// 6.2 模型训练: 对聚类群体进行划分
		s = System.currentTimeMillis();
		System.out.println("划分群体数据集...");
		new OutputClusterDistribution(dataSetName);
		System.out.println("划分群体数据集成功..." + (System.currentTimeMillis() - s) + "ms");
		
		// 6.3 模型训练： 聚类数据集转分类数据集
		s = System.currentTimeMillis();
		System.out.println("聚类数据集转分类数据集且生成对应arff文件...");
		new ClusterDataToClassify(dataSetName);
		CSV2ARFF.csv2Arff("data/classify/classify_normal_" + dataSetName + "train.csv");
		CSV2ARFF.csv2Arff("data/classify/classify_abnormal_" + dataSetName + "train.csv");
		System.out.println("聚类数据集转分类数据集且生成对应arff文件成功..." + (System.currentTimeMillis() - s) + "ms");
		
		// 6.4 模型训练：分类算法训练
		s = System.currentTimeMillis();
		System.out.println("分类算法训练中...");
		new Classifying(dataSetName);
		System.out.println("分类算法训练成功..." + (System.currentTimeMillis() - s) + "ms");
		
		System.err.println("模型训练完毕");
		
		// 7 测试数据结果输出
		s = System.currentTimeMillis();
		System.out.println("-------------------测试结果-------------------");
		model = new ICBCPredict(dataSetName);
		model.evalution(dataSetName);
		System.out.println("测试完毕..." + (System.currentTimeMillis() - s) + "ms");
		
		
		// 8 模型持久化
		try {
			String objFile = "data/model" + dataSetName + ".obj";
			FileHelper.clearFile(objFile);
			SerializationHelper.write(objFile, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int predict(ICBCRecord trans){
		return model.predict(trans);
	}
	
	public static void main(String[] args) {
		new Client("ICBC").model.evalution("ICBC");
	}
}
