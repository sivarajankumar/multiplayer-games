package model;

public abstract class GameParameter {

	String name;
	String type;
	
	public GameParameter(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public static class BooleanParameter extends GameParameter {
		
		boolean defaultValue;

		public BooleanParameter(String name, boolean defaultValue) {
			super(name, "boolean");
			this.defaultValue = defaultValue;
		}
		
	}
	
	public static class EnumerationParameter extends GameParameter {
		
		String[] values;
		int defaultValueIndex;
		
		public EnumerationParameter(String name, String[] values, int defaultValueIndex) {
			super(name, "enumeration");
			this.values = values;
			this.defaultValueIndex = defaultValueIndex;
		}

	}
	
}

