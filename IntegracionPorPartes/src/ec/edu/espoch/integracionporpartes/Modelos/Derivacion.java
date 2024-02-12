/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espoch.integracionporpartes.Modelos;
import org.lsmp.djep.djep.DJep;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

/**
 *
 * @author ElMajao
 */
public class Derivacion {
    private String funcion;
    private String funcionDerivada; 
    private DJep djep;
    private Node nodoFuncion;
    private Node nodoDerivada;
    
    public String getFuncionDerivada(){
        return funcionDerivada;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }
    
    public String derivar(){
        try{
            this.djep = new DJep();
            this.djep.addStandardFunctions();
            this.djep.addStandardConstants();
            this.djep.addComplex();
            this.djep.setAllowUndeclared(true);
            this.djep.setAllowAssignment(true);
            this.djep.setImplicitMul(true);
            this.djep.addStandardDiffRules();
            this.nodoFuncion = this.djep.parse(this.funcion);
            Node diff = this.djep.differentiate(nodoFuncion, "x");
            this.nodoDerivada = this.djep.simplify(diff);
            return this.funcionDerivada = this.djep.toString(this.nodoDerivada);
        } catch (ParseException e) {
            this.funcionDerivada = "NaN";
            return "Error "+e.getErrorInfo();
        }
    }
}
