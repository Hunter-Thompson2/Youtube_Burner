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
import com.github.kiulian.downloader.OnYoutubeDownloadListener;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.VideoDetails;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioVideoFormat;
import com.github.kiulian.downloader.model.formats.Format;
import com.github.kiulian.downloader.parser.DefaultParser;
import com.github.kiulian.downloader.parser.Parser;
//import com.github.kiulian.video.*;
public class Main{
    
    public static void main(String[] args){

        AppVariables appVariables = new AppVariables();
        appVariables.frame = new JFrame();
        appVariables.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appVariables.frame.setSize(2000,2000);

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
        appVariables.batchView = batchView;
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

    }
}

class BatchItem{
    File file;
    boolean isFile;
    YoutubeVideo video;

}
class AppVariables{
    JFrame frame;
    File fileChoose;
    JFileChooser fc;
    ArrayList<BatchItem> items = new ArrayList<BatchItem>();
    BatchView batchView;
    YoutubeDownloader downloader;
    public AppVariables(){
        Parser parser = new DefaultParser();
        downloader = new YoutubeDownloader(parser);
       downloader.setParserRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        downloader.setParserRetryOnFailure(1);
    }
}
class Header extends JPanel{
 
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
            public void actionPerformed(ActionEvent e) {
                appVariables.fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = appVariables.fc.showOpenDialog(Header.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {                    
                    appVariables.fileChoose = appVariables.fc.getSelectedFile();
                    System.out.println(appVariables.fileChoose.getName()+"This is a print");
                    String[] filesS = appVariables.fileChoose.list();
                    fileView.refresh();
                } else {
                    
                }                                
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


    }
    public void refresh(){

        for(File file : appVariables.fileChoose.listFiles()){
            if(file.getName().endsWith(".mp3")){
                JButton song = new JButton(file.getName());
                song.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        BatchItem temp = new BatchItem();
                        temp.file = file;
                        temp.isFile = true;
                        appVariables.items.add(temp);
                        batchView.refresh(appVariables);
                        SwingUtilities.updateComponentTreeUI(appVariables.frame);
                    }
                });
                panel.add(song);
            }
        }
        
        SwingUtilities.updateComponentTreeUI(appVariables.frame);
    }

}
class DropZone extends JPanel{

    private JTextField textField;
    private JButton addButton;
    private String text;
    //private DocumentListener textListener;
    AppVariables appVariables;
    public DropZone(AppVariables appVariables){
        this.appVariables = appVariables;
        textField = new JTextField("Enter Youtube Link");
        addButton = new JButton("Add To Batch");
        textField.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent documentEvent) {
                text = textField.getText();
                System.out.println(text);
            }
            public void removeUpdate(DocumentEvent documentEvent) {
                text = textField.getText();
                System.out.println(text);
            }
            public void insertUpdate(DocumentEvent documentEvent) {
                text = textField.getText();
                System.out.println(text);
            }
        });
        addButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                BatchItem tempItem = new BatchItem();
                YoutubeDownloader downloader = appVariables.downloader;
                YoutubeVideo tempVideo = null;
                try{
                    tempVideo = appVariables.downloader.getVideo(text);
                    
                }
                catch(YoutubeException env){
                    System.out.println("this didnt work");
                    System.out.println(env);
                }
                if(tempVideo!=null){
                    tempItem.isFile = false;
                    tempItem.video = tempVideo;
                    appVariables.items.add(tempItem);
                }

            }
        });
        add(textField);
        add(addButton);
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
            String name = "";
            if(iter.isFile == true){
                name = iter.file.getName();
                System.out.println(iter.file.getName());
            }
            else{
                name = iter.video.details().title();
            }
            JButton item = new JButton(name);

            class itemAction implements ActionListener{
                AppVariables appVariablesInner;
                JButton itemInner;
                BatchItem iterInner;
                public itemAction(AppVariables appTemp, JButton itemTemp, BatchItem iterTemp){
                    this.appVariablesInner = appTemp;
                    this.itemInner = itemTemp;
                    this.iterInner = iterTemp;
                }
                
                public void actionPerformed(ActionEvent e){
                    BatchView.this.remove(itemInner);
                    appVariablesInner.items.remove(iterInner);
                    refresh(appVariablesInner);
                    SwingUtilities.updateComponentTreeUI(appVariablesInner.frame);
                }
            }
            item.addActionListener(new itemAction(appVariables, item, iter));
            add(item);
            i++;
        }
    }
}
class MediaPlayer extends JPanel{
    public MediaPlayer(AppVariables appVariables){

    }
}


