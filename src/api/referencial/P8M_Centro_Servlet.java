package api.referencial;

import api.EmisaServlet;
import com.mysql.jdbc.Driver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class P8M_Centro_Servlet extends EmisaServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {

        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                imprimirErrorConexion(res);
                return;
            }

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM p8m_centro;");

            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            boolean esPrimer = true;
            while (rs.next()) {
                sb.append(esPrimer? "{": ",{")
                    .append("\"CenCod\":" + rs.getInt("CenCod") + ",")
                    .append("\"CenNom\":\"" + rs.getString("CenNom") + "\",")
                    .append("\"CenMinCod\":" + rs.getDouble("CenMinCod") + ",")
                    .append("\"CenEstReg\":\"" + rs.getString("CenEstReg") + "\"")
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

                    imprimirEnJson(res, "{\"count\":" + count + "}");

                } else if (operacion.equals("Inactivar") || operacion.equals("Reactivar")) {

                    int cenCod = Integer.parseInt(req.getParameter("CenCod"));

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE p8m_centro SET CenEstReg=? WHERE CenCod=?;"
                    );
                    statement.setString(1, operacion.equals("Inactivar")? "I": "A");
                    statement.setInt(2, cenCod);

                    int count = statement.executeUpdate();

                    imprimirEnJson(res, "{\"count\":" + count + "}");

                } else {
                    res.addHeader("Access-Control-Allow-Origin", "*");
                    res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                    res.addHeader("Access-Control-Allow-Headers", "Content-Type");
                    res.setStatus(400);
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
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                imprimirErrorConexion(res);
                return;
            }

            int cenCod = Integer.parseInt(req.getParameter("CenCod"));

            PreparedStatement statement = conn.prepareStatement(
                "UPDATE p8m_centro SET CenEstReg='*' WHERE CenCod=?;"
            );

            statement.setInt(1, cenCod);

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

}