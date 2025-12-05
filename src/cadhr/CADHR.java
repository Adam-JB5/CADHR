/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadhr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pojoshr.*;

/**
 *
 * @author DAM209
 */
public class CADHR {

    
    public Integer insertarRegion(Region region) {
        return null;
    }
    
    public Integer eliminarRegion(Integer regionId) throws ExcepcionHR {
        int registrosAfectados = 0;
        String dml = "";
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.209:1521:test", "HR", "kk");
            Statement sentencia = conexion.createStatement();
            dml = "DELETE REGIONS WHERE region_id = " + regionId;
            registrosAfectados = sentencia.executeUpdate(dml);
            sentencia.close();
            conexion.close();
            
        } catch (ClassNotFoundException ex) {
            ExcepcionHR e = new ExcepcionHR();
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(dml);
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");           
            throw e;
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
    
    public Integer eliminarCountry(String countryId) {
        int registrosAfectados = 0;
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.1.209:1521:test", "HR", "kk");
            Statement sentencia = conexion.createStatement();
            String dml = "DELETE FROM countries WHERE country_id = " + countryId;
            registrosAfectados = sentencia.executeUpdate(dml);
            sentencia.close();
            conexion.close();
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Error - Clase no Encontrada: " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getErrorCode() + " - " + ex.getMessage());
        }
        
        return registrosAfectados;
    }
    
    public Integer modificarDepartment(Integer departmentId, Department department) throws ExcepcionHR{
        int registrosAfectados = 0;
        String dml = "UPDATE departments SET department_name=?, manager_id=?, location_id=? WHERE department_id=?";
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.209:1521:test", "HR", "kk");
           
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);

            
            sentenciaPreparada.setString(1, department.getDepartmentName());
            sentenciaPreparada.setInt(2, department.getManager().getEmployeeId());
            sentenciaPreparada.setInt(3, department.getLocation().getLocationId());
            sentenciaPreparada.setInt(4, departmentId);
            registrosAfectados = sentenciaPreparada.executeUpdate();
            
            sentenciaPreparada.close();
            conexion.close();
            
        } catch (ClassNotFoundException ex) {
            ExcepcionHR e = new ExcepcionHR();
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(dml);
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");           
            throw e;
        } catch (SQLException ex) {
            
            ExcepcionHR e = new ExcepcionHR();
            
            switch (ex.getErrorCode()) {
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
    
}
