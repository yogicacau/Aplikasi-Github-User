package com.makhalibagas.submission2.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.makhalibagas.submission2.Model.UserGithub;

public class ResponseSearch {

	@SerializedName("total_count")
	private int totalCount;

	@SerializedName("incomplete_results")
	private boolean incompleteResults;

	@SerializedName("items")
	private List<UserGithub> items;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public boolean isIncompleteResults() {
		return incompleteResults;
	}

	public void setIncompleteResults(boolean incompleteResults) {
		this.incompleteResults = incompleteResults;
	}

	public List<UserGithub> getItems() {
		return items;
	}

	public void setItems(List<UserGithub> items) {
		this.items = items;
	}

	@Override
 	public String toString(){
		return 
			"UserSearchGithub{" + 
			"total_count = '" + totalCount + '\'' + 
			",incomplete_results = '" + incompleteResults + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}