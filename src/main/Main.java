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
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.github.kiulian.downloader.*;
public class Main{
    
    public static void main(String[] args){
        //JFrame frame = new JFrame();
        AppVariables appVariables = new AppVariables();
        appVariables.frame = new JFrame();
        appVariables.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appVariables.frame.setSize(2000,2000);
        //appVariables.frame.setLayout(new BorderLayout(10,10));
        //appVariables.frame.setPreferredSize(2000,2000);
		appVariables.frame.setVisible(true);
        //JPanel panels[];
        JPanel overall = new JPanel();
        overall.setPreferredSize(new Dimension(1000,1000));
        overall.setLayout(new BorderLayout(10,10));
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel(); //file view
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
        JScrollPane scrollPane2 = new JScrollPane(panel2);
        JScrollPane scrollPane4 = new JScrollPane(panel4);
        
		//panel1.setPreferredSize(new Dimension(100,50));
		//panel2.setPreferredSize(new Dimension(500,500));
		//panel3.setPreferredSize(new Dimension(500,500));
		//panel4.setPreferredSize(new Dimension(500,500));
		//panel5.setPreferredSize(new Dimension(100,100));

        panel1.setBorder(BorderFactory.createLineBorder(Color.black));
        panel2.setBorder(BorderFactory.createLineBorder(Color.black));
        panel3.setBorder(BorderFactory.createLineBorder(Color.black));
        panel4.setBorder(BorderFactory.createLineBorder(Color.black));
        panel5.setBorder(BorderFactory.createLineBorder(Color.black));

        
        
        BatchView batchView = new BatchView(appVariables);
        FileView fileView = new FileView(appVariables, batchView);
        DropZone dropZone = new DropZone(appVariables);
        MediaPlayer mediaPlayer = new MediaPlayer(appVariables);
        Header header = new Header(appVariables, fileView);

        panel1.add(header);
        panel2.add(fileView);
        panel3.add(dropZone);
        panel4.add(batchView);
        panel5.add(mediaPlayer);

        overall.add(panel1,BorderLayout.NORTH);
		overall.add(scrollPane2,BorderLayout.WEST);
		overall.add(scrollPane4,BorderLayout.EAST);
		overall.add(panel5,BorderLayout.SOUTH);
		overall.add(panel3,BorderLayout.CENTER);
        appVariables.frame.add(overall);
        appVariables.frame.pack();
        appVariables.frame.setVisible(true);
        /*appVariables.frame.add(panel1,BorderLayout.NORTH);
		appVariables.frame.add(scrollPane2,BorderLayout.WEST);
		appVariables.frame.add(panel4,BorderLayout.EAST);
		appVariables.frame.add(panel5,BorderLayout.SOUTH);
		appVariables.frame.add(panel3,BorderLayout.CENTER);
        appVariables.frame.pack();
        appVariables.frame.setVisible(true);*/
    }
}

class Youtils{

    public Youtils(AppVariables appVariables){

    }


}

class BatchItem{
    File file;
    boolean isFile;

}
class AppVariables{
    JFrame frame;
    File fileChoose;
    JFileChooser fc;
    ArrayList<BatchItem> items = new ArrayList<BatchItem>();
}
class Header extends JPanel{
    //File fileChoose;
    
