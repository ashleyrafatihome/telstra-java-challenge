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
  public Car(final String pRegistration, final CarModelInfo pModel) {
    registration = pRegistration;
    modelInfo = pModel;
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
