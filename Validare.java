import java.text.SimpleDateFormat;
import java.util.Date;

public class Validare {

    public static boolean esteEmailValid(String email) {
        return email != null && email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    }

    public static boolean esteNumeValid(String nume) {
        return nume != null && nume.matches("^[a-zA-Z ]+$");
    }

    public static boolean esteDataValida(String data) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date dataIntrodusa = sdf.parse(data);
            return !dataIntrodusa.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }


    public static boolean esteOraValida(String ora) {
        return ora != null && ora.matches("^([01]?\\d|2[0-3]):[0-5]\\d$");
    }

    public static boolean esteDoarLitere(String text) {
        return text != null && text.matches("^[a-zA-Z ]+$");
    }
}
