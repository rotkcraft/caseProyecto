package paquete;

import java.awt.Component;
import java.awt.Image;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class Conexion {
private String url; 
private String db; 
private String usu; 
private String clave;

public Conexion() {
	Archivo archivo=new Archivo("archivos/conexion.dat");
	String s[]=archivo.traeArchivo().trim().split(",");
	this.url=s[0];
	this.db=s[1];
	this.usu=s[2];
	this.clave=s[3];
	
}
public void insertar(String sql)
{
	try
	{
		Connection con=DriverManager.getConnection(url+"/"+db, usu, clave);
		Statement ps=con.createStatement();
		ps.execute(sql);
		
	}catch(Exception ex)
	{
		ex.printStackTrace();
	}
}

public void llenarTabla(String sql, DefaultTableModel modelo, JTable tabla)
{
    Connection conexion = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ResultSetMetaData md;
    Blob blob;
    modelo.setColumnCount(0);
    modelo.setRowCount(0);
    tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    try
    {

        conexion = DriverManager.getConnection(
        		url+"/"+db, usu, clave);
        ps = conexion.prepareStatement(sql);
        rs = ps.executeQuery();
        md = rs.getMetaData();
        int columnasContador = md.getColumnCount();
        int alto = 0;

        for (int i = 1; i <= columnasContador; i++)
        {
            modelo.addColumn(md.getColumnLabel(i));
        }


        Vector filas;
        while (rs.next())
        {

            filas = new Vector(columnasContador);
            for (int i = 1; i <= columnasContador; i++)
            {

                if (md.getColumnTypeName(i).equalsIgnoreCase("blob"))
                {
                    blob = rs.getBlob(i);
                    ImageIcon imagen = new ImageIcon(blob.getBytes(1, (int) blob.length()));
                    ImageIcon icono = new ImageIcon(imagen.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
                    alto = icono.getIconHeight();
                    filas.addElement(icono);
                    tabla.getColumnModel().getColumn(i - 1).setCellRenderer(new Renderizar());
                    tabla.setRowHeight(alto);

                }
                else
                {
                    filas.addElement(rs.getString(i));
                }

            }
            modelo.addRow(filas);

        }


       ajustarAnchoTabla(tabla);
        rs.close();
        ps.close();
        conexion.close();

    }
    catch (SQLException ignored)
    {

    }


}
private void ajustarAnchoTabla(JTable tabla)
{
    final TableColumnModel modelo = tabla.getColumnModel();
    int ancho;
    for (int columna = 0; columna < tabla.getColumnCount(); columna++)
    {
        TableCellRenderer renderizar = modelo.getColumn(columna).getHeaderRenderer();
        if (renderizar == null)
        {
            renderizar = tabla.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderizar.getTableCellRendererComponent(tabla, modelo.getColumn(columna).getHeaderValue(), false, false, 0, 0);
        ancho = comp.getPreferredSize().width;
        for (int fila = 0; fila < tabla.getRowCount(); fila++)
        {
            renderizar = tabla.getCellRenderer(fila, columna);
            Object valor = tabla.getValueAt(fila, columna);
            comp = renderizar.getTableCellRendererComponent(tabla, valor, false, false, fila, columna);
            ancho = Math.max(comp.getPreferredSize().width, ancho);
        }
        ancho += 4;
        modelo.getColumn(columna).setPreferredWidth(ancho);
    }
}

class Renderizar extends DefaultTableCellRenderer
{
    public Renderizar()
    {
        super();
    }

    public void setValue(Object value)
    {
        if (value == null)
        {
            setText("");
        }
        else
        {
            setIcon((Icon) value);
        }
    }
}


public int valorDev(String sql)
{
	int x=0;
	try
	{
		Connection con=DriverManager.getConnection(url+"/"+db, usu, clave);
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			x=rs.getInt(1);
		}
		
	}catch(Exception ex)
	{
		x=-1;
	}
	return x;
}

}
