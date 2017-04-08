package com.oodles.portraitmodescanner;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button scan;
    private TextView barcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    private void setListener() {
        scan.setOnClickListener(this);
    }

    private void initView() {
        scan = (Button) findViewById(R.id.scan);
        barcode = (TextView) findViewById(R.id.barcode);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.scan){
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setPrompt("Place a barcode inside the viewfinder rectangle to scan it");
            integrator.setBeepEnabled(false);
            integrator.setOrientationLocked(false);
            integrator.setTimeout(20000);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            Intent intent = integrator.createScanIntent();
            startActivityForResult(intent,101);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode== 101){
            IntentResult result = IntentIntegrator.parseActivityResult(IntentIntegrator.REQUEST_CODE, resultCode, intent);
            if(result != null) {
                if(result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Scanned", Toast.LENGTH_SHORT).show();
                    barcode.setText(result.getContents());
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }
}
