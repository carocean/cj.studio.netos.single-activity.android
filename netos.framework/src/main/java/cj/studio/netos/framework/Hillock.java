package cj.studio.netos.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caroceanjofers on 2018/1/19.
 */
//pin的集合
public class Hillock implements IHillock {
    Map<String, IMessagePin> pinMap;
    public Hillock(){
        pinMap=new HashMap<>();
    }
    @Override
    public void addPin(IMessagePin pin){
        pinMap.put(pin.name(),pin);
    }
    @Override
    public void removePin(String pinName){
        pinMap.remove(pinName);
    }
    @Override
    public void output(String pinName, Frame message) {
        IMessagePin pin = pinMap.get(pinName);
        if(pin==null){
            throw new RuntimeException();
        }
        pin.output(message);
    }
}
