package com.t3.client.ui.properties.detaileditors;

import java.awt.BorderLayout;

import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.t3.util.math.CappedInteger;

public class CappedIntegerEditor extends DetailEditor<CappedInteger> {
	private JSpinner	maxSpinner;
	private JSpinner	minSpinner;
	private JSlider	slider;
	private JSpinner	currentSpinner;

	public CappedIntegerEditor() {
		minSpinner=new JSpinner();
		maxSpinner=new JSpinner();
		currentSpinner=new JSpinner();
		currentSpinner.addChangeListener(new CurrentSpinnerChangedListener());
		
		slider=new JSlider();
		slider.addChangeListener(new SliderChangedListener());
		slider.setPaintTicks(true);
		//slider.setSnapToTicks(true);
		//slider.setMajorTickSpacing(1);

		this.add(minSpinner,BorderLayout.WEST);
		this.add(currentSpinner,BorderLayout.CENTER);
		this.add(maxSpinner,BorderLayout.EAST);
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
			int newValue=(Integer)currentSpinner.getValue();
			slider.setValue(newValue);
		}
	}

	@Override
	public CappedInteger getValue() {
		return new CappedInteger(slider.getValue(), (Integer)minSpinner.getValue(), (Integer)maxSpinner.getValue());
	}

	@Override
	public void setTypedValue(CappedInteger value) {
		resetSlider(value.getMin(), value.getMax(), value.getValue());
		
		
		//currentSpinner.getModel()
	}

	private void resetSlider(int min, int max, int value) {
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setValue(value);
	}
}
