package com.t3.client.ui.properties.detaileditors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.t3.util.math.CappedInteger;

public class CappedIntegerEditor extends DetailEditor<CappedInteger> {
	private SpinnerNumberModel	maxSpinner;
	private SpinnerNumberModel	minSpinner;
	private JSlider		slider;
	private SpinnerNumberModel	currentSpinner;

	public CappedIntegerEditor() {
		minSpinner=new SpinnerNumberModel(0,null,null,1);
		maxSpinner=new SpinnerNumberModel(0,null,null,1);
		MinMaxSpinnerChangedListener mmcl = new MinMaxSpinnerChangedListener();
		minSpinner.addChangeListener(mmcl);
		maxSpinner.addChangeListener(mmcl);
		currentSpinner=new SpinnerNumberModel(0,0,0,1);
		currentSpinner.addChangeListener(new CurrentSpinnerChangedListener());
		
		slider=new JSlider();
		slider.addChangeListener(new SliderChangedListener());
		slider.setPaintTicks(true);
		//slider.setSnapToTicks(true);
		//slider.setMajorTickSpacing(1);
		JPanel spinnerPanel=new JPanel();
		spinnerPanel.setLayout(new GridLayout(0,3));
		spinnerPanel.add(new JSpinner(minSpinner));
		spinnerPanel.add(new JSpinner(currentSpinner));
		spinnerPanel.add(new JSpinner(maxSpinner));
		this.add(spinnerPanel);
		this.add(slider,BorderLayout.NORTH);
	}

	private class SliderChangedListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent ce) {
			int newValue=slider.getValue();
			currentSpinner.setValue(newValue);
		}
	}
	
	private class CurrentSpinnerChangedListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			int newValue=(int) currentSpinner.getNumber();
			slider.setValue(newValue);
			minSpinner.setMaximum(newValue);
			maxSpinner.setMinimum(newValue);
		}
	}
	
	private class MinMaxSpinnerChangedListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			slider.setMinimum((int)minSpinner.getNumber());
			slider.setMaximum((int)maxSpinner.getNumber());
			currentSpinner.setMinimum((int)minSpinner.getNumber());
			currentSpinner.setMaximum((int)maxSpinner.getNumber());
		}
	}
	
	@Override
	public CappedInteger getValue() {
		return new CappedInteger(slider.getValue(), (int)minSpinner.getNumber(), (int)maxSpinner.getNumber());
	}

	@Override
	public void setTypedValue(CappedInteger value) {
		minSpinner.setValue(value.getMin());
		minSpinner.setMaximum(value.getValue());
		maxSpinner.setValue(value.getMax());
		maxSpinner.setMinimum(value.getValue());
		currentSpinner.setValue(value.getValue());
		resetSlider(value.getMin(), value.getMax(), value.getValue());
	}

	private void resetSlider(int min, int max, int value) {
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setValue(value);
	}
}
