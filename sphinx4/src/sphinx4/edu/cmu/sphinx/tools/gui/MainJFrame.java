/*
 * MainJFrame.java
 *
 * Portions Copyright 2007 Mitsubishi Electric Research Laboratories.
 * Portions Copyright 2007 Harvard Extension Schoool, Harvard University
 * All Rights Reserved.  Use is subject to license terms.
 * 
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL 
 * WARRANTIES.
 *
 * Created on October 20, 2006, 9:20 PM
 */
package edu.cmu.sphinx.tools.gui;

import edu.cmu.sphinx.tools.gui.reader.GUIReaderException;
import edu.cmu.sphinx.tools.gui.writer.GUIWriterException;
import edu.cmu.sphinx.tools.gui.util.ConfigurableUtilException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Holds the main GUI frame
 * Create the GUI tabbed-panels
 *
 * @author  Ariani
 */
 class MainJFrame extends javax.swing.JFrame {
    
    
    /** Creates new form MainJFrame, only accessible for the classes in this package  */
     MainJFrame(GUIMediator gm, Iterator groups) {
        super("Sphinx-4 Configuration");
        _gm = gm;
        
        initComponents();  // initialize and create GUI components  
        addGlobalPanel();
        addTextPanels(groups);         
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jDialogOutput = new javax.swing.JDialog();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextAreaOutput = new javax.swing.JTextArea();
        TabPanel = new javax.swing.JTabbedPane();
        MenuBarFile = new javax.swing.JMenuBar();
        MenuFile = new javax.swing.JMenu();
        MenuItemNew = new javax.swing.JMenuItem();
        MenuItemOpen = new javax.swing.JMenuItem();
        MenuItemSave = new javax.swing.JMenuItem();
        MenuItemExit = new javax.swing.JMenuItem();
        MenuModel = new javax.swing.JMenu();
        MenuItemRefresh = new javax.swing.JMenuItem();
        MenuConfig = new javax.swing.JMenu();
        MenuItemShow = new javax.swing.JMenuItem();

        jDialogOutput.setTitle("Current Configuration");
        jDialogOutput.setAlwaysOnTop(true);
        jDialogOutput.setModal(true);
        jDialogOutput.setName("dialogSource");
        jTextAreaOutput.setMargin(new java.awt.Insets(20, 20, 20, 20));
        jScrollPane6.setViewportView(jTextAreaOutput);

        jDialogOutput.getContentPane().add(jScrollPane6, java.awt.BorderLayout.CENTER);

        getContentPane().setLayout(new java.awt.GridLayout(1, 1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().add(TabPanel);

        MenuFile.setText("F i l e.....");
        MenuItemNew.setText("New");
        MenuItemNew.setActionCommand("File-New");
        MenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemActionPerformed(evt);
            }
        });

        MenuFile.add(MenuItemNew);

        MenuItemOpen.setText("Open");
        MenuItemOpen.setActionCommand("File-Open");
        MenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemActionPerformed(evt);
            }
        });

        MenuFile.add(MenuItemOpen);

        MenuItemSave.setText("Save");
        MenuItemSave.setActionCommand("File-Save");
        MenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemActionPerformed(evt);
            }
        });

        MenuFile.add(MenuItemSave);

        MenuItemExit.setText("Exit");
        MenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemActionPerformed(evt);
            }
        });

        MenuFile.add(MenuItemExit);

        MenuBarFile.add(MenuFile);

        MenuModel.setText("Sphinx Model...");
        MenuItemRefresh.setText("Refresh");
        MenuItemRefresh.setActionCommand("Model-Refresh");
        MenuItemRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemActionPerformed(evt);
            }
        });

        MenuModel.add(MenuItemRefresh);

        MenuBarFile.add(MenuModel);

        MenuConfig.setText("Configuration...");
        MenuItemShow.setText("Show Configuration Text");
        MenuItemShow.setActionCommand("Show-Configuration");
        MenuItemShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemActionPerformed(evt);
            }
        });

        MenuConfig.add(MenuItemShow);

        MenuBarFile.add(MenuConfig);

        setJMenuBar(MenuBarFile);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Method handler for File-Menu selection and Model Refresh 
     */
    private void MenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemActionPerformed
        String NEW = "File-New";
        String OPEN = "File-Open";
        String SAVE = "File-Save";
        String EXIT = "Exit";
        String REFRESH = "Model-Refresh";
        String SHOW_CONFIG = "Show-Configuration";
        
        String command = evt.getActionCommand ();        
        ConfigFilter cf = new ConfigFilter();
        JFileChooser fc = new JFileChooser ();
        fc.setCurrentDirectory (new File (".")); // Start in current directory
        fc.setFileFilter (cf); // Set filter for config files.
        
        boolean status = false;
        /** Process events from the chooser. **/
        try{
            if  (command.equals (OPEN)) {
                // Open a file
                status = openFile (fc);
                if (!status)
                    displayError("File Open Error: Error opening file!");
            } else if (command.equals (SAVE)) {
                // Save a file
                status = saveFile (fc);
                if (!status)
                    displayError("File Save Error: IO error in saving file!!");
            }else if (command.equals(NEW)){  
                if ( confirmAction("Discard all configuration set?") ) {
                    _gm.action(GUIMediator.NEW);
                }
            }else if ( command.equals(REFRESH) ){ 
                if ( confirmAction("Discard all configuration set?") ) {
                    deleteTabPanels();
                    _gm.action(GUIMediator.REFRESH);
                }
            } else if (command.equals(SHOW_CONFIG) ){ // show config output 

                _gm.action(GUIMediator.SHOW_CONFIG,jTextAreaOutput);
                
                jDialogOutput.setSize(800,500);
                jDialogOutput.setLocationRelativeTo(null);
                jDialogOutput.setVisible(true);
            } else if (command.equals (EXIT) ) {
                dispose ();
            }
        }catch(GUIReaderException re){
            displayError("Error occured while reading from file!");
        }catch(GUIWriterException we){
            displayError("Error occured while saving file!");        
        }catch(ConfigurableUtilException cue){
            displayError("Error while refreshing Sphinx model");
        }
       // actionPerformed        
    }//GEN-LAST:event_MenuItemActionPerformed
    
    /** 
     * re-confirm a user action
     */
   private boolean confirmAction(String message){ 
        int response;

        response = JOptionPane.showConfirmDialog(this, message, 
                "Confirm Action",JOptionPane.OK_CANCEL_OPTION);
        if ( response == JOptionPane.OK_OPTION){
            return true;
        }
        else
            return false;
   }
    
    /**
     * Use a JFileChooser in Open mode to select files
     * to open. Then a filter for FileFilter subclass to select
     * for *.config.xml files. If a file is selected then open the file
     * and hand it over to the xml reader
     *
     * @param fc <code>JFileChooser</code>
     * @throws GUIReaderException, GUIWriterException
    **/
  private boolean openFile (JFileChooser fc) throws GUIReaderException, GUIWriterException {
      File file;
      
      // Choose files and directories
      fc.setFileSelectionMode ( JFileChooser.FILES_AND_DIRECTORIES);
      fc.setDialogTitle ("Open File");
      int result = fc.showOpenDialog (this); // Now open chooser

      if (result == JFileChooser.CANCEL_OPTION) {
          return true;
      } else if (result == JFileChooser.APPROVE_OPTION) {

          file = fc.getSelectedFile ();    
          /*System.out.println(file.getPath());*/
          _gm.action(GUIMediator.OPEN, file);

      } else {
          return false;
      }
      return true;
   } // openFile


   /**
    * Use a JFileChooser in Save mode to select files
    * to open. Then a filter for FileFilter subclass to select
    * for ".config.xml" files. If a file is selected, then 
    *  give it to xml writer for file write
    *
    * @param fc <code>JFileChooser</code> reference
    * @throws GUIReaderException, GUIWriterException
   **/
   private boolean saveFile (JFileChooser fc) throws GUIReaderException, GUIWriterException{
     File file = null;
    
     fc.setDialogTitle ("Save File"); 
     int result = fc.showSaveDialog (this); // Open chooser dialog

     if (result == JFileChooser.CANCEL_OPTION) {
         return true;
     } else if (result == JFileChooser.APPROVE_OPTION) {
         file = fc.getSelectedFile ();
        
         if ( !file.getName().toLowerCase().endsWith(".config.xml") )
         {
             file = new File(file.getPath().concat(".config.xml"));
             fc.setSelectedFile(file);
         }
         if (file.exists ()) { // file exists
             int response = JOptionPane.showConfirmDialog (null,
               "Overwrite existing file?","Confirm Overwrite",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
             if (response == JOptionPane.OK_OPTION) 
             {
                 _gm.action(GUIMediator.SAVE,file);
                 return true;
             }
             else // do not overwrite, 
                 return saveFile(fc);
         } else { // file does not exist
             _gm.action(GUIMediator.SAVE,file);
                 return true;
         }
         
     } else {
       return false;
     }
  } // saveFile

    
    /* this private method is going to one more panel into the Main frame */
   private void addTabPanel(String title, Component comp,int tabindex){
       int c = (int)(tabindex + '1');
       TabPanel.addTab(title, comp);
       TabPanel.setMnemonicAt(tabindex, c);       
   }
   
   /* this private method create and add the Global Panel */
   private void addGlobalPanel(){
        _pGlobal = new PanelGlobal("Global settings",_gm);
        addTabPanel("General",_pGlobal,0);        
   }
   
   /* helper method to delete all tab panels except the first one ( global panel )
    */
   private void deleteTabPanels(){
        // Remove the last tab
       System.out.println("*** num of tabs :" + TabPanel.getTabCount());
        while( TabPanel.getTabCount() > 1 )
        {
            TabPanel.remove(TabPanel.getTabCount()-1);
        }  
   }
       
   /** 
    * helper method to create tab panels based on the groups 
    * that are given, one TabPanel for one group
    */
   public void addTextPanels(Iterator groups){
        int tempi=1;
        for ( Iterator it = groups; it.hasNext();){            
            Map.Entry propentry = (Map.Entry)it.next();
            String grpname = (String)propentry.getKey();
            Set grp = (Set)propentry.getValue();              
            _pConfigurable = new PanelConfigurable(_gm,grpname,grp);               
            addTabPanel(grpname.substring(1+grpname.lastIndexOf('.')).toUpperCase(),
                    _pConfigurable,tempi++);       
        }
    }
    
    // inner class that is the FileFilter for choosing file types
    class ConfigFilter extends FileFilter {
        //Accept all directories and .config.xml files only        
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String fname = f.getName();
            if ( fname.toLowerCase().endsWith(".config.xml") ){
                return true; // is a valid config file
            }
            return false;
        }
        
        /**
         * @return The description of this filter
         */
        public String getDescription() {
            return "Sphinx config file";
        }
    }
    
    // private helper function to display error message to user
    private void displayError(String message) {
        JOptionPane.showMessageDialog(this,message, 
                        "Sphinx-4", JOptionPane.ERROR_MESSAGE);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar MenuBarFile;
    private javax.swing.JMenu MenuConfig;
    private javax.swing.JMenu MenuFile;
    private javax.swing.JMenuItem MenuItemExit;
    private javax.swing.JMenuItem MenuItemNew;
    private javax.swing.JMenuItem MenuItemOpen;
    private javax.swing.JMenuItem MenuItemRefresh;
    private javax.swing.JMenuItem MenuItemSave;
    private javax.swing.JMenuItem MenuItemShow;
    private javax.swing.JMenu MenuModel;
    private javax.swing.JTabbedPane TabPanel;
    private javax.swing.JDialog jDialogOutput;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextArea jTextAreaOutput;
    // End of variables declaration//GEN-END:variables
    private GUIMediator _gm;
    private PanelGlobal _pGlobal;
    private PanelConfigurable _pConfigurable;
}
