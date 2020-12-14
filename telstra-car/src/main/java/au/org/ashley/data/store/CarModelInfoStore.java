package au.org.ashley.data.store;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import au.org.ashley.data.model.CarModelInfo;

/**
 * A store of car mdel information.
 */
public class CarModelInfoStore {
  private final Map<Integer, CarModelInfo> mapIDToModelInfo = new HashMap<>();
  private final Map<String, Map<String, Map<Short, Collection<CarModelInfo>>>> mapMakeModelYearToCar = new HashMap<>();

  /**
   * Adds a car model's information to the store.
   *
   * @param pModelInfo the information.
   * @throws CarModelStoreException thrown if an error occurs.
   */
  public void add(final CarModelInfo pModelInfo) throws CarModelStoreException {
    final int id = pModelInfo.getID();

    if (mapIDToModelInfo.put(id, pModelInfo) != null) {
      // The spec does not mention updating records, which would add complexity, hence this is not handled.

      throw new CarModelStoreException("Error: Duplicate model ID: " + id);
    }

    mapMakeModelYearToCar.computeIfAbsent(pModelInfo.getMake(), key -> new HashMap<>())
        .computeIfAbsent(pModelInfo.getModel(), key -> new HashMap<>())
        .computeIfAbsent(pModelInfo.getYear(), key -> new LinkedList<>()).add(pModelInfo);
  }

  /**
   * Gets the car model information.
   *
   * @param pID the model ID.
   * @return the information.
   */
  public CarModelInfo get(final int pID) {
    return mapIDToModelInfo.get(pID);
  }

  /**
   * Gets the car models with the specified parameters.
   *
   * @param pMake the make.
   * @param pModel the model.
   * @param pYear the year.
   * @return the cars.
   */
  public Collection<CarModelInfo> get(final String pMake, final String pModel, final Short pYear) {
    Collection<CarModelInfo> modelInfoList;

    try {
      modelInfoList = mapMakeModelYearToCar.get(pMake).get(pModel).get(pYear);
    } catch (final NullPointerException exception) {
      // It is safe to assume the standard Java libraries work.

      modelInfoList = Collections.emptyList();
    }

    return modelInfoList;
  }
}
