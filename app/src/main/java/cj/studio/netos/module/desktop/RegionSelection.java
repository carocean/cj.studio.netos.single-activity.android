package cj.studio.netos.module.desktop;

import android.app.Activity;

import cj.studio.netos.framework.IViewport;

public class RegionSelection implements IRegionSelection {
    IDesktopRegion selectRegion;


    @Override
    public IDesktopRegion selectRegion() {
        return selectRegion;
    }

    @Override
    public void setSelectRegion(IDesktopRegion region) {
        this.selectRegion=region;
    }
}
