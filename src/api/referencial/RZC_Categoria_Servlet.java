package api.referencial;

import api.EmisaServlet;
import com.mysql.jdbc.Driver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;

public final class RZC_Categoria_Servlet extends EmisaServlet {

    static final String nombreTabla = "r7c_categoria";
    static HashMap<String, String> campos = new HashMap<>();
    static final String campoId = "CatCod";
    static final String campoEstReg = "CatEstReg";
    static {
        campos.put("CatNom", "string");
        campos.put("CatSuel", "decimal");
    }

    public RZC_Categoria_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
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
                    doInactivarReactivar(req, res, operacion);
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

}
