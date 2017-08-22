package com.demon.dao;

public class ICBCRecord {
	
	private String user;
	
	private String feature1;
	
	private String feature2;
	
	private String feature3;
	
	private String feature4;
	
	private String feature5;
	
	private String feature6;
	
	private String feature7;
	
	private String feature8;
	
	private String label;
	
	public ICBCRecord(){
	}

	public ICBCRecord(String record){
		String[] splits = record.split("\\s+");
		this.user = splits[0];
		this.feature1 = splits[1];
		this.feature2 = splits[2];
		this.feature3 = splits[3];
		this.feature4 = splits[4];
		this.feature5 = splits[5];
		this.feature6 = splits[6];
		this.feature7 = splits[7];
		this.feature8 = splits[8];
		this.label = splits[9];
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getFeature1() {
		return feature1;
	}

	public void setFeature1(String feature1) {
		this.feature1 = feature1;
	}

	public String getFeature2() {
		return feature2;
	}

	public void setFeature2(String feature2) {
		this.feature2 = feature2;
	}

	public String getFeature3() {
		return feature3;
	}

	public void setFeature3(String feature3) {
		this.feature3 = feature3;
	}

	public String getFeature4() {
		return feature4;
	}

	public void setFeature4(String feature4) {
		this.feature4 = feature4;
	}

	public String getFeature5() {
		return feature5;
	}

	public void setFeature5(String feature5) {
		this.feature5 = feature5;
	}

	public String getFeature6() {
		return feature6;
	}

	public void setFeature6(String feature6) {
		this.feature6 = feature6;
	}

	public String getFeature7() {
		return feature7;
	}

	public void setFeature7(String feature7) {
		this.feature7 = feature7;
	}

	public String getFeature8() {
		return feature8;
	}

	public void setFeature8(String feature8) {
		this.feature8 = feature8;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String toStr(){
		StringBuilder sb = new StringBuilder();
		sb.append(user + " ");
		sb.append(feature1 + " ");
		sb.append(feature2 + " ");
		sb.append(feature3 + " ");
		sb.append(feature4 + " ");
		sb.append(feature5 + " ");
		sb.append(feature6 + " ");
		sb.append(feature7 + " ");
		sb.append(feature8 + " ");
		sb.append(label);
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "ICBCRecord [user=" + user + ", feature1=" + feature1 + ", feature2=" + feature2 + ", feature3="
				+ feature3 + ", feature4=" + feature4 + ", feature5=" + feature5 + ", feature6=" + feature6
				+ ", feature7=" + feature7 + ", feature8=" + feature8 + ", label=" + label + "]";
	}
}
