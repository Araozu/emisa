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

public final class GZZ_Astro_Servlet extends HttpServlet {

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

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM gzz_astros;");

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
                writer.print("\"AstCod\":" + rs.getInt("AstCod") + ",");
                writer.print("\"AstNom\":\"" + rs.getString("AstNom") + "\",");
                writer.print("\"AstTip\":" + rs.getDouble("AstTip") + ",");
                writer.print("\"AstEstReg\":\"" + rs.getString("AstEstReg") + "\"");
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

            int astCod = Integer.parseInt(req.getParameter("AstCod"));
            String astNom = req.getParameter("AstNom");
            int astTip = Integer.parseInt(req.getParameter("AstTip"));
            String astEstReg = req.getParameter("AstEstReg");

            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO gzz_astros (AstCod, AstNom, AstTip, AstEstReg) VALUES (?, ?, ?, ?)"
            );

            statement.setInt(1, astCod);
            statement.setString(2, astNom);
            statement.setInt(3, astTip);
            statement.setString(4, astEstReg);

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
                    int astCod = Integer.parseInt(req.getParameter("AstCod"));
                    String astNom = req.getParameter("AstNom");
                    int astTip = Integer.parseInt(req.getParameter("AstTip"));

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE gzz_astros SET AstNom=?, AstTip=? WHERE AstCod=?;"
                    );

                    statement.setString(1, astNom);
                    statement.setInt(2, astTip);
                    statement.setInt(3, astCod);

                    int count = statement.executeUpdate();

                    res.setStatus(200);
                    res.addHeader("Content-Type", "application/json");
                    res.addHeader("Access-Control-Allow-Origin", "*");
                    res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                    res.addHeader("Access-Control-Allow-Headers", "Content-Type");
                    res.getWriter().print("{\"count\":" + count + "}");

                } else if (operacion.equals("Inactivar") || operacion.equals("Reactivar")) {

                    int astCod = Integer.parseInt(req.getParameter("AstCod"));

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE gzz_astros SET AstEstReg=? WHERE AstCod=?;"
                    );
                    statement.setString(1, operacion.equals("inactivar")? "I": "A");
                    statement.setInt(2, astCod);

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

            int astCod = Integer.parseInt(req.getParameter("AstCod"));

            PreparedStatement statement = conn.prepareStatement(
                "UPDATE gzz_astros SET AstEstReg='*' WHERE AstCod=?;"
            );

            statement.setInt(1, astCod);

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
