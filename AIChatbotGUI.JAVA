import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class AIChatbotGUI extends JFrame{

JTextArea chatArea;
JTextField inputField;
JButton sendButton;

HashMap<String,String> responses=new HashMap<>();

public AIChatbotGUI(){

setTitle("AI Chatbot");
setSize(600,500);
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLayout(new BorderLayout());

responses.put("hello","Hello! How can I help you?");
responses.put("hi","Hi there!");
responses.put("how are you","I am fine. Humans are still confusing.");
responses.put("what is java","Java is a programming language.");
responses.put("what is ai","AI means Artificial Intelligence.");
responses.put("bye","Goodbye!");

JPanel titlePanel=new JPanel();
titlePanel.setBackground(Color.BLACK);

JLabel title=new JLabel("ARTIFICIAL INTELLIGENCE CHATBOT");
title.setForeground(Color.WHITE);
title.setFont(new Font("Arial",Font.BOLD,24));

titlePanel.add(title);

add(titlePanel,BorderLayout.NORTH);

chatArea=new JTextArea();

chatArea.setFont(new Font("Arial",Font.PLAIN,16));
chatArea.setEditable(false);
chatArea.setBackground(new Color(240,240,240));

JScrollPane scrollPane=new JScrollPane(chatArea);

add(scrollPane,BorderLayout.CENTER);

JPanel bottomPanel=new JPanel(new BorderLayout());

inputField=new JTextField();
inputField.setFont(new Font("Arial",Font.PLAIN,16));

sendButton=new JButton("SEND");

sendButton.setBackground(Color.BLACK);
sendButton.setForeground(Color.BLACK);
sendButton.setFont(new Font("Arial",Font.BOLD,16));

bottomPanel.add(inputField,BorderLayout.CENTER);
bottomPanel.add(sendButton,BorderLayout.EAST);

add(bottomPanel,BorderLayout.SOUTH);

sendButton.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent e){

sendMessage();
}
});

inputField.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent e){

sendMessage();
}
});

setVisible(true);
}

void sendMessage(){

String userInput=inputField.getText().toLowerCase().trim();

if(userInput.isEmpty()){
return;
}

chatArea.append("You: "+userInput+"\n");

String response=getResponse(userInput);

chatArea.append("Bot: "+response+"\n\n");

inputField.setText("");
}

String getResponse(String input){

for(String key:responses.keySet()){

if(input.contains(key)){
return responses.get(key);
}
}

if(input.contains("time")){
return "I cannot check real time yet.";
}

if(input.contains("name")){
return "I am your AI Chatbot.";
}

if(input.contains("help")){
return "I can answer basic questions.";
}

return "Sorry, I do not understand.";
}

public static void main(String[] args){

try{

UIManager.setLookAndFeel(
UIManager.getSystemLookAndFeelClassName());

}catch(Exception e){

e.printStackTrace();
}

new AIChatbotGUI();
}
}