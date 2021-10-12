package com.example.RoleBasedJwtAuthentication.Log;

public interface LogKeys {

    String ELAPSE_TIME = "elapseTime";


    /**
     * JSON Message received from message broker
     */


    String DECRYPTED_RESPONSE = "decryptedResponseBody";
    String DECRYPTED_REQUEST = "decryptedRequestBody";


    interface LogEvent {

        String MESSAGE = "message";

        String ACTION = "action";
    }

}
