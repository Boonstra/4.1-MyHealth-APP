package com.example.myhealth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.widget.Toast;

public class Bluetooth extends Activity {

	private static final int REQUEST_ENABLE_BT = 1;
	private BluetoothAdapter btAdapter = null;
	private BluetoothServerSocket btSocket = null;
	private InputStream inputStream = null;

	private static final UUID MY_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805f9b34fb");

	public Bluetooth() {

		btAdapter = BluetoothAdapter.getDefaultAdapter();

	}

	public InputStream setupConnection() {
		
		try {
			btSocket = btAdapter.listenUsingRfcommWithServiceRecord("bluetooth", MY_UUID);
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(),
					"Socket create failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		btAdapter.cancelDiscovery();
		
		try {
			BluetoothSocket bss = btSocket.accept();
			
			inputStream = bss.getInputStream();

			return inputStream;
		} catch (IOException e) {
			closeConnection();
			
			Toast.makeText(getApplicationContext(),"Exception during creation InputStream: " + e.getMessage(), Toast.LENGTH_LONG).show();
			return null;
		}
		
	}
	
	public String receiveData() {
		
		byte[] buffer = new byte[1024];
		int bytes;
		try {
			bytes = inputStream.read(buffer);
			
			int i = 0;
			String readMessage = new String(buffer, 0, bytes);
			
			return readMessage;
		} catch (IOException e) {
			
			Toast.makeText(getApplicationContext(),
					"Error receiving data: " + e.getMessage(), Toast.LENGTH_LONG).show();
			
			return null;
		}

	}
	
	public void closeConnection() {
		
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(),
						"Failed to close input stream: " + e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}

		try {
			btSocket.close();
		} catch (IOException e2) {
			Toast.makeText(getApplicationContext(),
					"Failed to close socket: " + e2.getMessage(), Toast.LENGTH_LONG).show();
		}
		
	}

	public boolean isEnabled() {
		if (btAdapter == null) {
			Toast.makeText(getApplicationContext(),
					"Bluetooth niet ondersteund.", Toast.LENGTH_LONG).show();
			return false;
		} else if (!btAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(btAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		
		return true;
	}

}