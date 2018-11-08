package com.myself.nettychat.tcptest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class TCPTestClient {

    public static void main(String[] args) throws IOException {
        
        for (int i = 0;i<100000;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        runtest();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
            sleep(100);
        }
    }

    private static void runtest() throws IOException{
        
        Socket client = new Socket("127.0.0.1", 8092);
        client.setSoTimeout(10000);
        
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        
        PrintStream out = new PrintStream(client.getOutputStream());
        
        BufferedReader buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
        boolean flag = true;
        int i = 1;
        while(flag){
            
            
            String str = "test";
            
            out.println(str);
            if("bye".equals(str)){
                flag = false;
            }else{
                try{
                    
                    String echo = buf.readLine();
                    System.out.println(echo);
                }catch(SocketTimeoutException e){
                    System.out.println("Time out, No response");
                }
            }
            sleep(5000);
        }
        input.close();
        if(client != null){
            
            client.close(); 
        }
    }

    private static void sleep(Integer time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
