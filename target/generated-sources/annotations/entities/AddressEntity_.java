package entities;

import entities.CityInfoEntity;
import entities.PersonEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-08T18:06:29")
@StaticMetamodel(AddressEntity.class)
public class AddressEntity_ { 

    public static volatile ListAttribute<AddressEntity, PersonEntity> persons;
    public static volatile SingularAttribute<AddressEntity, String> street;
    public static volatile SingularAttribute<AddressEntity, String> additionalInfo;
    public static volatile SingularAttribute<AddressEntity, CityInfoEntity> cityInfo;
    public static volatile SingularAttribute<AddressEntity, Integer> id;

}