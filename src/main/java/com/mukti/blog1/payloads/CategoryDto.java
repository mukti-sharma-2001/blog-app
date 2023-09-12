package com.mukti.blog1.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CategoryDto {
	private Integer categoryId;
	@NotEmpty
	@Size(min=4,max=20,message="Size of your title must be in between 4 and 20 included")
	private String categoryTitle;
	@NotEmpty
	@Size(min=4,max=30,message="Size of your description must be in between 4 and 30 included")
	private String categoryDescription;
	public CategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	
}
