
package ru.mipt.asklector.data.api.v1.objects.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectPOJO {

    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("department")
    @Expose
    private DepartmentPOJO department;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SubjectPOJO() {
    }

    /**
     * 
     * @param guid
     * @param title
     * @param department
     */
    public SubjectPOJO(String guid, String title, DepartmentPOJO department) {
        this.guid = guid;
        this.title = title;
        this.department = department;
    }

    /**
     * 
     * @return
     *     The guid
     */
    public String getGuid() {
        return guid;
    }

    /**
     * 
     * @param guid
     *     The guid
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The department
     */
    public DepartmentPOJO getDepartment() {
        return department;
    }

    /**
     * 
     * @param department
     *     The department
     */
    public void setDepartment(DepartmentPOJO department) {
        this.department = department;
    }

}
