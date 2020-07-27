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

import api.EmisaServlet;
import com.mysql.jdbc.Driver;

public final class GZZ_Astro_Servlet extends EmisaServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {

        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                imprimirError(res, null, "Error al iniciar conexion a la base de datos.");
                return;
            }

            ResultSet rs = conn.prepareStatement("SELECT * FROM gzz_astros;").executeQuery();
            StringBuilder sb = new StringBuilder();

            sb.append("[");
            boolean esPrimer = true;
            while (rs.next()) {
                sb.append(esPrimer? "{": ",{")
                    .append("\"AstCod\":" + rs.getInt("AstCod") + ",")
                    .append("\"AstNom\":\"" + rs.getString("AstNom") + "\",")
                    .append("\"AstTip\":" + rs.getDouble("AstTip") + ",")
                    .append("\"AstEstReg\":\"" + rs.getString("AstEstReg") + "\"")
                    .append("}");
                esPrimer = false;
            }
            sb.append("]");

            imprimirEnJson(res, sb.toString());
        } catch (Exception e) {
            imprimirError(res, e, "Error al enviar los datos al cliente.");
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
                imprimirError(res, null, "Error al iniciar conexion a la base de datos.");
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

            imprimirEnJson(res, "{\"count\":" + count + "}");

        } catch (Exception e) {
            imprimirError(res, e, "Error al enviar los datos al cliente.");
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
                imprimirError(res, null, "Error al iniciar conexion a la base de datos.");
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

                    imprimirEnJson(res, "{\"count\":" + count + "}");

                } else if (operacion.equals("Inactivar") || operacion.equals("Reactivar")) {

                    int astCod = Integer.parseInt(req.getParameter("AstCod"));

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE gzz_astros SET AstEstReg=? WHERE AstCod=?;"
                    );
                    statement.setString(1, operacion.equals("inactivar")? "I": "A");
                    statement.setInt(2, astCod);

                    int count = statement.executeUpdate();

                    imprimirEnJson(res, "{\"count\":" + count + "}");

                } else {
                    imprimir400(res);
                }
            } catch (NullPointerException e) {
                imprimir400(res);
            }

        } catch (Exception e) {
            imprimirError(res, e, "Error al enviar los datos al cliente.");
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
                imprimirError(res, null, "Error al iniciar conexion a la base de datos.");
                return;
            }

            int astCod = Integer.parseInt(req.getParameter("AstCod"));

            PreparedStatement statement = conn.prepareStatement(
                "UPDATE gzz_astros SET AstEstReg='*' WHERE AstCod=?;"
            );

            statement.setInt(1, astCod);

            int count = statement.executeUpdate();

            imprimirEnJson(res, "{\"count\":" + count + "}");

        } catch (Exception e) {
            imprimirError(res, e, "Error al enviar los datos al cliente.");
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
