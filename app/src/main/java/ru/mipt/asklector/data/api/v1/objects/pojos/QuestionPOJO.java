
package ru.mipt.asklector.data.api.v1.objects.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionPOJO {

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
    private String rating;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("user")
    @Expose
    private AskerPOJO user;
    @SerializedName("commentsCount")
    @Expose
    private int commentsCount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public QuestionPOJO() {
    }

    /**
     * 
     * @param id
     * @param text
     * @param rating
     * @param date
     * @param voted
     * @param user
     * @param commentsCount
     */
    public QuestionPOJO(Integer id, String text, Boolean voted, String rating, String date, AskerPOJO user, int commentsCount) {
        this.id = id;
        this.text = text;
        this.voted = voted;
        this.rating = rating;
        this.date = date;
        this.user = user;
        this.commentsCount = commentsCount;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return
     *     The voted
     */
    public Boolean getVoted() {
        return voted;
    }

    /**
     * 
     * @param voted
     *     The voted
     */
    public void setVoted(Boolean voted) {
        this.voted = voted;
    }

    /**
     * 
     * @return
     *     The rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * 
     * @param rating
     *     The rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The user
     */
    public AskerPOJO getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(AskerPOJO user) {
        this.user = user;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
}
