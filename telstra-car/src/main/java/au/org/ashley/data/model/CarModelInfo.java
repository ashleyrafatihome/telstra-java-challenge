package au.org.ashley.data.model;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Information associated with a car model.
 */
public class CarModelInfo implements Iterable<CarProperty> {
  private final Map<String, Integer> mapPropertyToIndex;
  private final String[] propertyList;

  /**
   * Constructor.
   * 
   * @param pMapPropertyToIndex maps a property to its index.
   * @param pPropertyList the list of properties of this model.
   */
  public CarModelInfo(Map<String, Integer> pMapPropertyToIndex, String[] pPropertyList) {
    mapPropertyToIndex = pMapPropertyToIndex;
    propertyList = pPropertyList;
  }

  @Override
  public Iterator<CarProperty> iterator() {
    return new Iterator<CarProperty>() {
      private final Iterator<Entry<String, Integer>> iterator = mapPropertyToIndex.entrySet().iterator();

      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }

      @Override
      public CarProperty next() {
        Entry<String, Integer> entry = iterator.next();

        return new CarProperty(entry.getKey(), propertyList[entry.getValue()]);
      }
    };
  }

  /**
   * Gets the ID.
   * 
   * @return the ID.
   */
  public int getID() {
    return Integer.valueOf(getProperty(EnumCarProperty.ID));
  }

  /**
   * Gets the year of manufacture.
   * 
   * @return the year.
   */
  public short getYear() {
    return Short.valueOf(getProperty(EnumCarProperty.YEAR));
  }

  /**
   * Gets the make.
   * 
   * @return the make.
   */
  public String getMake() {
    return getProperty(EnumCarProperty.MAKE);
  }

  /**
   * Gets the model.
   * 
   * @return the model.
   */
  public String getModel() {
    return getProperty(EnumCarProperty.MODEL);
  }

  /**
   * Gets the specified property value.
   * 
   * @param aProperty the property.
   * @return the value.
   */
  public String getProperty(EnumCarProperty aProperty) {
    return getProperty(aProperty.getName());
  }

  /**
   * Gets the specified property value.
   * 
   * @param aProperty the property.
   * @return the value.
   */
  public String getProperty(String aProperty) {
    return propertyList[mapPropertyToIndex.get(aProperty)];
  }
}
