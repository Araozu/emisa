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

public class GZZ_Astro_Servlet extends HttpServlet {

    final static String url = "jdbc:mysql://localhost:3306/";
    final static String dbName = "emisa";
    final static String driver = "com.mysql.jdbc.Driver";
    final static String userName = "root";
    final static String password = "";
    final static String timezoneFix = "?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
        "&useLegacyDatetimeCode=false&serverTimezone=UTC";

    static Connection conn = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                System.err.println("Error al iniciar conexion a la base de datos.");
                response.setStatus(500);
                return;
            }

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM gzz_astros;");

            ResultSet rs = stmt.executeQuery();
            PrintWriter writer = response.getWriter();

            response.setStatus(200);
            response.addHeader("Content-Type", "application/json");
            response.addHeader("Access-Control-Allow-Origin", "*");
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                System.err.println("Error al iniciar conexion a la base de datos.");
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
            
            response.getWriter().print("{\"count\":" + count + "}");

        } catch (Exception e) {
            System.err.println("Error al enviar los datos al cliente.");
            e.printStackTrace();
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

}
