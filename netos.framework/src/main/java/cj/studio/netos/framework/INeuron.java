package cj.studio.netos.framework;

/**
 * Created by caroceanjofers on 2018/1/18.
 */
//一个神经元包括：包体、树、轴及对外通讯功能
public interface INeuron {

    void refresh();
    void exit();

    void reconnect();

    ICell cell();

    boolean isConnected();

    boolean isOnline();
    IServiceProvider provider();
}
