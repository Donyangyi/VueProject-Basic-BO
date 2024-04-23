package kr.co.basic.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuBean {
	private String menuSeq;
	private String menuType;
	private String menuNm;
	private String menuUrl;
	private String parentSeq;
}
