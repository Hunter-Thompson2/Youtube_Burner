import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
import java.io.File;
import javax.swing.BoxLayout;

public class Main{
    
    public static void main(String[] args){
        //JFrame frame = new JFrame();
        AppVariables appVariables = new AppVariables();
        appVariables.frame = new JFrame();
        appVariables.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appVariables.frame.setSize(500,500);
        appVariables.frame.setLayout(new BorderLayout(10,10));

		appVariables.frame.setVisible(true);
        //JPanel panels[];
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();

		panel1.setPreferredSize(new Dimension(100,50));
		panel2.setPreferredSize(new Dimension(200,200));
		panel3.setPreferredSize(new Dimension(150,100));
		panel4.setPreferredSize(new Dimension(100,100));
		panel5.setPreferredSize(new Dimension(100,100));

        panel1.setBorder(BorderFactory.createLineBorder(Color.black));
        panel2.setBorder(BorderFactory.createLineBorder(Color.black));
        panel3.setBorder(BorderFactory.createLineBorder(Color.black));
        panel4.setBorder(BorderFactory.createLineBorder(Color.black));
        panel5.setBorder(BorderFactory.createLineBorder(Color.black));

        
        Header header = new Header(appVariables);
        FileView fileView = new FileView(appVariables);
        DropZone dropZone = new DropZone(appVariables);
        BatchView batchView = new BatchView(appVariables);
        MediaPlayer mediaPlayer = new MediaPlayer(appVariables);

        panel1.add(header);
        panel2.add(fileView);
        panel3.add(dropZone);
        panel4.add(batchView);
        panel5.add(mediaPlayer);

        appVariables.frame.add(panel1,BorderLayout.NORTH);
		appVariables.frame.add(panel2,BorderLayout.WEST);
		appVariables.frame.add(panel3,BorderLayout.EAST);
		appVariables.frame.add(panel4,BorderLayout.SOUTH);
		appVariables.frame.add(panel5,BorderLayout.CENTER);
        appVariables.frame.pack();
        appVariables.frame.setVisible(true);
    }
}
class AppVariables{
    JFrame frame;
    File fileChoose;
    JFileChooser fc;
}
class Header extends JPanel{
    //File fileChoose;
    
    //JFileChooser fc; 
    public Header(AppVariables appVariables){
        JButton settings = new JButton("SETTINGS");
        JButton file = new JButton("FILE");
        JButton play = new JButton("PLAY");
        appVariables.fc = new JFileChooser();
        settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Insert code here
            }
        });
        file.addActionListener(new ActionListener() {
            //JFileChooser fc = new JFileChooser();
            //JFileChooser.addActionListener
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                appVariables.fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = appVariables.fc.showOpenDialog(Header.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    
                    appVariables.fileChoose = appVariables.fc.getSelectedFile();
                    System.out.println(appVariables.fileChoose.getName()+"This is a print");
                    String[] filesS = appVariables.fileChoose.list();
                    //File[] files = fileChoose.
                    //for(String iter : filesS){
                    //    System.out.println(iter);
                    //}

                    //This is where a real application would open the file.
                    //log.append("Opening: " + file.getName() + "." + newline);
                } else {
                    //log.append("Open command cancelled by user." + newline);
                }
                //log.setCaretPosition(log.getDocument().getLength());

                
                
            }
        });
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Insert code here
            }
        });
        add(settings);
        add(file);
        add(play);
    }
}

// class otherContent extends ContentView{}  now has static variables
class FileView extends JPanel{
    public FileView(AppVariables appVariables){
        JButton load = new JButton("Load Files");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(load);
        add(panel);
        //appVariables.fc.addActionListener(new ActionListener() {
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("This occured");
                //if(appVariables.fileChoose != null && appVariables.fileChoose.length() > 0){
                    //for(String iter : appVariables.fileChoose.list()){
                    //    System.out.println(iter);
                    //}
                    for(File file : appVariables.fileChoose.listFiles()){
                        if(file.getName().endsWith(".mp3")){
                            System.out.println("This got chosen");
                            JButton song = new JButton(file.getName());
                            song.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e){
                                    System.out.println(file.getName());
                                }
                            });
                            panel.add(song);
                        }
                    }
                
                //}
                SwingUtilities.updateComponentTreeUI(appVariables.frame);
            }
        });
        /*file.addActionListener(new ActionListener() {
            
            
            public void actionPerformed(ActionEvent e) {
                String[] filesS = appVariables.fileChoose.list();
                //File[] files = fileChoose.
                System.out.println("This is FileView");
                for(String iter : filesS){
                    System.out.println(iter);
                }
            }
        });
        add(file);*/
    }
}
class DropZone extends JPanel{
    public DropZone(AppVariables appVariables){

    }
}
class BatchView extends JPanel{
    public BatchView(AppVariables appVariables){

    }
}
class MediaPlayer extends JPanel{
    public MediaPlayer(AppVariables appVariables){

    }
}


