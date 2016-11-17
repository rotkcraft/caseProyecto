package paquete;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import java.awt.Image;
import javax.swing.JFrame;

import javax.swing.JTable;
import javax.swing.JScrollPane;


public class Ingresar extends JFrame {
	private JTextField impu;
	private JTextField descri;
	private JTextField valor;
	private JTable table;
	private Conexion conexion;
	private String id;
	private DefaultTableModel tb;
        private JPanel contenedor;

	public Ingresar() 
	{
            super();
            contenedor=new JPanel();
            setContentPane(contenedor);
		contenedor.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 138, 714, 324);
		contenedor.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Ingreso de Datos", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblIngresoDeDatos = new JLabel("Ingreso de Datos");
		lblIngresoDeDatos.setFont(new Font("Arial", Font.PLAIN, 12));
		lblIngresoDeDatos.setHorizontalAlignment(SwingConstants.CENTER);
		lblIngresoDeDatos.setBounds(104, 34, 200, 50);
		panel.add(lblIngresoDeDatos);
		
		JLabel lblidDeImpuesto = new JLabel("*Id de Impuesto");
		lblidDeImpuesto.setFont(new Font("Arial", Font.PLAIN, 12));
		lblidDeImpuesto.setHorizontalAlignment(SwingConstants.CENTER);
		lblidDeImpuesto.setBounds(25, 95, 132, 50);
		panel.add(lblidDeImpuesto);
		
		impu = new JTextField();
		impu.setBounds(146, 108, 132, 27);
		panel.add(impu);
		impu.setColumns(10);
		
		JLabel lbldescripcion = new JLabel("*Descripcion");
		lbldescripcion.setFont(new Font("Arial", Font.PLAIN, 12));
		lbldescripcion.setHorizontalAlignment(SwingConstants.CENTER);
		lbldescripcion.setBounds(35, 139, 132, 50);
		panel.add(lbldescripcion);
		
		descri = new JTextField();
		descri.setColumns(10);
		descri.setBounds(146, 152, 132, 27);
		panel.add(descri);
		
		JLabel lblvalor = new JLabel("*Valor");
		lblvalor.setHorizontalAlignment(SwingConstants.CENTER);
		lblvalor.setFont(new Font("Arial", Font.PLAIN, 12));
		lblvalor.setBounds(45, 186, 132, 50);
		panel.add(lblvalor);
		
		valor = new JTextField();
		valor.setColumns(10);
		valor.setBounds(146, 199, 132, 27);
		panel.add(valor);
		
		JButton nuevo = new JButton("Nuevo");
		sizeL(nuevo, new ImageIcon(getClass().getResource("imagenes/nuevo.png")), 40, 40);
		nuevo.setBounds(9, 39, 125, 50);
		contenedor.add(nuevo);
		
		JButton guardar = new JButton("Guardar");
		guardar.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				
				conexion.insertar("insert into datos(impuesto,descripcion,valor) values("+impu.getText().trim()+",'"+descri.getText()+"',"+valor.getText()+")");
				conexion.llenarTabla("select * from datos", tb, table);
			  impu.setText("");
			  valor.setText("");
			  descri.setText("");
			}
		});
		sizeL(guardar, new ImageIcon(getClass().getResource("imagenes/guardar.png")), 40, 40);
		guardar.setBounds(144, 39, 125, 50);
		contenedor.add(guardar);
		
		JButton modificar = new JButton("Modificar");
		sizeL(modificar, new ImageIcon(getClass().getResource("imagenes/modificar.png")), 40, 40);
		modificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(id!=null)
				{
					conexion.insertar("update datos set impuesto="+impu.getText().trim()+", descripcion='"+descri.getText()+"', valor="+valor.getText()+" where iddatos="+id);
					conexion.llenarTabla("select * from datos", tb, table);
				  id=null; 
					impu.setText("");
				  valor.setText("");
				  descri.setText("");
				
				}
			}
		});
		modificar.setBounds(279, 39, 145, 50);
		contenedor.add(modificar);
		
		JButton eliminar = new JButton("Eliminar");
		eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(id!=null)
				{
					conexion.insertar("delete from datos where iddatos="+id);
					conexion.llenarTabla("select * from datos", tb, table);
				  id=null; 
					impu.setText("");
				  valor.setText("");
				  descri.setText("");
				
				}
			}
		});
		sizeL(eliminar, new ImageIcon(getClass().getResource("imagenes/eliminar.png")), 40, 40);
		eliminar.setBounds(432, 39, 125, 50);
		contenedor.add(eliminar);
		
		JButton salir = new JButton("Salir");
		sizeL(salir, new ImageIcon(getClass().getResource("imagenes/salir.png")), 40, 40);
		salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		salir.setBounds(567, 39, 125, 50);
		contenedor.add(salir);
		
		panel.addAncestorListener(new AncestorListener() {
			
			@Override
			public void ancestorRemoved(AncestorEvent event) {
				
				
			}
			
			@Override
			public void ancestorMoved(AncestorEvent event) {
				
				
			}
			
			@Override
			public void ancestorAdded(AncestorEvent event) {
				
				guardar.setEnabled(true);
				modificar.setEnabled(true);
				eliminar.setEnabled(true);
			}
		});
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Consulta de Datos", null, panel_1, null);
		panel_1.addAncestorListener(new AncestorListener() {
			
			@Override
			public void ancestorRemoved(AncestorEvent event) {
				
				
			}
			
			@Override
			public void ancestorMoved(AncestorEvent event) {
				
				
			}
			
			@Override
			public void ancestorAdded(AncestorEvent event) {
				
				guardar.setEnabled(false);
				modificar.setEnabled(false);
				eliminar.setEnabled(false);
				
			}
		});;;
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(61, 29, 616, 256);
		panel_1.add(scrollPane);
		
		table = new JTable(){ 
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setRowSelectionAllowed(true);
		
		table.addMouseListener(new  MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				id=table.getValueAt(table.getSelectedRow(), 0).toString();
				impu.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
				descri.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
			    valor.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
			    tabbedPane.setSelectedIndex(0);
				
			}
		});
		
	    tb=new DefaultTableModel(); 
		table.setModel(tb);
		
		conexion=new Conexion(); 
		conexion.llenarTabla("select * from datos", tb, table);
		scrollPane.setViewportView(table);
		
		
		setTitle("Ingrese los Datos");
		setSize(730, 500);
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
	
	
	
	public void sizeL(JButton l ,ImageIcon icon,int ancho,int alto)
	{	 
				Image img = icon.getImage();
				Image newimg = img.getScaledInstance(ancho,alto,  java.awt.Image.SCALE_SMOOTH);
				ImageIcon newIcon = new ImageIcon(newimg);
				l.setIcon(newIcon);			
	}
	

}
