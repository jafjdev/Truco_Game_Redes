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
    
    
    public boolean conectarHardware() {
        //Funcion para conectarse al puerto
        System.out.println("Conectado al puerto");
        boolean success = false;

        //Configuracion de los parametros del puerto
        try {
            serialPort.openPort();
            serialPort.setParams(
                    SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setEventsMask(MASK_RXCHAR);
            serialPort.addEventListener((SerialPortEvent serialPortEvent) -> {
                if (serialPortEvent.isRXCHAR()) {
                    try {
                        //Lectura del puerto COM
                        String st = serialPort.readString();
                        System.out.println(st);

                    } catch (SerialPortException ex) {
                        Logger.getLogger(Principal.class.getName())
                                .log(Level.SEVERE, null, ex);
                        System.out.println("No se pudo recibir el dato");
                    }
                }
            });

            success = true;
        } catch (SerialPortException ex) {
            Logger.getLogger(Principal.class.getName())
                    .log(Level.SEVERE, null, ex);
            System.out.println("Error al tratar de conectarse al puerto");
            JOptionPane.showMessageDialog(null, "No se pudo realizar la conexión, por favor seleccione otro puerto" );
        }
        return success;
    }

    public void detectarPuertos() {
        //Detectar los puerto COM disponibles en nuestra PC
        String[] portNames = SerialPortList.getPortNames();
        String s = (String) JOptionPane.showInputDialog(new JFrame("NADA"), "Introduce el puerto",
                "Configuración de puerto", JOptionPane.QUESTION_MESSAGE, null,
                portNames, portNames[0]);
        serialPort = new SerialPort(s);
        
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
    
    public void sentTrama(String trama){
        //En este evento realizamos la escritura en el puerto serial
        
        if(serialPort != null){//Verificamos que estamos conectados a el puerto serial
            try {
                serialPort.writeString(trama);//Aqui se realiza escritura en el puerto serial

            } catch (SerialPortException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Se ha enviado el dato "+ trama);

        }else{
            System.out.println("arduinoPort not connected!");
        }
    }

    public static void sentTurno()
    {

    }
}
