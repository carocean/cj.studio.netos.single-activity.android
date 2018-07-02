package cj.studio.netos.portal;

import android.support.constraint.ConstraintLayout;
import android.widget.LinearLayout;

import cj.studio.netos.R;
import cj.studio.netos.framework.IDisplay;

/**
 * Created by caroceanjofers on 2018/2/24.
 */

public class Display implements IDisplay {
    int display;
    public Display(int display) {
        this.display=display;
    }

    @Override
    public int viewId() {
        return display;
    }
}
