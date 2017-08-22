package com.demon.main;

import com.demon.service.DataDivider;

public class Client {
	
	public Client(){
		// 加载数据
		
		// 样本分类 
		DataDivider divider = new DataDivider("data/ICBCTrain.txt");
		
		// 特征工程
		
		// 模型训练
		
		// 模型加载
		
		// 测试输出
	}
	
	public static void main(String[] args) {
		new Client();
	}
}
