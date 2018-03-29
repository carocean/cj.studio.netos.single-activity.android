package cj.studio.netos.framework;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caroceanjofers on 2018/2/3.
 */

public class ServiceSite implements IServiceSite {
    private Map<String, Object> services;
    private IServiceProvider parent;
    public ServiceSite(IServiceProvider parent){
        services=new HashMap<>();
        this.parent=parent;
    }
    @Override
    public Object getService(String name) {
        Object service=parent.getService(name);
        if(service!=null){
            return service;
        }
        return services.get(name);
    }

    @Override
    public void dispose() {
        services.clear();
    }

    @Override
    public <T> T getService(Class<T> clazz) {
        T service=parent.getService(clazz);
        if(service!=null){
            return service;
        }
        Collection<Object> col = this.services.values();
        for (Object obj : col) {
            if (clazz.isAssignableFrom(obj.getClass())) {
                return (T) obj;
            }
        }
        return null;
    }

    @Override
    public void addService(String name, Object service) {
        services.put(name,service);
    }

    @Override
    public void removeService(String name) {
        services.remove(name);
    }
}
