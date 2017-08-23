package com.demon.dao;

public class ICBCFeature implements Instance {
	
	public static final String[] heads = { "user", "transAmount", "maxMoney", "minMoney", "avgMoney", "IP", "label"};
	
	private String user;
	
	private String transAmount; // 实际一笔交易
	private String maxMoney; // 突出异常 （定义一个异常率）
	private String minMoney; 
	private String avgMoney; // 均值 在聚类本身难道不能反映出来么？
	
	private String ip; // 是否常用IP
	
	private String label;
	
	public ICBCFeature(){}
	
	public ICBCFeature(String data){
		String[] record = data.split("\\s+");
		this.user = record[0];
		this.transAmount = record[1];
		this.maxMoney = record[2];
		this.minMoney = record[3];
		this.avgMoney = record[4];
		this.ip = record[5];
		
		if (6 < record.length){
			this.label = record[6];
		}
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIP() {
		return ip;
	}

	public void setIP(String ip) {
		this.ip = ip;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(String maxMoney) {
		this.maxMoney = maxMoney;
	}

	public String getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(String minMoney) {
		this.minMoney = minMoney;
	}

	public String getAvgMoney() {
		return avgMoney;
	}

	public void setAvgMoney(String avgMoney) {
		this.avgMoney = avgMoney;
	}
	
	public String toStr() {
		//注意最后一个元素不带空格
		StringBuilder sb = new StringBuilder();
		sb.append(user + " ");
		sb.append(transAmount + " ");
		sb.append(maxMoney + " ");
		sb.append(minMoney + " ");
		sb.append(avgMoney + " "); 
		sb.append(ip + " ");
		
		if (label != null ) sb.append(label);
		return sb.toString(); 
	}
	
	@Override
	public String[] toHead() {
		return ICBCFeature.heads;
	}

	@Override
	public String[] toContent() {
		return new String[]{user, transAmount, maxMoney, minMoney, avgMoney, ip, label};
	}

}
