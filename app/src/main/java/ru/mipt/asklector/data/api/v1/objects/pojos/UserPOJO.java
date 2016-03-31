package ru.mipt.asklector.data.api.v1.objects.pojos;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class UserPOJO implements BasePOJO{

    private String nickname;
    private String first_name;
    private String mid_name;
    private String last_name;

    public UserPOJO(String nickname, String first_name, String mid_name, String last_name){
        this.nickname = nickname;
        this.first_name = first_name;
        this.mid_name = mid_name;
        this.last_name = last_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMid_name() {
        return mid_name;
    }

    public void setMid_name(String mid_name) {
        this.mid_name = mid_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
