package api;

import com.mysql.jdbc.Driver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmisaServlet extends HttpServlet {

    protected static String url = "jdbc:mysql://localhost:3306/";
    protected static String dbName = "emisa";
    protected static String userName = "root";
    protected static String password = "";
    protected static String timezoneFix = "?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
        "&useLegacyDatetimeCode=false&serverTimezone=UTC";

    protected static Connection conn = null;

    private final String nombreTabla;
    private final List<InfoCampo> campos;
    private final String campoId;
    private final String campoEstReg;

    public EmisaServlet(String nombreTabla, List<InfoCampo> campos, String campoId, String campoEstReg) {
        this.nombreTabla = nombreTabla;
        this.campos = campos;
        this.campoId = campoId;
        this.campoEstReg = campoEstReg;
    }

    protected void imprimirEnJson(HttpServletResponse res, String json) throws IOException {
        res.setStatus(200);
        res.addHeader("Content-Type", "application/json");
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");

        res.getWriter().print(json);
    }

    protected void imprimirError(HttpServletResponse res, Exception e, String razon) {
        System.err.println(razon);
        if (e != null) {
            e.printStackTrace();
        }
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setStatus(500);
    }

    protected void imprimir400(HttpServletResponse res) {
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setStatus(400);
    }

    protected void imprimirErrorConexion(HttpServletResponse res) {
        imprimirError(res, null, "Error al iniciar conexion a la base de datos.");
    }

    protected void imprimirErrorEnvio(HttpServletResponse res, Exception e) {
        imprimirError(res, e, "Error al enviar los datos al cliente.");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                imprimirErrorConexion(res);
                return;
            }

            ResultSet rs = conn.prepareStatement("SELECT * FROM " + nombreTabla + ";").executeQuery();

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            boolean esPrimer = true;
            while (rs.next()) {
                sb.append(esPrimer ? "{" : ",{");
                sb.append("\"" + campoId + "\":" + rs.getInt(campoId) + ",")
                    .append("\"" + campoEstReg + "\":\"" + rs.getString(campoEstReg) + "\"");
                for (InfoCampo i : campos) {
                    String nombre = i.nombre;
                    String tipo = i.tipo;

                    sb.append(",\"" + nombre + "\":");
                    switch (tipo) {
                        case "int": {
                            sb.append(rs.getInt(nombre));
                            break;
                        }
                        case "decimal": {
                            sb.append(rs.getDouble(nombre));
                            break;
                        }
                        case "string": {
                            sb.append("\"" + rs.getString(nombre) + "\"");
                            break;
                        }
                    }
                }
                sb.append("}");
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

            int vCampoId = Integer.parseInt(req.getParameter(campoId));
            String catNom = req.getParameter("CatNom");
            double catSuel = Double.parseDouble(req.getParameter("CatSuel"));
            String catEstReg = req.getParameter("CatEstReg");

            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO rzc_categoria (CatCod, CatNom, CatSuel, CatEstReg) VALUES (?, ?, ?, ?)"
            );

            statement.setInt(1, vCampoId);
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

    protected void doInactivarReactivar(HttpServletRequest req, HttpServletResponse res, String operacion) throws SQLException, IOException {
        int catCod = Integer.parseInt(req.getParameter(campoId));

        PreparedStatement statement = conn.prepareStatement(
            "UPDATE " + nombreTabla + " SET " + campoEstReg + "=? WHERE " + campoId + "=?;"
        );
        statement.setString(1, operacion.equals("Inactivar") ? "I" : "A");
        statement.setInt(2, catCod);

        int count = statement.executeUpdate();

        imprimirEnJson(res, "{\"count\":" + count + "}");
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

            int alimCod = Integer.parseInt(req.getParameter(campoId));

            PreparedStatement statement = conn.prepareStatement(
                "UPDATE " + nombreTabla + " SET " + campoEstReg + "='*' WHERE " + campoId + "=?;"
            );

            statement.setInt(1, alimCod);

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
    public void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        super.doOptions(req, res);
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
    }

}
