package cj.studio.netos.framework;

/**
 * Created by caroceanjofers on 2018/2/24.
 */

public interface INetOSResource {
    public String getNetOSString(int resid);
    String getNetOSAnyResourceName(int resid);
    int getIdentifier(String name,String defType);
}
