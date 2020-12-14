package au.org.ashley.data.store;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import au.org.ashley.data.model.Car;

/**
 * A store of cars.
 */
public class CarStore {
  private final Map<String, Car> mapRegistrationToCar = new HashMap<>();
  private final Map<Integer, Collection<Car>> mapModelIDToCar = new HashMap<>();

  /**
   * Adds a car to the store.
   * 
   * @param pCar the car.
   */
  public void add(Car pCar) {
    if (mapRegistrationToCar.put(pCar.getRegistration(), pCar) != null) {
      // The spec does not mention updating records, which would add complexity, hence this is not handled.

      throw new RuntimeException("Error: Duplicate registration: " + pCar.getRegistration());
    }

    mapModelIDToCar.computeIfAbsent(pCar.getModelInfo().getID(), key -> new LinkedList<>()).add(pCar);
  }

  /**
   * Gets the car with the specified parameters.
   * 
   * @param pRegistration the registration.
   * @return the car.
   */
  public Car get(String pRegistration) {
    return mapRegistrationToCar.get(pRegistration);
  }

  /**
   * Gets the cars of the specified model.
   * 
   * @param pModelID the model ID.
   * @return the cars.
   */
  public Collection<Car> get(int pModelID) {
    Collection<Car> carList = mapModelIDToCar.get(pModelID);

    return carList == null ? Collections.emptyList() : carList;
  }
}
