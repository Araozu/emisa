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

public final class RZC_Empleo_Servlet extends HttpServlet {

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

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM rzc_empleo;");

            ResultSet rs = stmt.executeQuery();
            PrintWriter writer = res.getWriter();

            res.setStatus(200);
            res.addHeader("Content-Type", "application/json");
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
            writer.print("[");
            boolean esPrimer = true;
            // TODO: Corregir
            while (rs.next()) {
                writer.print(esPrimer? "{": ",{");
                writer.print("\"EmpCod\":" + rs.getInt("EmpCod") + ",");
                writer.print("\"EmpNom\":\"" + rs.getString("EmpNom") + "\",");
                writer.print("\"EmpSue\":" + rs.getDouble("EmpSue") + ",");
                writer.print("\"EmpViaCod\":" + rs.getInt("EmpViaCod") + ",");
                writer.print("\"ViaFacCod\":" + rs.getInt("ViaFacCod") + ",");
                writer.print("\"FacUbiCod\":" + rs.getInt("FacUbiCod") + ",");
                writer.print("\"UbiAstCod\":" + rs.getInt("UbiAstCod") + ",");
                writer.print("\"ViaNavCod\":" + rs.getInt("ViaNavCod") + ",");
                writer.print("\"EmpEstReg\":\"" + rs.getString("AstEstReg") + "\"");
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

            int empCod = Integer.parseInt(req.getParameter("EmpCod"));
            String empNom = req.getParameter("EmpNom");
            double empSue = Double.parseDouble(req.getParameter("EmpSue"));
            int empViaCod = Integer.parseInt(req.getParameter("EmpViaCod"));
            int viaFacCod = Integer.parseInt(req.getParameter("ViaFacCod"));
            int facUbiCod = Integer.parseInt(req.getParameter("FacUbiCod"));
            int ubiAstCod = Integer.parseInt(req.getParameter("UbiAstCod"));
            int viaNavCod = Integer.parseInt(req.getParameter("ViaNavCod"));
            String empEstReg = req.getParameter("EmpEstReg");

            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO rzc_empleo (EmpCod, EmpNom, EmpSue, EmpViaCod, ViaFacCod, FacUbiCod, UbiAstCod, ViaNavCod, EmpEstReg) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

            statement.setInt(1, empCod);
            statement.setString(2, empNom);
            statement.setDouble(3, empSue);
            statement.setInt(4, empViaCod);
            statement.setInt(5, viaFacCod);
            statement.setInt(6, facUbiCod);
            statement.setInt(7, ubiAstCod);
            statement.setInt(8, viaNavCod);
            statement.setString(9, empEstReg);

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
                    int empCod = Integer.parseInt(req.getParameter("EmpCod"));
                    String empNom = req.getParameter("EmpNom");
                    double empSue = Double.parseDouble(req.getParameter("EmpSue"));
                    int empViaCod = Integer.parseInt(req.getParameter("EmpViaCod"));
                    int viaFacCod = Integer.parseInt(req.getParameter("ViaFacCod"));
                    int facUbiCod = Integer.parseInt(req.getParameter("FacUbiCod"));
                    int ubiAstCod = Integer.parseInt(req.getParameter("UbiAstCod"));
                    int viaNavCod = Integer.parseInt(req.getParameter("ViaNavCod"));
                    String empEstReg = req.getParameter("EmpEstReg");
                    
                    //esta parte no se como se hace (0.0)/
                    //ya que segun la base de datos hay 5 llaves primarias, ademas del empcod
                    
                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE rzc_empleo SET EmpNom=?, EmpSue=? WHERE EmpCod=?;"
                    );

                    statement.setString(1, empNom);
                    statement.setDouble(2, empSue);
                    statement.setInt(3, empViaCod);
                    statement.setInt(4, viaFacCod);
                    statement.setInt(5, facUbiCod);
                    statement.setInt(6, ubiAstCod);
                    statement.setInt(7, viaNavCod);
                    statement.setInt(8, empCod);

                    int count = statement.executeUpdate();

                    res.setStatus(200);
                    res.addHeader("Content-Type", "application/json");
                    res.addHeader("Access-Control-Allow-Origin", "*");
                    res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                    res.addHeader("Access-Control-Allow-Headers", "Content-Type");
                    res.getWriter().print("{\"count\":" + count + "}");

                } else if (operacion.equals("Inactivar") || operacion.equals("Reactivar")) {

                    int empCod = Integer.parseInt(req.getParameter("EmpCod"));

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE rzc_empleo SET EmpEstReg=? WHERE EmpCod=?;"
                    );
                    statement.setString(1, operacion.equals("inactivar")? "I": "A");
                    statement.setInt(2, empCod);

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

            int empCod = Integer.parseInt(req.getParameter("EmpCod"));

            PreparedStatement statement = conn.prepareStatement(
                "UPDATE rzc_empleo SET EmpEstReg='*' WHERE EmpCod=?;"
            );

            statement.setInt(1, empCod);

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
