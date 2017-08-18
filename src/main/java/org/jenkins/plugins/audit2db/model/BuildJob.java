package org.jenkins.plugins.audit2db.model;

public interface BuildJob{
    int getId();
    void setId(int id);

    String getJobName();
    void setJobName(String jobName);

}