package com.mukti.blog1.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
	private Integer commentId;
	private String content;

}
