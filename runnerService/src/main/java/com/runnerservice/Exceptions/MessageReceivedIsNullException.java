package com.runnerservice.Exceptions;

public class MessageReceivedIsNullException extends Exception{
    public MessageReceivedIsNullException() {
        super("Received a message but is was null");
    }

}
