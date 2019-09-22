package com.ldg.study.springCloud.socket.nio.reactor.singleThreadReactor.server;

import java.io.IOException;

/**
 * Author:  ldg
 * Date:    2019/9/22 9:10
 * Desc:    this is file description
 */
public class SingleThreadServerMain {
    public static void main(String[] args) {
        try {
            SingleThreadReactor reactor = new SingleThreadReactor(9090);
            reactor.monitorReadiness();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
