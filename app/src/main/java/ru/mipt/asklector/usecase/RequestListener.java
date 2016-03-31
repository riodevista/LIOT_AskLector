package ru.mipt.asklector.usecase;

/**
 * Created by Dmitry Bochkov on 07.12.2015.
 */
public interface RequestListener {

    void onSuccess();
    void onConnectionError();
    void onUnknownError();
}
