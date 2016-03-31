package ru.mipt.asklector.data.api.v1.objects.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dmitry Bochkov on 11.12.2015.
 */
public class CommentToAskPOJO {
    
    public CommentToAskPOJO(String text) {
        this.text = text;
    }

    @SerializedName("text")
    @Expose
    private String text;

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

}
