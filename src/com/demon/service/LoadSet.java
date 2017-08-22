package com.demon.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.demon.dao.Instance;

public interface LoadSet {
	
	public Map<String, List<Instance>> getDataSet();
	
	public Set<String> getAllUsers();

}
