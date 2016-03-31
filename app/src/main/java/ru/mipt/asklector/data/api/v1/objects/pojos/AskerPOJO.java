
package ru.mipt.asklector.data.api.v1.objects.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AskerPOJO {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("mid_name")
    @Expose
    private String midName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("nickname")
    @Expose
    private String nickname;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AskerPOJO() {
    }

    /**
     * 
     * @param id
     * @param lastName
     * @param nickname
     * @param midName
     * @param firstName
     */
    public AskerPOJO(Integer id, String firstName, String midName, String lastName, String nickname) {
        this.id = id;
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.nickname = nickname;
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
     *     The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return
     *     The midName
     */
    public String getMidName() {
        return midName;
    }

    /**
     * 
     * @param midName
     *     The mid_name
     */
    public void setMidName(String midName) {
        this.midName = midName;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * @return
     *     The nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 
     * @param nickname
     *     The nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
