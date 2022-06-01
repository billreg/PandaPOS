package com.floki.inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.floki.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InventoryAddBarcode extends AppCompatActivity {

    private CameraSource cameraSource;
    private SurfaceView cameraView;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    private TextView barcodeEscanner;
    Button addBarcodeScanner;
    Button cancelBarcodeScanner;

    private String token = "";
    private String tokenanterior = "";
    List<String> tokensList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_add_barcode);

        cameraView = findViewById(R.id.camera_view);

        barcodeEscanner = (TextView) findViewById(R.id.barcode_escanner);
        addBarcodeScanner = findViewById(R.id.add_barcode_scanner);
        cancelBarcodeScanner = findViewById(R.id.cancel_barcode_scanner);
        initQR();
        addBarcodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("barcode", barcodeEscanner.getText().toString());
                setResult(InventoryAddBarcode.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    public void initQR() {
        BarcodeDetector barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.ALL_FORMATS)
                        .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(400, 800)
                .setAutoFocusEnabled(true)
                .build();


        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                if (ActivityCompat.checkSelfPermission(InventoryAddBarcode.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) ;
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                    return;
                } else {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release() {
            }


            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                int countValidToken = 0;
                if (barcodes.size() > 0) {

                    token = barcodes.valueAt(0).displayValue;
                    tokensList.add(token);
                    if (tokensList.size()==5){

                        for (String str: tokensList){

                            if (str.equals(token)){
                                countValidToken = countValidToken +1;
                                if (countValidToken == 5){
                                    barcodeEscanner.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            barcodeEscanner.setText(token);
                                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                            v.vibrate(100);
                                        }
                                    });

                                }
                            }

                        }

                    }

                }


            }
        });

    }

}