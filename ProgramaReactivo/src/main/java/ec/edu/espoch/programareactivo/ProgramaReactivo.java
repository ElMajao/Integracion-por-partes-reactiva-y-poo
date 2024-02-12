/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ec.edu.espoch.programareactivo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.lsmp.djep.djep.DJep;

import org.nfunk.jep.*;

import reactor.core.publisher.Mono;

public class ProgramaReactivo extends JFrame {
    private JTextField textField1;
    private JTextField base;
    private JTextField textField3;
    private JTextField resultadoField;
    private JButton botonCalcular;

    private String funcion1;
    private String funcion2;
    private String funcionDerivada1;
    private String funcionDerivada2;

    public ProgramaReactivo() {
        setTitle("Programa Reactivo");
        setSize(500, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Función Algebraica:"));
        add(textField1);
        add(new JLabel("Función Base:"));
        add(base);
        add(new JLabel("Función Exponencial:"));
        add(textField3);
        add(new JLabel("Resultado:"));
        add(resultadoField);
        add(new JLabel());
        add(botonCalcular);

        botonCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activarProceso();
            }
        });
    }

    private void initComponents() {
        textField1 = new JTextField();
        base = new JTextField();
        textField3 = new JTextField();
        resultadoField = new JTextField();
        botonCalcular = new JButton("Integrar");
        resultadoField.setEditable(false);
    }

    private void activarProceso() {
        funcion1 = textField1.getText();
        funcion2 = textField3.getText();

        // Crear Mono para cada método y encadenar las operaciones
        Mono<String> proceso1 = Mono.fromCallable(() -> derivar(funcion1));
        Mono<String> proceso2 = Mono.fromCallable(() -> derivar(funcion2));

        // Suscribirse para obtener los resultados
        proceso1.subscribe(
                resultado -> funcionDerivada1 = resultado,
                error -> JOptionPane.showMessageDialog(this, "Ocurrió un error en el proceso 1: " + error)
        );

        proceso2.subscribe(
                resultado -> funcionDerivada2 = resultado,
                error -> JOptionPane.showMessageDialog(this, "Ocurrió un error en el proceso 2: " + error),
                () -> mostrarResultados()
        );
    }

    private String derivar(String funcion) {
        DJep djep = new DJep();
        try {
            // Configuración de DJep
            djep.addStandardFunctions();
            djep.addStandardConstants();
            djep.addComplex();
            djep.setAllowUndeclared(true);
            djep.setAllowAssignment(true);
            djep.setImplicitMul(true);
            djep.addStandardDiffRules();

            // Parsear la función
            Node nodoFuncion = djep.parse(funcion);
            // Derivar la función con respecto a x
            Node diff = djep.differentiate(nodoFuncion, "x");
            // Simplificar la función derivada
            Node nodoDerivada = djep.simplify(diff);
            // Convertir el valor simplificado en un String
            return djep.toString(nodoDerivada);
        } catch (ParseException e) {
            return "Error " + e.getErrorInfo();
        }
    }

    private void mostrarResultados() {
        if ("e".equals(base.getText())){
            resultadoField.setText(textField1.getText()+" * ("+base.getText()+"^("+textField3.getText()+")) / ("+funcionDerivada2+") - "+funcionDerivada1+" * ("+base.getText()+"^("+textField3.getText()+")) / ("+funcionDerivada2+" * "+funcionDerivada2+") + C");
        }else{
            resultadoField.setText(textField1.getText()+" * ("+base.getText()+"^("+textField3.getText()+")) / (("+funcionDerivada2+") * ln("+base.getText()+")) - "+funcionDerivada1+" * ("+base.getText()+"^("+textField3.getText()+")) / ("+funcionDerivada2+" * "+funcionDerivada2+" * ln("+base.getText()+")) + C");
        }
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ProgramaReactivo programa = new ProgramaReactivo();
                programa.setVisible(true);
            }
        });
    }
}