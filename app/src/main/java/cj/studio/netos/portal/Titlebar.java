package cj.studio.netos.portal;

import android.widget.TextView;

import cj.studio.netos.framework.ITitlebar;

/**
 * Created by caroceanjofers on 2018/2/24.
 */

public class Titlebar implements ITitlebar {
    TextView tvui;
    public Titlebar(TextView tvui) {
        this.tvui=tvui;
    }

    @Override
    public void setText(int text) {
        tvui.setText(text);
    }
    @Override
    public void setText(String text) {
        tvui.setText(text);
    }
}
