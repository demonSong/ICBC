package com.demon.newModel;

import com.demon.dao.Instance;

public class DataRecord implements Instance{
	
	public static final String[] heads = {"time", "amount", "label"};
	
	String user;
	String time;
	String amount;
	String label;
	
	DataRecord(){}
	
	DataRecord(String data){
		String[] line = data.split("\\s+");
		this.user   = line[0];
		this.time   = line[1];
		this.amount = line[2];
		this.label  = line[3];
	}
	
	
	@Override
	public String[] toHead() {
		return DataRecord.heads;
	}

	@Override
	public String[] toContent() {
		return new String[]{time, amount, label};
	}

	@Override
	public String toStr() {
		StringBuilder sb = new StringBuilder();
		sb.append(user + " ");
		sb.append(time + " ");
		sb.append(amount + " ");
		sb.append(label + " ");
		return sb.toString(); 
	}
}
