package cj.studio.netos.framework.net;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.NetException;
import cj.studio.netos.framework.os.ISender;


/**
 * Created by caroceanjofers on 2018/1/20.
 */

public class TcpConnection implements IConnection {
    Address address;
    Socket socket;
    String domain;
    String token;
    IMessageLooper looper;

    @Override
    public void close() {

    }


    @Override
    public boolean isConnected() {
        return socket == null ? false : socket.isConnected();
    }

    @Override
    public void tryconnect(String domain, String token, String[] addresslist) throws NetException {
        List<String> list = new ArrayList<>();
        for(String a:addresslist){
            list.add(a);
        }
        StringBuffer sb=new StringBuffer();
        tryconnectimpl(domain, token, list,sb);

    }

    protected void tryconnectimpl(String domain, String token, List<String> list,StringBuffer err) throws NetException {
        if(list.isEmpty()){
            throw new NetException("建立网络连接失败:"+err);
        }
        int index = new Random().nextInt() % list.size();
        String address = list.get(index);

        try {
            connect(domain, token, address);
        } catch (NetException e) {
            list.remove(address);
            tryconnectimpl(domain, token, list,err);
            Log.e("Net", e.getMessage());
            err.append(e.getMessage()+" ");
        }


    }

    @Override
    public void connect(String domain, String token, String address) throws NetException {
        this.address = new Address(address);
        if (!"tcp".equals(this.address.protocol)) {
            throw new NetException("网络协议不是tcp");
        }
        socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(this.address.host, this.address.port);
        try {
            socket.connect(socketAddress);
            handshake(domain, token);
        } catch (IOException e) {
            throw new NetException(e);
        }

    }

    private void handshake(String domain, String token) throws NetException {
        Frame f = new Frame("handshake / gateway/1.0");
        f.head("Gateway-Inputer", domain);
        f.head("Gateway-Token", token);
        byte[] box = TcpFrameBox.box(f.toBytes());
        try {
            socket.getOutputStream().write(box);
        } catch (IOException e) {
            throw new NetException(e);
        }
    }

    @Override
    public void send(Frame frame) throws NetException {
        byte[] box = TcpFrameBox.box(frame.toBytes());
        try {
            socket.getOutputStream().write(box);
        } catch (IOException e) {
            throw new NetException(e);
        }
    }

    @Override
    public void loop(ISender sender) throws NetException {
        InputStream in = null;
        looper = new MessageLooper(sender);
        try {
            in = socket.getInputStream();
            int readit = 0;
            while ((readit = in.read()) > -1) {
                looper.accept((byte) readit);
            }
        } catch (IOException e) {
            throw new NetException(e);
        }
        Log.i("looper", "exit");
        disconnect();

    }

    @Override
    public void disconnect() {
        Frame frame = new Frame("ok /disconnect gateway/1.0");
        try {
            looper.onMessage(frame);
            looper = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                Log.e("Net", e.getMessage());
            }
        }


    }

}
