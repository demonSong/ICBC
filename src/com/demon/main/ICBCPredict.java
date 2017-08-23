package com.demon.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.demon.dao.ICBCRecord;
import com.demon.service.Evaluation;
import com.demon.service.LoadDataSet;
import com.demon.service.Statics;

import weka.classifiers.Classifier;
import weka.clusterers.Clusterer;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

public class ICBCPredict {
	
	private Map<String, Statics> users;
	private Clusterer cluster;
	private Map<String, Classifier> classifiers;
	private Instances dataSet;
	
	public ICBCPredict(){
		//1. 加载所有用户统计指标
		try {
			users = (Map<String, Statics>) SerializationHelper.read("data/stats/total.obj");
		} catch (Exception e) {
			throw new IllegalArgumentException("未找到用户统计指标");
		}
		
		//2. 加载聚类模型
		try {
			cluster = (Clusterer) SerializationHelper.read("data/model/em.model");
		} catch (Exception e) {
			throw new IllegalArgumentException("未找到聚类模型");
		}
		
		//3. 加载分类器
		try {
			dataSet = DataSource.read("data/classify/classify_normal_ICBCtrain.arff");
			dataSet.setClassIndex(dataSet.numAttributes() - 2);
			classifiers = (Map<String, Classifier>) SerializationHelper.read("data/model/user.model");
		} catch (Exception e) {
			throw new IllegalArgumentException("未找到分类器模型");
		}
	}
	
	// 对测试数据进行计算，并统计指标， 前提条件，所有的模型都已经训练完毕
	
	// 根据当前用户找出该用户的所有交易记录 ，对其进行指标统计(记录在文件内，方便调用)
	
	
	
	// 测试集 从训练集中划分出来
	public int predict(ICBCRecord trans){
		String user = trans.getUser();
		
		if (!users.containsKey(user)){
			System.err.println("注意：没有找到该用户的行为证书，应送入群体分类器进行学习---->" + user);
			return -1;
		}
		
		Statics stat = users.get(user);
		double transAmount = Double.parseDouble(trans.getFeature6());
		double maxAmount = Math.max(transAmount, Double.parseDouble(stat.max_money));
		double minAmount = Math.min(transAmount, Double.parseDouble(stat.min_money));
		double avgAmount = Double.parseDouble(stat.avg_money);
		double IP = Double.parseDouble(trans.getFeature8());
		double label = Double.parseDouble(trans.getLabel());
		Instance test = new DenseInstance(1, new double[]{transAmount, maxAmount, minAmount, avgAmount, IP});
		
		int clu = -1;
		try {
			clu = cluster.clusterInstance(test);
		} catch (Exception e) {
			throw new IllegalArgumentException("聚类模型测试失败");
		}
		
		int base = 100;
		Classifier classifier = classifiers.get(base + clu + "");
		
		double pred = -1;
		test = new DenseInstance(1, new double[]{transAmount, maxAmount, minAmount, avgAmount, IP, label});
		test.setDataset(dataSet);
		test.setClassValue(label);
		
		try {
			pred = classifier.classifyInstance(test);
		} catch (Exception e) {
			throw new IllegalArgumentException("分类器测试失败");
		}
		
		return pred > 0.5 ? 1 : 0;
	}
	
	
	/***
	 * 交易标签：
	 * 1 : 表示异常交易
	 * 0 : 表示正常交易
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ICBCPredict model = new ICBCPredict();
		LoadDataSet test = new LoadDataSet("data/ICBCtest.txt");
		List<Integer> real = new ArrayList<>();
		List<Integer> pred = new ArrayList<>();
		for (String user : test.getAllUsers()){
			for (com.demon.dao.Instance record : test.getDataSet().get(user)){
				if (record instanceof ICBCRecord){
					ICBCRecord rec = (ICBCRecord) record;
					int p = model.predict(rec);
					if (p != -1){
						int label = Integer.parseInt(rec.getLabel());
						if (label >= 7 && label <= 10){
							label = 1;
						}
						real.add(label);
						pred.add(p);
					}
				}
			}
		}
		
		Evaluation eval = new Evaluation(real, pred);
		eval.printLog();
	}
	
}
