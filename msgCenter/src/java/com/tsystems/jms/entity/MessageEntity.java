package com.tsystems.jms.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessageEntity {

	private Long id;  
    private String type;  
    private String name;  
    private String content;
    
    public MessageEntity() {
    	
    }
    
	public MessageEntity(Long id, String type, String name, String content) {
		
		this.id = id;
		this.type = type;
		this.name = name;
		this.content = content;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
