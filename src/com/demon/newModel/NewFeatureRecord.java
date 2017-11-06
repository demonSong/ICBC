package com.demon.newModel;

import java.util.Arrays;

import com.demon.dao.Instance;

public class NewFeatureRecord implements Instance{
	
	public static final int WINDOW_SIZE = 50;
	
	private String user;
	private String amount;
	private String maxAmount;
	private String minAmount;
	private String averageAmount;
	
	private String label;
	private String[] gap = new String[WINDOW_SIZE - 1];
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public NewFeatureRecord() {}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}

	public String getAverageAmount() {
		return averageAmount;
	}

	public void setAverageAmount(String averageAmount) {
		this.averageAmount = averageAmount;
	}

	public String[] getGap() {
		return gap;
	}

	public void setGap(String[] gap) { // 49 gap
		this.gap = gap;
	}

	@Override
	public String[] toHead() {
		String[] heads = new String[54];
		heads[0] = "users";
		for (int i = 0; i < 49; ++i){
			heads[i + 1] = "gap" + i;
		}
		heads[50] = "amount";
		heads[51] = "maxAmount";
		heads[52] = "minAmount";
		heads[53] = "averageAmount";
		
		return heads;
	}

	@Override
	public String[] toContent() {
		return null;
	}

	@Override
	public String toStr() {
		StringBuilder sb = new StringBuilder();
		sb.append(user + " ");
		for (int i = 0; i < WINDOW_SIZE - 1; ++i){
			sb.append(gap[i] + " ");
		}
		sb.append(amount + " ");
		sb.append(maxAmount + " ");
		sb.append(minAmount + " ");
		sb.append(averageAmount + " ");
		sb.append(label);
		return sb.toString();
	}

	@Override
	public String toString() {
		return "NewFeatureRecord [user=" + user + ", amount=" + amount + ", maxAmount=" + maxAmount + ", minAmount="
				+ minAmount + ", averageAmout=" + averageAmount + ", label=" + label + ", gap=" + Arrays.toString(gap)
				+ "]";
	}
}
