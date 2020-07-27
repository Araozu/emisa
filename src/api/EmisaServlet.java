package api;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

public class EmisaServlet extends HttpServlet {

    protected static String url = "jdbc:mysql://localhost:3306/";
    protected static String dbName = "emisa";
    protected static String driver = "com.mysql.jdbc.Driver";
    protected static String userName = "root";
    protected static String password = "";
    protected static String timezoneFix = "?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
        "&useLegacyDatetimeCode=false&serverTimezone=UTC";

    protected static Connection conn = null;

    public void imprimirEnJson(HttpServletResponse res, String json) throws IOException {
        res.setStatus(200);
        res.addHeader("Content-Type", "application/json");
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");

        res.getWriter().print(json);
    }

    public void imprimirError(HttpServletResponse res, Exception e, String razon) {
        System.err.println(razon);
        if (e != null) {
            e.printStackTrace();
        }
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setStatus(500);
    }

    public void imprimir400(HttpServletResponse res) {
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setStatus(400);
    }

    @Override
    public void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        super.doOptions(req, res);
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
    }

}
