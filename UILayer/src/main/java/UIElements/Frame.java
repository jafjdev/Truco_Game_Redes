package UIElements;

import GameManager.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;

public class Frame extends JFrame implements FrameControl
{
    private static final int FRAME_HEIGHT = 500;
    
    private static final int FRAME_WIDTH = 700;
    
    private static final URL ICON_PATH=ClassLoader.getSystemResource("sword/12.jpeg");

    private JPanel gameManager;
    
    public Frame(GameGraphicControl gameGraphicControl)
    {
        this.gameManager=(JPanel)gameGraphicControl;
        createFrame();
        this.setVisible(true);
    }
    
    private void createFrame()
    {
        this.setTitle("Truco");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /**Creamos un toolkit para usar la posición relativa de un archivo dentro del módulo*/
        Toolkit toolkit= Toolkit.getDefaultToolkit();
        Image icon=toolkit.getImage(ICON_PATH);
        this.setIconImage(icon);
        this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        this.setLocationRelativeTo(null);
        GameManager.setFrameControl(this);
        this.add(gameManager);
        MouseListener a=new MouseAction(this, (GameGraphicControl)this.gameManager);
        addMouseListener(a);
        addMouseMotionListener((MouseMotionListener)a);
    }
    
    @Override
    public void update()
    {
        this.repaint();
    }
    
}
