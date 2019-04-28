package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Servergui extends JFrame implements ActionListener,MouseListener {
	JButton b;
	JTextArea ar;
	Servergui( String s){super(s);}
	public void setComponent() {
		b=new JButton("START SERVER");
		ar=new JTextArea();
		setLayout(null);
		b.setBounds(200,10,150,20);
		ar.setBounds(170,40,200,200);
		add(b);
		add(ar);
		b.addMouseListener(this);
		b.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent ai) {
		if(ai.getSource()==b) {
			ser();
		}
		
	}
	public static void main(String []args) {
		Servergui sr=new Servergui("Server");
		sr.setComponent();
		sr.setSize(500,500);
		sr.setVisible(true);
		
	}
	public void ser() {
		try {
			ServerSocket ss=new ServerSocket(6578);
			while(true) {
				System.out.println("waiting for client");
				new Thread(new clients(ss.accept())).start();
				 
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent ai) {
		if(ai.getSource()==b) {
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		ar.setText("SERVER RUNNING");
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

class clients implements Runnable{
	Socket sr;
	String dir="C:\\Users\\akkii\\Desktop\\my";
	DataOutputStream ds;
	DataInputStream din;
	OutputStream os;
public clients(Socket e) {
	
	try {
		sr=e;
		os = sr.getOutputStream();
		ds=new DataOutputStream(os);
		din=new DataInputStream(sr.getInputStream());
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	 
}

@Override
public void run() {
	try {
		File []files=new File(dir).listFiles();
		 ds.writeInt(files.length);
		 for(File f:files) {
			 System.out.println(f);
			 ds.writeUTF(f.getName());
		 }
		 String s=din.readUTF();
		 if(s.equals("akib")) {
				
			}
			 else {
				 System.out.println(s);
					FileInputStream fr=new FileInputStream(dir+"\\"+s);
					byte[] b=new byte[4096];
					int t;
					while((t=fr.read(b))!=-1) {
					ds.write(b,0,t);
					ds.flush();
					}
					fr.close();
			 }
			ds.close();
			din.close();
			os.close();
			ds.close();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
}

}