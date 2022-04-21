package org.example;

import com.convertapi.client.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Locale;

public class Conversion implements ActionListener {
    public JFrame frame;
    private JPanel panel;

    private String sourceExtension;
    private String destinationExtension;

    private JTextPane textAreaPathSource;
    private JTextPane textAreaPathDestination;
    private JTextPane textAreaFileName;

    private JButton buttonSource;
    private JButton buttonDestination;
    private JButton buttonReturn;
    private JButton buttonSubmit;

    private JLabel fileNameLabel;

    private String sourceFilePath = "";
    private String destinationDirectory = "";
    private String destinationFileName = "";

    private JDialog popUpNA;
    private JPanel popUpNAPanel;
    private JLabel popUpNALabel;

    private JDialog popUp;
    private JPanel popUpPanel;
    private JLabel popUpLabel;
    private JButton popUpButton;

    String slashType = "";

    Thread t1;
    Thread t2;

    public Conversion(JFrame frame, String sourceExtension, String destinationExtension)
    {
        this.frame = frame;
        this.sourceExtension = sourceExtension;
        this.destinationExtension = destinationExtension;
        Config.setDefaultSecret("yWgkFW7Ae2IG1eGq");
        initFrame();

        String osType = System.getProperty("os.name").toLowerCase();
        if(osType.contains("windows"))
        {
            slashType = "\\";
        }
        else if(osType.contains("linux"))
        {
            slashType = "//";
        }
    }

