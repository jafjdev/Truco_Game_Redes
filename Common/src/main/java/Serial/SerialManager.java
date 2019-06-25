package Serial;

import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortList;
import javax.swing.*;
import static jssc.SerialPort.MASK_RXCHAR;
import jssc.SerialPortEvent;
import jssc.SerialPortException;

public class SerialManager
{
    
    /**
     * Inicio de la trama   ($$)
     * Emisor               (A,B,C,D,S,T)
     * Destinatario         (A,B,C,D,S,T)
     * GANADOR JUEGO        (AC, BD, SG)
     * TURNO                (A,B,C,D)
     * PODER DE ENVIDO      (S,#)
     * RESPUESTA ENVIDO     (S,N,#)
     * PEDIR TRUCO          (S,#)
     * RESPUESTA TRUCO      (S,N,#)
     * CARTA JUGADA         (#1-12 B, C, E, O)
     * FINAL DE TRAMA       (%%)
     * */
    private static SerialPort serialPort;
    public  void start()
    {
        String[] portNames = SerialPortList.getPortNames();
        String s=(String) JOptionPane.showInputDialog(new JFrame("NADA"),"Introduce el puerto",
                "Configuración de puerto", JOptionPane.QUESTION_MESSAGE,null,
                portNames,portNames[0]);
        /**Cree en VSPE un par con puertos COM4 y COM5*/
        serialPort=new SerialPort(s);
        try
        {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
            serialPort.addEventListener(new PortReader(serialPort));
            serialPort.writeString("Hola mundo");
        }
        catch (Exception e)
        {
            System.err.println("Error al abrir puerto");
            JOptionPane.showMessageDialog(new JFrame("NADA"),
                    "Error al abrir puerto, el puerto que ha seleccionado no se encuantra disponible",
                    "Error al abrir puerto",JOptionPane.ERROR_MESSAGE);
            start();
        }
    }
    
    public boolean conectarHardware(String port) {
        //Esta es la función que nos permite conectarnos a puerto serial,
        //para esto tenemos que enviarle el nombre del puerto seleccionado
        serialPort = new SerialPort(port);
        System.out.println("Conectado al puerto:" + " " + port);
        boolean success = false;

        //Aqui configuramos los parámetros del puerto serial
        try {
            serialPort.openPort();
            serialPort.setParams(
                    SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setEventsMask(MASK_RXCHAR);
            

            success = true;
        } catch (SerialPortException ex) {
            Logger.getLogger(Principal.class.getName())
                    .log(Level.SEVERE, null, ex);
            System.out.println("Error al tratar de conectarse al puerto" + port);
            JOptionPane.showMessageDialog(null, "No se pudo realizar la conexión, por favor seleccione otro puerto:" + " " + port);
        }
        return success;
    }

    public void detectarPuertos() {
        //Esta función nos permite detectar los puerto COM disponibles en nuestra PC
        String[] portNames = SerialPortList.getPortNames();
        String s = (String) JOptionPane.showInputDialog(new JFrame("NADA"), "Introduce el puerto",
                "Configuración de puerto", JOptionPane.QUESTION_MESSAGE, null,
                portNames, portNames[0]);
        conectarHardware(s);
        
    }

    public void desconectar() {
        //Esta función es la que utilizamos para desconectarnos del puerto COM
        if (serialPort != null) {//Verificamos si estamos conectados
            try {
                serialPort.removeEventListener();
                if (serialPort.isOpened()) {
                    serialPort.closePort();
                    System.out.println("Se ha desconectado del puerto exitosamente");
                }

                serialPort = null;
            } catch (SerialPortException ex) {
                Logger.getLogger(Principal.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void sentTurno()
    {

    }
}
