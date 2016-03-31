package ru.mipt.asklector.data.api.v1.objects.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dmitry Bochkov on 09.12.2015.
 */
public class CommentPOJO {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("voted")
    @Expose
    private Boolean voted;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("commentsCount")
    @Expose
    private Integer commentsCount;
    @SerializedName("user")
    @Expose
    private AskerPOJO user;

    /**
     * No args constructor for use in serialization
     *
     */
    public CommentPOJO() {
    }

    /**
     *
     * @param id
     * @param text
     * @param commentsCount
     * @param rating
     * @param date
     * @param voted
     * @param user
     */
    public CommentPOJO(Integer id, String text, Boolean voted, Integer rating, String date, Integer commentsCount, AskerPOJO user) {
        this.id = id;
        this.text = text;
        this.voted = voted;
        this.rating = rating;
        this.date = date;
        this.commentsCount = commentsCount;
        this.user = user;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     * The voted
     */
    public Boolean getVoted() {
        return voted;
    }

    /**
     *
     * @param voted
     * The voted
     */
    public void setVoted(Boolean voted) {
        this.voted = voted;
    }

    /**
     *
     * @return
     * The rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The commentsCount
     */
    public Integer getCommentsCount() {
        return commentsCount;
    }

    /**
     *
     * @param commentsCount
     * The commentsCount
     */
    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    /**
     *
     * @return
     * The user
     */
    public AskerPOJO getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(AskerPOJO user) {
        this.user = user;
    }

}