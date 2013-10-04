package com.example.myhealth;
import com.example.myhealth.Pulse.PulseMeasurement;
import com.example.myhealth.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.app.Activity;


public class pmArrayAdapter extends ArrayAdapter<PulseMeasurement> {
	Context context;
	int layoutResourceId; 
	PulseMeasurement[] data;
	
	
	public pmArrayAdapter(Context context, int resource, PulseMeasurement[] objects) {
		super(context, resource, objects);
		this.context = context;
		this.layoutResourceId = resource;
		this.data = objects;
		
		
	}
	public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MeasurementHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new MeasurementHolder();
            
            holder.pulseValue = (TextView)row.findViewById(R.id.pulseValue);
            holder.datetime = (TextView)row.findViewById(R.id.datepulse);
           
            row.setTag(holder);
        }
        else
        {
            holder = (MeasurementHolder)row.getTag();
        }
        
        if(data.length <= position || data[position] == null){
        	return null;
        }
        
        PulseMeasurement measurement = data[position];
        holder.pulseValue.setText(String.valueOf(measurement.getpulseValue()));
        holder.datetime.setText(measurement.getDate());   
        
        return row;
    }
    
   
    static class MeasurementHolder
    {
        TextView pulseValue;
        TextView datetime;
    }
}
