package com.sinlord.aatdesktop.util;

/**
 * @author SINLORDEP
 */
public interface EventListener<T> {
    void onEvent(String event, T data);
}
