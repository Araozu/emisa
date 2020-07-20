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

public final class RZC_Categoria_Servlet extends HttpServlet {

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

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM rzc_categoria;");

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
                writer.print("\"CatCod\":" + rs.getInt("CatCod") + ",");
                writer.print("\"CatNom\":\"" + rs.getString("CatNom") + "\",");
                writer.print("\"CatSuel\":" + rs.getDouble("CatSuel") + ",");
                writer.print("\"CatEstReg\":\"" + rs.getString("CatEstReg") + "\"");
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

            int catCod = Integer.parseInt(req.getParameter("CatCod"));
            String catNom = req.getParameter("CatNom");
            double catSuel = Double.parseDouble(req.getParameter("CatSuel"));
            String catEstReg = req.getParameter("CatEstReg");

            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO rzc_categoria (CatCod, CatNom, CatSuel, CatEstReg) VALUES (?, ?, ?, ?)"
            );

            statement.setInt(1, catCod);
            statement.setString(2, catNom);
            statement.setDouble(3, catSuel);
            statement.setString(4, catEstReg);

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
                    int catCod = Integer.parseInt(req.getParameter("CatCod"));
                    String catNom = req.getParameter("CatNom");
                    double catSuel = Double.parseDouble(req.getParameter("CatSuel"));

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE rzc_categoria SET CatNom=?, CatSuel=? WHERE CatCod=?;"
                    );

                    statement.setString(1, catNom);
                    statement.setDouble(2, catSuel);
                    statement.setInt(3, catCod);

                    int count = statement.executeUpdate();

                    res.setStatus(200);
                    res.addHeader("Content-Type", "application/json");
                    res.addHeader("Access-Control-Allow-Origin", "*");
                    res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                    res.addHeader("Access-Control-Allow-Headers", "Content-Type");
                    res.getWriter().print("{\"count\":" + count + "}");

                } else if (operacion.equals("Inactivar") || operacion.equals("Reactivar")) {

                    int catCod = Integer.parseInt(req.getParameter("CatCod"));

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE rzc_categoria SET CatSuel=? WHERE CatCod=?;"
                    );
                    statement.setString(1, operacion.equals("inactivar")? "I": "A");
                    statement.setInt(2, catCod);

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

            int catCod = Integer.parseInt(req.getParameter("CatCod"));

            PreparedStatement statement = conn.prepareStatement(
                "UPDATE rzc_categoria SET CatSuel='*' WHERE CatCod=?;"
            );

            statement.setInt(1, catCod);

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
