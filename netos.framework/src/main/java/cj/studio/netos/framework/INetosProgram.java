package cj.studio.netos.framework;

/**
 * Created by caroceanjofers on 2018/2/1.
 */
//网口桌面、微应用、微站，均是netos程序
public interface INetosProgram extends IModule {
    void main(NetOSContext context);
}
