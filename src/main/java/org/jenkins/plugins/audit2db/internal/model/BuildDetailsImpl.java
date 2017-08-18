/**
 *
 */
package org.jenkins.plugins.audit2db.internal.model;

import hudson.model.*;
import hudson.model.Cause.UserIdCause;
import hudson.scm.ChangeLogSet;
import hudson.scm.ChangeLogSet.Entry;
import hudson.scm.SCM;
import hudson.scm.SubversionSCM;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.jenkins.plugins.audit2db.model.*;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;

import javax.persistence.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data class for build details.
 *
 * @author Marco Scata
 */
@Entity(name = "jenkins_build_details")
public class BuildDetailsImpl implements BuildDetails {
    private final static Logger LOGGER = Logger
            .getLogger(BuildDetailsImpl.class.getName());

    private int id;
    private String name;
    private String fullName;
    private Date startDate = new Date();
    private Date endDate;
    private Long duration;
    private String result;
    private String userId;
    private String userName;
    private final List<BuildParameter> parameters = new ArrayList<BuildParameter>();
    private BuildNode node = new BuildNodeImpl();
    private BuildJob buildJob = new BuildJobImpl();
    private String svnUrl;
    private long svnRev;
    private final List  <BuildChange> buildChange = new ArrayList<BuildChange>();
    private int buildNum;
    private int buildPreSuccNum;
    private int buildPreFailNum;
    private long repairTime;
    private double successRate;

    @Override
    @Column(nullable = false, unique = false)
    public double getSuccessRate() {
        return successRate;
    }

    @Override
    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    //private ChangeLogSet changeLog =  new ChangeLogSet();

