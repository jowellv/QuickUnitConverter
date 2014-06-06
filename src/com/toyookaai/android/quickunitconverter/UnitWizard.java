package com.toyookaai.android.quickunitconverter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;

//Class handles the logic of calculating the units.
//Also, specifies the units are allowed in the app.
public class UnitWizard {
	private ArrayList<Unit> mUnits;
	private Context mAppContext;
	private ArrayList<String> mToDisplay = new ArrayList<String>();
	private static UnitWizard sUnitWizard;
	private String[] mUnitNames;	
	private ArrayList<String> mUnitNamesList;
	
	// sets up the units and conversion factors
	// the conversion is based off of ratios between the current unit and the default unit
	private UnitWizard(Context appContext) {
		mAppContext = appContext;
		mUnits = new ArrayList<Unit>();

		//start length
		//for every unit time add the names into an array list
		mUnitNames = mAppContext.getResources().getStringArray(R.array.length_units);
		mUnitNamesList = new ArrayList<String>(Arrays.asList(mUnitNames));
		Unit meter = new Unit();
		meter.addUnitNames(mUnitNamesList);
		meter.setName("meter");
		meter.addConvert("1000.0");
		meter.addConvert("100.0");
		meter.addConvert("1.0");
		meter.addConvert("0.001");
		meter.addConvert("39.3700787402");
		meter.addConvert("3.28083989501");
		meter.addConvert("1.09361329834");
		meter.addConvert("0.000621371192237");
		mUnits.add(meter);
		
		addConvert("millimeter", "0.001");
		addConvert("centimeter", "0.01");
		addConvert("kilometer", "1000.0");
		addConvert("inch", "0.02539999999997257");
		addConvert("feet", "0.30480000000029017");
		addConvert("yard", "0.9143999999980834");
		addConvert("mile", "1609.344000000865");
		
		//start mass
		mUnitNames = mAppContext.getResources().getStringArray(R.array.mass_units);		
		mUnitNamesList = new ArrayList<String>(Arrays.asList(mUnitNames));
		Unit kilogram = new Unit();
		kilogram.addUnitNames(mUnitNamesList);
		kilogram.setName("kilogram");
		kilogram.addConvert("1000000.0");
		kilogram.addConvert("1000.0");
		kilogram.addConvert("1.0");
		kilogram.addConvert("35.2739619496");
		kilogram.addConvert("2.20462262185");
		kilogram.addConvert("0.00110231131092");
		mUnits.add(kilogram);
		
		addConvert("milligram", "0.000001");
		addConvert("gram", "0.001");
		addConvert("ounce", "0.028349523124984257");
		addConvert("pound", "0.4535923699997481");
		addConvert("ton", "907.1847400036112");
		
		//start temp
		mUnitNames = mAppContext.getResources().getStringArray(R.array.temp_units);		
		mUnitNamesList = new ArrayList<String>(Arrays.asList(mUnitNames));
		Unit c = new Unit();
		c.addUnitNames(mUnitNamesList);
		c.setName("\u2103");
		mUnits.add(c);
		
		Unit f = new Unit();
		f.addUnitNames(mUnitNamesList);
		f.setName("\u2109");
		mUnits.add(f); 
		
		Unit k = new Unit();
		k.addUnitNames(mUnitNamesList);
		k.setName("K");
		mUnits.add(k); 

		//start area
		mUnitNames = mAppContext.getResources().getStringArray(R.array.area_units);
		mUnitNamesList = new ArrayList<String>(Arrays.asList(mUnitNames));
		Unit squareKilometer = new Unit();
		squareKilometer.addUnitNames(mUnitNamesList);
		squareKilometer.setName("kilometer\u00b2");
		squareKilometer.addConvert("1000000.0");
		squareKilometer.addConvert("1.0");
		squareKilometer.addConvert("10763910.4167");
		squareKilometer.addConvert("0.386102158542");
		squareKilometer.addConvert("247.105381467");
		squareKilometer.addConvert("604997.277512");
		mUnits.add(squareKilometer);
		
		addConvert("meter\u00b2", "0.000001");
		addConvert("feet\u00b2", "0.00000009290304000008392");
		addConvert("mile\u00b2", "2.589988110338991");
		addConvert("acre", "0.004046856422402708");
		addConvert("tatami", "0.0000016529000000006864");
		
		//start volume
		mUnitNames = mAppContext.getResources().getStringArray(R.array.volume_units);
		mUnitNamesList = new ArrayList<String>(Arrays.asList(mUnitNames));
		Unit liter = new Unit();
		liter.addUnitNames(mUnitNamesList);
		liter.setName("liter");
		liter.addConvert("1000.0");
		liter.addConvert("1.0");
		liter.addConvert("0.001");
		liter.addConvert("202.884135354");
		liter.addConvert("67.6280451178");
		liter.addConvert("33.8140225589");
		liter.addConvert("2.11337640993");
		liter.addConvert("1.05668820497");
		liter.addConvert("0.264172051242");
		mUnits.add(liter);
		
		addConvert("mL", "0.001");
		addConvert("meter\u00b3", "1000");
		addConvert("tsp", "0.004928921614571597");
		addConvert("tbsp", "0.014786764843758519");
		addConvert("fl oz", "0.029573529687517038");
		addConvert("pint", "0.4731764750005525");
		addConvert("quart", "0.9463529499966271");
		addConvert("gallon", "3.785411799993673");

	}
	
