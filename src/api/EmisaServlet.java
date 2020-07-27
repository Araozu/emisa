package api;

import com.mysql.jdbc.Driver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EmisaServlet extends HttpServlet {

    protected static String url = "jdbc:mysql://localhost:3306/";
    protected static String dbName = "emisa";
    protected static String driver = "com.mysql.jdbc.Driver";
    protected static String userName = "root";
    protected static String password = "";
    protected static String timezoneFix = "?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
        "&useLegacyDatetimeCode=false&serverTimezone=UTC";

    protected static Connection conn = null;

    protected void imprimirEnJson(HttpServletResponse res, String json) throws IOException {
        res.setStatus(200);
        res.addHeader("Content-Type", "application/json");
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");

        res.getWriter().print(json);
    }

    protected void imprimirError(HttpServletResponse res, Exception e, String razon) {
        System.err.println(razon);
        if (e != null) {
            e.printStackTrace();
        }
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setStatus(500);
    }

    protected void imprimir400(HttpServletResponse res) {
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setStatus(400);
    }

    protected void imprimirErrorConexion(HttpServletResponse res) {
        imprimirError(res, null, "Error al iniciar conexion a la base de datos.");
    }

    protected void imprimirErrorEnvio(HttpServletResponse res, Exception e) {
        imprimirError(res, e, "Error al enviar los datos al cliente.");
    }

    protected void doGetG(HttpServletRequest req, HttpServletResponse res, String tabla, HashMap<String, String> campos) {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                imprimirErrorConexion(res);
                return;
            }

            ResultSet rs = conn.prepareStatement("SELECT * FROM " + tabla + ";").executeQuery();

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            boolean esPrimer = true;
            while (rs.next()) {
                sb.append(esPrimer ? "{" : ",{");
                int i = 0;
                int max = campos.size();
                for (Map.Entry<String, String> entry : campos.entrySet()) {
                    sb.append("\"" + entry.getKey() + "\":");
                    switch (entry.getValue()) {
                        case "int": {
                            sb.append(rs.getInt(entry.getKey()));
                            break;
                        }
                        case "decimal": {
                            sb.append(rs.getDouble(entry.getKey()));
                            break;
                        }
                        case "string": {
                            sb.append("\"" + rs.getString(entry.getKey()) + "\"");
                            break;
                        }
                    }
                    if (i < max) sb.append(",");
                    i++;
                }
                sb.append("}");
                esPrimer = false;
            }
            sb.append("]");

            imprimirEnJson(res, sb.toString());
        } catch (Exception e) {
            imprimirErrorEnvio(res, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.err.println("Error al terminar la conexión con la base de datos.");
                e.printStackTrace();
            }
        }
    }

    protected void doInactivarReactivar(HttpServletRequest req, HttpServletResponse res, String nombreTabla, String estReg, String clave, String operacion) throws SQLException, IOException {
        int catCod = Integer.parseInt(req.getParameter(clave));

        PreparedStatement statement = conn.prepareStatement(
            "UPDATE " + nombreTabla + " SET " + estReg + "=? WHERE " + clave + "=?;"
        );
        statement.setString(1, operacion.equals("Inactivar")? "I": "A");
        statement.setInt(2, catCod);

        int count = statement.executeUpdate();

        imprimirEnJson(res, "{\"count\":" + count + "}");
    }

    protected void doDeleteG(HttpServletRequest req, HttpServletResponse res, String nombreTabla, String estReg, String clave) {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                imprimirErrorConexion(res);
                return;
            }

            int alimCod = Integer.parseInt(req.getParameter(clave));

            PreparedStatement statement = conn.prepareStatement(
                "UPDATE " + nombreTabla + " SET " + estReg + "='*' WHERE " + clave + "=?;"
            );

            statement.setInt(1, alimCod);

            int count = statement.executeUpdate();

            imprimirEnJson(res, "{\"count\":" + count + "}");

        } catch (Exception e) {
            imprimirErrorEnvio(res, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.err.println("Error al terminar la conexión con la base de datos.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        super.doOptions(req, res);
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
    }

}
