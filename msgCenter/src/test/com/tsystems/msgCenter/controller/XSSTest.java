package com.tsystems.msgCenter.controller;

import com.tsystems.aviation.imf.client.commons.ImfServiceType;
import com.tsystems.aviation.imf.client.exception.ImfClientConnectionException;
import com.tsystems.aviation.imf.client.exception.ImfClientInvalidServiceTypeException;
import com.tsystems.aviation.imf.client.exception.ImfClientSsException;
import com.tsystems.aviation.imf.client.message.ImfMessageListener;
import com.tsystems.aviation.imf.client.subsystem.ImfSsAutoClient;

/*
 * Copyright 2013 T-systems.com All right reserved. This software is the
 * confidential and proprietary information of T-systems.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with T-systems.com.
 */
/**
 * class XRSTest.java's description on implement: TODO description on implement 
 *
 * @author cchen2 2013-4-24 上午10:21:29
 */
public class XSSTest {

    static ImfMessageListener listener = new ImfMessageListener() {

                                           @Override
                                           public void handleMessage(String msg) {
                                               System.out.println("received message from IMF " + msg);
                                           }
                                       };

    public static void main(String[] args) throws ImfClientInvalidServiceTypeException, ImfClientConnectionException, ImfClientSsException {

        ImfSsAutoClient ssClient = ImfSsAutoClient.getImfSsAutoClient(ImfServiceType.FSS1, listener);
        
        ssClient.subscribe();

    }

}
