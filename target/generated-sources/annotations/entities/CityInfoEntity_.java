package entities;

import entities.AddressEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-08T13:43:13")
@StaticMetamodel(CityInfoEntity.class)
public class CityInfoEntity_ { 

    public static volatile SingularAttribute<CityInfoEntity, String> zipCode;
    public static volatile ListAttribute<CityInfoEntity, AddressEntity> addresses;
    public static volatile SingularAttribute<CityInfoEntity, String> city;
    public static volatile SingularAttribute<CityInfoEntity, Integer> id;

}