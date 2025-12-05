/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import cadhr.CADHR;
import cadhr.ExcepcionHR;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DAM209
 */
public class Prueba {

    public static void main(String[] args) {
        CADHR cad = new CADHR();

        int registrosAfectados = 0;
        try {
            registrosAfectados = cad.eliminarRegion(2);
            System.out.println("Registros eliminados: " + registrosAfectados);
        } catch (ExcepcionHR ex) {
            System.out.println(ex);
        }

    }
}
