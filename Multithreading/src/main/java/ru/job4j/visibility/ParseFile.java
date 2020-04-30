package ru.job4j.visibility;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.*;

@ThreadSafe
public class ParseFile {

    @GuardedBy("this")
    private File file;

    public synchronized void setFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return this.file;
    }

    public synchronized String getContent() throws IOException {
        try (InputStream inputStream = new FileInputStream(this.file);
             BufferedInputStream bis = new BufferedInputStream(inputStream)) {
            StringBuilder result = new StringBuilder();
            int data;
            while ((data = bis.read()) > 0) {
                result.append((char) data);
            }
            return result.toString();
        }
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        try (InputStream inputStream = new FileInputStream(this.file);
             BufferedInputStream bis = new BufferedInputStream(inputStream)) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = bis.read()) > 0) {
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }

    public synchronized void saveContent(String content) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(this.file);
        BufferedOutputStream bos = new BufferedOutputStream(outputStream)) {
            for (int index = 0; index < content.length(); index++) {
                bos.write(content.charAt(index));
            }
        }
    }
}
