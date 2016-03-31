
package ru.mipt.asklector.data.api.v1.objects.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LecturePOJO {

    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("subject")
    @Expose
    private SubjectPOJO subject;
    @SerializedName("lecturer")
    @Expose
    private LecturerPOJO lecturer;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("date_start")
    @Expose
    private String dateStart;
    @SerializedName("date_end")
    @Expose
    private String dateEnd;
    @SerializedName("groups")
    @Expose
    private List<GroupPOJO> groups = new ArrayList<GroupPOJO>();
    @SerializedName("settings")
    @Expose
    private LectureSettingsPOJO settings;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LecturePOJO() {
    }

    /**
     * 
     * @param guid
     * @param location
     * @param subject
     * @param dateEnd
     * @param dateStart
     * @param groups
     * @param lecturer
     */
    public LecturePOJO(String guid, SubjectPOJO subject, LecturerPOJO lecturer, String location, String dateStart, String dateEnd, List<GroupPOJO> groups) {
        this.guid = guid;
        this.subject = subject;
        this.lecturer = lecturer;
        this.location = location;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.groups = groups;
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
     *     The subject
     */
    public SubjectPOJO getSubject() {
        return subject;
    }

    /**
     * 
     * @param subject
     *     The subject
     */
    public void setSubject(SubjectPOJO subject) {
        this.subject = subject;
    }

    /**
     * 
     * @return
     *     The lecturer
     */
    public LecturerPOJO getLecturer() {
        return lecturer;
    }

    /**
     * 
     * @param lecturer
     *     The lecturer
     */
    public void setLecturer(LecturerPOJO lecturer) {
        this.lecturer = lecturer;
    }

    /**
     * 
     * @return
     *     The location
     */
    public String getLocation() {
        return location;
    }

    /**
     * 
     * @param location
     *     The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 
     * @return
     *     The dateStart
     */
    public String getDateStart() {
        return dateStart;
    }

    /**
     * 
     * @param dateStart
     *     The date_start
     */
    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * 
     * @return
     *     The dateEnd
     */
    public String getDateEnd() {
        return dateEnd;
    }

    /**
     * 
     * @param dateEnd
     *     The date_end
     */
    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * 
     * @return
     *     The groups
     */
    public List<GroupPOJO> getGroups() {
        return groups;
    }

    /**
     * 
     * @param groups
     *     The groups
     */
    public void setGroups(List<GroupPOJO> groups) {
        this.groups = groups;
    }

    /**
     *
     * @return
     * The settings
     */
    public LectureSettingsPOJO getLectureSettingsPOJO() {
        return settings;
    }

    /**
     *
     * @param settings
     * The settings
     */
    public void setLectureSettingsPOJO(LectureSettingsPOJO settings) {
        this.settings = settings;
    }


}
