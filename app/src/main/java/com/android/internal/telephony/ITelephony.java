package com.android.internal.telephony;

/**
 * Created by Reaper on 7/18/2017.
 */


public interface ITelephony {

    boolean endCall();

    void answerRingingCall();

    void silenceRinger();

}