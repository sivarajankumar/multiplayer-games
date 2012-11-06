package model;

public abstract class GameParameter {

	String name;
	String type;
	String label;
	
	public GameParameter(String name, String label, String type) {
		this.name = name;
		this.label = label;
		this.type = type;
	}

	public static class BooleanParameter extends GameParameter {
		
		boolean defaultValue;

		public BooleanParameter(String name, String label, boolean defaultValue) {
			super(name, label, "boolean");
			this.defaultValue = defaultValue;
		}
		
	}
	
	public static class EnumerationParameter extends GameParameter {
		
		String[] values;
		int defaultValueIndex;
		
		public EnumerationParameter(String name, String label, String[] values, int defaultValueIndex) {
			super(name, label, "enumeration");
			this.values = values;
			this.defaultValueIndex = defaultValueIndex;
		}

	}
	
}

