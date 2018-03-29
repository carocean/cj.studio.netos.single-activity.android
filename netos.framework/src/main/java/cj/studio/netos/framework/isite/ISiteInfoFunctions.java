package cj.studio.netos.framework.isite;

/**
 * Created by caroceanjofers on 2018/3/20.
 */

public interface ISiteInfoFunctions {
    String title();
    String name();
    String version();
    String group();
    String company();
    String description();
     void title(String title);
    void name(String name);
    void version(String version);
    void group(String group);
    void company(String company);
    void description(String desc);
}
