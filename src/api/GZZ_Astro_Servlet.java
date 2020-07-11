package api;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                System.err.println("Error al iniciar conexion a la base de datos.");
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.setStatus(500);
                return;
            }

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM gzz_astros;");

            ResultSet rs = stmt.executeQuery();
            PrintWriter writer = response.getWriter();

            response.setStatus(200);
            response.addHeader("Content-Type", "application/json");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
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
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
            response.setStatus(500);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                System.err.println("Error al iniciar conexion a la base de datos.");
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                response.setStatus(500);
                return;
            }

            int astCod = Integer.parseInt(request.getParameter("AstCod"));
            String astNom = request.getParameter("AstNom");
            int astTip = Integer.parseInt(request.getParameter("AstTip"));
            String astEstReg = request.getParameter("AstEstReg");

            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO gzz_astros (AstCod, AstNom, AstTip, AstEstReg) VALUES (?, ?, ?, ?)"
            );

            statement.setInt(1, astCod);
            statement.setString(2, astNom);
            statement.setInt(3, astTip);
            statement.setString(4, astEstReg);

            int count = statement.executeUpdate();

            response.setStatus(200);
            response.addHeader("Content-Type", "application/json");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
            
            response.getWriter().print("{\"count\":" + count + "}");

        } catch (Exception e) {
            System.err.println("Error al enviar los datos al cliente.");
            e.printStackTrace();
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
            response.setStatus(500);
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
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            System.out.println(request.getRequestURL());

            if (conn.isClosed()) {
                System.err.println("Error al iniciar conexion a la base de datos.");
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                response.addHeader("Access-Control-Allow-Headers", "Content-Type");
                response.setStatus(500);
                return;
            }

            try {
                String operacion = request.getParameter("operacion");

                if (operacion.equals("Modificar")) {
                    int astCod = Integer.parseInt(request.getParameter("AstCod"));
                    String astNom = request.getParameter("AstNom");
                    int astTip = Integer.parseInt(request.getParameter("AstTip"));

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE gzz_astros SET AstNom=?, AstTip=? WHERE AstCod=?;"
                    );

                    statement.setString(1, astNom);
                    statement.setInt(2, astTip);
                    statement.setInt(3, astCod);

                    int count = statement.executeUpdate();

                    response.setStatus(200);
                    response.addHeader("Content-Type", "application/json");
                    response.addHeader("Access-Control-Allow-Origin", "*");
                    response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                    response.addHeader("Access-Control-Allow-Headers", "Content-Type");

                    response.getWriter().print("{\"count\":" + count + "}");

                } else {
                    response.addHeader("Access-Control-Allow-Origin", "*");
                    response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                    response.addHeader("Access-Control-Allow-Headers", "Content-Type");
                    response.setStatus(400);
                }
            } catch (NullPointerException e) {
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                response.addHeader("Access-Control-Allow-Headers", "Content-Type");
                response.setStatus(400);
            }

        } catch (Exception e) {
            System.err.println("Error al enviar los datos al cliente.");
            e.printStackTrace();
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setStatus(500);
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
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
