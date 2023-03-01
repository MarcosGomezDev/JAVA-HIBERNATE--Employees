/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad_t4_hibernate;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author FOC
 */
public class EmpleadosHIBERNATE {
    
    public static void Insert() {
        
        SessionFactory sesionPrincipal = SessionFactorySingleton.getSessionFactory();        
        Session sesionActual = sesionPrincipal.openSession();        
        Transaction tx = sesionActual.beginTransaction();
        Employees emp = null;
        
        try {            
            System.out.println("Insercion de un empleado.");  
            
            emp = new Employees(208, null, "Marcos", "Gomez", 
                    "marcosgomez@foc.es", "666999333", new Date(21,02,21), 
                    "IT_PROG", BigDecimal.valueOf(15000), null, null, null);
            
            sesionActual.save(emp);
            tx.commit();
            System.out.println("Inserción completada.");
        }catch (Exception ex){
            System.out.println("Error " + ex);
            tx.rollback();
        }finally {
            sesionActual.close();
            sesionPrincipal.close();
        }   
    }
    
    public static void Consulta () {
        SessionFactory sesionPrincipal = SessionFactorySingleton.getSessionFactory();        
        Session sesionActual = sesionPrincipal.openSession();
        Employees emp = null;
        
        System.out.println("Datos en la tabla Employees.");
        Query q = sesionActual.createQuery("FROM Employees WHERE employee_id = 208");

        List <Employees> lista = q.list();
        Iterator it = lista.iterator();
        while(it.hasNext()) {
            emp = (Employees) it.next();
            System.out.println("Nombre: " + emp.getFirstName() + "\n"
                             + "Apellido: " + emp.getLastName() + "\n"
                             + "Salario: " + emp.getSalary() + "\n");
        } 
        sesionActual.close();
        sesionPrincipal.close();
    }
    
    public static String Consulta2 () {
        SessionFactory sesionPrincipal = SessionFactorySingleton.getSessionFactory();        
        Session sesionActual = sesionPrincipal.openSession();
        String cadena = "";
        
        Query q = sesionActual.createQuery(
                  "SELECT firstName, lastName, salary "
                + "FROM Employees "
                + "WHERE salary > 20000 ORDER BY salary");

        List <Employees> lista = q.list();
        Iterator it = lista.iterator();
        while(it.hasNext()) {
            Object[] empleado_actual = (Object[]) it.next();
            cadena += "---------------------------------\n"
                    + "Nombre: " + empleado_actual[0] + "\n"
                    + "Apellido: " + empleado_actual[1] + "\n"
                    + "Salario: " + empleado_actual[2] + "\n"
                    + "---------------------------------\n";
        } 
        sesionActual.close();
        sesionPrincipal.close();
        return cadena;
    }
    
    public static void Update() {
        System.out.println("Modificando empleado.");
        SessionFactory sesionPrincipal = SessionFactorySingleton.getSessionFactory();        
        Session sesionActual = sesionPrincipal.openSession();
        Transaction tx = sesionActual.beginTransaction();
        Employees emp = null;
        
        System.out.println("Datos en la tabla Employees.");
        Query q = sesionActual.createQuery("FROM Employees WHERE employee_id = 208");
        try {
            List <Employees> lista = q.list();
            Iterator it = lista.iterator();
            while(it.hasNext()) {
                emp = (Employees) it.next();
                if(emp.getEmployeeId() == 208) {
                    emp.setSalary(BigDecimal.valueOf(30000));
                }
            }
            tx.commit();
            System.out.println("Empleado modificado");
        }catch(Exception ex) {
            System.out.println("Error --> " + ex);
            tx.rollback();
            System.out.println("No se ha podido modificar el empleado.");
        }finally {
            sesionActual.close();
            sesionPrincipal.close();
        }
    }
}
 