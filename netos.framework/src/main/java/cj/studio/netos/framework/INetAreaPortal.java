package cj.studio.netos.framework;

/**
 * Created by caroceanjofers on 2018/2/24.
 */

public interface INetAreaPortal {
    INetAreaDesktop desktop();

    INetAreaMutuboard mutuboard();

    INetAreaAppboard appboard();

    INetosProgramTemplate programTemplate();
    INetAreaNavigation navigation();


    INetAreaSelection selection();

    INetAreaTitlebar titlebar();
}
