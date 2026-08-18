package com.byron.salud;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class EvaluadorSaludGUI extends JFrame {
    private final JTextField edad = new JTextField();
    private final JTextField peso = new JTextField();
    private final JTextField estatura = new JTextField();
    private final JTextField sistolica = new JTextField();
    private final JTextField diastolica = new JTextField();
    private final JTextField frecuencia = new JTextField();
    private final JTextField temperatura = new JTextField();
    private final JTextArea resultado = new JTextArea();

    public EvaluadorSaludGUI() {
        setTitle("Mi Estado de Salud");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);
        construirInterfaz();
    }

    private void construirInterfaz() {
        JPanel principal = new JPanel(new BorderLayout(20, 20));
        principal.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        principal.setBackground(new Color(245, 248, 252));

        JLabel titulo = new JLabel("Evaluador de Estado de Salud", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(new Color(30, 70, 100));
        principal.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(7, 2, 12, 12));
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        agregarCampo(formulario, "Edad (años):", edad);
        agregarCampo(formulario, "Peso (kg):", peso);
        agregarCampo(formulario, "Estatura (m):", estatura);
        agregarCampo(formulario, "Presión sistólica:", sistolica);
        agregarCampo(formulario, "Presión diastólica:", diastolica);
        agregarCampo(formulario, "Frecuencia cardíaca:", frecuencia);
        agregarCampo(formulario, "Temperatura (°C):", temperatura);

        JPanel centro = new JPanel(new BorderLayout(15, 15));
        centro.setOpaque(false);
        centro.add(formulario, BorderLayout.NORTH);

        resultado.setEditable(false);
        resultado.setFont(new Font("SansSerif", Font.PLAIN, 15));
        resultado.setLineWrap(true);
        resultado.setWrapStyleWord(true);
        resultado.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        resultado.setText("Ingrese sus datos y presione 'Evaluar salud'.");
        centro.add(new JScrollPane(resultado), BorderLayout.CENTER);
        principal.add(centro, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        botones.setOpaque(false);
        JButton evaluar = new JButton("Evaluar salud");
        JButton borrar = new JButton("Borrar");
        evaluar.setFont(new Font("SansSerif", Font.BOLD, 15));
        borrar.setFont(new Font("SansSerif", Font.BOLD, 15));
        evaluar.addActionListener(e -> evaluarSalud());
        borrar.addActionListener(e -> borrar());
        botones.add(evaluar);
        botones.add(borrar);
        principal.add(botones, BorderLayout.SOUTH);

        setContentPane(principal);
    }

    private void agregarCampo(JPanel panel, String texto, JTextField campo) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(etiqueta);
        panel.add(campo);
    }

    private void evaluarSalud() {
        try {
            double p = Double.parseDouble(peso.getText());
            double h = Double.parseDouble(estatura.getText());
            double sis = Double.parseDouble(sistolica.getText());
            double dia = Double.parseDouble(diastolica.getText());
            double fc = Double.parseDouble(frecuencia.getText());
            double temp = Double.parseDouble(temperatura.getText());
            int ed = Integer.parseInt(edad.getText());

            if (p <= 0 || h <= 0 || ed <= 0 || sis <= 0 || dia <= 0 || fc <= 0 || temp <= 0) {
                throw new NumberFormatException();
            }

            double imc = p / (h * h);
            DecimalFormat df = new DecimalFormat("0.00");
            StringBuilder r = new StringBuilder();
            r.append("RESULTADO DE LA EVALUACIÓN\n\n");
            r.append("IMC: ").append(df.format(imc)).append("\n");
            r.append("Clasificación del IMC: ").append(clasificarIMC(imc)).append("\n");
            r.append("Presión: ").append(df.format(sis)).append(" / ").append(df.format(dia)).append(" mmHg\n");
            r.append("Frecuencia cardíaca: ").append(df.format(fc)).append(" lpm\n");
            r.append("Temperatura: ").append(df.format(temp)).append(" °C\n\n");

            boolean alerta = sis >= 180 || dia >= 120 || temp >= 40 || temp < 35 || fc >= 130 || fc < 45;
            if (alerta) {
                r.append("⚠ ATENCIÓN: algunos valores pueden requerir valoración médica.\n");
            } else {
                r.append("✓ No se detectan valores extremos según los criterios básicos del programa.\n");
            }
            r.append("\nEste resultado es orientativo y NO constituye un diagnóstico médico. Si presenta síntomas importantes o se siente mal, consulte a un profesional de salud.");
            resultado.setText(r.toString());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese todos los datos correctamente.", "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String clasificarIMC(double imc) {
        if (imc < 18.5) return "Bajo peso";
        if (imc < 25) return "Rango habitual";
        if (imc < 30) return "Sobrepeso";
        return "Obesidad";
    }

    private void borrar() {
        edad.setText(""); peso.setText(""); estatura.setText("");
        sistolica.setText(""); diastolica.setText(""); frecuencia.setText(""); temperatura.setText("");
        resultado.setText("Ingrese sus datos y presione 'Evaluar salud'.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EvaluadorSaludGUI().setVisible(true));
    }
}
