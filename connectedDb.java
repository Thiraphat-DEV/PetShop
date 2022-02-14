import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.event.MouseInputAdapter;

public class connectedDb extends JFrame {
  // start attributes
  private JLabel lUserName = new JLabel();
  private JLabel jLabel1 = new JLabel();
  private JTextField tfname = new JTextField();
  private JLabel jLabel2 = new JLabel();
  private JTextField tfprice = new JTextField();
  private JLabel jLabel3 = new JLabel();
  private JTextField tfcount = new JTextField();
  private JLabel jLabel4 = new JLabel();
  private JNumberField nfcustomer = new JNumberField();
  private JButton btnsave = new JButton();
  private JButton btnclose = new JButton();
  private JTextField tfid = new JTextField();
  private JTable jTable1 = new JTable(0, 4);
  private DefaultTableModel jTable1Model = (DefaultTableModel) jTable1.getModel();
  private JScrollPane jTable1ScrollPane = new JScrollPane(jTable1);
  private JButton btnsearch = new JButton();
  private JNumberField jcustomer = new JNumberField();
  private JLabel jLabel5 = new JLabel();
  private JButton btncal = new JButton();
  private JButton btnupdate = new JButton();
  private JButton btndelete = new JButton();
  private JComboBox<String> joption = new JComboBox<String>();
    private DefaultComboBoxModel<String> joptionModel = new DefaultComboBoxModel<String>();
  private JNumberField nfSumprice = new JNumberField();
  private JLabel jLabel6 = new JLabel();
  // end attributes
  private Connection con = null;  // connect to database
  private Statement state = null; // state from input 
  private ResultSet rs = null; // get state
  private PreparedStatement ps;  
  private String driver =  "com.mysql.cj.jdbc.Driver";//init driver

