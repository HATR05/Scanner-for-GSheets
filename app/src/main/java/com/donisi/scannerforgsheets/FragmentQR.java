package com.donisi.scannerforgsheets;



import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Switch;


import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentQR.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentQR#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentQR extends Fragment{
    /* TODO: Rename parameter arguments, choose names that match
     the fragment initialization parameters, e.g. ARG_ITEM_NUMBER*/
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private CameraSource cameraSource;
    private SurfaceView cameraView;
    private String token = "";
    private Switch mode;
    String mode_flag = "";
    private QRDetector qrDetector;

    public FragmentQR() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentQR.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentQR newInstance(String param1, String param2) {
        FragmentQR fragment = new FragmentQR();
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
        View vista = inflater.inflate(R.layout.fragment_qr, container, false);

        cameraView = vista.findViewById(R.id.camera_view);
        mode = vista.findViewById(R.id.switch_mode);
        initQR(getContext().getApplicationContext());
        //change_info.change_info(qrDetector.getToken());
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    //-------------------------------------------------------------------------------------------------------------

    public void which_mode(Switch mode){

        if (mode.isChecked()){
            mode_flag = "1,";
        }else{
            mode_flag = "0,";
        }
    }

    //-------------------------------------------------------------------------------------------------------------

   private void initQR(Context context) {

        // creo el detector qr
        BarcodeDetector barcodeDetector =
                new BarcodeDetector.Builder(context).setBarcodeFormats(Barcode.QR_CODE).build();

        // creo la camara
        cameraSource = new CameraSource
                .Builder(context, barcodeDetector)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        // listener de ciclo de vida de la camara
        CameraPermit permissions = new CameraPermit(cameraSource,context,cameraView);
        cameraView.getHolder().addCallback(permissions);

        // preparo el detector de QR
        qrDetector = new QRDetector(context, token);
        barcodeDetector.setProcessor(qrDetector);
    }
}
