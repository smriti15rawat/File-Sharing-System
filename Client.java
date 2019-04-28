package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket sr=new Socket("localhost", 6578);
			String dir= "D";
			Scanner s=new Scanner(System.in);
			int i;
			byte b[]=new byte[4096];
			InputStream is=sr.getInputStream();
			DataInputStream din=new DataInputStream(is);
			DataOutputStream dout=new DataOutputStream(sr.getOutputStream());
			int j=din.readInt();
			String []f=new String[j];
			for( i=0;i<j;i++) {
				f[i]=din.readUTF();
				System.out.println(i+"for"+f[i]);	
			}
			System.out.println("enter file no to download it");
			int num=s.nextInt();
			dout.writeUTF(f[num]);
			FileOutputStream fo=new FileOutputStream(dir+":\\"+f[num]);
			int t;
			while((t=is.read(b))!=-1) {
			fo.write(b,0,t);
			fo.flush();
			}
			din.close();
			dout.close();
			fo.close();
			sr.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
