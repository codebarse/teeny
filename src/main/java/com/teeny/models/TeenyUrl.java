package com.teeny.models;

import javax.persistence.*;

@Entity
@Table(name = "teeny_url")
@NamedQueries({
        @NamedQuery(name = "com.teeny.models.TeenyUrl.findAll",
                query = "select id, url from TeenyUrl")
})

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