    //private BuildChange  buildeChange = new BuildChangeImpl();

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    @Column(nullable = false, unique = false)
    public long getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(long repairTime) {
        this.repairTime = repairTime;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getName()
     */
    @Override
    @Column(nullable = false, unique = false)
    public String getName() {
        return name;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setName(java.lang.String)
     */
    @Override
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getFullName()
     */
    @Column(nullable = false, unique = false)
    @Override
    public String getFullName() {
        return fullName;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setFullName(java.lang.String)
     */
    @Override
    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getStartDate()
     */
    @Column(nullable = false, unique = false)
    @Override
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setStartDate(java.util.Date)
     */
    @Override
    public void setStartDate(final Date start) {
        this.startDate = start;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getEndDate()
     */
    @Column(nullable = true, unique = false)
    @Override
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setEndDate(java.util.Date)
     */
    @Override
    public void setEndDate(final Date end) {
        this.endDate = end;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getDuration()
     */
    @Column(nullable = true, unique = false)
    @Override
    public Long getDuration() {
        return duration;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setDuration(java.lang.Long)
     */
    @Override
    public void setDuration(final Long duration) {
        this.duration = duration;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getResult()
     */

    @Column(nullable = true, unique = false)
    @Override
    public String getResult() {
        return result;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setResult(java.lang.String)
     */
    @Override
    public void setResult(final String result) {
        if (result != null) {
            this.result = result.toString();
        }
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getUserId()
     */
    @Column(nullable = true, unique = false)
    @Override
    public String getUserId() {
        return userId;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setUserId(java.lang.String)
     */
    @Override
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getUserName()
     */
    @Column(nullable = true, unique = false)
    @Override
    public String getUserName() {
        return userName;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setUserName(java.lang.String)
     */
    @Override
    public void setUserName(final String userName) {
        this.userName = userName;

    }

    @Column(nullable = true, unique = false)
    @Override
    public int getBuildNum() {
        return buildNum;
    }

    public void setBuildNum(int buildNum) {
        this.buildNum = buildNum;
    }


    @Column(nullable = true, unique = false)
    @Override
    public int getBuildPreSuccNum() {
        return buildPreSuccNum;
    }

    public void setBuildPreSuccNum(int preSuccNum) {
        this.buildPreSuccNum = preSuccNum;
    }

    @Column(nullable = true, unique = false)
    @Override
    public int getBuildPreFailNum() {
        return buildPreFailNum;
    }

    public void setBuildPreFailNum(int buildPreFailNum) {
        this.buildPreFailNum = buildPreFailNum;
    }


    @Override
    @Column(nullable = true, unique = false)
    public long getSvnRev() {
        return svnRev;
    }

    public String getSvnUrl() {
        return svnUrl;
    }

    public void setSvnRev(long svnrev) {
        this.svnRev = svnrev;
    }

    public void setSvnUrl(String svnurl) {
        this.svnUrl = svnurl;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getParameters()
     */
    @OneToMany(cascade = CascadeType.ALL, targetEntity = BuildParameterImpl.class, mappedBy = "buildDetails")
    @Column(nullable = true, unique = false)
    @Override
    public List<BuildParameter> getParameters() {
        return parameters;
    }

    @OneToMany(cascade = CascadeType.ALL, targetEntity = BuildChangeImpl.class, mappedBy = "buildDetails")
    @Column(nullable = true, unique = false)
    @Override
    public List<BuildChange> getBuildChange() {
        return buildChange;
    }

    @Override
    public void setBuildChange(final List<BuildChange> buildchange) {
            final BuildChange[] temBuildchang = buildchange.toArray(new BuildChange[]{});
            this.buildChange.clear();
            Collections.addAll(this.buildChange, temBuildchang);
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setParameters(java.util.List)
     */
    @Override
    public void setParameters(final List<BuildParameter> params) {
        if (null != params) {
            // need a temporary array otherwise hibernate
            // will clear the property bag too
            final BuildParameter[] tempParams = params
                    .toArray(new BuildParameter[]{});
            this.parameters.clear();
            Collections.addAll(this.parameters, tempParams);
        }

    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getNode()
     */
    @ManyToOne(targetEntity = BuildNodeImpl.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, unique = false)
    @Override
    public BuildNode getNode() {
        return node;
    }

    @ManyToOne(targetEntity = BuildJobImpl.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "jobId", nullable = true, unique = false)
    @Override
    public BuildJob getBuildJob() {
        return buildJob;
    }

    public void setBuildJob(BuildJob buildjob) {
        this.buildJob = buildjob;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setNode(BuildNode)
     */
    @Override
    public void setNode(final BuildNode node) {
        this.node = node;
    }


    @Override
    public String toString() {
        return String.format("%s [%s]", this.fullName, this.buildNum);
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(fullName);
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
        if (!(obj instanceof BuildDetails)) {
            return false;
        }

        final BuildDetails other = (BuildDetails) obj;

        return other.hashCode() == this.hashCode();
    }

    private List<BuildParameter> resolveBuildParameters(
            final Map<String, String> buildVariables) {
        final List<BuildParameter> retval = new ArrayList<BuildParameter>();
        if (buildVariables != null) {
            for (final Map.Entry<String, String> buildVariable : buildVariables.entrySet()) {
                retval.add(new BuildParameterImpl(String.format("%s", buildVariable.getKey()), buildVariable.getValue(), this));
            }
        }

        return retval;
    }


    private List<BuildChange> resloveBuildChange(final ChangeLogSet<?> changeset) {
        //ChangeLogSet.Entry test = new ChangeLogSet.Entry();
        final List<BuildChange> retval = new ArrayList<BuildChange>();
        if (changeset != null) {
            for (Entry entry :  changeset) {
                retval.add(
                        new BuildChangeImpl(entry.getMsg(), entry.getAuthor().getDisplayName(), entry.getTimestamp(),
                                this.name, this.svnUrl,
                                entry.getCommitId(), this.buildNum,
                                entry.getAffectedFiles(), this));

            }

        }
        return retval;
    }

    private BuildJob resolveBuildJob(AbstractBuild<?, ?> build) {
        final BuildJob retval = new BuildJobImpl(build);
        return retval;

    }

    private BuildNode resolveBuildNode(final Node node) {
        String address = "UNKNOWN";
        String hostname = "UNKNOWN";
        try {
            final InetAddress iaddr = InetAddress.getLocalHost();
            address = iaddr.getHostAddress();
            hostname = iaddr.getHostName();
        } catch (final UnknownHostException e) {
            LOGGER.log(
                    Level.SEVERE,
                    "An error occurred while trying to resolve the master's network name and address: "
                            + e.getMessage(), e);
        }

        final Computer computer = node.toComputer();
        final BuildNode retval = new BuildNodeImpl(address, hostname,
                computer.getDisplayName(), String.format("%s/%s", hostname,
                computer.getUrl()), node.getNodeName(),
                node.getNodeDescription(), node.getLabelString());
        return retval;
    }

    /**
     * Default constructor.
     */
    public BuildDetailsImpl() {
    }

    /**
     * Constructs a new object with the specified properties.
     *
     * @param id         the build id.
     * @param name       the build name.
     * @param fullName   the build full name.
     * @param startDate  the build start date.
     * @param endDate    the build end date.
     * @param duration   the build duration.
     * @param userId     the id of the user who started the build.
     * @param userName   the name of the user who started the build.
     * @param parameters the build parameters (if any).
     */
    public BuildDetailsImpl(final int id, final String name,
                            final String fullName, final Date startDate, final Date endDate,
                            final long duration, final String userId, final String userName,
                            final List<BuildParameter> parameters, final BuildNode node, final BuildJob buildJob,
                            final String svnUrl, final long svnRev, int buildNum) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.userId = userId;
        this.userName = userName;
        if ((parameters != null) && !parameters.isEmpty()) {
            this.parameters.addAll(parameters);
        }
        this.node = node;
        this.svnUrl = svnUrl;
        this.svnRev = svnRev;
        this.buildNum = buildNum;
        this.buildJob = buildJob;
    }

    /**
     * Constructs a new BuildDetailsImpl object using the details of the given
     * Jenkins build.
     *
     * @param build a valid Jenkins build object.
     */
    public BuildDetailsImpl(final AbstractBuild<?, ?> build) {
        // this.id = build.getId();
        this.name = build.getRootBuild().getProject().getDisplayName();
        this.fullName = build.getFullDisplayName();
        this.startDate = build.getTime();
        this.buildNum = build.getNumber();


        SCM scm = build.getProject().getScm();
        if (scm instanceof SubversionSCM) {
            SubversionSCM svn = (SubversionSCM) scm;
            SubversionSCM.ModuleLocation[] locs = svn.getLocations();
            this.setSvnUrl(locs[0].getURL());
            long r = 0;
            try {
                SVNRepository repo = locs[0].openRepository(build.getProject(), scm, true);
                r = repo.getLatestRevision();
                this.setSvnRev(r);
            } catch (SVNException e) {

            }

        }

        final List<CauseAction> actions = build.getActions(CauseAction.class);
        boolean userFound = false;
        for (final CauseAction action : actions) {
            for (final Cause cause : action.getCauses()) {
                if (cause instanceof UserIdCause) {
                    userFound = true;
                    this.userId = ((UserIdCause) cause).getUserId();
                    this.userName = ((UserIdCause) cause).getUserName();
                    break;
                }
            }
            if (userFound) {
                break;
            }
        }

        this.node = resolveBuildNode(build.getBuiltOn());
        this.parameters
                .addAll(resolveBuildParameters(build.getBuildVariables()));
        this.buildChange.addAll(resloveBuildChange(build.getChangeSet()));
        this.buildNum = build.getNumber();
        this.buildJob = resolveBuildJob(build);

    }
}
