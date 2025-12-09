/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import cadhr.CADHR;
import cadhr.ExcepcionHR;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojoshr.*;

/**
 *
 * @author DAM209
 */
public class Prueba {

    public static void main(String[] args) {
        CADHR cad = new CADHR();

//        int registrosAfectados = 0;
//        try {
//            Department dep = new Department();
//            Location loc = new Location();
//            Employee man = new Employee();
//            
//            man.setEmployeeId(100);
//            loc.setLocationId(2);
//            
//            dep.setDepartmentName(null);
//            dep.setLocation(loc);
//            dep.setManager(man);
//            
//            registrosAfectados = cad.modificarDepartment(20, dep);
//            System.out.println("Registros eliminados: " + registrosAfectados);
//        } catch (ExcepcionHR ex) {
//            System.out.println(ex);
//        }

        try {
            ArrayList lista = cad.leerLocations();
            System.out.println(lista);
        } catch (ExcepcionHR e) {
            System.out.println(e);
        }

    }
}
