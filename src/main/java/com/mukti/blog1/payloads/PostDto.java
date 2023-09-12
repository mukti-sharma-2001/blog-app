package com.mukti.blog1.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.mukti.blog1.entities.Comment;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	private Integer postId;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	private UserDto user;
	private CategoryDto category;
	private Set<CommentDto>comments=new HashSet();
	
	

	@Override
	public String toString() {
		return "PostDto [postId=" + postId + ", title=" + title + ", content=" + content + ", imageName=" + imageName
				+ ", addedDate=" + addedDate + ", user=" + user + ", category=" + category + "]";
	}
	
	
}
