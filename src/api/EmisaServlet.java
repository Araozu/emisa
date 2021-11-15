package api;

import com.mysql.jdbc.Driver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class EmisaServlet extends HttpServlet {

    protected static String url = "jdbc:mysql://localhost:3306/";
    protected static String dbName = "emisa";
    protected static String userName = "admin";
    protected static String password = "password";
    protected static String timezoneFix = "?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
        "&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8";

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

    private void checkId(HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (req.getParameter("operacion").equals("checkId")) {
            int id = Integer.parseInt(req.getParameter("id"));
            PreparedStatement ps = conn.prepareStatement(
                "SELECT " + campoId + " FROM " + nombreTabla + " WHERE " + campoId + "=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            boolean esPrimer = true;
            while (rs.next()) {
                sb.append(esPrimer ? "{" : ",{");
                sb.append("\"" + campoId + "\":" + rs.getInt(campoId));
                sb.append("}");
                esPrimer = false;
            }
            sb.append("]");

            imprimirEnJson(res, sb.toString());
        } else {
            throw new Exception("Error en doGet. Operacion invalida");
        }
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

            if (req.getParameter("operacion") != null) {
                checkId(req, res);
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
            String vCampoEstReg = req.getParameter(campoEstReg);

            StringBuilder sql = new StringBuilder(
                "INSERT INTO " + nombreTabla + "(" + campoId + ", " + campoEstReg);
            for (InfoCampo i : campos) {
                String nombre = i.nombre;
                sql.append(", ").append(nombre);
            }
            sql.append(") VALUES (?, ?");
            for (int i = 0; i < campos.size(); i++) {
                sql.append(", ?");
            }
            sql.append(");");

            PreparedStatement statement = conn.prepareStatement(sql.toString());

            statement.setInt(1, vCampoId);
            statement.setString(2, vCampoEstReg);

            int pos = 3;
            getPos(req, pos, statement);

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

                int vCampoId = Integer.parseInt(req.getParameter(campoId));
                String operacion = req.getParameter("operacion");

                if (operacion.equals("Modificar")) {

                    StringBuilder sql = new StringBuilder(
                        "UPDATE " + nombreTabla + " SET");
                    int pos = 0;
                    int max = campos.size();
                    for (InfoCampo i : campos) {
                        String nombre = i.nombre;
                        sql.append(" " + nombre + "=?");
                        if (pos < max) sql.append(",");
                        pos++;
                    }
                    sql.append(" WHERE " + campoId + "=?;");

                    PreparedStatement statement = conn.prepareStatement(sql.toString());

                    pos = 1;
                    pos = getPos(req, pos, statement);
                    statement.setInt(pos, vCampoId);

                    int count = statement.executeUpdate();

                    imprimirEnJson(res, "{\"count\":" + count + "}");

                } else if (operacion.equals("Inactivar") || operacion.equals("Reactivar")) {

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE " + nombreTabla + " SET " + campoEstReg + "=? WHERE " + campoId + "=?;"
                    );
                    statement.setString(1, operacion.equals("Inactivar") ? "I" : "A");
                    statement.setInt(2, vCampoId);

                    int count = statement.executeUpdate();

                    imprimirEnJson(res, "{\"count\":" + count + "}");
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

    private int getPos(HttpServletRequest req, int pos, PreparedStatement statement) throws SQLException {
        for (InfoCampo infoCampo : campos) {
            String valorCampo = req.getParameter(infoCampo.nombre);
            switch (infoCampo.tipo) {
                case "int": {
                    statement.setInt(pos, Integer.parseInt(valorCampo));
                    break;
                }
                case "decimal": {
                    statement.setDouble(pos, Double.parseDouble(valorCampo));
                    break;
                }
                case "string": {
                    statement.setString(pos, valorCampo);
                    break;
                }
            }
            pos++;
        }
        return pos;
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
