package Serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class PortReader implements SerialPortEventListener
{
    private SerialPort serialPort;
    
    
    public PortReader(SerialPort serialPort)
    {
        this.serialPort=serialPort;
    }
    
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent)
    {
        if(serialPortEvent.isRXCHAR()&&serialPortEvent.getEventValue()>0)
        {
            try
            {
                String data = this.serialPort.readString(serialPortEvent.getEventValue());
                for(int i=0;i<data.length()/16;i++)
                {
                    String substring = data.substring(i * 16, i * 16 + 16);
                    System.out.println("Recibido: "+ substring);
                    filter(substring);
                }
                //System.out.println("Recibido: "+data);
            }
            catch (Exception e)
            {
                System.out.println("Error recibiendo información");
                System.err.println(e.getMessage());
            }
        }
    }
    
    public void filter(String s)
    {
        if(s.substring(0,12).equals("$$$$$$$$$$$$"))//12$=pedir truco
        {
            SerialManager.trick(s.substring(12,14));
        }
        else if(s.substring(0,11).equals("$$$$$$$$$$$"))//11$ responder truco
        {
            SerialManager.trickResponse(s.substring(11,14));
        }
        else if(s.substring(0,9).equals("$$$$$$$$$"))//9$ poner carta en juego
        {
            SerialManager.playCard(s.substring(9,14));
        }
        else if(s.substring(0,9).equals("$$$$$$$$#"))//8$ + 1# poner vira
        {
            SerialManager.setVira(s.substring(9,14));
        }
        else if(s.substring(0,3).equals("$$$"))//3$ dar cartas a jugador X.
        {
            SerialManager.giveCards(s.substring(3,14));
        }
    }
}