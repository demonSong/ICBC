package com.demon.service;

import java.io.Serializable;
import java.util.List;

import com.demon.dao.ICBCRecord;
import com.demon.dao.Instance;

public class Statics implements Serializable {

	private static final long serialVersionUID = 8995119939367847401L;
	
	public String max_money;
	public String min_money;
	public String avg_money;
	
	public String size;

	public Statics(List<Instance> records) {

		int n = records.size();
		this.size = n + "";
		
		// 计算交易金额的一些特征

		double max = Double.NEGATIVE_INFINITY;
		double min = Double.POSITIVE_INFINITY;
		double avg = 0.0;

		double sum = 0.0;
		for (Instance rec : records) {
			if (rec instanceof ICBCRecord) {
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