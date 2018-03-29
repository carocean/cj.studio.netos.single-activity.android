package cj.studio.netos.framework;

/**
 * Created by caroceanjofers on 2018/1/19.
 */

public class Axon implements IAxon {
    IHillock hillock;

    public Axon(IHillock hillock){
        this.hillock=hillock;
    }

    @Override
    public void output(String pinName, Frame message) {
        hillock.output(pinName,message);
    }
}
