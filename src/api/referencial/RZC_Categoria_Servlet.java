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

public final class RZC_Categoria_Servlet extends EmisaServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {

        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                imprimirErrorConexion(res);
                return;
            }

            ResultSet rs = conn.prepareStatement("SELECT * FROM rzc_categoria;").executeQuery();

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            boolean esPrimer = true;
            while (rs.next()) {
                sb.append(esPrimer? "{": ",{")
                    .append("\"CatCod\":" + rs.getInt("CatCod") + ",")
                    .append("\"CatNom\":\"" + rs.getString("CatNom") + "\",")
                    .append("\"CatSuel\":" + rs.getDouble("CatSuel") + ",")
                    .append("\"CatEstReg\":\"" + rs.getString("CatEstReg") + "\"")
                    .append("}");
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

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                imprimirErrorConexion(res);
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
    public void doPut(HttpServletRequest req, HttpServletResponse res) {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                imprimirErrorConexion(res);
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

                    imprimirEnJson(res, "{\"count\":" + count + "}");

                } else if (operacion.equals("Inactivar") || operacion.equals("Reactivar")) {
                    doInactivarReactivar(req, res, "rzc_categoria", "CatEstReg", "CatCod", operacion);
                } else {
                    imprimir400(res);
                }
            } catch (NullPointerException e) {
                imprimir400(res);
            }

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
    public void doDelete(HttpServletRequest req, HttpServletResponse res) {
        doDeleteG(req, res, "r7c_categoria", "CatEstReg", "CatCod");
    }

}
