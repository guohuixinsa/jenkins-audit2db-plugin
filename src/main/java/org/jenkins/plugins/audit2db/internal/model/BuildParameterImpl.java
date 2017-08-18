/**
 * 
 */
package org.jenkins.plugins.audit2db.internal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
import   org.apache.commons.lang.builder.EqualsBuilder;
import   org.apache.commons.lang.builder.HashCodeBuilder;


import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.model.BuildParameter;

/**
 * Data class for build parameters.
 * 
 * @author Marco Scata
 *
 */
@Entity(name="jenkins_build_parma")
public class BuildParameterImpl implements BuildParameter {
    private int id;
    private String name;
    private String value;
    private BuildDetails buildDetails;

    public BuildParameterImpl() {
        super();
    }

    public BuildParameterImpl(final String name, final String value, final BuildDetails buildDetails) {
        //this.id = id;
        this.name = name;
        this.value = value;
        this.buildDetails = buildDetails;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#getId()
     */
    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    public int getId()
    {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#getName()
     */
    @Column(nullable=false, unique=false)
    @Override
    public String getName() {
        return name;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#setName(java.lang.String)
     */
    @Override
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#getValue()
     */
    @Column(nullable=true, unique=false)
    @Override
    public String getValue() {
        return value;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#setValue(java.lang.String)
     */
    @Override
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#getBuildDetails()
     */
    @ManyToOne(targetEntity=BuildDetailsImpl.class)
    @JoinColumn(nullable=false, unique=false)
    @Override
    public BuildDetails getBuildDetails() {
        return buildDetails;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#setBuildDetails(java.lang.String)
     */
    @Override
    public void setBuildDetails(final BuildDetails buildDetails) {
        this.buildDetails = buildDetails;
    }

    @Override
    public String toString() {
        return String.format("%d=%s",
                this.id, this.value);
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(id);
        hcb.append(buildDetails);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        // fail-fast logic
        if (this == obj) { return true; }
        if (null == obj) { return false; }
        if (!(obj instanceof BuildParameter)) { return false; }

        final BuildParameter other = (BuildParameter) obj;

        return other.hashCode() == this.hashCode();
    }
}
