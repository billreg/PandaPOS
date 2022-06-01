package com.floki.newsales;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ListView;

import com.floki.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.floki.entity.Product;
import com.floki.adapter.ProductSalesAdapter;
import com.floki.sqlite.SqliteHelperJarvis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SalesCarSacanner extends AppCompatActivity {

    private CameraSource cameraSource;
    private SurfaceView cameraView;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    private ListView listAddProduct;
    private String token = "";
    private String tokenanterior = "";
    List<String> tokensList = new ArrayList<>();

    SqliteHelperJarvis sqliteHelperJarvis;

    private ArrayList<Product> productListView = new ArrayList<>();
    private ArrayList<Product> productListViewArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_car_sacanner);

        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getBaseContext());

        cameraView = findViewById(R.id.camera_view);
        listAddProduct = findViewById(R.id.list_car_scanner);

        initQR();
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

                if (ActivityCompat.checkSelfPermission(SalesCarSacanner.this, Manifest.permission.CAMERA)
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


            @RequiresApi(api = Build.VERSION_CODES.O)
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
                                    listAddProduct.post(new Runnable() {
                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                        @Override
                                        public void run() {
                                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                            v.vibrate(100);

                                            List<Product> listProduct = sqliteHelperJarvis.listProductsByBarcode(token);

                                            if (listProduct.size()>0){

                                                for (Product product: listProduct){
                                                    productListViewArrayList.add(product);
                                                }
                                                ProductSalesAdapter productSalesAdapter = new ProductSalesAdapter(getBaseContext(), productListViewArrayList);
                                                listAddProduct.setAdapter(productSalesAdapter);
                                                barcodeDetector.release();

                                            }else{

                                            }

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