package com.stewart.loyaltypoints;

import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;

import static com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.Q;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private ImageView imageView;
    private String EditTextValue;
    Thread thread;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        //Text to be generated - this will need changin to the users points
        editText = (EditText) findViewById( R.id.etMainGenerator );
        //Button to generate the image - this will need removing as the qr code will be pre loaded

        button = (Button) findViewById( R.id.btnGenerate );
        //Where the QR Code will be put
        imageView = (ImageView) findViewById( R.id.generatedImage );


        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextValue = editText.getText().toString().trim();

                try {
                    bitmap = TextToImageEncode(EditTextValue);
                    imageView.setImageBitmap( bitmap );

                } catch (WriterException e) {
                    e.printStackTrace();
                }


            }




        } );
    }

    private Bitmap TextToImageEncode(String editTextValue) throws WriterException {

        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode( editTextValue,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    200, 200, null

                    );
        } catch (IllegalArgumentException Illegalarguementexception) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for(int y=0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get( x, y ) ?
                        getResources().getColor( R.color.black ):
                        getResources().getColor( R.color.white );
            }
        }
        Bitmap bitmap = Bitmap.createBitmap( bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444 );

        bitmap.setPixels( pixels, 0, 500, 0,0,bitMatrixWidth, bitMatrixHeight );
        return bitmap;
    }


}
