package com.demon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.demon.dao.ICBCRecord;
import com.demon.dao.Instance;
import com.demon.tools.DWriter;

public class DataDivider {
	
	private static final String NORMAL = "data/process/normal_";
	private static final String ABNORMAL = "data/process/abnormal_";
	
	private LoadDataSet loader;
	private DWriter out;
	
	public DataDivider(String fileName){
		loader = new LoadDataSet(fileName);
		String[] splits = fileName.split("/");
		divide(loader.getDataSet(), splits[splits.length - 1]);
	}
	
	
	/**
	 * 过滤出正常样本 和异常样本
	 * @param dataSet
	 */
	private void divide(Map<String, List<Instance>> dataSet, String fileName){
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
		DataDivider divider = new DataDivider("data/ICBCtrain.txt");
	}
}
