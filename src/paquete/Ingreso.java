package paquete;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class Ingreso extends JFrame {

    private JTextField direccion;
    private JTextField puerto;
    private JTextField usuario;
    private JTextField clave;
    private DefaultComboBoxModel<String> modeloComboBase;
    private JComboBox<String> comboBoxBase;
    private JPanel panel;

    public Ingreso() {
        super();
        panel=new JPanel(); 
        panel.setLayout(null);
        setContentPane(panel);
        JLabel lblDireccion = new JLabel("Direccion");
        lblDireccion.setHorizontalAlignment(SwingConstants.CENTER);
        lblDireccion.setBounds(97, 104, 114, 14);
        panel.add(lblDireccion);

        direccion = new JTextField();
        direccion.setBounds(83, 121, 143, 20);
        panel.add(direccion);
        direccion.setColumns(10);

        JLabel lblPuerto = new JLabel("Puerto");
        lblPuerto.setBounds(318, 104, 46, 14);
        getContentPane().add(lblPuerto);

        puerto = new JTextField();
        puerto.setBounds(292, 121, 86, 20);
        panel.add(puerto);
        puerto.setColumns(10);

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setBounds(131, 162, 46, 14);
        panel.add(lblUsuario);

        usuario = new JTextField();
        usuario.setBounds(97, 183, 114, 20);
        panel.add(usuario);
        usuario.setColumns(10);

        JLabel lblClave = new JLabel("Clave");
        lblClave.setBounds(318, 162, 46, 14);
        panel.add(lblClave);

        clave = new JTextField();
        clave.setColumns(10);
        clave.setBounds(292, 183, 86, 20);
        panel.add(clave);

        modeloComboBase = new DefaultComboBoxModel<String>();
        comboBoxBase = new JComboBox();
        comboBoxBase.setModel(modeloComboBase);
        comboBoxBase.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                if (Ingreso.this.modeloComboBase.getSize() <= 0) {

                    if (direccion.getText().isEmpty() && puerto.getText().isEmpty() && usuario.getText().isEmpty() && clave.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "La Configuracion Ingresada es Incorrecta");
                    } else {
                        try {
                            Connection con = DriverManager.getConnection("jdbc:mysql://" + direccion.getText() + ":" + puerto.getText(), usuario.getText(), clave.getText());
                            ResultSet rs = con.getMetaData().getCatalogs();
                            while (rs.next()) {
                                modeloComboBase.addElement(rs.getString("TABLE_CAT"));
                            }

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "La Configuracion Ingresada es Incorrecta");

                        }
                    }
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });

        comboBoxBase.setBounds(97, 237, 114, 20);
        panel.add(comboBoxBase);

        JButton conti = new JButton("Continuar");
        conti.setEnabled(false);
        conti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                JOptionPane.showMessageDialog(null, "Ha sido Configurado Correctamente");

                new Ingresar();

                dispose();

            }
        });
        conti.setBounds(195, 342, 104, 50);
        panel.add(conti);

        JButton veri = new JButton("Verificar");
        veri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Archivo archivo = new Archivo("archivos/conexion.dat");
                archivo.limpiarArchivo();
                System.out.println(comboBoxBase.getSelectedItem());
                archivo.guardar("jdbc:mysql://" + direccion.getText() + ":" + puerto.getText() + "," + comboBoxBase.getSelectedItem() + "," + usuario.getText() + "," + clave.getText());

                Conexion con = new Conexion();

                if (con.valorDev("select 1 from datos") != -1) {
                    conti.setEnabled(true);

                } else {
                    archivo.limpiarArchivo();
                }
            }
        });
        veri.setBounds(274, 245, 104, 50);
        panel.add(veri);

        JLabel lblIngreso = new JLabel("Ingreso");
        lblIngreso.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblIngreso.setHorizontalAlignment(SwingConstants.CENTER);
        lblIngreso.setBounds(148, 40, 179, 32);
        panel.add(lblIngreso);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                 Archivo archivo = new Archivo("archivos/conexion.dat");
        if (archivo.traeArchivo().isEmpty()) {
            new Ingreso();
        } else {
            Conexion con = new Conexion();
            if (con.valorDev("select 1 from datos") == -1) {
                archivo.limpiarArchivo();
                new Ingreso();

            } else {
                new Ingresar();
            }

        }
            }
        });
    }

}
