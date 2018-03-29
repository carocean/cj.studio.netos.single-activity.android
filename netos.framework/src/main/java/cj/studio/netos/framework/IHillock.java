package cj.studio.netos.framework;

/**
 * Created by caroceanjofers on 2018/1/19.
 */

public interface IHillock {
    void addPin(IMessagePin pin);

    void removePin(String pinName);

    void output(String pinName, Frame message);
}
