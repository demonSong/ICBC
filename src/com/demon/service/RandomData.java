package com.demon.service;

import java.util.ArrayList;
import java.util.List;

import com.demon.tools.DRandom;
import com.demon.tools.DWriter;

public class RandomData {
	
	private static String randomData(List<String> users, List<String> merchants){
		DRandom rand = new DRandom();
		StringBuilder sb = new StringBuilder();
		
		// 1. USER
		sb.append(rand.nextSet(users) + " ");
		
		// 2. feature 1
		sb.append(rand.nextChar(1, true) + " ");
		
		// 3. feature 2
		sb.append(rand.nextInt(1) + " ");
		
		// 4. feature 3
		sb.append(rand.nextInt(1) + " ");
		
		// 5. feature 4
		sb.append(rand.nextDate() + " ");
		
		// 6. feature 5
		sb.append(rand.nextIP() + " ");
		
		// 7. feature 6
		sb.append(rand.nextDoule() + " ");
		
		// 8. feature 7
		sb.append(rand.nextSet(merchants) + " ");
		
		// 9. feature 8
		sb.append(rand.nextBoolean() + " ");
		
		// 10. label
		sb.append(rand.nextInt(1));
		
		return sb.toString();
	}
	
	private static List<String> initUser(int len, int size){
		List<String> set = new ArrayList<>();
		DRandom rand = new DRandom();
		for (int i = 0; i < size; ++i){
			set.add(rand.nextInt(len));
		}
		return set;
	}
	
	private static List<String> initMerchant(int size){
		List<String> set = new ArrayList<>();
		DRandom rand = new DRandom();
		for(int i = 0; i < size; ++i){
			set.add(rand.nextInt(4) + rand.nextChar(2, true) + rand.nextInt(8));
		}
		return set;
	}
	
	public static void persistData(String path, int num){
		DWriter out = new DWriter(path + "train.txt");
		List<String> users = initUser(15, 1600);
		List<String> merchants = initMerchant(50);
		
		for (int i = 0; i < num; ++i){
			out.println(randomData(users, merchants));
		}
		out.close();
		
		out = new DWriter(path + "test.txt");
		for (int i = 0; i < num / 3; ++i){
			out.println(randomData(users, merchants));
		}
		out.close();
	}
	
	public static void main(String[] args) {
		RandomData.persistData("data/ICBC", 16592);
	}
}
