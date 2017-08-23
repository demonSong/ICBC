package com.demon.service;

import java.util.List;

public class Evaluation {
	
	int TP;
	int TN;
	int FP;
	int FN;
	
	
	double accuracy;
	double recall;
	double specificity;
	double precision;
	double f_measure;
	double g_mean;
	
	
	/**
	 * label : 1: 表示欺诈
	 *         0: 表示正常
	 * @param real
	 * @param pred
	 */
	public Evaluation(List<Integer> real, List<Integer> pred){
		TP = 0; TN = 0; FP = 0; FN = 0;
		int n = real.size();
		for (int i = 0; i < n; ++i){
			int re = real.get(i);
			int pe = pred.get(i);
			if (re == 1 && pe == 1){
				TP ++;
			}
			if (re == 0 && pe == 0){
				TN ++;
			}
			if (re == 0 && pe == 1){
				FP ++;
			}
			if (re == 1 && pe == 0){
				FN ++;
			}
		}
		
		accuracy = (TP + TN) / (1.0 * (TP + FP + TN + FN));
		recall = TP / (1.0 * (TP + FN));
		specificity = TN / (1.0 * (FP + TN));
		precision = TP / (1.0 * (TP + FP));
		f_measure = 2 * precision * recall / (precision + recall);
		g_mean = Math.sqrt(recall * specificity);
		
	}
	
	public String printLog(){
		StringBuilder sb = new StringBuilder();
		sb.append("-------------数据检测指标-------------\n");
		sb.append("样本总数：   " + (TP + FP + TN + FN) + "\n");
		sb.append("欺诈检测为欺诈：   " + TP + "\n");
		sb.append("正常检测为正常：   " + TN + "\n");
		sb.append("欺诈检测为正常：   " + FN + "\n");
		sb.append("正常检测为异常：   " + FP + "\n");
		sb.append(String.format("accuracy:     %.6f", accuracy) + "\n");
		sb.append(String.format("recall:       %.6f", recall) + "\n");
		sb.append(String.format("specificity:  %.6f", specificity) + "\n");
		sb.append(String.format("precision:    %.6f", precision) + "\n");
		sb.append(String.format("f_measure:    %.6f", f_measure) + "\n");
		sb.append(String.format("g_mean:       %.6f", g_mean) + "\n");
		System.out.println(sb.toString());
		return sb.toString();
	}

}
