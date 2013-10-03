package com.example.myhealth;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;

public class BluetoothListen extends Thread {

	private InputStream inputStream;
	private Device device;
	private boolean running = true;
	
	public BluetoothListen(Device device, InputStream inputStream) {
		this.inputStream = inputStream;
		this.device = device;
	}
	
	@Override
	public void run() {
		byte[] buffer = new byte[1024];
		int bytes;
		
		while (running) {
			
			try {
				bytes = inputStream.read(buffer);
				
				int i = 0;
				String readMessage = new String(buffer, 0, bytes);
				System.out.println("Receive:--" + readMessage + "--");
				try {
					device.parseData(readMessage);
				} catch (NumberFormatException e) {
					//device.output.setText("Connection closed");
				} catch (JSONException e) {
					//device.output.setText("Connection closed");
				}
				
			} catch (IOException e) {
				//device.output.setText("Connection closed");
			}
		}
		
	}
	
	public void stopThread() {
		running = false;
	}
	
}
