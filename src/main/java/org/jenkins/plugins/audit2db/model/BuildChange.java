package org.jenkins.plugins.audit2db.model;

import java.util.Date;
import java.util.List;


/**
 * Created by guohuixin on 2017/7/3.
 */
public interface BuildChange {

    int getId();

    void setId(int id);

    String getCommitMsg();

    void setCommitMsg(String commitmsg);


    void setCommitRev(String commitrev);

    String  getCommitRev();

    String getCommiter();


    void setCommiter(String commiter);

    String getJobName();

    void setJobName(String jobName);

    String getSvnUrl();

    void setSvnUrl(String svnUrl);

    Date getCommitDate();

    void setCommitDate(Date commitDate);

    int getBuildNum();

    void setBuildNum(int buildNum);

    BuildDetails getBuildDetails();

    void setBuildDetails(BuildDetails buildDetails);

    List<ChangeFile> getChangefile();
    void setChangefile(List<ChangeFile> changefile);


}
