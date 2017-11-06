package com.demon.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.demon.tools.DWriter;

public class FraudGene {
	
	Random random;
	
	FraudGene(){
		random = new Random();
	}
	
	double[] ratio(){
		return new double[]{0.25, 0.25, 0.25, 0.25};
	}
	
	public String amount(int base, double diff){ // single user amount  2000, 20%
		double mean = base + diff * base; 
		double variance = mean / 5;
		double amount = random.nextGaussian() * variance + mean;
		return String.format("%.4f", amount);
	}
	
	public List<String> add(String user, int times, int base, double diff){
		List<String> trans = new ArrayList<>();
		StringBuilder sb = new StringBuilder(user + " ");
		int month = 1 + random.nextInt(12);
		if (month < 10) sb.append("0" + month);
		else sb.append(month);
		
		int day = 1 + random.nextInt(28);
		if (day < 10) sb.append("0" + day + " ");
		else sb.append(day + " ");
		
		for (int i = 0; i < times; ++i){
			trans.add(sb.toString() + amount(base, diff) + " " + 1);
		}
		return trans;
	}
	
	
	
	public static void main(String[] args) {
		DWriter out = new DWriter("data/data_train.txt");
		DWriter out_test = new DWriter("data/data_test.txt");
		// time amount
		FraudGene g = new FraudGene();
		// 100
		Set<String> users = new HashSet<>();
		while (users.size() < 100){
			int user = 1 + new Random().nextInt(300);
			String u = String.format("%04d", user);
			users.add(u);
		}
	}

}
