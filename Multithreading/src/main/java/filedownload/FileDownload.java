package filedownload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

//String file = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";

public class FileDownload {

    private String tempDir = System.getProperty("java.io.tmpdir");

    public void downLoadWithLimit(String url, int speedLimit) {
        long methodTimeStart = System.nanoTime();
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(tempDir + File.separator + "pom_tmp.xml")) {
            byte[] dataBuffer = new byte[10485760];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                long start = System.nanoTime();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long end = System.nanoTime();
                if ((end - start) / 1000000 >= 1000) {
                    float sleep = ((float) bytesRead - speedLimit) / speedLimit;
                    Thread.sleep((int) sleep * 1000);
                }
            }
            long methodTimeEnd = System.nanoTime();
            System.out.println((methodTimeEnd - methodTimeStart) / 1000000);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void downLoadListWithLimit(List<String> urlList, int speedLimit) {
        try {
            for (String url: urlList) {
                Thread thread = new Thread(() -> downLoadWithLimit(url, speedLimit));
                thread.join();
                thread.start();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileDownload test = new FileDownload();
        test.downLoadWithLimit("https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml", 500);
    }
}
