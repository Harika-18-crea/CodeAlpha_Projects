import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame{

Stock tcs=new Stock("TCS",3500);
Stock infosys=new Stock("Infosys",1500);
Stock wipro=new Stock("Wipro",450);

User user=new User("Harika",50000);

Portfolio portfolio=new Portfolio();

JComboBox<String> stockBox;
JTextField quantityField;
JLabel balanceLabel;
JTable table;
DefaultTableModel model;
JButton buyButton,sellButton,reportButton;

public Main(){

setTitle("Stock Trading Platform");
setSize(900,600);
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLayout(new BorderLayout());

JPanel titlePanel=new JPanel();
titlePanel.setBackground(Color.BLACK);

JLabel title=new JLabel("STOCK TRADING PLATFORM");
title.setForeground(Color.WHITE);
title.setFont(new Font("Arial",Font.BOLD,30));

titlePanel.add(title);

add(titlePanel,BorderLayout.NORTH);

JPanel leftPanel=new JPanel();

leftPanel.setLayout(new GridLayout(10,1,10,10));
leftPanel.setBorder(new EmptyBorder(20,20,20,20));
leftPanel.setBackground(new Color(240,240,240));

JLabel stockLabel=new JLabel("Select Stock:");
stockLabel.setFont(new Font("Arial",Font.BOLD,16));

stockBox=new JComboBox<>();

stockBox.addItem("TCS - ₹3500");
stockBox.addItem("Infosys - ₹1500");
stockBox.addItem("Wipro - ₹450");

JLabel quantityLabel=new JLabel("Enter Quantity:");
quantityLabel.setFont(new Font("Arial",Font.BOLD,16));

quantityField=new JTextField();

buyButton=createButton("BUY STOCK");
sellButton=createButton("SELL STOCK");
reportButton=createButton("VIEW REPORT");

leftPanel.add(stockLabel);
leftPanel.add(stockBox);
leftPanel.add(quantityLabel);
leftPanel.add(quantityField);
leftPanel.add(buyButton);
leftPanel.add(sellButton);
leftPanel.add(reportButton);

add(leftPanel,BorderLayout.WEST);

model=new DefaultTableModel();

model.addColumn("Stock Name");
model.addColumn("Quantity");

table=new JTable(model);

table.setRowHeight(30);
table.setFont(new Font("Arial",Font.PLAIN,15));

table.getTableHeader().setFont(new Font("Arial",Font.BOLD,16));
table.getTableHeader().setBackground(Color.BLACK);
table.getTableHeader().setForeground(Color.BLACK);

JScrollPane scrollPane=new JScrollPane(table);

add(scrollPane,BorderLayout.CENTER);

JPanel bottomPanel=new JPanel();

bottomPanel.setLayout(new GridLayout(1,1));
bottomPanel.setBorder(new EmptyBorder(20,20,20,20));

balanceLabel=createInfoLabel("Balance: ₹"+user.getBalance());

bottomPanel.add(balanceLabel);

add(bottomPanel,BorderLayout.SOUTH);

buyButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e){
try{
int qty=Integer.parseInt(quantityField.getText());
Stock stock=getSelectedStock();
boolean success=Transaction.buy(user,stock,qty,portfolio);
if(success){
updateTable();
balanceLabel.setText("Balance: ₹"+user.getBalance());
JOptionPane.showMessageDialog(null,"Stock Purchased");
}else{
JOptionPane.showMessageDialog(null,"Insufficient Balance");
}
}catch(Exception ex){
JOptionPane.showMessageDialog(null,"Enter valid quantity");
}
}
});

sellButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e){
try{
int qty=Integer.parseInt(quantityField.getText());
Stock stock=getSelectedStock();
Transaction.sell(user,stock,qty,portfolio);
updateTable();
balanceLabel.setText("Balance: ₹"+user.getBalance());
JOptionPane.showMessageDialog(null,"Stock Sold");
}catch(Exception ex){
JOptionPane.showMessageDialog(null,"Enter valid quantity");
}
}
});

reportButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e){
String report="===== PORTFOLIO =====\n\n";
for(String stock:portfolio.getHoldings().keySet()){
report+=stock+" : "+portfolio.getHoldings().get(stock)+" shares\n";
}
report+="\nBalance: ₹"+user.getBalance();
JOptionPane.showMessageDialog(null,report);
}
});

setVisible(true);
}

Stock getSelectedStock(){
int index=stockBox.getSelectedIndex();
if(index==0)
return tcs;
else if(index==1)
return infosys;
else
return wipro;
}

void updateTable(){
model.setRowCount(0);
for(String stock:portfolio.getHoldings().keySet()){
model.addRow(new Object[]{stock,portfolio.getHoldings().get(stock)});
}
}

JButton createButton(String text){
JButton button=new JButton(text);
button.setBackground(Color.BLACK);
button.setForeground(Color.BLACK);
button.setFont(new Font("Arial",Font.BOLD,15));
button.setFocusPainted(false);
return button;
}

JLabel createInfoLabel(String text){
JLabel label=new JLabel(text,SwingConstants.CENTER);
label.setOpaque(true);
label.setBackground(new Color(220,220,220));
label.setFont(new Font("Arial",Font.BOLD,16));
label.setBorder(new LineBorder(Color.BLACK,2));
return label;
}

public static void main(String[] args){
try{
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
}catch(Exception e){
e.printStackTrace();
}
new Main();
}
}