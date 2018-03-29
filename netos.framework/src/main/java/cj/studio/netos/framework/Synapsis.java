package cj.studio.netos.framework;

/**
 * Created by caroceanjofers on 2018/1/19.
 */

public class Synapsis implements ISynapsis {

    IModule module;

    public Synapsis(IModule module) {
        this.module = module;
    }

    @Override
    public void dispose() {

        module=null;
    }

    @Override
    public IModule bindModule() {
        return module;
    }

    @Override
    public void input(Frame frame, ICell cell) {
        if(module==null)return;
        module.input(frame, cell);
    }
}
