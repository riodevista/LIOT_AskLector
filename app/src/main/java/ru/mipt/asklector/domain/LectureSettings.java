package ru.mipt.asklector.domain;

import ru.mipt.asklector.domain.enums.ModerationType;

/**
 * Created by Dmitry Bochkov on 21.12.2015.
 */
public class LectureSettings {

    private ModerationType moderationType;
    private boolean isCommentsAvailable;

    public LectureSettings(ModerationType moderationType, boolean isCommentsAvailable) {
        this.moderationType = moderationType;
        this.isCommentsAvailable = isCommentsAvailable;
    }

    public boolean areCommentsAvailable() {
        return isCommentsAvailable;
    }

    public void setIsCommentsAvailable(boolean isCommentsAvailable) {
        this.isCommentsAvailable = isCommentsAvailable;
    }

    public ModerationType getModerationType() {
        return moderationType;
    }

    public void setModerationType(ModerationType moderationType) {
        this.moderationType = moderationType;
    }
}

