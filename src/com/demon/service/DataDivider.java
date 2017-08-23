package com.demon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.demon.dao.ICBCRecord;
import com.demon.dao.Instance;
import com.demon.tools.DWriter;
import com.demon.tools.FileHelper;

public class DataDivider {
	
	private static final String NORMAL = "data/process/normal_";
	private static final String ABNORMAL = "data/process/abnormal_";
	
	private LoadDataSet loader;
	private DWriter out;
	private String dataSetName;
	
	/**
	 * 
	 * @param fileName
	 * @param withTest
	 */
	public DataDivider(String dataSetName, boolean withTest){
		this.dataSetName = dataSetName;
		String fileName = "data/" + dataSetName + "train.txt";
		loader = new LoadDataSet(fileName);
		String[] splits = fileName.split("/");
		
		if (!withTest){
			extractTest(loader.getDataSet(), 0.3);
		}
		
		loader = new LoadDataSet(fileName);
		divide(loader.getDataSet(), splits[splits.length - 1]);
	}
	
	private void extractTest(Map<String, List<Instance>> dataSet, double ratio){
		List<Instance> test = new ArrayList<>();
		List<Instance> train = new ArrayList<>();
		
		for (String user : dataSet.keySet()){
			List<Instance> records = dataSet.get(user);
			int n = records.size();
			int testSize = (int) (n * ratio);
			for (int i = 0; i < testSize; ++i){
				Random rand = new Random();
				int nxt = rand.nextInt(records.size());
				Instance hit = records.get(nxt);
				test.add(hit);
				records.remove(hit);
			}
			train.addAll(records);
		}
		
		FileHelper.clearFile("data/" + dataSetName + "test.txt");
		out = new DWriter("data/" + dataSetName + "test.txt");
		for (Instance record : test){
			if (record instanceof ICBCRecord){
				ICBCRecord rec = (ICBCRecord) record;
				out.println(rec.toStr());
			}
		}
		out.close();
		
		FileHelper.clearFile("data/" + dataSetName + "train.txt");
		out = new DWriter("data/" + dataSetName + "train.txt");
		for (Instance record : train){
			if (record instanceof ICBCRecord){
				ICBCRecord rec = (ICBCRecord) record;
				out.println(rec.toStr());
			}
		}
		out.close();
	}
	
	
	/**
	 * 过滤出正常样本 和异常样本
	 * @param dataSet
	 */
	private void divide(Map<String, List<Instance>> dataSet, String fileName){
		FileHelper.clearFile(NORMAL + fileName);
		FileHelper.clearFile(ABNORMAL + fileName);
		
		out = new DWriter(NORMAL + fileName);
		List<String> abnormal = new ArrayList<>();
		for (String user : dataSet.keySet()){
			for (Instance record : dataSet.get(user)){
				if (record instanceof ICBCRecord){
					ICBCRecord rec = (ICBCRecord)record;
					if (!isAbnormal(rec)){
						out.println(rec.toStr());
					}
					else{
						abnormal.add(rec.toStr());
					}
				}
			}
		}
		out.close();
		
		out = new DWriter(ABNORMAL + fileName);
		for (String record : abnormal){
			out.println(record);
		}
		out.close();
	}
	
	
	
	
	/**
	 * 检测出欺诈群体
	 * @param record
	 * @return
	 */
	private boolean isAbnormal(ICBCRecord record){
		int label = Integer.parseInt(record.getLabel());
		return label >= 7 && label <= 10;
	}
	
	
	public static void main(String[] args) {
		DataDivider divider = new DataDivider("Demo", false);
	}
}
