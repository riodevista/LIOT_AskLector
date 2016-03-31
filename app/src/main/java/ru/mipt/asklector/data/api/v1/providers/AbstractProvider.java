package ru.mipt.asklector.data.api.v1.providers;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public abstract class AbstractProvider {

    protected OnFinishedListener onFinishedListener = null;

    public abstract void run();

    public void setOnFinishedListener(AbstractProvider.OnFinishedListener onFinishedListener){
        this.onFinishedListener = onFinishedListener;
    }

    public interface OnFinishedListener{

        void onSuccess();

        void onFailure(int error);
    }
}
