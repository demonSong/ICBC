package com.demon.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.demon.tools.DWriter;

public class NormalGene {
	
	Random random;
	Map<String, P> mem;
	
	NormalGene(){
		random = new Random();
		mem = new HashMap<>();
	}
	
	public String amount(int base, double diff){ // single user amount
		double mean = base + diff * base; 
		double variance = mean / 5;
		double amount = random.nextGaussian() * variance + mean;
		return String.format("%.4f", amount);
	}
	
	public List<String> time(int times, double[] rate, int month){
		List<String> trans = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		if (month < 10) sb.append("0" + month);
		else sb.append(month);
		// 1 - 7
		int a = (int) (times * rate[0]);
		for (int i = 0; i < a; ++i){
			trans.add(sb.toString() + day(1));
		}
		// 8 - 14
		int b = (int)(times * rate[1]);
		for (int i = 0; i < b; ++i){
			trans.add(sb.toString() + day(8));
		}
		
		// 15 - 21
		int c = (int)(times * rate[2]);
		for (int i = 0; i < c; ++i){
			trans.add(sb.toString() + day(15));
		}
		// 21 - 28
		int d = times - a - b - c;
		for (int i = 0; i < d; ++i){
			trans.add(sb.toString() + day(21));
		}
		Collections.sort(trans);
		return trans;
	}
	
	private String day(int base){
		int day = base + random.nextInt(7);
		if (day < 10) return "0" + day;
		return day + "";
	}
	
	
	class P{
		
		int amount;
		double diff;
		
		int fir;
		int sec;
		
		P(int amount, double diff, int fir, int sec){
			this.amount = amount;
			this.diff = diff;
			this.fir  = fir;
			this.sec  = sec;
		}

		@Override
		public String toString() {
			return "P [amount=" + amount + ", fir=" + fir + ", sec=" + sec + "]";
		}
	}
	
	public List<String> userNormalTrainData(String user){
		int[] amount = new int[]{1000, 5000, 10000};
		double diff = random.nextInt(21) * 1.0 / 100;
		int scale = amount[random.nextInt(3)];
		
		List<String> trans = new ArrayList<>();
		double[][] rates = ratio();
		// time = 50
		int fir = 1 + random.nextInt(10); // 1-10
		int sec = fir + 1 + random.nextInt(12 - fir);
		
		mem.put(user, new P(scale, diff, fir, sec));
		
		int k = 0;
		for (int i = 1; i <= 12; ++i){
			if (k > fir) k = 1;
			if (k > sec) k = 2;
			List<String> times = time(50, rates[k], i);
			for (int j = 0; j < times.size(); ++j){
				StringBuilder sb = new StringBuilder(user + " ");
				sb.append(times.get(j) + " ");
				sb.append(amount(scale, diff) + " ");
				sb.append("0");
				trans.add(sb.toString());
			}
		}
		return trans;
	}
	
	public List<String> userNormalTestData(String user){
		P p = mem.get(user);
		int scale = p.amount;
		double diff = p.diff;
		
		int fir = p.fir;
		int sec = p.sec;
		
		double[][] rates = ratio();
		
		List<String> trans = new ArrayList<>();
		
		int k = 0;
		for (int i = 1; i <= 12; ++i){
			if (k > fir) k = 1;
			if (k > sec) k = 2;
			List<String> times = time(50, rates[k], i);
			for (int j = 0; j < times.size(); ++j){
				StringBuilder sb = new StringBuilder(user + " ");
				sb.append(times.get(j) + " ");
				sb.append(amount(scale, diff) + " ");
				sb.append("0");
				trans.add(sb.toString());
			}
		}
		return trans;
	}
	
	
	public double[][] ratio(){ // single user three behavior
		double[][] rate = new double[3][4];
		rate[0] = new double[]{0.6, 0.2, 0.15, 0.05};
		rate[1] = new double[]{0.05, 0.4, 0.5, 0.05};
		rate[2] = new double[]{0.05, 0.15, 0.2, 0.6};
		return rate;
	}
	
	public static void main(String[] args) {
		DWriter out = new DWriter("data/data_train.txt");
		NormalGene g = new NormalGene();
		for (int i = 0; i < 300; ++i){
			List<String> trans = g.userNormalTrainData(String.format("%04d", i));
			for (String tran : trans){
				out.println(tran);
			}
		}
		out.close();
		out = new DWriter("data/data_test.txt");
		for (int i = 0; i < 300; ++i){
			List<String> trans = g.userNormalTestData(String.format("%04d", i));
			for (String tran : trans){
				out.println(tran);
			}
		}
		out.close();
		
		Set<String> users = new HashSet<>();
		while (users.size() < 100){
			int user = 1 + new Random().nextInt(300);
			String u = String.format("%04d", user);
			users.add(u);
		}
		
		
		out = new DWriter("data/data_train.txt");
		FraudGene fg = new FraudGene();
		for (String user : users){
			P feature = g.mem.get(user);
			List<String> trans = fg.add(user, 20, feature.amount, feature.diff);
			for (String tran : trans){
				out.println(tran);
			}
		}
		out.close();
		
		out = new DWriter("data/data_test.txt");
		for (String user : users){
			P feature = g.mem.get(user);
			List<String> trans = fg.add(user, 20, feature.amount, feature.diff);
			for (String tran : trans){
				out.println(tran);
			}
		}
		out.close();
	}
}
