package au.org.ashley.data.model;

/**
 * A car.
 */
public class Car {
  private final String registration;
  private final CarModelInfo modelInfo;

  /**
   * Constructor.
   * 
   * @param pRegistration the registration.
   * @param pModel the model.
   */
  public Car(String pRegistration, CarModelInfo pModel) {
    registration = pRegistration;
    modelInfo = pModel;
  }

  @Override
  public int hashCode() {
    return getRegistration().hashCode();
  }

  @Override
  public boolean equals(Object pObject) {
    return pObject instanceof Car && registration.equals(((Car) pObject).getRegistration());
  }

  /**
   * Gets the registration.
   * 
   * @return the registration.
   */
  public String getRegistration() {
    return registration;
  }

  /**
   * Gets the car model info.
   * 
   * @return the model info.
   */
  public CarModelInfo getModelInfo() {
    return modelInfo;
  }
}
