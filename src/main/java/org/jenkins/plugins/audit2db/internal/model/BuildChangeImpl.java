package org.jenkins.plugins.audit2db.internal.model;

import javax.persistence.*;
import javax.print.DocFlavor;

import hudson.model.AbstractBuild;
import hudson.scm.ChangeLogSet;
import hudson.scm.ChangeLogSet.AffectedFile;
import org.hibernate.annotations.GenericGenerator;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.jenkins.plugins.audit2db.model.BuildChange;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.model.BuildParameter;
import org.jenkins.plugins.audit2db.model.ChangeFile;

import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by guohuixin on 2017/7/3.
 */
@Entity(name = "jenkins_build_changelog")
public class BuildChangeImpl implements BuildChange{

    private String commitMsg;
    private String Commiter;
    private String jobName;
    private String svnUrl;
    private String commitRev;
    private Date  commitDate;
    private int id;
    private int buildNum;
    private BuildDetails buildDetails;
    private Collection<? extends ChangeLogSet.AffectedFile>	 effectfiles;
    private final List<ChangeFile> changefiles = new ArrayList<ChangeFile>();

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

    @Override
    @Column(nullable = false, unique = false )
    public String getCommitMsg() {
        return commitMsg;
    }

    public void setCommitMsg(String commitMsg) {
        this.commitMsg = commitMsg;
    }

    @Override
    @Column(nullable = false, unique = false )
    public String getCommiter()
    {
        return Commiter;
    }

    @Override
    public void setCommiter( final  String Commiter )
    {
        this.Commiter = Commiter;
    }

    @Override
    @Column(nullable = false, unique = false )
    public String getJobName()
    {
        return jobName;
    }
    @Override
    public void setJobName( final String jobName )
    {
        this.jobName = jobName;
    }

    @Override
    @Column(nullable = false , unique = false)
    public String getCommitRev()
    {
        return commitRev;
    }
    public void setCommitRev(final String commitRev)
    {
        this.commitRev=commitRev;
    }

    @Override
    @Column(nullable = false , unique = false)
    public String getSvnUrl()
    {
        return svnUrl;
    }
    public void setSvnUrl(final String svnUrl)
    {
        this.svnUrl=svnUrl;
    }


    @Override
    @Column(nullable = false , unique = false)
    public Date getCommitDate()
    {
        return commitDate;
    }
    public void setCommitDate(final Date commitDate)

    {
        this.commitDate = commitDate;
    }
    //@Override

    @Override
    @Column(nullable = false , unique = false)
    public int getBuildNum(){
        return  this.buildNum;
    }
    public void setBuildNum(int buildNum)
    {
        this.buildNum = buildNum;
    }

    @ManyToOne(targetEntity=BuildDetailsImpl.class)
    @JoinColumn(nullable=false, unique=false)
    @Override
    public BuildDetails getBuildDetails()
    {
        return buildDetails;
    }

    @Override
    public void setBuildDetails(final BuildDetails buildDetails) {
        this.buildDetails = buildDetails;
    }

    @Override
    @OneToMany(targetEntity = ChangeFileImpl.class, cascade = CascadeType.ALL, mappedBy = "buildChange")
    @Column(nullable = true, unique = false)
    public List <ChangeFile> getChangefile() {
        return changefiles;
    }

    @Override
    public void setChangefile(final  List <ChangeFile> changefile) {
            final ChangeFile[] temchangfile = changefile.toArray(new ChangeFile[]{});
            this.changefiles.clear();
            Collections.addAll(this.changefiles, temchangfile);
    }


    public List<ChangeFile> CreateChangeFile( Collection<? extends AffectedFile>  affectfiles)
    {
        final List<ChangeFile> retval = new ArrayList<ChangeFile>();
        for ( AffectedFile file : affectfiles) {
            retval.add(new ChangeFileImpl(file.getPath(),file.getEditType().getName(),this));

        }
        return retval;
    }

    private Date getCommitDate(final long commitdate) {
        final Calendar commitDate = Calendar.getInstance();
        commitDate.setTimeInMillis(commitdate);
        commitDate.set(Calendar.HOUR_OF_DAY, 23);
        commitDate.set(Calendar.MINUTE, 59);
        commitDate.set(Calendar.SECOND, 59);
        commitDate.set(Calendar.MILLISECOND, 999);
        return commitDate.getTime();
    }



    public BuildChangeImpl()
    {
        super();
    }
    /**
     * Default constructor.
     */


    public BuildChangeImpl(String commitMsg,String commiter,long commitDate,String jobname,String svnUrl,String commitRev,
                           int buildNum,Collection<? extends AffectedFile> 	effectfiles,BuildDetails buildDetails) {
        this.commitMsg = commitMsg;
        this.Commiter = commiter;
        this.jobName = jobname;
        this.commitDate = getCommitDate(commitDate);
        this.svnUrl =  svnUrl;
        this.commitRev = commitRev;
        this.buildNum = buildNum;
        this.buildDetails=buildDetails;
        this.changefiles.addAll(CreateChangeFile(effectfiles));

    }

    public BuildChangeImpl(AbstractBuild<?, ?> build,BuildDetails buildetails) {
        this.svnUrl = buildetails.getSvnUrl();
        this.buildNum = build.getNumber();
        this.buildDetails = buildetails;
        this.jobName = build.getProject().getDisplayName();

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
        hcb.append(buildDetails);
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



}
