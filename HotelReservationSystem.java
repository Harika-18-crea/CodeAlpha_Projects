import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

class Booking{
String customerName;
String roomType;
int days;
double amount;

Booking(String customerName,String roomType,int days,double amount){
this.customerName=customerName;
this.roomType=roomType;
this.days=days;
this.amount=amount;
}
}

public class HotelReservationSystem extends JFrame{

JTextField nameField,daysField;
JComboBox<String> roomBox;
JButton bookButton,cancelButton,viewButton;
JTable table;
DefaultTableModel model;

ArrayList<Booking> bookings=new ArrayList<>();

String fileName="bookings.txt";

public HotelReservationSystem(){

setTitle("Hotel Reservation System");
setSize(900,600);
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLayout(new BorderLayout());

JPanel titlePanel=new JPanel();
titlePanel.setBackground(Color.BLACK);

JLabel title=new JLabel("HOTEL RESERVATION SYSTEM");

title.setForeground(Color.WHITE);
title.setFont(new Font("Arial",Font.BOLD,28));

titlePanel.add(title);

add(titlePanel,BorderLayout.NORTH);

JPanel leftPanel=new JPanel();

leftPanel.setLayout(new GridLayout(10,1,10,10));

leftPanel.setBorder(new EmptyBorder(20,20,20,20));

leftPanel.setBackground(new Color(240,240,240));

JLabel nameLabel=new JLabel("Customer Name:");
nameLabel.setFont(new Font("Arial",Font.BOLD,16));

nameField=new JTextField();

JLabel roomLabel=new JLabel("Select Room:");
roomLabel.setFont(new Font("Arial",Font.BOLD,16));

roomBox=new JComboBox<>();

roomBox.addItem("Standard - ₹2000");
roomBox.addItem("Deluxe - ₹4000");
roomBox.addItem("Suite - ₹7000");

JLabel daysLabel=new JLabel("Number of Days:");
daysLabel.setFont(new Font("Arial",Font.BOLD,16));

daysField=new JTextField();

bookButton=createButton("BOOK ROOM");
cancelButton=createButton("CANCEL BOOKING");
viewButton=createButton("VIEW BOOKINGS");

leftPanel.add(nameLabel);
leftPanel.add(nameField);
leftPanel.add(roomLabel);
leftPanel.add(roomBox);
leftPanel.add(daysLabel);
leftPanel.add(daysField);
leftPanel.add(bookButton);
leftPanel.add(cancelButton);
leftPanel.add(viewButton);

add(leftPanel,BorderLayout.WEST);

model=new DefaultTableModel();

model.addColumn("Customer Name");
model.addColumn("Room Type");
model.addColumn("Days");
model.addColumn("Amount");

table=new JTable(model);

table.setRowHeight(30);

table.setFont(new Font("Arial",Font.PLAIN,15));

table.getTableHeader().setFont(new Font("Arial",Font.BOLD,16));

table.getTableHeader().setBackground(Color.BLACK);

table.getTableHeader().setForeground(Color.BLACK);

JScrollPane scrollPane=new JScrollPane(table);

add(scrollPane,BorderLayout.CENTER);

loadBookings();

bookButton.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent e){

try{

String name=nameField.getText();

int days=Integer.parseInt(daysField.getText());

String roomType=getRoomType();

double price=getRoomPrice();

double amount=price*days;

Booking booking=new Booking(name,roomType,days,amount);

bookings.add(booking);

model.addRow(new Object[]{
name,
roomType,
days,
amount
});

saveBookings();

JOptionPane.showMessageDialog(null,"Room Booked Successfully\nPayment: ₹"+amount);

nameField.setText("");
daysField.setText("");

}catch(Exception ex){

JOptionPane.showMessageDialog(null,"Enter valid details");
}
}
});

cancelButton.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent e){

int row=table.getSelectedRow();

if(row!=-1){

bookings.remove(row);

model.removeRow(row);

saveBookings();

JOptionPane.showMessageDialog(null,"Booking Cancelled");

}else{

JOptionPane.showMessageDialog(null,"Select booking to cancel");
}
}
});

viewButton.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent e){

String details="===== BOOKING DETAILS =====\n\n";

for(Booking b:bookings){

details+="Name: "+b.customerName+"\n";
details+="Room: "+b.roomType+"\n";
details+="Days: "+b.days+"\n";
details+="Amount: ₹"+b.amount+"\n\n";
}

JOptionPane.showMessageDialog(null,details);
}
});

setVisible(true);
}

String getRoomType(){

int index=roomBox.getSelectedIndex();

if(index==0)
return "Standard";

else if(index==1)
return "Deluxe";

else
return "Suite";
}

double getRoomPrice(){

int index=roomBox.getSelectedIndex();

if(index==0)
return 2000;

else if(index==1)
return 4000;

else
return 7000;
}

void saveBookings(){

try{

BufferedWriter writer=
new BufferedWriter(
new FileWriter(fileName));

for(Booking b:bookings){

writer.write(
b.customerName+","
+b.roomType+","
+b.days+","
+b.amount);

writer.newLine();
}

writer.close();

}catch(Exception e){

e.printStackTrace();
}
}

void loadBookings(){

try{

BufferedReader reader=
new BufferedReader(
new FileReader(fileName));

String line;

while((line=reader.readLine())!=null){

String data[]=line.split(",");

Booking booking=
new Booking(
data[0],
data[1],
Integer.parseInt(data[2]),
Double.parseDouble(data[3]));

bookings.add(booking);

model.addRow(new Object[]{
booking.customerName,
booking.roomType,
booking.days,
booking.amount
});
}

reader.close();

}catch(Exception e){

System.out.println("No previous bookings");
}
}

JButton createButton(String text){

JButton button=new JButton(text);

button.setBackground(Color.BLACK);

button.setForeground(Color.BLACK);

button.setFont(new Font("Arial",Font.BOLD,15));

button.setFocusPainted(false);

button.setCursor(new Cursor(Cursor.HAND_CURSOR));

return button;
}

public static void main(String[] args){

try{

UIManager.setLookAndFeel(
UIManager.getSystemLookAndFeelClassName());

}catch(Exception e){

e.printStackTrace();
}

new HotelReservationSystem();
}
}