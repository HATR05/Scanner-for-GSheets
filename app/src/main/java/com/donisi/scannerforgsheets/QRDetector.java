package com.donisi.scannerforgsheets;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;


public class QRDetector implements Detector.Processor {

    private Context context;
    private String token;
    private String tokenanterior;

    public  QRDetector(Context context, String token){
        this.context = context;
        this.token = token;
        tokenanterior = "";
    }

    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections detections) {
        final SparseArray<Barcode> barcodes = detections.getDetectedItems();

        if (barcodes.size() > 0) {

            token = barcodes.valueAt(0).displayValue;   // obtenemos el token
            if (!token.equals(tokenanterior)) {
                tokenanterior = token;
                Log.i("token", token);
                Log.i("YAA", token);



                new Thread(new Runnable() {
                    public void run() {
                        try {
                            synchronized (this) {
                                wait(5000);
                                // limpiamos el token
                                tokenanterior = "";
                            }
                        } catch (InterruptedException e) {
                            /* TODO Auto-generated catch block */
                            Log.e("Error", "Waiting didnt work!!");
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
}
