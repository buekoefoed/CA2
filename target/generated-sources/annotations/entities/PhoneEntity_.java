package entities;

import entities.PersonEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-09T10:44:29")
@StaticMetamodel(PhoneEntity.class)
public class PhoneEntity_ { 

    public static volatile SingularAttribute<PhoneEntity, String> number;
    public static volatile SingularAttribute<PhoneEntity, PersonEntity> person;
    public static volatile SingularAttribute<PhoneEntity, String> description;
    public static volatile SingularAttribute<PhoneEntity, Integer> id;

}