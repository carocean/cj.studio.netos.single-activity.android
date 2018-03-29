package cj.studio.netos.framework;

/**
 * 网域的界面表述
 * 工作台只有一个实例，它用于装载不同网域，也就是装载网域对应的工作空间
 * Created by caroceanjofers on 2018/2/1.
 */
//网域既是模块
//网域是微程序容器,它来负责运行和管理wisite,wiapp
//网域与平台服务相连
public interface INetAreaWorkbench extends IModule, ICloseable,IServiceProvider {
    //网口桌面
    INetosProgram openDesktop();
    INetosProgram openProgram(String url);
    void goDesktop();
    String[] listProgramUrl();
    void closeAllProgram();

    void renderTo(INetAreaPortal portal,INetAreaWorkspace workspace);

    void close();

}
