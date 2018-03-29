package cj.studio.netos.framework;

/**
 * Created by caroceanjofers on 2018/1/19.
 */

public class Dendrite implements IDendrite {
    ISynapsis synapsis;
    public  Dendrite(ISynapsis synapsis){
        this.synapsis=synapsis;
    }
    @Override
    public ISynapsis synapsis() {
        return synapsis;
    }

    @Override
    public void dispose() {
        synapsis.dispose();
        synapsis=null;
    }

    @Override
    public void input(Frame frame,ICell cell) {
        if(synapsis==null)return;
        synapsis.input(frame,cell);
    }
}
