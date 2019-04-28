package server;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
public class Server {
	public static void main(String []args) {
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
			 System.out.println(s);
			FileInputStream fr=new FileInputStream(dir+"\\"+s);
			byte[] b=new byte[4096];
			int t;
			while((t=fr.read(b))!=-1) {
			ds.write(b,0,t);
			ds.flush();
			}
			ds.close();
			din.close();
			os.close();
			ds.close();
			fr.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
}
