package paquete;



import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Archivo
{
    String nombreArchivo;
    
    public Archivo() 
    {
    	
	}

    public Archivo(String na)
    {         
        nombreArchivo = na;
    }

    public String traeArchivo()
    {
        String cadena = "";
        FileReader entrada = null;
        StringBuffer str = new StringBuffer();

        try
        {
            entrada = new FileReader(nombreArchivo);
            int c = 0;
            while((c = entrada.read())!= -1)
            {
                cadena += (char)c;                         
            }
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        return cadena;
    }
    
    public void guardar(String ultimaLinea,boolean t){

        String linea [] = traeArchivo().split("\n");

        try
        {
        	FileWriter fichero=new FileWriter(nombreArchivo,t);
            PrintWriter g = new PrintWriter(fichero);
            g.flush();

            if(!linea[0].isEmpty())
            {

                for(int i=0; i<linea.length; i++)
                {                
                    g.write(linea[i]);
                    g.println();
                }
            }

            g.write(ultimaLinea);
            g.println();
            g.close();
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public void guardar(String ultimaLinea){

        String linea [] = traeArchivo().split("\n");

        try
        {
            PrintWriter g = new PrintWriter(nombreArchivo);
            g.flush();

            if(!linea[0].isEmpty())
            {

                for(int i=0; i<linea.length; i++)
                {                
                    g.write(linea[i]);
                    g.println();
                }
            }

            g.write(ultimaLinea);
            g.println();
            g.close();
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public void modificar(String modificar[]){


        try
        {
            PrintWriter g = new PrintWriter(nombreArchivo);
            g.flush();

            for(int i=0; i<modificar.length; i++){
                g.write(modificar[i]);
                g.println();
            }
            g.close();
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public String trae_archivo(String dir)
    {
        String cadena="";
        FileReader fr=null;
        //StringBuffer sb = new StringBuffer();
        
        try
        {
            fr = new FileReader(dir);
            int c;
            
            while((c=fr.read())!=-1)
            {
                cadena+=(char)c;
            
            }
        }
        catch (Exception ex)
        {
           // System.out.print(ex.getMessage());
            
        }
        return cadena;
        }
    
    

    public void modificar(String modificar){

        String linea [] = modificar.split("\n");

        try
        {
            PrintWriter g = new PrintWriter(nombreArchivo);
            g.flush();

            for(int i=0; i<linea.length; i++){
                g.write(linea[i]);
                g.println();
            }
            g.close();
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    public void limpiarArchivo( )
    {
    	FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(nombreArchivo);
            pw = new PrintWriter(fichero);
            pw.flush();
                 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
          
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
public void writeInFile( String dir, String s, boolean b){
        
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(dir,b);
            pw = new PrintWriter(fichero);
            String temp [] = s.split("\n");
          for(int x = 0 ; x< temp.length ; x++ ){
            pw.println(temp[x]);
            }
                 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
          
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
}