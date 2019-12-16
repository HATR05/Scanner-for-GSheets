package com.donisi.scannerforgsheets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements Scanned.OnFragmentInteractionListener, FragmentQR.OnFragmentInteractionListener {

    Scanned fragment_scanned;
    FragmentQR fragment_qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_scanned = new Scanned();
        fragment_qr = new FragmentQR();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_data,fragment_qr).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onClick(View view){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (view.getId()){
            case R.id.scanner_btn:
                transaction.replace(R.id.fragment_data, fragment_qr);
                break;
            case R.id.scanned_btn:
                transaction.replace(R.id.fragment_data, fragment_scanned);
                break;
        }

        transaction.commit();
    }
}
