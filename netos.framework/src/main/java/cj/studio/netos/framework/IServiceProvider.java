package cj.studio.netos.framework;

/**
 * Created by caroceanjofers on 2018/1/25.
 */

public interface IServiceProvider {
    Object getService(String name);
    <T> T getService(Class<T> clazz);
}
