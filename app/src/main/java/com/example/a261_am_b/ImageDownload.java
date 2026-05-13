package com.example.a261_am_b;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageDownload extends AsyncTask<String, Integer, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... strings) {
        //TODO: traer la url de la imagen a descargar
        String urlImagen = strings[0];
        //TODO: crear objeto para de la descarga URL
        try {
            URL urlOrigen = new URL(urlImagen);
            //TODO: traer contenido Stream
            InputStream input = (InputStream) urlOrigen.getContent();
            //TODO: formatear para llevar a Bitmap
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }
}
