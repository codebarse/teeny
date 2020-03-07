package com.teeny.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "teeny_url")
@NamedQueries({
		@NamedQuery(name = "com.teeny.model.TeenyUrl.findAll",
				query = "select e from TeenyUrl e")
})
@XmlRootElement
public class TeenyUrl {
	/**
	 * Entity's unique identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/**
	 * Complete Url
	 */
	@XmlElement
	@Column(name = "url")
	private String url;
	
	public TeenyUrl() {
	}
	
	public TeenyUrl(String url) {
		this.url = url;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}