	// Creates a new Unit object for the rest of the units in the same category
	private void addConvert(String unit, String factor) {
		Unit u = new Unit();
		Unit baseUnit;
		u.setName(unit);
		BigDecimal bdFactor = BigDecimal.valueOf(Double.parseDouble(factor));
		if(mUnitNamesList.contains("meter")){
			baseUnit = getUnit("meter");
		} else if(mUnitNamesList.contains("kilogram")){
			baseUnit = getUnit("kilogram"); 
		} else if(mUnitNamesList.contains("kilometer\u00b2")){
			baseUnit = getUnit("kilometer\u00b2"); 
		} else { // == liter
			baseUnit = getUnit("liter"); 
		}
		u.addUnitNames(mUnitNamesList);
		// add the correct conversion factor for every unit
		for (BigDecimal d: baseUnit.getConvert()) {
			u.addConvert(bdFactor.multiply(d).toString());
		}
		mUnits.add(u);
	}
	//Calculates the converted value and stores it in the string array
	private void createResult(BigDecimal d, Unit type) {
		ArrayList<String> unitNames = type.getUnitNames();
		ArrayList<BigDecimal> convert = type.getConvert();
		Double tempD = d.doubleValue();
		//limits the number of significant figures displayed
		DecimalFormat df = new DecimalFormat("@##########");
		// different math required to calculate temperature
		if(unitNames.contains("\u2103")) {
			if(type.getName().equals("\u2103")) {
				mToDisplay.add(df.format(tempD) + " : " + unitNames.get(0));
				mToDisplay.add(df.format(tempD * 1.8 + 32) + " : " + unitNames.get(1));
				mToDisplay.add(df.format(tempD + 273.15) + " : " + unitNames.get(2));
			} else if(type.getName().equals("\u2109")) {
				mToDisplay.add(df.format((tempD - 32) / 1.8) + " : " + unitNames.get(0));
				mToDisplay.add(df.format(tempD) + " : " + unitNames.get(1));
				mToDisplay.add(df.format((tempD - 32) / 1.8 + 273.15)  + " : " + unitNames.get(2));
			} else {
				mToDisplay.add(df.format(tempD - 273.15) + " : " + unitNames.get(0));
				mToDisplay.add(df.format((tempD - 273.15) * 1.8 + 32) + " : " + unitNames.get(1));
				mToDisplay.add(df.format(tempD) + " : " + unitNames.get(2));
			}
		} else { // unit is not a temperature; default way to handle conversion
			for (int a = 0; a < unitNames.size(); a++) {
				 mToDisplay.add((df.format(convert.get(a).multiply(d))) + " : " + unitNames.get(a));
		        }
		}
	}
	
	public static UnitWizard get(Context c) {
		if (sUnitWizard == null) {
			sUnitWizard = new UnitWizard(c.getApplicationContext());
		}
		return sUnitWizard;
	}
	
	public ArrayList<Unit> getUnits() {
		return mUnits;
	}
	
	public ArrayList<String> getUnitNames(String u) {
		Unit type = getUnit(u);
		return type.getUnitNames();
	}
	
	public  ArrayList<String> getResult(BigDecimal d, String u) {
		Unit type = getUnit(u);
		mToDisplay.clear();
		createResult(d, type);
		return mToDisplay;
	}

	
	public Unit getUnit(String name) {
		for (Unit u: mUnits) {
			if(u.getName().equals(name))
				return u;
		}
		return null;
		
	}
	
	public int getUnitIndex(String name) {
		Unit currentUnit = getUnit(name);
		ArrayList<String> unitList = currentUnit.getUnitNames();
		return unitList.indexOf(name);
	}
	
}
