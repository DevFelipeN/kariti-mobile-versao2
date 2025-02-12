package online.padev.kariti;

import android.graphics.Bitmap;
import com.google.zxing.LuminanceSource;

public class BitmapLuminanceSource extends LuminanceSource {
    private final byte[] luminances;

    public BitmapLuminanceSource(Bitmap bitmap) {
        super(bitmap.getWidth(), bitmap.getHeight());

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        // Convertendo os pixels para o formato de luminância em escala de cinza
        luminances = new byte[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixels[y * width + x];
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;
                // Calculando a luminância média
                luminances[y * width + x] = (byte) ((r + g + b) / 3);
            }
        }
    }

    @Override
    public byte[] getMatrix() {
        return luminances;
    }

    @Override
    public byte[] getRow(int y, byte[] row) {
        int width = getWidth();
        if (row == null || row.length < width) {
            row = new byte[width];
        }
        System.arraycopy(luminances, y * width, row, 0, width);
        return row;
    }
}
