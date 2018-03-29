package cj.studio.netos.framework;

/**
 * Created by caroceanjofers on 2018/1/19.
 */

public interface IDendrite extends  IDisposable{
    ISynapsis synapsis();
    void input(Frame frame, ICell cell);
}
