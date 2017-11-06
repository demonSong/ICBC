package com.demon.newModel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;

import com.demon.tools.DWriter;

public class FeatureExtract {
	
	LoadData dataSet;
	
	public FeatureExtract(String fileName) {
		dataSet = new LoadData(fileName);
		for (String user : dataSet.getAllUsers()){
			extract(user);
		}
	}
	
	public void extract(String user){
		List<DataRecord> records = dataSet.getDataSet().get(user);
		
		Collections.sort(records, new Comparator<DataRecord>() {
			@Override
			public int compare(DataRecord o1, DataRecord o2) {
				String time1 = o1.time;
				String time2 = o2.time;
				
				String p1 = o1.label;
				String p2 = o2.label;
				
				return time1.compareTo(time2) == 0 ? p2.compareTo(p1) : time1.compareTo(time2);
			}
		});
		
		int n = records.size();
		
		double[] money = new double[records.size()];
		for (int i = 0; i < records.size(); ++i){
			money[i] = Double.parseDouble(records.get(i).amount);
		}
		
		double[] max = maxSlidingWindow(money, NewFeatureRecord.WINDOW_SIZE);
		double[] min = minSlidingWindow(money, NewFeatureRecord.WINDOW_SIZE);
		
		List<NewFeatureRecord> ans = new ArrayList<>();
		
		double sum = 0;
		for (int i = 0; i < NewFeatureRecord.WINDOW_SIZE - 1; ++i){
			sum += money[i]; // 0 ~ 49
		}
		
		int[] time = new int[n];
		for (int i = 0; i < n; ++i){
			String date = records.get(i).time;
			int month = Integer.parseInt(date.substring(0, 2));
			int day   = Integer.parseInt(date.substring(2));
			time[i] = 28 * (month - 1) + day;
		}
		
		List<String> gap = new ArrayList<>();
		for (int i = 1; i < NewFeatureRecord.WINDOW_SIZE - 1; ++i){
			int g = time[i] - time[i - 1];
			gap.add(g + "");
		}
		
		
		int k = n - NewFeatureRecord.WINDOW_SIZE + 1;
		for (int i = 0; i < k; ++i){
			int g = time[i + NewFeatureRecord.WINDOW_SIZE - 1] - time[i + NewFeatureRecord.WINDOW_SIZE - 2];
			gap.add(g + "");
			sum += money[i + NewFeatureRecord.WINDOW_SIZE - 1];
			
			NewFeatureRecord nfeature = new NewFeatureRecord();
			nfeature.setAmount(records.get(i + NewFeatureRecord.WINDOW_SIZE - 1).amount);
			nfeature.setMaxAmount(String.valueOf(max[i]));
			nfeature.setMinAmount(String.valueOf(min[i]));
			
			double averageMoney = sum / NewFeatureRecord.WINDOW_SIZE;
			nfeature.setAverageAmount(String.valueOf(averageMoney));
			sum -= money[i];
			
			nfeature.setGap(gap.toArray(new String[0]));
			gap.remove(0);
			
			nfeature.setLabel(records.get(i + NewFeatureRecord.WINDOW_SIZE - 1).label);
			nfeature.setUser(records.get(i + NewFeatureRecord.WINDOW_SIZE - 1).user);
			
			ans.add(nfeature);
		}
		
		DWriter out = new DWriter("data/sjh/feature/data_train_featured.txt");
		for (NewFeatureRecord rec : ans){
			out.println(rec.toStr());
		}
		out.close();
	}
	
	private double[] maxSlidingWindow(double[] nums, int k){
		int n = nums.length;
		double[] ans = new double[n - k + 1];
		Deque<Integer> queue = new ArrayDeque<>();
		for (int i = 0, c = 0; i < n; ++i){
			while (!queue.isEmpty() && queue.peek() < i - k + 1) queue.poll();
			while (!queue.isEmpty() && Double.compare(nums[queue.peekLast()], nums[i]) <= 0) queue.pollLast();
			queue.offer(i);
			if (i >= k - 1) ans[c++] = nums[queue.peek()]; 
		}
		return ans;
	}
	
	private double[] minSlidingWindow(double[] nums, int k){
		int n = nums.length;
		double[] ans = new double[n - k + 1];
		Deque<Integer> queue = new ArrayDeque<>();
		for (int i = 0, c = 0; i < n; ++i){
			while (!queue.isEmpty() && queue.peek() < i - k + 1) queue.poll();
			while (!queue.isEmpty() && Double.compare(nums[queue.peekLast()], nums[i]) >= 0) queue.pollLast();
			queue.offer(i);
			if (i >= k - 1) ans[c++] = nums[queue.peek()];
		}
		return ans;
	}
	
	public static void main(String[] args) {
		new FeatureExtract("data/data_train.txt");
	}

}
