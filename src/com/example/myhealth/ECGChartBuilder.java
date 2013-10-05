package com.example.myhealth;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;

public class ECGChartBuilder
{
	/**
	 * The ECG Measurement as string
	 */
	protected String ecgMeasurement;
	
	/**
	 * @param ecgMeasurement
	 */
	public ECGChartBuilder(String ecgMeasurement)
	{
		this.ecgMeasurement = ecgMeasurement;
	}
	
	/**
	 * Build a GraphicalView from the passed ECG measurement.
	 * 
	 * @param context
	 * @return
	 */
	public GraphicalView buildECGChart(Context context)
	{
		// Renderer
		XYMultipleSeriesRenderer multipleSeriesRenderer = new XYMultipleSeriesRenderer();
		
		// Set renderer properties
		multipleSeriesRenderer.setAxisTitleTextSize(16);
		multipleSeriesRenderer.setChartTitleTextSize(20);
		multipleSeriesRenderer.setLabelsTextSize(15);
		multipleSeriesRenderer.setLegendTextSize(15);
		multipleSeriesRenderer.setMargins(new int[] { 0, 0, 0, 0/*20, 20, 20, 20*/ });
		multipleSeriesRenderer.setPointSize(0);
		multipleSeriesRenderer.setShowGrid(true);
		multipleSeriesRenderer.setBackgroundColor(Color.WHITE);
		multipleSeriesRenderer.setGridColor(Color.RED);
		multipleSeriesRenderer.setLabelsTextSize(0);
		multipleSeriesRenderer.setZoomEnabled(false);
		multipleSeriesRenderer.setPanEnabled(false);
		
		// Create a renderer for these series
		XYSeriesRenderer seriesRenderer = new XYSeriesRenderer();
		
		// Set renderer properties
		seriesRenderer.setPointStyle(PointStyle.CIRCLE);
		seriesRenderer.setFillPoints(true);
		seriesRenderer.setDisplayChartValues(true);
		seriesRenderer.setDisplayChartValuesDistance(10);
		seriesRenderer.setColor(Color.BLACK);
		
		multipleSeriesRenderer.addSeriesRenderer(seriesRenderer);
		
		XYMultipleSeriesDataset multipleSeriesDataset = new XYMultipleSeriesDataset();
		
		multipleSeriesDataset.addSeries(generateSeriesFromECGMeasurement());
		
		return ChartFactory.getLineChartView(context, multipleSeriesDataset, multipleSeriesRenderer);
	}
	
	/**
	 * Generate an XYSeries object from the ecgMeasurement string.
	 * 
	 * @return
	 */
	protected XYSeries generateSeriesFromECGMeasurement()
	{
		XYSeries series = new XYSeries("ECG");
		
		// An ECG measurement is stored as string values separated by a forward slash
		String[] ecgMeasurementStringValues = ecgMeasurement.split("/");
		
		for (int i = 0; i < ecgMeasurementStringValues.length; i++)
		{
			String ecgMeasurementStringValue = ecgMeasurementStringValues[i];
			
			// Converting a string to an integer value can cause number format exceptions
			try
			{
				series.add(i, Integer.parseInt(ecgMeasurementStringValue));
			}
			catch (NumberFormatException e)
			{
				continue;
			}
		}
		
		return series;
	}
}
