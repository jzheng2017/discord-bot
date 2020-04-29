package app.utils;

import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

    public static void saveImageFromByteArray(byte[] byteArray, String imageType, String filePath, String fileName) {
        try {
            FileOutputStream imgOutFile = new FileOutputStream(String.format("%s%s.%s", filePath, fileName, imageType));
            imgOutFile.write(byteArray);
            imgOutFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
