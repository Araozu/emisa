package api.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Utils {

    public static void imprimirEnJson(HttpServletResponse res, String json) throws IOException {
        res.setStatus(200);
        res.addHeader("Content-Type", "application/json");
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");

        res.getWriter().print(json);
    }

    public static void imprimirError(HttpServletResponse res, Exception e, String razon) {
        System.err.println(razon);
        if (e != null) {
            e.printStackTrace();
        }
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setStatus(500);
    }

    public static void imprimir400(HttpServletResponse res) {
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setStatus(400);
    }

    public static void doOptionsCors(HttpServletResponse res) {
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
    }

}
