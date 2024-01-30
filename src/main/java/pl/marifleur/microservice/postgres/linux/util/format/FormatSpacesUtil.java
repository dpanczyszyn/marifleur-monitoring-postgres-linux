package pl.marifleur.microservice.postgres.linux.util.format;

public class FormatSpacesUtil {

    public String getSpaces(String word, int width) {
        String string = "";
        int diff = width - word.length();
        for (int i = 0; i < diff; i++) {
            string += " ";
        }
        return string;
    }
}
