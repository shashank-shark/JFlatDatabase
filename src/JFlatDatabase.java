import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class JFlatDatabase {

    public static final int PNUMLEN = 20;
    public static final int DESCLEN = 30;
    public static final int QUANLEN = 4;
    public static final int COSTLEN = 4;

    public static final String READ_WRITE_ACCESS_MODE = "rw";

    private static final int RECLEN = (2 * PNUMLEN) + (2 * DESCLEN) + QUANLEN + COSTLEN;
    private RandomAccessFile raf;

    public JFlatDatabase(String path) {
        try {
            raf = new RandomAccessFile(path, READ_WRITE_ACCESS_MODE);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public void append(String partNum, String partDesc, int quantity, int unitCost) {
        try {
            raf.seek(raf.length());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void write(String partNum, String partDesc, int quantity, int unitCost) {
        StringBuffer sb = new StringBuffer(partNum);

        if (sb.length() > PNUMLEN)
            sb.setLength(PNUMLEN);
        else if (sb.length() < PNUMLEN) {

        }
    }

    public static void main (String[] args) {

    }
}
