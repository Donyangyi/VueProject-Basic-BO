package kr.co.basic.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comments {
	private String boardSeq;
	private String commentSeq;
	private String parentCommentSeq;
	private String commentText;
	private String commentRegiDate;

}
