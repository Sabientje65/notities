package Notities;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

class Notities implements ActionListener{
    ImageIcon saveIcon = new ImageIcon("save.gif");
    ImageIcon loadIcon = new ImageIcon("load.gif");
    ImageIcon clearIcon = new ImageIcon("clear.gif");
    JButton save = new JButton("Opslaan", saveIcon);
    JButton load = new JButton("Laden", loadIcon);
    JButton clear = new JButton("Wissen", clearIcon);
    JTextArea txt = new JTextArea(20, 40);
    JScrollPane scroll;
    
    public Notities(){
        JFrame frame = new JFrame("Notities");
        JPanel pan = new JPanel();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        save.addActionListener(this);
        load.addActionListener(this);
        clear.addActionListener(this);
        
        scroll = new JScrollPane(txt);
        pan.add(save);
        pan.add(load);
        pan.add(clear);
        
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(pan, BorderLayout.SOUTH);
        frame.pack();
        frame.setSize(frame.getWidth()+10, frame.getHeight()+35);
        frame.setLocationRelativeTo(null);
        
        setLookAndFeel();
        load(false);
        
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent evt){
        Object sauce = evt.getSource();
        
        String text = txt.getText();
        
        if (sauce == save)
            save(text);
        if (sauce == load)
            load(true);
        if (sauce == clear)
            txt.setText("");
    }
    
    private void save(String text){
        int g = 0;
        try (FileOutputStream out = new FileOutputStream("Text.txt");
            BufferedOutputStream buff = new BufferedOutputStream(out);
            DataOutputStream data = new DataOutputStream(buff)){
            
            try {
                if (text.length() == 0){
                    File f = new File("Text.txt");
                    f.delete();
                    f.createNewFile();
                }
                while(g < text.length()){
                    data.write((int)text.charAt(g));
                    g++;
                }
                data.close();
                JOptionPane.showMessageDialog(null, "Opgeslagen!");
            } catch(IOException ioe){
                data.close();
                JOptionPane.showMessageDialog(null, "Error: " + ioe);
            }
        } catch(Exception ioe){
            JOptionPane.showMessageDialog(null, "Error: " + ioe);
        }
    }
    
    private void load(boolean message){
        int x = 0;
        int lines = 0;
        
        File f = new File("Text.txt");
        if (!f.exists()){
            try{
                f.createNewFile();
            } catch(IOException ioe){
                JOptionPane.showMessageDialog(null, "Error creating text file: " + ioe);
            }
        }
        try(FileReader fil = new FileReader("Text.txt");
            BufferedReader buf = new BufferedReader(fil)){
            try{
                while(true){
                    String line = buf.readLine();
                    if(line == null)
                        break;
                    else
                        lines++;
                }
            } catch(IOException ioe){
                JOptionPane.showMessageDialog(null, "Error: " + ioe);
            }
        } catch(IOException ioe){
            JOptionPane.showMessageDialog(null, "Error: " + ioe);
        }
        
        try (FileReader file = new FileReader("Text.txt");
            BufferedReader buff = new BufferedReader(file)){
            try{
                while (true){
                    String line = buff.readLine();
                    if (line == null)
                        break;
                    else if (x == lines -1 & x == 0)
                        txt.setText(line);
                    else if (x == lines -1)
                        txt.setText(txt.getText() + line);
                    else if (x!=0)
                        txt.setText(txt.getText() + line + "\n");
                    else
                        txt.setText(line + "\n");
                    x++;
                }
                buff.close();
                if (message)
                    JOptionPane.showMessageDialog(null, "Bestand succesvol geladen!");
            } catch(IOException ioe){
                JOptionPane.showMessageDialog(null, "Error: " + ioe);
            }
        } catch (IOException ioe){
            JOptionPane.showMessageDialog(null, "Error: " + ioe);
        }
    }
    
    private void setLookAndFeel(){
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception exc){
            JOptionPane.showMessageDialog(null, "Error setting look and feel: " + exc);
        }
    }
    
    public static void main(String[] args){
        Notities kat = new Notities();
    }
}