package com.teeny.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

@Entity
@Table(name = "teeny_url")
@NamedQueries({
		@NamedQuery(name = "com.teeny.model.TeenyUrl.findAll",
				query = "select e from TeenyUrl e"),
		@NamedQuery(name = "com.teeny.model.TeenyUrl.findByUrl",
				query = "select e from TeenyUrl e where url = :url"),
		@NamedQuery(name = "com.teeny.model.TeenyUrl.findByKey",
				query = "select e from TeenyUrl e where key = :key")
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
	@NotNull
	@Column(name = "url")
	private String url;

	@XmlElement
	@Column(name = "teenykey")
	private String key;

	@XmlElement
	@NotNull
	@Column(name = "created_on")
	private long createdOn;
	
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}
}