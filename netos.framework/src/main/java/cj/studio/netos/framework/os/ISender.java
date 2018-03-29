package cj.studio.netos.framework.os;

import cj.studio.netos.framework.Frame;

/**
 * Created by caroceanjofers on 2018/1/24.
 */

public interface ISender {
    void set(IReciever reciever);
    void send(Frame frame);
}
