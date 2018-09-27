package cj.studio.netos.framework;

/**
 * Created by caroceanjofers on 2018/1/19.
 */

public interface IModule extends IWindow{
    void input(Frame frame, IServiceProvider site);

    IWidget widget(String navigateable);
}
