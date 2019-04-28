package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ClientGui extends JFrame implements ActionListener {
	JComboBox<String> c;
	JButton b,b1;
	JTextField f1,f2;
	JLabel l1,l2;
	ClientGui( String s){super(s);}
	public void setComponents() {
		c=new JComboBox<String>();
		c.setVisible(false);
		f1=new JTextField();
		f2=new JTextField();
		b=new JButton("Connect");
		b1=new JButton("DOWNLOAD");
		l1=new JLabel("enter ip:");
		l2=new JLabel("enter port:");
		b.addActionListener(this);
		b1.addActionListener(this);
		setLayout(null);
		add(c);
		add(b);
		add(b1);
		add(f1);
		add(f2);
		add(l1);
		add(l2);
		l1.setBounds(5,19,50,20);
		f1.setBounds(70,20,150,20);
		l2.setBounds(5,45,80,20);
		f2.setBounds(70,40,150,20);
		b.setBounds(40,70, 100,15);
		c.setBounds(50,100,100,20);
		b1.setBounds(50,140,200,20);
		b1.setEnabled(false);
	}

	public static void main(String[] args) {
		ClientGui r=new ClientGui("CLIENT");
		r.setComponents();
		r.setVisible(true);
		r.setSize(400,400);
	}

	@Override
	public void actionPerformed(ActionEvent ai) {
		if(ai.getSource()==b) {
			event e=new event();
			String s[]=e.fun(1);
			for(String i:s) {
				c.addItem(i);
			}
			c.setVisible(true);
			b1.setEnabled(true);
		}
		if(ai.getSource()==b1) {
			try {
				String host=f1.getText();
				int port=Integer.parseInt(f2.getText());
				Socket sr=new Socket(host, port);
				String dir= "D";
				int i;
				byte b[]=new byte[4096];
				DataInputStream din=new DataInputStream(sr.getInputStream());
				DataOutputStream dout=new DataOutputStream(sr.getOutputStream());
				int j=din.readInt();
				String []f=new String[j];
				for( i=0;i<j;i++) {
					f[i]=din.readUTF();
				}
				int num=c.getSelectedIndex();
				dout.writeUTF(f[num]);
				FileOutputStream fo=new FileOutputStream(dir+":\\"+f[num]);
				int t;
				while((t=din.read(b))!=-1) {
				fo.write(b,0,t);
				fo.flush();
				}
				din.close();
				dout.close();
				fo.close();
				sr.close();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
class event{
	public String[] fun( int k) {
		String []f=null;
	try {
		Socket sr=new Socket("localhost", 6578);
		int i;
		DataInputStream din=new DataInputStream(sr.getInputStream());
		DataOutputStream dout=new DataOutputStream(sr.getOutputStream());
		int j=din.readInt();
		f=new String[j];
		for( i=0;i<j;i++) {
			f[i]=din.readUTF();
		}
		dout.writeUTF("akib");
		din.close();
		dout.close();
		sr.close();
	} catch (UnknownHostException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return f; 
}
	
}
