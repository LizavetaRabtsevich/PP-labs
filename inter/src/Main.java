import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        // Локализация для разных языков
        Locale[] locales = {
                new Locale("en", "US"), // Английский (США)
                new Locale("ru", "RU"), // Русский
                new Locale("es", "ES"), // Испанский
                Locale.FRENCH,          // Французский
                Locale.GERMAN           // Немецкий
        };

        for (Locale locale : locales) {
            ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);
            System.out.println(locale.getDisplayName() + ":");
            System.out.println("  " + messages.getString("greeting"));
            System.out.println("  " + messages.getString("welcome"));
            System.out.println("  " + messages.getString("farewell"));
            System.out.println();
        }
    }
}