package cj.studio.netos.framework;

/**
 * Created by caroceanjofers on 2018/2/26.
 */

public interface INetAreaNavigation {
    void naviToDesktop();
    void naviToMutuboard();
    void naviToAppboard();
    void openProgram(String url);

    /**
     * 打开站点服务。
     * 站点服务是交互板中属于超链接的服务
     * @param sid
     */
    void naviToSiteService(String sid);

    void init(IServiceProvider provider);
}
