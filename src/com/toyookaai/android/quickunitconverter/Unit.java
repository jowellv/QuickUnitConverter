package com.toyookaai.android.quickunitconverter;

import java.math.BigDecimal;
import java.util.ArrayList;

//The Unit object contains the unit name
public class Unit {
	String mName;
	private ArrayList<String> mUnitNames = new ArrayList<String>();
	private ArrayList<BigDecimal> mConvert = new ArrayList<BigDecimal>();

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public ArrayList<String> getUnitNames() {
		return mUnitNames;
	}

	public void addUnitName(String unitName) {
		mUnitNames.add(unitName);
	}
	
	public void addUnitNames(ArrayList<String> unitNames) {
		mUnitNames = unitNames;
	}

	public ArrayList<BigDecimal> getConvert() {
		return mConvert;
	}

	public void addConvert(String convert) {
		mConvert.add(BigDecimal.valueOf(Double.parseDouble(convert)));
	}
	
	public void addConverts(ArrayList<BigDecimal> converts) {
		mConvert = converts;
	}

	@Override
	public String toString() {
		return mName;
	}
	
	
}
