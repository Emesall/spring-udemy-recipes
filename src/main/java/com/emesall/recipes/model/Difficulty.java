package com.emesall.recipes.model;


public enum Difficulty {
	EASY("Easy"), 
	MEDIUM ("Medium"),
	HARD ("Hard");
	
	private final String displayName;
	
	private Difficulty(String name) {
		this.displayName=name;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
