package com.org.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.json.JSONArray;
import org.json.JSONObject;

import com.org.dao.FileUpdataDao;
import com.org.dao.impl.FileUpdataDaoImpl;
import com.org.entity.FileData;

public class MainServer {     
    private static MainServer mainServer = null;     
    private SocketAcceptor acceptor = new NioSocketAcceptor();     
    private DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();     
    private int bindPort = 4000;     
    
    
    public static MainServer getInstances() {     
        if (null == mainServer) {     
            mainServer = new MainServer();     
        }     
        return mainServer;     
    }     
    
    private MainServer() {     
        chain.addLast("myChin", new ProtocolCodecFilter(     
                new ObjectSerializationCodecFactory()));     
        acceptor.setHandler(ServerHandler.getInstances());     
        try {     
            acceptor.bind(new InetSocketAddress(bindPort));     
        } catch (IOException e) {     
            e.printStackTrace();     
        }     
    }     
    
    public static void main(String[] args) throws Exception {     
        MainServer.getInstances();  
        
        System.out.println("mainServer!!!!!!!!!!!!!");
        
    }     
}    