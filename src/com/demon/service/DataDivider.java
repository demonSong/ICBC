package com.demon.service;

import java.util.List;
import java.util.Map;

import com.demon.dao.ICBCRecord;
import com.demon.tools.DWriter;

public class DataDivider {
	
	private static final String OUT_PUT_PATH = "data/process/div_";
	
	private LoadDataSet loader;
	private DWriter out;
	
	public DataDivider(String fileName){
		loader = new LoadDataSet(fileName);
		String[] splits = fileName.split("/");
		out = new DWriter(OUT_PUT_PATH + splits[splits.length - 1]);
		divide(loader.getDataSet());
	}
	
	private void divide(Map<String, List<ICBCRecord>> dataSet){
		for (String user : dataSet.keySet()){
			for (ICBCRecord record : dataSet.get(user)){
				if (!isAbnormal(record)){
					out.println(record.toStr());
				}
			}
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
