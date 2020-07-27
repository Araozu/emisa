package api.referencial;

import api.EmisaServlet;
import com.mysql.jdbc.Driver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class R7C_Alimento_Servlet extends EmisaServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {

        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                imprimirErrorConexion(res);
                return;
            }

            ResultSet rs = conn.prepareStatement("SELECT * FROM r7c_alimento;").executeQuery();

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            boolean esPrimer = true;
            while (rs.next()) {
                sb.append(esPrimer? "{": ",{")
                    .append("\"AlimCod\":" + rs.getInt("AlimCod") + ",")
                    .append("\"AlimNom\":\"" + rs.getString("AlimNom") + "\",")
                    .append("\"AlimCos\":" + rs.getDouble("AlimCos") + ",")
                    .append("\"AlimCan\":" + rs.getDouble("AlimCan") + ",")
                    .append("\"AlimEstReg\":\"" + rs.getString("AlimEstReg") + "\"")
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

            int alimCod = Integer.parseInt(req.getParameter("AlimCod"));
            String alimNom = req.getParameter("AlimNom");
            double alimCos = Double.parseDouble(req.getParameter("AlimCos"));
            double alimCan = Double.parseDouble(req.getParameter("AlimCan"));
            String alimEstReg = req.getParameter("AlimEstReg");

            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO r7c_alimento (AlimCod, AlimNom, AlimCos, AlimCan, AlimEstReg) VALUES (?, ?, ?, ?, ?)"
            );

            statement.setInt(1, alimCod);
            statement.setString(2, alimNom);
            statement.setDouble(3, alimCos);
            statement.setDouble(4, alimCan);
            statement.setString(5, alimEstReg);

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
                    int alimCod = Integer.parseInt(req.getParameter("AlimCod"));
                    String alimNom = req.getParameter("AlimNom");
                    double alimCos = Double.parseDouble(req.getParameter("AlimCos"));
                    double alimCan = Double.parseDouble(req.getParameter("AlimCan"));

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE r7c_alimento SET AlimNom=?, AlimCos=?, AlimCan=? WHERE AlimCod=?;"
                    );

                    statement.setString(1, alimNom);
                    statement.setDouble(2, alimCos);
                    statement.setDouble(3, alimCan);
                    statement.setInt(4, alimCod);

                    int count = statement.executeUpdate();

                    imprimirEnJson(res, "{\"count\":" + count + "}");

                } else if (operacion.equals("Inactivar") || operacion.equals("Reactivar")) {

                    int alimCod = Integer.parseInt(req.getParameter("AlimCod"));

                    PreparedStatement statement = conn.prepareStatement(
                        "UPDATE r7c_alimento SET AlimEstReg=? WHERE AlimCod=?;"
                    );
                    statement.setString(1, operacion.equals("Inactivar")? "I": "A");
                    statement.setInt(2, alimCod);

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

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) {
        try {
            new Driver();
            conn = DriverManager.getConnection(url + dbName + timezoneFix, userName, password);

            if (conn.isClosed()) {
                imprimirErrorConexion(res);
                return;
            }

            int alimCod = Integer.parseInt(req.getParameter("AlimCod"));

            PreparedStatement statement = conn.prepareStatement(
                "UPDATE r7c_alimento SET AlimEstReg='*' WHERE AlimCod=?;"
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

}
