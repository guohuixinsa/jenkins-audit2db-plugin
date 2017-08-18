package org.jenkins.plugins.audit2db.internal.model;

import hudson.model.AbstractBuild;
import hudson.model.Cause;
import hudson.model.CauseAction;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.jenkins.plugins.audit2db.model.BuildJob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;


@Entity(name = "jenkins_build_jobs")
public class BuildJobImpl implements BuildJob {
    private int id;
    private String jobName;

    @Override
    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    @Override
    @Column(unique = true, nullable = false)
    public String getJobName() {
        return jobName;
    }
    @Override
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public BuildJobImpl(final AbstractBuild<?,?> build) {
        this.jobName=build.getRootBuild().getProject().getDisplayName();
    }

    /* public BuildJobImpl(String name) {
            this.jobName = name;
        }
    */
    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(jobName);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        // fail-fast logic
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (!(obj instanceof BuildJobImpl)) {
            return false;
        }

        final BuildJobImpl other = (BuildJobImpl) obj;

        return other.hashCode() == this.hashCode();
    }

    public BuildJobImpl() {
        super();
    }
}