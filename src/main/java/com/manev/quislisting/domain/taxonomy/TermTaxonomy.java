package com.manev.quislisting.domain.taxonomy;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ql_term_taxonomy")
@Inheritance
@DiscriminatorColumn(name = "taxonomy", discriminatorType = DiscriminatorType.STRING, length = 32)
public abstract class TermTaxonomy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "term_id")
    private Term term;

    @Column(name = "taxonomy", insertable = false, updatable = false)
    private String taxonomy;

    @Column
    private String description;

    @Column
    private Long parentId;

    @OneToMany(mappedBy = "parentId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TermTaxonomy> children;

    @Column
    private Long count = 0L;

    public TermTaxonomy() {
        this.term = new Term();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Set<TermTaxonomy> getChildren() {
        return children;
    }

    public void setChildren(Set<TermTaxonomy> children) {
        this.children = children;
    }

}