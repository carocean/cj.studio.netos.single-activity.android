package cj.studio.netos.framework;

import java.util.Set;

/**
 * Created by caroceanjofers on 2018/1/19.
 * 胞体，提供营养，它是模块和服务容器
 */

public interface ICell extends ICloseable,IServiceProvider {
    void refresh();
    Object getService(String name);
    <T> T getService(Class<T> clazz);
    IAxon axon();
    IDendrite dendrite(String moduleName);
    Set<String> listDendriteName();

    IProfile profile();
    IDevice forDevice(String name);
    MobileInfo info();
    IStorage storage();
    boolean isOnline();
    boolean isConnected();

}
