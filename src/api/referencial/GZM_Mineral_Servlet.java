package api.referencial;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.mysql.jdbc.Driver;
import api.utils.Utils;

public final class GZM_Mineral_Servlet extends HttpServlet {

    final static String url = "jdbc:mysql://localhost:3306/";
    final static String dbName = "emisa";
    final static String driver = "com.mysql.jdbc.Driver";
    final static String userName = "root";
    final static String password = "";
    final static String timezoneFix = "?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
        "&useLegacyDatetimeCode=false&serverTimezone=UTC";

    static Connection conn = null;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {

        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                Utils.imprimirError(res, null, "Error al iniciar conexion a la base de datos.");
                return;
            }

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM gzm_mineral;");

            ResultSet rs = stmt.executeQuery();
            PrintWriter writer = res.getWriter();

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            boolean esPrimer = true;
            while (rs.next()) {
                sb.append(esPrimer? "{": ",{")
                    .append("\"MinCod\":" + rs.getInt("MinCod") + ",")
                    .append("\"MinNom\":\"" + rs.getString("MinNom") + "\",")
                    .append("\"MinEstReg\":\"" + rs.getString("MinEstReg") + "\"")
                    .append("}");
                esPrimer = false;
            }
            sb.append("]");

            Utils.imprimirEnJson(res, sb.toString());

        } catch (Exception e) {
            Utils.imprimirError(res, e, "Error al enviar los datos al cliente.");
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
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                Utils.imprimirError(res, null, "Error al iniciar conexion a la base de datos.");
                return;
            }

            int minCod = Integer.parseInt(req.getParameter("MinCod"));
            String minNom = req.getParameter("MinNom");
            String minEstReg = req.getParameter("MinEstReg");

            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO gzm_mineral (MinCod, MinNom, MinEstReg) VALUES (?, ?, ?)"
            );

            statement.setInt(1, minCod);
            statement.setString(2, minNom);
            statement.setString(3, minEstReg);

            int count = statement.executeUpdate();
            Utils.imprimirEnJson(res, "{\"count\":" + count + "}");

        } catch (Exception e) {
            Utils.imprimirError(res, e, "Error al enviar los datos al cliente.");
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
    public void doPut(HttpServletRequest req, HttpServletResponse res) {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                Utils.imprimirError(res, null, "Error al iniciar conexion a la base de datos.");
                return;
            }

            try {
                String operacion = req.getParameter("operacion");

                if (operacion.equals("Modificar")) {
                    int minCod = Integer.parseInt(req.getParameter("MinCod"));
                    String minNom = req.getParameter("MinNom");

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE gzm_mineral SET MinNom=? WHERE MinCod=?;"
                    );

                    statement.setString(1, minNom);
                    statement.setInt(2, minCod);

                    int count = statement.executeUpdate();
                    Utils.imprimirEnJson(res, "{\"count\":" + count + "}");

                } else if (operacion.equals("Inactivar") || operacion.equals("Reactivar")) {

                    int minCod = Integer.parseInt(req.getParameter("MinCod"));

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE gzm_mineral SET MinEstReg=? WHERE MinCod=?;"
                    );
                    statement.setString(1, operacion.equals("Inactivar")? "I": "A");
                    statement.setInt(2, minCod);

                    int count = statement.executeUpdate();
                    Utils.imprimirEnJson(res, "{\"count\":" + count + "}");

                } else {
                    Utils.imprimir400(res);
                }
            } catch (NullPointerException e) {
                Utils.imprimir400(res);
            }

        } catch (Exception e) {
            Utils.imprimirError(res, e, "Error al enviar los datos al cliente.");
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
    public void doDelete(HttpServletRequest req, HttpServletResponse res) {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                Utils.imprimirError(res, null, "Error al iniciar conexion a la base de datos.");
                return;
            }

            int minCod = Integer.parseInt(req.getParameter("MinCod"));

            PreparedStatement statement = conn.prepareStatement(
                "UPDATE gzm_mineral SET MinEstReg='*' WHERE MinCod=?;"
            );

            statement.setInt(1, minCod);

            int count = statement.executeUpdate();
            Utils.imprimirEnJson(res, "{\"count\":" + count + "}");

        } catch (Exception e) {
            Utils.imprimirError(res, e, "Error al enviar los datos al cliente.");
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
        Utils.doOptionsCors(res);
    }

}