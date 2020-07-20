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

public final class P8M_Centro_Servlet extends HttpServlet {

    final static String url = "jdbc:mysql://localhost:3306/";
    final static String dbName = "emisa";
    final static String driver = "com.mysql.jdbc.Driver";
    final static String userName = "root";
    final static String password = "";
    final static String timezoneFix = "?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
        "&useLegacyDatetimeCode=false&serverTimezone=UTC";

    static Connection conn = null;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                System.err.println("Error al iniciar conexion a la base de datos.");
                res.addHeader("Access-Control-Allow-Origin", "*");
                res.setStatus(500);
                return;
            }

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM p8m_centro;");

            ResultSet rs = stmt.executeQuery();
            PrintWriter writer = res.getWriter();

            res.setStatus(200);
            res.addHeader("Content-Type", "application/json");
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
            writer.print("[");
            boolean esPrimer = true;
            while (rs.next()) {
                writer.print(esPrimer? "{": ",{");
                writer.print("\"CenCod\":" + rs.getInt("CenCod") + ",");
                writer.print("\"CenNom\":\"" + rs.getString("CenNom") + "\",");
                writer.print("\"CenMinCod\":" + rs.getDouble("CenMinCod") + ",");
                writer.print("\"CenEstReg\":\"" + rs.getString("CenEstReg") + "\"");
                writer.print("}");
                esPrimer = false;
            }
            writer.print("]");

        } catch (Exception e) {
            System.err.println("Error al enviar los datos al cliente.");
            e.printStackTrace();
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
            res.setStatus(500);
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
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                System.err.println("Error al iniciar conexion a la base de datos.");
                res.addHeader("Access-Control-Allow-Origin", "*");
                res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                res.setStatus(500);
                return;
            }

            int cenCod = Integer.parseInt(req.getParameter("CenCod"));
            String cenNom = req.getParameter("CenNom");
            int cenMinCod = Integer.parseInt(req.getParameter("CenMinCod"));
            String cenEstReg = req.getParameter("CenEstReg");

            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO p8m_centro (CenCod, CenNom, CenMinCod, CenEstReg) VALUES (?, ?, ?, ?)"
            );

            statement.setInt(1, cenCod);
            statement.setString(2, cenNom);
            statement.setInt(3, cenMinCod);
            statement.setString(4, cenEstReg);

            int count = statement.executeUpdate();

            res.setStatus(200);
            res.addHeader("Content-Type", "application/json");
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");

            res.getWriter().print("{\"count\":" + count + "}");

        } catch (Exception e) {
            System.err.println("Error al enviar los datos al cliente.");
            e.printStackTrace();
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
            res.setStatus(500);
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
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                System.err.println("Error al iniciar conexion a la base de datos.");
                res.addHeader("Access-Control-Allow-Origin", "*");
                res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                res.addHeader("Access-Control-Allow-Headers", "Content-Type");
                res.setStatus(500);
                return;
            }

            try {
                String operacion = req.getParameter("operacion");

                if (operacion.equals("Modificar")) {
                    int cenCod = Integer.parseInt(req.getParameter("CenCod"));
                    String cenNom = req.getParameter("CenNom");
                    int cenMinCod = Integer.parseInt(req.getParameter("CenMinCod"));

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE p8m_centro SET CenNom=?, CenMinCod=? WHERE CenCod=?;"
                    );

                    statement.setString(1, cenNom);
                    statement.setInt(2, cenMinCod);
                    statement.setInt(3, cenCod);

                    int count = statement.executeUpdate();

                    res.setStatus(200);
                    res.addHeader("Content-Type", "application/json");
                    res.addHeader("Access-Control-Allow-Origin", "*");
                    res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                    res.addHeader("Access-Control-Allow-Headers", "Content-Type");
                    res.getWriter().print("{\"count\":" + count + "}");

                } else if (operacion.equals("Inactivar") || operacion.equals("Reactivar")) {

                    int cenCod = Integer.parseInt(req.getParameter("CenCod"));

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE p8m_centro SET CenEstReg=? WHERE CenCod=?;"
                    );
                    statement.setString(1, operacion.equals("inactivar")? "I": "A");
                    statement.setInt(2, cenCod);

                    int count = statement.executeUpdate();

                    res.setStatus(200);
                    res.addHeader("Content-Type", "application/json");
                    res.addHeader("Access-Control-Allow-Origin", "*");
                    res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                    res.addHeader("Access-Control-Allow-Headers", "Content-Type");
                    res.getWriter().print("{\"count\":" + count + "}");

                } else {
                    res.addHeader("Access-Control-Allow-Origin", "*");
                    res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                    res.addHeader("Access-Control-Allow-Headers", "Content-Type");
                    res.setStatus(400);
                }
            } catch (NullPointerException e) {
                res.addHeader("Access-Control-Allow-Origin", "*");
                res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                res.addHeader("Access-Control-Allow-Headers", "Content-Type");
                res.setStatus(400);
            }

        } catch (Exception e) {
            System.err.println("Error al enviar los datos al cliente.");
            e.printStackTrace();
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
            res.addHeader("Access-Control-Allow-Headers", "Content-Type");
            res.setStatus(500);
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
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                System.err.println("Error al iniciar conexion a la base de datos.");
                res.addHeader("Access-Control-Allow-Origin", "*");
                res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                res.addHeader("Access-Control-Allow-Headers", "Content-Type");
                res.setStatus(500);
                return;
            }

            int cenCod = Integer.parseInt(req.getParameter("CenCod"));

            PreparedStatement statement = conn.prepareStatement(
                "UPDATE p8m_centro SET CenEstReg='*' WHERE CenCod=?;"
            );

            statement.setInt(1, cenCod);

            int count = statement.executeUpdate();

            res.setStatus(200);
            res.addHeader("Content-Type", "application/json");
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
            res.addHeader("Access-Control-Allow-Headers", "Content-Type");

            res.getWriter().print("{\"count\":" + count + "}");

        } catch (Exception e) {
            System.err.println("Error al enviar los datos al cliente.");
            e.printStackTrace();
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
            res.addHeader("Access-Control-Allow-Headers", "Content-Type");
            res.setStatus(500);
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