    //JFileChooser fc; 
    public Header(AppVariables appVariables, FileView fileView){
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
                    fileView.refresh();
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
    private AppVariables appVariables;
    private BatchView batchView;
    private JPanel panel;
    public FileView(AppVariables appVariables, BatchView batchView){
        this.appVariables = appVariables;
        this.batchView = batchView;
       
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        add(panel);
        //appVariables.fc.addActionListener(new ActionListener() {
        //load.addActionListener(new ActionListener() {
        //    public void actionPerformed(ActionEvent e) {
         //       refresh();
                /*panel.removeAll();
                panel.revalidate();
                panel.repaint();
                //System.out.println("This occured");
                //if(appVariables.fileChoose != null && appVariables.fileChoose.length() > 0){
                    //for(String iter : appVariables.fileChoose.list()){
                    //    System.out.println(iter);
                    //}
                    for(File file : appVariables.fileChoose.listFiles()){
                        if(file.getName().endsWith(".mp3")){
                            //System.out.println("This got chosen");
                            JButton song = new JButton(file.getName());
                            song.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e){
                                    BatchItem temp = new BatchItem();
                                    temp.file = file;
                                    temp.isFile = true;
                                    appVariables.items.add(temp);
                                    //System.out.println(file.getName());
                                    batchView.refresh(appVariables);
                                    SwingUtilities.updateComponentTreeUI(appVariables.frame);
                                    //batchView.revalidate();
                                    //batchView.repaint();
                                    //appVariables.frame.getComponentAt(BorderLayout.EAST).revalidate();
                                }
                            });
                            panel.add(song);
                        }
                    }
                
                //}
                
                SwingUtilities.updateComponentTreeUI(appVariables.frame);
                 *///}
        //});

    }
    public void refresh(){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        //System.out.println("This occured");
        //if(appVariables.fileChoose != null && appVariables.fileChoose.length() > 0){
            //for(String iter : appVariables.fileChoose.list()){
            //    System.out.println(iter);
            //}
            for(File file : appVariables.fileChoose.listFiles()){
                if(file.getName().endsWith(".mp3")){
                    //System.out.println("This got chosen");
                    JButton song = new JButton(file.getName());
                    song.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e){
                            BatchItem temp = new BatchItem();
                            temp.file = file;
                            temp.isFile = true;
                            appVariables.items.add(temp);
                            //System.out.println(file.getName());
                            batchView.refresh(appVariables);
                            SwingUtilities.updateComponentTreeUI(appVariables.frame);
                            //batchView.revalidate();
                            //batchView.repaint();
                            //appVariables.frame.getComponentAt(BorderLayout.EAST).revalidate();
                        }
                    });
                    panel.add(song);
                }
            }
        
        //}
        
        SwingUtilities.updateComponentTreeUI(appVariables.frame);
    }

}
class DropZone extends JPanel{

    private JTextField textField;
    private JButton addButton;
    //private DocumentListener textListener;
    AppVariables appVariables;
    public DropZone(AppVariables appVariables){
        this.appVariables = appVariables;
        textField = new JTextField("Enter Youtube Link");
        addButton = new JButton("Add To Batch");
        textField.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent documentEvent) {
                System.out.println(documentEvent);
            }
            public void removeUpdate(DocumentEvent documentEvent) {
                System.out.println(documentEvent);
            }
            public void insertUpdate(DocumentEvent documentEvent) {
                System.out.println(documentEvent);
            }
        });
        //textListener.addActionListener( new ActionListener() {
        //    public void actionPerformed(ActionEvent e){
//
       //     }
       // });
        add(textField);
        add(addButton);
        //textListener = new DocumentListener();
    }

}
class BatchView extends JPanel{
    public BatchView(AppVariables appVariables){
        
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        System.out.println("this should be more unique");
        for(BatchItem iter : appVariables.items){
            System.out.println(iter.file.getName());
            JButton item = new JButton(iter.file.getName());
            add(item);
        }
    }
    public void refresh(AppVariables appVariables){
        removeAll();
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        System.out.println("this should be more unique");
        int i = 0;
        for(BatchItem iter : appVariables.items){
            System.out.println(iter.file.getName());
            JButton item = new JButton(iter.file.getName());
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    //remove(item);
                    appVariables.items.remove(iter);
                    refresh(appVariables);
                    SwingUtilities.updateComponentTreeUI(appVariables.frame);
                }
            });
            add(item);
            i++;
        }
    }
}
class MediaPlayer extends JPanel{
    public MediaPlayer(AppVariables appVariables){

    }
}


