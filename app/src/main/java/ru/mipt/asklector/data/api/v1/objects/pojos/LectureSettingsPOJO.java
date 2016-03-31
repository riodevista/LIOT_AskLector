package ru.mipt.asklector.data.api.v1.objects.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LectureSettingsPOJO {

    @SerializedName("moderation")
    @Expose
    private Integer moderation;
    @SerializedName("comments")
    @Expose
    private Integer comments;

    /**
     *
     * @return
     * The moderation
     */
    public Integer getModeration() {
        return moderation;
    }

    /**
     *
     * @param moderation
     * The moderation
     */
    public void setModeration(Integer moderation) {
        this.moderation = moderation;
    }

    /**
     *
     * @return
     * The comments
     */
    public Integer getComments() {
        return comments;
    }

    /**
     *
     * @param comments
     * The comments
     */
    public void setComments(Integer comments) {
        this.comments = comments;
    }

}