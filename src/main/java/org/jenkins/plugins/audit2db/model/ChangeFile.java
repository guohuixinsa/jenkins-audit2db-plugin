package org.jenkins.plugins.audit2db.model;


/**
 * Created by guohuixin on 2017/7/12.
 */
public interface ChangeFile {
    void setId(int id);

    int getId();

    void setFile(String file);

    String getFile();

    void setStatus(String status);

    String getStatus();


    void setBuildChange(BuildChange buildchange);

    BuildChange getBuildChange();
}
