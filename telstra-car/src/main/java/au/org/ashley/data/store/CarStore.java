package au.org.ashley.data.store;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import au.org.ashley.data.model.Car;
import au.org.ashley.data.model.CarModelInfo;

/**
 * A store of cars.
 */
public class CarStore {
  private final Map<String, Car> mapRegistrationToCar = new HashMap<>();
  private final Map<Integer, Collection<Car>> mapModelIDToCar = new HashMap<>();
  private final CarModelInfoStore modelStore;

  /**
   * Constructor.
   *
   * @param pModelStore the car model store.
   */
  public CarStore(final CarModelInfoStore pModelStore) {
    modelStore = pModelStore;
  }

  /**
   * Adds a car to the store.
   *
   * @param pRegistration the registration. This must not have been added previously.
   * @param pModelID the model ID. A car cannot be added if the model does not exist in the car model store.
   * @throws CarStoreException thrown if an error occurs.
   */
  public void add(final String pRegistration, final int pModelID) throws CarStoreException {
    final CarModelInfo model = modelStore.get(pModelID);

    if (model == null) {
      throw new CarStoreException("Error: The model ID " + pModelID + " does not exist in the model store.");
    }

    final Car car = new Car(pRegistration, model);

    if (mapRegistrationToCar.put(pRegistration, car) != null) {
      // The spec does not mention updating records, which would add complexity, hence this is not handled.

      throw new CarStoreException("Error: Duplicate registration: " + pRegistration);

    }

    mapModelIDToCar.computeIfAbsent(pModelID, key -> new LinkedList<>()).add(car);
  }

  /**
   * Gets the car with the specified parameters.
   *
   * @param pRegistration the registration.
   * @return the car.
   */
  public Car get(final String pRegistration) {
    return mapRegistrationToCar.get(pRegistration);
  }

  /**
   * Gets the cars of the specified model.
   *
   * @param pModelID the model ID.
   * @return the cars.
   */
  public Collection<Car> get(final int pModelID) {
    final Collection<Car> carList = mapModelIDToCar.get(pModelID);

    return carList == null ? Collections.emptyList() : carList;
  }

  /**
   * Gets the cars with the specified parameters.
   *
   * @param pMake the make.
   * @param pModel the model.
   * @param pYear the year.
   * @return the cars.
   */
  public Collection<Car> get(final String pMake, final String pModel, final short pYear) {
    return modelStore.get(pMake, pModel, pYear).stream().map(CarModelInfo::getID).map(id -> get(id))
        .flatMap(Collection::stream).collect(Collectors.toList());

  }
}
