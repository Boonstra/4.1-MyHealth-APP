package com.example.myhealth;
import com.example.myhealth.BloodPressure.BPMeasurement;
import com.example.myhealth.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.app.Activity;


public class bpmArrayAdapter extends ArrayAdapter<BPMeasurement> {
	Context context;
	int layoutResourceId; 
	BPMeasurement[] data;
	
	
	public bpmArrayAdapter(Context context, int resource, BPMeasurement[] objects) {
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
            
            holder.high = (TextView)row.findViewById(R.id.high);
            holder.low = (TextView)row.findViewById(R.id.low);
            holder.datetime = (TextView)row.findViewById(R.id.date);
           
            row.setTag(holder);
        }
        else
        {
            holder = (MeasurementHolder)row.getTag();
        }
        
        if(data.length <= position || data[position] == null){
        	return null;
        }
        
        BPMeasurement measurement = data[position];
        holder.high.setText(String.valueOf(measurement.getHigh()));
        holder.low.setText(String.valueOf(measurement.getLow()));
        holder.datetime.setText(measurement.getDate());   
        
        return row;
    }
    
   
    static class MeasurementHolder
    {
        TextView high;
        TextView low;
        TextView datetime;
    }
}
