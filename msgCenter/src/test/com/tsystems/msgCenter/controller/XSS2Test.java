package com.tsystems.msgCenter.controller;

import java.util.Date;

import com.tsystems.aviation.imf.client.commons.ImfServiceType;
import com.tsystems.aviation.imf.client.exception.ImfClientConnectionException;
import com.tsystems.aviation.imf.client.exception.ImfClientInvalidServiceTypeException;
import com.tsystems.aviation.imf.client.exception.ImfClientSsException;
import com.tsystems.aviation.imf.client.exception.ImfSynchronizationForbiddenException;
import com.tsystems.aviation.imf.client.message.ImfMessageListener;
import com.tsystems.aviation.imf.client.subsystem.ImfSsManualClient;

/*
 * Copyright 2013 T-systems.com All right reserved. This software is the
 * confidential and proprietary information of T-systems.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with T-systems.com.
 */
/**
 * class XSS2Test.java's description on implement: TODO description on implement 
 *
 * @author cchen2 2013-4-24 上午10:53:37
 */
public class XSS2Test {

    static ImfMessageListener listener = new ImfMessageListener() {

                                           @Override
                                           public void handleMessage(String msg) {
                                               System.out.println("received message from IMF " + msg);
                                           }
                                       };

    public static void main(String[] args) throws ImfClientInvalidServiceTypeException, ImfClientConnectionException, ImfSynchronizationForbiddenException, ImfClientSsException {
        ImfSsManualClient ssClient = ImfSsManualClient.getImfSsManualClient(ImfServiceType.FSS, listener);

        ssClient.sync(new Date(113, 3, 23));
        
//        ssClient.subscribe();
    }
}
