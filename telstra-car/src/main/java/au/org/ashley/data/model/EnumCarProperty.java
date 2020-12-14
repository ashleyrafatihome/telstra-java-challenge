package au.org.ashley.data.model;

/**
 * The properties of a car.
 */
public enum EnumCarProperty {
  ID("ID"),
  YEAR("Identification.Year"),
  MAKE("Identification.Make"),
  MODEL("Indentification.Model");

  private final String name;

  /**
   * Constructor.
   *
   * @param pName the property name.
   */
  EnumCarProperty(final String pName) {
    name = pName;
  }

  /**
   * Gets the property name.
   *
   * @return the name.
   */
  public String getName() {
    return name;
  }
}