    private void initFrame()
    {
        frame.setTitle("."+sourceExtension+" to ."+destinationExtension);
        panel = new JPanel(new GridLayout(0,2));
        frame.setContentPane(panel);
        initComponents();
        createPopUps();
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void initComponents()
    {
        buttonSource = new JButton("Select source file");
        buttonSource.addActionListener(this);
        Style.initButtonStyle(buttonSource);
        panel.add(buttonSource);

        textAreaPathSource = new JTextPane();
        textAreaPathSource.setDropTarget(new DropTarget(){
            public synchronized void drop(DropTargetDropEvent event) {
                try
                {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for(File file : droppedFiles)
                    {
                        if(file.getAbsolutePath().endsWith("."+sourceExtension))
                        {
                            textAreaPathSource.setText(file.getAbsolutePath());
                            setDefaultDestinationDirectory(file.getAbsolutePath());
                            sourceFilePath = file.getAbsolutePath();
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        Style.initJTextAreaStyle(textAreaPathSource);
        panel.add(textAreaPathSource);

        buttonDestination = new JButton("Select destination directory");
        buttonDestination.addActionListener(this);
        Style.initButtonStyle(buttonDestination);
        panel.add(buttonDestination);

        textAreaPathDestination = new JTextPane();
        Style.initJTextAreaStyle(textAreaPathDestination);
        panel.add(textAreaPathDestination);

        fileNameLabel = new JLabel("Insert new file name");
        Style.initLabelStyle(fileNameLabel);
        panel.add(fileNameLabel);

        textAreaFileName = new JTextPane();
        Style.initJTextAreaStyle(textAreaFileName);
        panel.add(textAreaFileName);

        buttonReturn = new JButton("Return");
        buttonReturn.addActionListener(this);
        Style.initButtonStyle(buttonReturn);
        panel.add(buttonReturn);

        buttonSubmit = new JButton("Convert!");
        buttonSubmit.addActionListener(this);
        Style.initButtonStyle(buttonSubmit);
        panel.add(buttonSubmit);
    }

    public void createPopUps()
    {
        // popup with no action
        popUpNA = new JDialog();
        popUpNA.setSize(Main.SCREEN_WIDTH-300, Main.SCREEN_HEIGHT-300);
        popUpNAPanel = new JPanel(new GridLayout());

        popUpNALabel = new JLabel("");
        Style.initLabelStyle(popUpNALabel);
        popUpNAPanel.add(popUpNALabel);

        popUpNA.setContentPane(popUpNAPanel);

        //popup with action
        popUp = new JDialog();
        popUp.setSize(Main.SCREEN_WIDTH-300, Main.SCREEN_HEIGHT-300);
        popUpPanel = new JPanel(new GridLayout(0,1));

        popUpLabel = new JLabel("");
        Style.initLabelStyle(popUpLabel);
        popUpPanel.add(popUpLabel);

        popUpButton = new JButton("ok");
        Style.initButtonStyle(popUpButton);
        popUpButton.setBackground(Color.decode(Menu.BG_COLOR_HOVER));
        popUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popUp.setVisible(false);
            }
        });
        popUpPanel.add(popUpButton);

        popUp.setContentPane(popUpPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //get source file path
        if(e.getSource() == buttonSource)
        {
                //open JFileChooser
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("."+sourceExtension, sourceExtension);
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(frame);

                if(returnVal == JFileChooser.APPROVE_OPTION)
                {
                    //get file path
                    File file = chooser.getSelectedFile();
                    sourceFilePath = file.getAbsolutePath();

                    //show file path on Jtx
                    textAreaPathSource.setText(sourceFilePath);

                    //set default directory path as selected file
                    setDefaultDestinationDirectory(sourceFilePath);
                }
                else if(returnVal == JFileChooser.CANCEL_OPTION)
                {

                }
                else if(returnVal == JFileChooser.ERROR_OPTION)
                {
                    popUp.setLocationRelativeTo(frame);
                    popUpLabel.setText("Error while selecting file! Try again!");
                    popUp.setVisible(true);
                }
        }
        //get destination directory path
        else if(e.getSource() == buttonDestination)
        {
                //open JFileChooser
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                int returnVal = chooser.showOpenDialog(frame);

                if(returnVal == JFileChooser.APPROVE_OPTION)
                {
                    //get directory path
                    File file = chooser.getSelectedFile();
                    destinationDirectory = file.getAbsolutePath();

                    //show directory path
                    textAreaPathDestination.setText(destinationDirectory);
                }
                else if(returnVal == JFileChooser.CANCEL_OPTION)
                {

                }
                else if(returnVal == JFileChooser.ERROR_OPTION)
                {
                    popUp.setLocationRelativeTo(frame);
                    popUpLabel.setText("Error while selecting directory! Try again!");
                    popUp.setVisible(true);
                }
        }
        else if(e.getSource() == buttonReturn)
        {
            new Menu(frame);
        }
        else if(e.getSource() == buttonSubmit)
        {
            t1 = new Thread()
            {
              public void run()
              {
                  popUpNALabel.setText("Converting. Please wait!");
                  popUpNA.setLocationRelativeTo(frame);
                  popUpNA.setVisible(true);
              }
            };
            t2 = new Thread() {
                public void run()
                {
                    convertFile();
                }
            };
            t1.start();
            t2.start();
        }
        popUp.requestFocus();
    }

    private void convertFile()
    {
        sourceFilePath = textAreaPathSource.getText();
        destinationDirectory = textAreaPathDestination.getText();
        destinationFileName = textAreaFileName.getText();

        if(!sourceFilePath.isEmpty() && !destinationDirectory.isEmpty() && !destinationFileName.isEmpty())
        {
            if(sourceFilePath.endsWith("."+sourceExtension))
            {
                String destinationP = destinationDirectory+slashType.charAt(0)+destinationFileName+"."+destinationExtension;
                try
                {
                    ConvertApi.convertFile(sourceFilePath, destinationP);
                    popUpNA.setVisible(false);
                    popUpLabel.setText("Conversion done!");
                }
                catch (Exception e)
                {
                    popUpNA.setVisible(false);
                    popUpLabel.setText("Failed to convert file. Check your internet connection, source/destination paths for files. Remember that the new file name should be without any extension!");
                }
                finally
                {
                    popUp.setLocationRelativeTo(frame);
                    popUp.setVisible(true);
                }
            }
        }
        else
        {
            popUpNA.setVisible(false);
            popUp.setLocationRelativeTo(frame);
            popUpLabel.setText("You left empty source/destination path or file name!");
            popUp.setVisible(true);
        }
    }

    public void setDefaultDestinationDirectory(String sourceFilePath)
    {
        if(System.getProperty("os.name").toLowerCase().contains("win"))
        {
            String tmpPathDestination = sourceFilePath;
            int index = tmpPathDestination.lastIndexOf("\\");
            tmpPathDestination = (String)tmpPathDestination.subSequence(0,index);
            textAreaPathDestination.setText(tmpPathDestination);
            destinationDirectory = tmpPathDestination;
        }
        else if(System.getProperty("os.name").toLowerCase().contains("linux"))
        {
            String tmpPathDestination = sourceFilePath;
            int index = tmpPathDestination.lastIndexOf("/");
            tmpPathDestination = (String)tmpPathDestination.subSequence(0,index);
            textAreaPathDestination.setText(tmpPathDestination);
            destinationDirectory = tmpPathDestination;
        }

    }

}
