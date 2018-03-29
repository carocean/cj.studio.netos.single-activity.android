package cj.studio.netos.module;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cj.studio.netos.NetAreaActivity;
import cj.studio.netos.R;
import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.IAxon;
import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.IModule;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MineModule.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MineModule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineModule extends Fragment implements IModule {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ICell cell;
    private OnFragmentInteractionListener mListener;

    public MineModule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineModule.
     */
    // TODO: Rename and change types and number of parameters
    public static MineModule newInstance(String param1, String param2) {
        MineModule fragment = new MineModule();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_mine_module, container, false);

        Button button=v.findViewById(R.id.open_area_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("xxxxx","0000----");
                Intent intent=new Intent(getActivity(),NetAreaActivity.class);
                String url="http://192.168.3.1/website/?nan=13939394449339&token=skdjiowegjxjs";
//                String url="http://news.163.com";
//                 url="netos://cj.com/";//直接访问对方网域，对方可以在本地服务也可再连接到他的电脑上也可是远程
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public String name() {
        return "mine";
    }

    @Override
    public void input(Frame frame, ICell cell) {
        IAxon axon=cell.axon();
        Frame f=new Frame("test /test/ netos/1.0");
        axon.output("netos.mpusher",f);
    }



    @Override
    public int cnameId() {
        return R.string.module_mine;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
