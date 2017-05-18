package com.example.ptmarketing04.pruebaadjuntos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    protected TextView tv;
    protected Button btImg,btPdf,btAceptar;
    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.tvName);
        btImg = (Button)findViewById(R.id.btImg);
        btPdf = (Button)findViewById(R.id.btPdf);
        btAceptar = (Button)findViewById(R.id.btAceptar);

        btPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });

    }

    //De esta manera consigues abrir la pantalla para elegir documento
    // tambien consigues ver el documento seleccionado
    // lo que no has conseguido es subirlo a bd / servidor
    // tienes que plantearte lo de FIREBASE


    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("application/pdf");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.e( "Uri: " , uri.toString());
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+uri.getPath());
                Log.e( "doc: " ,file.toString());
                Log.e( "doc abc: " ,file.getAbsolutePath());

                showFile(uri);

            }
        }
    }

    private void showFile(Uri fileUri){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        //Para ver imagenes será diferente tendrás que cambiar el application/pdf mira en android developer
        intent.setDataAndType(fileUri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(intent);
    }


}
