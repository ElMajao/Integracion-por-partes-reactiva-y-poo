/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espoch.integracionporpartes.Controladores;
import ec.edu.espoch.integracionporpartes.Vistas.Vista;
import ec.edu.espoch.integracionporpartes.Modelos.Derivacion;

/**
 *
 * @author ElMajao
 */
public class Controlador {
    private Vista vista;
    private Derivacion derivacion;
    
    public Controlador(Vista vista){
        this.vista = vista;
        this.derivacion = new Derivacion();
    }
    
    public void procesoDerivar(){
        String du;
        String v;
        String dExp;
        derivacion.setFuncion(vista.getFuncionAlg());
        derivacion.derivar();
        du=derivacion.getFuncionDerivada();
        derivacion.setFuncion(vista.getExponente());
        derivacion.derivar();
        if(vista.getBaseExp().equals("e")){
            v="("+vista.getBaseExp()+" ^ ("+vista.getExponente()+")) / ("+derivacion.getFuncionDerivada()+")";
            dExp="("+vista.getBaseExp()+" ^ ("+vista.getExponente()+")) / ("+derivacion.getFuncionDerivada()+" * "+derivacion.getFuncionDerivada()+")";
        }else{
            v="("+vista.getBaseExp()+" ^ ("+vista.getExponente()+") / ("+derivacion.getFuncionDerivada()+"* ln("+vista.getBaseExp()+"))";
            dExp="("+vista.getBaseExp()+" ^ ("+vista.getExponente()+")) / ("+derivacion.getFuncionDerivada()+"* ln("+vista.getBaseExp()+") * "+derivacion.getFuncionDerivada()+")";
        }
        vista.ingresarDatos(du, v,dExp);
    }
}
