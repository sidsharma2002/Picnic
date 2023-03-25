package com.example.picnic.common.image;

import android.graphics.Bitmap;

import androidx.annotation.WorkerThread;

public class BitmapUtils {

    // https://github.com/firebase/quickstart-android/issues/932#issuecomment-531204396
    @WorkerThread
    public byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        int[] argb = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(argb, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        int yuvSize = (int) (bitmap.getHeight() * bitmap.getWidth()
                + 2 * Math.ceil(bitmap.getHeight() / 2.0) * Math.ceil(bitmap.getWidth() / 2.0));

        byte[] yuv = new byte[yuvSize];

        encodeYUV420SP(yuv, argb, bitmap.getWidth(), bitmap.getHeight());

        return yuv;
    }

    private void encodeYUV420SP(byte[] yuv420sp, int[] argb, int width, int height) {
        int frameSize = width * height;

        int yIndex = 0;
        int uvIndex = frameSize;

        int R;
        int G;
        int B;
        int Y;
        int U;
        int V;

        int index = 0;

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                R = argb[index] & (0xff0000 >> 16);
                G = argb[index] & (0xff00 >> 8);
                B = argb[index] & (0xff);

                Y = (66 * R + 129 * G + 25 * B + 128 >> 8) + 16;
                U = (-38 * R - 74 * G + 112 * B + 128 >> 8) + 128;
                V = (112 * R - 94 * G - 18 * B + 128 >> 8) + 128;

                if (Y < 0) Y = 0;
                else if (Y > 255) Y = 255;

                yuv420sp[yIndex++] = (byte) Y;

                if (j % 2 == 0 && index % 2 == 0) {

                    if (V < 0) V = 0;
                    else if (V > 255) V = 255;
                    yuv420sp[uvIndex++] = (byte) V;

                    if (U < 0) U = 0;
                    else if (U > 255) U = 255;
                    yuv420sp[uvIndex++] = (byte) U;
                }

                index++;
            }
        }
    }
}
