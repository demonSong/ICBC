package com.demon.service;

import java.util.List;

import com.demon.dao.ICBCFeature;
import com.demon.dao.ICBCRecord;
import com.demon.dao.Instance;
import com.demon.tools.DWriter;
import com.demon.tools.DataToCSV;

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
	
	public FeatureExtract(String fileName){
		String[] splits = fileName.split("/");
		this.file = splits[splits.length - 1];
		dataSet = new LoadDataSet(fileName);
		processFeature(dataSet);
	}
	
	public void processFeature(LoadDataSet dataSet){
		String fileName = "data/process/feature/feature_" + file;
		out = new DWriter(fileName);
		for (String user : dataSet.getAllUsers()){
			List<Instance> records = dataSet.getDataSet().get(user);
			Statics stat = new Statics(records);
			for (Instance record : records){
				if (record instanceof ICBCRecord){
					ICBCRecord rec = (ICBCRecord) record;
					ICBCFeature feature = new ICBCFeature();
					feature.setUser(user);
					
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
	}
	
	class Statics{
		
		String max_money;
		String min_money;
		String avg_money;
		
		public Statics(List<Instance> records){
			
			int n = records.size();
			
			// 计算交易金额的一些特征
			
			double max = Double.NEGATIVE_INFINITY;
			double min = Double.POSITIVE_INFINITY;
			double avg = 0.0;
			
			double sum = 0.0;
			for (Instance rec : records){
				if (rec instanceof ICBCRecord){
					ICBCRecord record = (ICBCRecord) rec;
					double money = Double.parseDouble(record.getFeature6());
					max = Math.max(max, money);
					min = Math.min(min, money);
					sum += money;
				}
			}
			
			avg = sum / n;

			max_money = String.valueOf(max);
			min_money = String.valueOf(min);
			avg_money = String.valueOf(String.format("%.3f", avg));
		}
	}
	
	
	public static void main(String[] args) {
		FeatureExtract feature = new FeatureExtract("data/process/normal_ICBCtrain.txt");
		feature = new FeatureExtract("data/process/abnormal_ICBCtrain.txt");
	}
	
}
