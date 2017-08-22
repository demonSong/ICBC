package com.demon.tools;

import java.util.List;
import java.util.Random;

public class DRandom {
	
	private Random rand;
	
	public DRandom(){
		this.rand = new Random();
	}
	
	public String nextSet(List<String> set){
		int size = set.size();
		int r = rand.nextInt(size);
		return set.get(r);
	}
	
	public String nextInt(int len){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < len; ++i){
			int r = rand.nextInt(10);
			sb.append(r + "");
		}
		return sb.toString();
	}
	
	public String nextChar(int len, boolean upper){
		StringBuilder sb = new StringBuilder();
		char base = upper ? 'A' : 'a';
		for(int i = 0; i < len; ++i){
			int r = rand.nextInt(26);
			char c = (char) (base + r);
			sb.append(c);
		}
		return sb.toString();
	}
	
	public String nextBoolean(){
		return String.valueOf(rand.nextInt(2));
	}
	
	public String nextDoule(){
		int from = rand.nextInt(10000);
		int dist = rand.nextInt(10000);
		int precision = rand.nextInt(6) + 1;
		return nextDouble(from, from + dist, precision);
	}
	
	public String nextDouble(int from, int to, int precision){
		StringBuilder sb = new StringBuilder();
		int r = rand.nextInt(to - from + 1) + from;
		sb.append(r + ".");
		for (int i = 0; i < precision; ++i){
			int n = rand.nextInt(10);
			sb.append(n);
		}
		return sb.toString();
	}
	
	public String nextIP(){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; ++i){
			int ip = 100 + rand.nextInt(156);
			sb.append("." + ip);
		}
		return sb.deleteCharAt(0).toString();
	}
	
	public String nextDate(){
		StringBuilder sb = new StringBuilder();
		int year = 2017 + rand.nextInt(5);
		int month = 1 + rand.nextInt(12);
		int day = 1 + rand.nextInt(31);
		int hour = rand.nextInt(24);
		int minute = rand.nextInt(60);
		int second = rand.nextInt(60);
		
		sb.append(year);
		sb.append(needZero(month) ? "0" + month : month);
		sb.append(needZero(day) ? "0" + day : day);
		sb.append(needZero(hour) ? "0" + hour : hour);
		sb.append(needZero(minute) ? "0" + minute :minute);
		sb.append(needZero(second) ? "0" + second : second);
		
		return sb.toString();
	}
	
	private boolean needZero(int num){
		return num >= 0 && num <= 9;
	}
	
	public static void main(String[] args) {
		DRandom random = new DRandom();
		for(int i = 0; i < 20; ++i){
			System.out.println(random.nextIP());
		}
	}
}
