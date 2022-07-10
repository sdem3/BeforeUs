package com.example.beforeus.Models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ServerConnect
{
    public static byte[] ServerRequest(String request) {
        try {
            Socket socket = new Socket("192.168.0.10", 1234);
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inStream = new DataInputStream(socket.getInputStream());
            outStream.writeUTF(request);
            outStream.flush();
            byte[] array = new byte[225000];
            int arrayPointer = 0;
            byte[] buffer = new byte[2048];
            int readCount;
            while ((readCount = inStream.read(buffer)) != -1) {
                System.arraycopy(buffer, 0, array, arrayPointer, readCount);
                arrayPointer += readCount;
            }
            socket.close();
            return array;
        } catch (Exception ex) {
            return null;
        }
    }
}
