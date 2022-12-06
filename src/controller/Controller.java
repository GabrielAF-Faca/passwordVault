
package controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.User;
import model.UserDAO;
import view.Menu;

/**
 *
 * @author Gabriel Azenha Fachim
 */

public class Controller {
    
    private final Menu menu;

    private final UserDAO dao;
    
    private int last_id;
    
    public Controller(Menu menu) {
        this.menu = menu;
        
        this.dao = new UserDAO();
    }
    
    public void initController() {
        updateTable();
        menu.setVisible(true);
        
        menu.getjButtonAdd().addActionListener(e -> gotoAdd());
        menu.getjButtonDelete().addActionListener(e -> deleteUser());
        menu.getjButtonUpdate().addActionListener(e -> gotoUpdate());
        
        menu.getjButtonCancel().addActionListener(e -> menu.updateButtons(false));
        menu.getjButtonAddUpdate().addActionListener(e -> addUpdate());
        
    }
    
    
    private void gotoAdd() {
        menu.updateButtons(true);
        menu.getjButtonAddUpdate().setText("Add");
    }
    
    private void gotoUpdate() {
        menu.updateButtons(true);
        menu.getjButtonAddUpdate().setText("Update");
        
        int row = menu.getjTableList().getSelectedRow();
        
        for (int i = 1; i < menu.getjTableList().getColumnCount(); i++) {
            menu.getTextFields().get(i-1).setText(menu.getjTableList().getValueAt(row, i)+"");
        }
        
        last_id = Integer.parseInt(menu.getjTableList().getValueAt(row, 0)+"");
        
    }
    
    private void deleteUser() {
        
        int row = menu.getjTableList().getSelectedRow();
        
        int id = Integer.parseInt(menu.getjTableList().getValueAt(row, 0)+"");
        
        String[] opcoes = {"No", "Yes"};
        
        int x = JOptionPane.showOptionDialog(null, "Are you sure you want to delete?",
                "ATTENTION",JOptionPane.DEFAULT_OPTION, 
                JOptionPane.WARNING_MESSAGE, null, opcoes, opcoes[0]);
        
        switch (x) {
            
            case 1 -> {
                dao.deleteUser(id);
            }
        }
        
        menu.updateButtons(false);
        updateTable();
        
        
    }
    
    private void addUpdate() {
        
        switch (menu.getjButtonAddUpdate().getText()) {
            case "Add" -> {
                
                addUser();
                
            }
            
            case "Update" -> {
                
            try {
                updateUser();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            }
        }
        
        updateTable();
        menu.updateButtons(false);
    }
    

    
    private void updateUser() throws UnsupportedEncodingException {
        User u;
        
        ArrayList<JTextField> fields = menu.getTextFields();
        
        int id = last_id;
        String site = fields.get(0).getText();
        String name = fields.get(1).getText();
        String email = fields.get(2).getText();
        String password = fields.get(3).getText();
        
        u = new User(id, site, name, email, password);
        
        dao.updateUser(u, site, name, email, password);
        
    }
    
    private void addUser() {
        
        ArrayList<String> fields = new ArrayList<>();
        
        for (JTextField field : menu.getTextFields()) {
            fields.add(field.getText());
        }
        
        try {
            dao.insertUser(new User(fields.get(
                    0),
                    fields.get(1),
                    fields.get(2),
                    fields.get(3)));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void updateTable() {
        ArrayList<User> users_list = dao.getUsers();

        DefaultTableModel model = ((DefaultTableModel) menu.getjTableList().getModel());
        
        model.setRowCount(0);
        
        for (var user : users_list) {
            model.addRow( new String[] {
                          user.getId()+"",
                          user.getSite(),
                          user.getName(),
                          user.getEmail(),
                          user.getPassword()
                } );
        }
        
    }
    
}
