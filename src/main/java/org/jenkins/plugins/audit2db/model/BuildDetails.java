/**
 * 
 */
package org.jenkins.plugins.audit2db.model;

import java.util.Date;
import java.util.List;

/**
 * Data model to map build details.
 * 
 * @author Marco Scata
 * 
 */
public interface BuildDetails {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    String getFullName();

    void setFullName(String fullName);

    Date getStartDate();

    void setStartDate(Date start);

    Date getEndDate();

    void setEndDate(Date end);

    Long getDuration();

    void setDuration(Long duration);

    String getResult();

    void setResult(String result);

    String getUserId();

    void setUserId(String userId);

    String getUserName();

    void setUserName(String userName);

    List<BuildParameter> getParameters();

    void setParameters(List<BuildParameter> params);

    BuildNode getNode();
    void setNode(BuildNode node);

    long getSvnRev();
    void setSvnRev(long svnRev);

    String getSvnUrl();
    void setSvnUrl(String svnUrl);

    int getBuildNum();
    void setBuildNum(int buildNum);

    int getBuildPreSuccNum();
    void setBuildPreSuccNum(int preSuccNum);

    int getBuildPreFailNum();
    void setBuildPreFailNum(int PreFailNum);

    List<BuildChange> getBuildChange();
    void setBuildChange(List <BuildChange> changlogs);

    BuildJob getBuildJob();
    void setBuildJob( BuildJob buildJob);

    long getRepairTime();
    void setRepairTime(long times);

    double  getSuccessRate();
    void setSuccessRate(double  successRate);

}
