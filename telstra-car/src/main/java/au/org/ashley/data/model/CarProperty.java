package au.org.ashley.data.model;

/**
 * A property of a car.
 */
public class CarProperty {
  private final String name;
  private final String value;

  /**
   * Constructor.
   *
   * @param pName the name.
   * @param pValue the value.
   */
  public CarProperty(final String pName, final String pValue) {
    name = pName;
    value = pValue;
  }

  /**
   * The name.
   *
   * @return the name.
   */
  public String getName() {
    return name;
  }

  /**
   * The value.
   *
   * @return the value.
   */
  public String getValue() {
    return value;
  }

}
