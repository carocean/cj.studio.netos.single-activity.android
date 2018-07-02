package cj.studio.netos.framework.isite;

import cj.studio.netos.framework.Frame;

/**
 * webview中window函数的扩展
 * Created by caroceanjofers on 2018/3/9.
 */

public interface IWindowFunctions {
    ISubjectFunctions subject();
    ISensorFunctions sensors();
    Object require(String js_module_package);
    void print(String v);
    void ouput(Frame frame);
}
