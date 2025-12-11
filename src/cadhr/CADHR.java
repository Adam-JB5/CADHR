/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadhr;

import pojoshr.ExcepcionHR;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pojoshr.*;

/**
 *
 * @author DAM209
 */
public class CADHR {
    
    private Connection conexion;
    
    public CADHR() throws ExcepcionHR{
        try {

            System.out.println("Conexion");
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException ex) {
            ExcepcionHR e = new ExcepcionHR();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
    }
    
    private void conectarBD() throws ExcepcionHR {
        try {
            
        conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.209.1:1521:test", "HR", "kk");
        
        }  catch (SQLException ex) {
            ExcepcionHR e = new ExcepcionHR();
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            throw e;
        }
    }

    public Integer insertarRegion(Region region) {
        return null;
    }

    /**
     * Elimina un único registro de la tabla REGIONS
     * @param regionId Identificador de región del registro que se desea eliminar
     * @return Cantidad de registros eliminados
     * @throws pojoshr.ExcepcionHR Se lanzará cuando se produzca un error de base de datos
     * @author Adam Janah Benyoussef
     * @version 1.0
     * @since 11/12/2025 DD/MM/YYYY
     */
    public Integer eliminarRegion(Integer regionId) throws ExcepcionHR {
        int registrosAfectados = 0;
        String dml = "";
        try {
            conectarBD();
            Statement sentencia = conexion.createStatement();
            dml = "DELETE REGIONS WHERE region_id = " + regionId;
            registrosAfectados = sentencia.executeUpdate(dml);
            sentencia.close();
            conexion.close();
        } catch (SQLException ex) {

            ExcepcionHR e = new ExcepcionHR();

            switch (ex.getErrorCode()) {
                case 2292:
                    e.setMensajeErrorUsuario("No se puede eliminar la región porque tiene países asociados");
                    break;
                default:
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                    break;
            }

            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(dml);

            throw e;
        }

        return registrosAfectados;
    }

    public Integer modificarRegion(Integer regionId, Region region) {
        return null;
    }

    public Region leerRegion(Integer regionId) {
        return null;
    }

    public ArrayList<Region> leerRegions() {
        return null;
    }

    public Integer modificarDepartment(Integer departmentId, Department department) throws ExcepcionHR {
        int registrosAfectados = 0;
        String dml = "UPDATE departments SET department_name=?, manager_id=?, location_id=? WHERE department_id=?";
        try {

            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);

            sentenciaPreparada.setString(1, department.getDepartmentName());
            sentenciaPreparada.setInt(2, department.getManager().getEmployeeId());
            sentenciaPreparada.setInt(3, department.getLocation().getLocationId());
            sentenciaPreparada.setInt(4, departmentId);
            registrosAfectados = sentenciaPreparada.executeUpdate();

            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex) {

            ExcepcionHR e = new ExcepcionHR();

            switch (ex.getErrorCode()) {
                case 1407:
                    e.setMensajeErrorUsuario("El nombre de departamento es obligatorio");
                    break;
                case 2291:
                    e.setMensajeErrorUsuario("No se ha podido modificar debido a que el empleado o localización no existen");
                    break;
                default:
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                    break;
            }

            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(dml);

            throw e;
        }

        return registrosAfectados;
    }


    
    /**
     * 
     * @return Un ArrayList de tipo Location
     * @throws ExcepcionHR
     * @author Adam Janah Benyoussef
     * @version 1.0
     * @since 11/12/2025
     */
    public ArrayList<Location> leerLocations() throws ExcepcionHR {
        ArrayList listaLocations = new ArrayList();
        Location l;
        Country c;
        Region r;
        String dql = "SELECT * FROM regions r, countries c, locations l WHERE r.region_id = c.region_id and c.country_id = l.country_id";
        try {
            conectarBD();
            Statement sentencia = conexion.createStatement();

            ResultSet resultado = sentencia.executeQuery(dql);
            while (resultado.next()) {
                l = new Location();
                l.setLocationId(resultado.getInt("location_id"));
                l.setStreetAdress(resultado.getString("street_address"));
                l.setPostalCode(resultado.getString("postal_code"));
                l.setCity(resultado.getString("city"));
                l.setStateProvince(resultado.getString("state_province"));

                c = new Country();
                c.setCountryId(resultado.getString("country_id"));
                c.setCountryName(resultado.getString("country_name"));

                l.setCountry(c);

                listaLocations.add(l);
            }
            resultado.close();

            sentencia.close();
            conexion.close();

        } catch (SQLException ex) {

            ExcepcionHR e = new ExcepcionHR();

            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(dql);

            throw e;
        }
        return listaLocations;
    }

}