  private String dbURL = "jdbc:mysql://localhost/petshop"; 
  
  
  public connectedDb() { 
    // Frame-Init
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 861; 
    int frameHeight = 508;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("connectedDb");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    // start components
    try {
      Class.forName(driver);
    } catch(Exception e) {
      System.out.println("Driver is undefined" + e.getMessage());
    }  // end of try
    
    lUserName.setBounds(4, 8, 95, 36);
    lUserName.setText("ProductID");
    lUserName.setHorizontalTextPosition(SwingConstants.CENTER);
    lUserName.setOpaque(false);
    cp.add(lUserName);
    jLabel1.setBounds(0, 56, 97, 33);
    jLabel1.setText("ProductName");
    cp.add(jLabel1);
    tfname.setBounds(104, 56, 193, 41);
    cp.add(tfname);
    jLabel2.setBounds(0, 144, 100, 33);
    jLabel2.setText("CountofStock");
    cp.add(jLabel2);
    tfprice.setBounds(104, 104, 193, 33);
    cp.add(tfprice);
    jLabel3.setBounds(0, 104, 97, 33);
    jLabel3.setText("Price(10kg./BATH)");
    cp.add(jLabel3);
    tfcount.setBounds(104, 144, 193, 33);
    cp.add(tfcount);
    jLabel4.setBounds(0, 296, 108, 25);
    jLabel4.setText("MoneyOfChange");
    cp.add(jLabel4);
    nfcustomer.setBounds(120, 296, 105, 33);
    nfcustomer.setText("");
    cp.add(nfcustomer);
    btnsave.setBounds(16, 344, 97, 41);
    btnsave.setText("SaveData");
    btnsave.setMargin(new Insets(2, 2, 2, 2));
    btnsave.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btnsave_ActionPerformed(evt);
      }
    });
    cp.add(btnsave);
    btnclose.setBounds(472, 344, 105, 41);
    btnclose.setText("close");
    btnclose.setMargin(new Insets(2, 2, 2, 2));
    btnclose.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btnclose_ActionPerformed(evt);
      }
    });
    cp.add(btnclose);
    tfid.setBounds(104, 8, 193, 41);
    cp.add(tfid);
    jTable1ScrollPane.setBounds(320, 8, 393, 185);
    jTable1.getColumnModel().getColumn(0).setHeaderValue("productID");
    jTable1.getColumnModel().getColumn(1).setHeaderValue("productName");
    jTable1.getColumnModel().getColumn(2).setHeaderValue("productPrice");
    jTable1.getColumnModel().getColumn(3).setHeaderValue("productCount");
    jTable1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
    // add Jtable and edit column Table
    try { 
     Class.forName(driver);    
      con = DriverManager.getConnection(dbURL, "root", "");// connect to localhost 
      state = con.createStatement();// create State with showState from server
      rs = state.executeQuery("SELECT * FROM productpet");
      while(rs.next()) {
      jTable1Model.addRow(new Object[]{
        rs.getString("productID"),
        rs.getString("productName"),
        rs.getString("productPrice"),
        rs.getString("productCount"),
        });
      }     
    } catch(Exception e) {
       System.out.println("Error of ShowData" + e.toString());
    } // end of try
    cp.add(jTable1ScrollPane);
    btnsearch.setBounds(336, 344, 121, 41);
    btnsearch.setText("SearchData");
    btnsearch.setMargin(new Insets(2, 2, 2, 2));
    btnsearch.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btnsearch_ActionPerformed(evt);
      }
    });
    cp.add(btnsearch);
    jcustomer.setBounds(128, 184, 105, 33);
    jcustomer.setText("");
    cp.add(jcustomer);
    jLabel5.setBounds(0, 184, 113, 41);
    jLabel5.setText("MoneyCustomer");
    cp.add(jLabel5);
    btncal.setBounds(232, 296, 97, 33);
    btncal.setText("Calculated");
    btncal.setMargin(new Insets(2, 2, 2, 2));
    btncal.addMouseListener(new MouseInputAdapter() { 
      public void mouseClicked(MouseEvent evt) { 
        btncal_mouseClicked(evt);
      }
    });
    cp.add(btncal);
    btnupdate.setBounds(224, 344, 97, 41);
    btnupdate.setText("Update");
    btnupdate.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent evt) {
          btnupdate_ActionPerformed(evt);
         }
      });
    cp.add(btnupdate);
    btndelete.setBounds(122, 347, 91, 41);
    btndelete.setText("DELETE");
    btndelete.addActionListener(new ActionListener() {;
        public void actionPerformed(ActionEvent evt) {
               btndelete_ActionPerformed(evt);
          }
      });
    cp.add(btndelete);
    joption.setModel(joptionModel);
    joption.setBounds(424, 200, 129, 49);
    cp.add(joption);
    nfSumprice.setBounds(120, 240, 105, 41);
    nfSumprice.setText("");
    
    cp.add(nfSumprice);

    jLabel6.setBounds(0, 238, 110, 44);
    jLabel6.setText("SumOfProduct");
    cp.add(jLabel6);
    // end components
    productOption(); //show option of PetFood
    setVisible(true);
  } // end of public connectedDb
  
  // start methods
  
  public static void main(String[] args) {
    new connectedDb();
  } // end of main

  public void btnsave_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    try{   
      state  = con.createStatement();
      ps = con.prepareStatement("INSERT INTO productpet VALUES(?, ?, ?, ?)"); //insert data to mysql
      //values
      ps.setString(1, tfid.getText());
      ps.setString(2, tfname.getText());
      ps.setString(3, tfprice.getText());
      ps.setString(4, tfcount.getText());
      ps.executeUpdate();// run sql
      JOptionPane.showMessageDialog(this, "Insert Successed");
      //close connection and statement
      ps.close();
      state.close();
      con.close();
      tfid.setText("");
      tfname.setText("");
      tfprice.setText("");
      tfcount.setText("");
      jcustomer.setText("");
      nfcustomer.setText("");
      nfSumprice.setText("");
      tfid.requestFocus(); // focus of textfieldID
    }catch (Exception e) {
      System.out.println("Error Connect to mysql" + e.getMessage());
    }
  } // end of btnsave_ActionPerformed
  public void productOption() {
    try {
      ps = con.prepareStatement("SELECT productName FROM productpet");
      rs = ps.executeQuery();
      joption.removeAllItems();
      
      while(rs.next()) {
         joption.addItem(rs.getString(1));
        }
    } catch(Exception e) {
        System.out.println(e.getMessage());
    } // end of try
  }
  public void btnclose_ActionPerformed(ActionEvent evt) {
    // close of form
    System.exit(0);
  } // end of btnclose_ActionPerformed

  public void btnsearch_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    String pName = joption.getSelectedItem().toString();
    
    try {
      ps = con.prepareStatement("SELECT * FROM productpet WHERE productName = ?");
      ps.setString(1, pName);
      rs = ps.executeQuery();
      
      //check option  and setOption 
      if(rs.next()) {
        tfid.setText(rs.getString(1));
        tfname.setText(rs.getString(2));
        tfprice.setText(rs.getString(3));
        tfcount.setText(rs.getString(4));
        
        }
    } catch(Exception e) {
      System.out.println("Error of Fetch" +  e.getMessage());
    }  // end of try
  } // end of btnsearch_ActionPerformed

  public void btncal_mouseClicked(MouseEvent evt) {
    // TODO add your code here
    try { 
    //sumofPrice price * count
    double sumPrice = Double.parseDouble(tfprice.getText()) * Double.parseDouble(tfcount.getText());
  
    
    if(jcustomer.getInt() < 0) {
        JOptionPane.showMessageDialog(this, "Please EnterOfYourMoney");  
    }else {
       double sumChange = Double.parseDouble(jcustomer.getText()) - sumPrice;
       nfcustomer.setDouble(sumChange);
       nfSumprice.setDouble(sumPrice);   
    } // end of if-else

     
       
      
    } catch(NumberFormatException e) {
      System.out.println("Error of calculate" +  e.getMessage());
    } // end of try
  } // end of btncal_ActionPerformed
  // delete data
  public void btndelete_ActionPerformed(ActionEvent evt) {
     try {
      ps = con.prepareStatement("DELETE FROM productpet WHERE productName = ?");
      ps.setString(1, tfname.getText());
      //get command
      String str = evt.getActionCommand(); 
      int rowOfdelete = ps.executeUpdate(); // get command is compared to String
      
      //check row is not null
      if(rowOfdelete > 0 && str.equals("DELETE")) { //true is 2 option 
        System.out.println("Delete Data Successed");
        JOptionPane.showMessageDialog(this, "Delete Data Successed");
        joption.removeItem(tfname.getText());
        tfname.setText("");
        tfprice.setText("");
        tfcount.setText("");
        tfid.setText("");
        jcustomer.setText("");
        nfcustomer.setText("");
        nfSumprice.setText("");
        tfid.requestFocus();
      }
     } catch(Exception e) {
       System.out.println("Error of DeleteData" + e.getMessage());
     } //end of try    
    
    }
  public void btnupdate_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
     String pid = joption.getSelectedItem().toString();
    
    try {
     ps = con.prepareStatement("UPDATE productpet SET productName = ? , productPrice = ? , productCount = ?  WHERE productID = ?");
     ps.setString(1, tfname.getText());
     ps.setString(2, tfprice.getText());
     ps.setString(3, tfcount.getText());
     ps.setString(4, tfid.getText());
     
     int result = ps.executeUpdate();
     
     if(result == 1) {  // after result is updated setTextfield is emptyString 
        System.out.println("Update Record to Successed");
        JOptionPane.showMessageDialog(this, "Update Successed");
        tfname.setText("");
        tfprice.setText("");
        tfcount.setText("");
        tfid.setText("");
        jcustomer.setText("");
        nfcustomer.setText("");
        nfSumprice.setText("");
        tfid.requestFocus();
       }
    } catch(Exception e) {
       System.out.println(e.getMessage());
    }
  } // end of btnupdate_StateChanged

  // end methods
} // end of class connectedDb
