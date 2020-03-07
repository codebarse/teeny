package com.teeny.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Url {
	
	@XmlElement
	public String url;
}
