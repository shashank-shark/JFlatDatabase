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

    public void close() {
        try {
            raf.close();
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
    }

    public void append(String partNum, String partDesc, int quantity, int unitCost) {
        try {
            raf.seek(raf.length());
            write(partNum, partDesc, quantity, unitCost);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public int numberOfRecords() throws IOException {
        return (int) raf.length() / RECLEN;
    }

    public Part select(int recordNo) throws IOException {
        if (recordNo < 0 || recordNo > numberOfRecords()) {
            throw new IllegalArgumentException(recordNo + " out of range.");
        }
        raf.seek((long) recordNo * RECLEN);
        return read();
    }

    public void update(int recordNo, String partNum, String partDesc, int quantity, int unitCost) throws IOException {
        if (recordNo < 0 || recordNo > numberOfRecords()) {
            throw new IllegalArgumentException(recordNo + " out of range.");
        }
        raf.seek((long) recordNo * RECLEN);
        write(partNum, partDesc, quantity, unitCost);
    }

    private Part read() throws IOException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < PNUMLEN; i++) {
            sb.append(raf.readChar());
        }
        String partNum = sb.toString().trim();
        sb.setLength(0);

        for (int i = 0; i < DESCLEN; i++) {
            sb.append(raf.readChar());
        }
        String partDesc = sb.toString().trim();
        int quantity = raf.readInt();
        int unitCost = raf.readInt();

        return new Part(partNum, partDesc, quantity, unitCost);
    }

    public void write(String partNum, String partDesc, int quantity, int unitCost) throws IOException {
        StringBuffer sb = new StringBuffer(partNum);

        if (sb.length() > PNUMLEN)
            sb.setLength(PNUMLEN);
        else if (sb.length() < PNUMLEN) {
            int len = PNUMLEN - sb.length();
            for (int i = 0; i < len; i++)   sb.append(" ");
        }
        raf.writeChars(sb.toString());

        sb = new StringBuffer(partDesc);
        if (sb.length() > DESCLEN)
            sb.setLength(DESCLEN);
        else if (sb.length() < DESCLEN) {
            int len = DESCLEN - sb.length();
            for (int i = 0; i < len; i++)   sb.append(" ");
        }
        raf.writeChars(sb.toString());
        raf.writeInt(quantity);
        raf.writeInt(unitCost);
    }

    public static class Part {
        private String partNum;
        private String partDesc;
        private int quantity;
        private int unitCost;

        public Part(String partNum, String partDesc, int quantity, int unitCost) {
            this.partNum = partNum;
            this.partDesc = partDesc;
            this.quantity = quantity;
            this.unitCost = unitCost;
        }

        public String getPartNum() {
            return partNum;
        }

        public String getPartDesc() {
            return partDesc;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getUnitCost() {
            return unitCost;
        }
    }
}
