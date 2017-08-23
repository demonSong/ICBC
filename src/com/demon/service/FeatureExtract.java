package com.demon.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demon.dao.ICBCFeature;
import com.demon.dao.ICBCRecord;
import com.demon.dao.Instance;
import com.demon.tools.DWriter;
import com.demon.tools.DataToCSV;
import com.demon.tools.FileHelper;

import weka.core.SerializationHelper;

/**
 * 做特征处理 + 特征持久化 + 转csv
 * @author Administrator
 *
 */
public class FeatureExtract {
	
	// load DataSet
	private LoadDataSet dataSet;
	
	// 输出
	private DWriter out;
	private String file;
	
	//每个用户的指标统计
	private Map<String, Statics> cache;
	
	public FeatureExtract(String fileName){
		cache = new HashMap<>();
		String[] splits = fileName.split("/");
		this.file = splits[splits.length - 1];
		dataSet = new LoadDataSet(fileName);
		processFeature(dataSet);
	}
	
	private void processFeature(LoadDataSet dataSet){
		String fileName = "data/process/feature/feature_" + file;
		FileHelper.clearFile(fileName);
		out = new DWriter(fileName);
		for (String user : dataSet.getAllUsers()){
			List<Instance> records = dataSet.getDataSet().get(user);
			// 每个用户有个 统计指标
			Statics stat = new Statics(records);
			cache.put(user, stat);
			for (Instance record : records){
				if (record instanceof ICBCRecord){
					ICBCRecord rec = (ICBCRecord) record;
					ICBCFeature feature = new ICBCFeature();
					feature.setUser(user);
					feature.setLabel(rec.getLabel());
					
					// 交易金额维度
					feature.setTransAmount(rec.getFeature6());
					feature.setMaxMoney(stat.max_money);
					feature.setMinMoney(stat.min_money);
					feature.setAvgMoney(stat.avg_money);
					
					// 常用IP维度
					feature.setIP(rec.getFeature8()); //是否常用IP
					
					// 交易时间维度
					
					// 交易地点维度
					
					out.println(feature.toStr());
				}
			}
		}
		out.close();
		new DataToCSV(new LoadFeatureSet(fileName), fileName, new ICBCFeature());
		try {
			String objFile = "data/stats/total.obj";
			FileHelper.clearFile(objFile);
			SerializationHelper.write(objFile, cache);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		FeatureExtract feature = new FeatureExtract("data/process/normal_ICBCtrain.txt");
		feature = new FeatureExtract("data/process/abnormal_ICBCtrain.txt");
	}
	
}
