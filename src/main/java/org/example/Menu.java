package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JButton docxToPdf;
    private JButton pdfToDocx;
    private JButton docToPdf;
    private JButton pdfToDoc;

    public static final String BG_COLOR = "#63C36C";
    public static final String BG_COLOR_HOVER = "#69B971";
    public static final String BG_COLOR_PRESSED = "#56A55E";

    public Menu(JFrame frame) {this.frame = frame; initMenu();}

    private void initMenu()
    {
        frame.setTitle("Convert App - CaptainBomPa");
        panel = new JPanel(new GridLayout(0,2));
        frame.setContentPane(panel);
        initComponents();
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void initComponents()
    {
        docxToPdf = new JButton(".docx to .pdf");
        docxToPdf.addActionListener(this::actionPerformed);
        Style.initButtonStyle(docxToPdf);
        panel.add(docxToPdf);

        pdfToDocx = new JButton(".pdf to .docx");
        pdfToDocx.addActionListener(this::actionPerformed);
        Style.initButtonStyle(pdfToDocx);
        panel.add(pdfToDocx);

        docToPdf = new JButton(".doc to .pdf");
        docToPdf.addActionListener(this::actionPerformed);
        Style.initButtonStyle(docToPdf);
        panel.add(docToPdf);

        pdfToDoc = new JButton(".pdf to .doc");
        pdfToDoc.addActionListener(this::actionPerformed);
        Style.initButtonStyle(pdfToDoc);
        panel.add(pdfToDoc);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == docxToPdf)
        {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            docxToPdf.setBackground(Color.decode(BG_COLOR_HOVER));
            new Conversion(frame, "docx", "pdf");
        }
        else if(e.getSource() == pdfToDocx)
        {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            new Conversion(frame, "pdf", "docx");
        }
    }

}
