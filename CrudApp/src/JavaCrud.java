import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class JavaCrud {

    private JPanel Main;
    private JTextField txtName;
    private JTextField txtPrice;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton searchButton;
    private JTextField txtQty;
    private JTextField txtpid;

    public static void main(String[] args) {
        JFrame frame = new JFrame("JavaCrud");
        frame.setContentPane(new JavaCrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;
    public JavaCrud() {
        Connect();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name,price,qty;
                name=txtName.getText();
                price=txtPrice.getText();
                qty=txtQty.getText();
                try {
                    pst=con.prepareStatement("insert into products(pname,price,qty)values(?,?,?)");
                    pst.setString(1,name);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Add Successfully");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String pid=txtpid.getText();
                    pst= con.prepareStatement("select pname,price,qty from products where pid=?");
                    pst.setString(1,pid);
                    ResultSet rs=pst.executeQuery();

                    if (rs.next()==true){
                        String name=rs.getString(1);
                        String price=rs.getString(2);
                        String qty=rs.getString(3);

                        txtName.setText(name);
                        txtPrice.setText(price);
                        txtQty.setText(qty);
                    }
                    else {
                        txtName.setText("");
                        txtPrice.setText("");
                        txtQty.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Products ID");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name,price,qty,pid;
                name=txtName.getText();
                price=txtPrice.getText();
                qty=txtQty.getText();
                pid=txtpid.getText();
                try {
                    pst=con.prepareStatement("update products set pname=?,price=?,qty=? where pid=?");
                    pst.setString(1,name);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.setString(4,pid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Products Updated");
                    txtName.setText(name);
                    txtPrice.setText(price);
                    txtQty.setText(qty);
                    txtpid.setText(pid);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid;
                pid=txtpid.getText();
                try {
                    pst=con.prepareStatement("delete from products where pid=?");
                    pst.setString(1,pid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Product Deleted");
                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtpid.setText("");
                    txtName.requestFocus();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }




    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
    public void Connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/manageproducts","root","");
            System.out.println("Connected");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
