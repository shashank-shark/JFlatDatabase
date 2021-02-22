import java.io.IOException;

public class UseJFlatDB {

    public static void main (String[] args) {
        JFlatDatabase db = null;

        try {
            db = new JFlatDatabase("simple.db");

            if (db.numberOfRecords() == 0) {
                // we populate some of the records here
                db.append("1-9009-3323-4x", "Wiper Blade Micro Edge", 30, 2468);
                db.append("1-3233-44923-7j", "Parking Brake Cable", 5, 1439);
                db.append("2-3399-6693-2m", "Halogen Bulb H4 55/60W", 22, 813);
                db.append("2-599-2029-6k", "Turbo Oil Line O-Ring ", 26, 155);
                db.append("3-1299-3299-9u", "Air Pump Electric", 9, 20200);

                dumpRecords(db);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }

    private static void dumpRecords(JFlatDatabase db) throws IOException {

        for (int i = 0; i < db.numberOfRecords(); i++) {
            JFlatDatabase.Part part = db.select(i);
            System.out.print(format(part.getPartNum(), JFlatDatabase.PNUMLEN, true));
            System.out.print(" | ");
            System.out.print(format(part.getPartDesc(), JFlatDatabase.DESCLEN, true));
            System.out.print(" | ");
            System.out.print(format("" + part.getQuantity(), 10, false));
            System.out.print(" | ");
            String s = part.getUnitCost() / 100.0 + "." + part.getUnitCost() % 100;
            if (s.charAt(s.length() - 2) == '.')
                s = s + "0";
            System.out.println(format(s, 10, false));
        }

        System.out.println("Number of records : " + db.numberOfRecords());
        System.out.println();
    }

    private static String format(String value, int maxWidth, boolean leftAlign) {
        StringBuffer sb = new StringBuffer();
        int len = value.length();

        if (len > maxWidth) {
            len = maxWidth;
            value = value.substring(0, len);
        }

        if (leftAlign) {
            sb.append(value);
            for (int i = 0; i < maxWidth - len; i++) {
                sb.append(" ");
            }
        } else {
            for (int i = 0; i < maxWidth - len; i++) {
                sb.append(" ");
            }
            sb.append(value);
        }

       return sb.toString();
    }
}
