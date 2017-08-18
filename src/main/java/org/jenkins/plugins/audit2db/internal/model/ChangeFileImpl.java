package org.jenkins.plugins.audit2db.internal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import  org.apache.commons.lang.builder.HashCodeBuilder;

import org.jenkins.plugins.audit2db.model.BuildChange;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.model.BuildParameter;
import org.jenkins.plugins.audit2db.model.ChangeFile;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by guohuixin on 2017/7/12.
 */
@Entity(name = "jenkins_build_changfile")
public class ChangeFileImpl implements ChangeFile{
    private int id;
    private String file;
    private String status;
    private BuildChange buildChange;

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    @Column(nullable = false, unique = false )
    public String getFile() {
        return file;
    }

    public void setFile(String file)
    {
        this.file = file;
    }


    @Override
    @Column(nullable = false, unique = false )
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne(targetEntity = BuildChangeImpl.class)
    @JoinColumn(name = "changeid", nullable = false)
    public BuildChange getBuildChange() {
        return buildChange;
    }

    public void setBuildChange(BuildChange buildchange) {
        this.buildChange = buildchange;
    }

    public BuildChange resolveBuildChange(BuildChange buildChange)
    {
        BuildChange bc = new BuildChangeImpl();
        return bc ;

    }

    public ChangeFileImpl(String file,String status,BuildChange buildchange) {
        this.file = file;
        this.status = status;
        this.buildChange = buildchange;
    }

    @Override
    public String toString()
    {
        return String.format("%s", this.id);
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(id);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        // fail-fast logic
        if (this == obj) { return true; }
        if (null == obj) { return false; }
        if (!(obj instanceof BuildChange)) { return false; }

        final BuildChange other = (BuildChange) obj;

        return other.hashCode() == this.hashCode();
    }

    public ChangeFileImpl() {
        super();
    }
}
