package org.smart.services;

/**
 * Created by fabiano on 09/04/18.
 */

public interface MessageDeliveryServiceDelegate {
    public void postReceivedMessage(String message);
}
