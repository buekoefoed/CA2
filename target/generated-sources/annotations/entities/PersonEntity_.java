package entities;

import entities.AddressEntity;
import entities.HobbyEntity;
import entities.PhoneEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-08T17:45:47")
@StaticMetamodel(PersonEntity.class)
public class PersonEntity_ { 

    public static volatile SingularAttribute<PersonEntity, String> firstName;
    public static volatile SingularAttribute<PersonEntity, String> lastName;
    public static volatile SingularAttribute<PersonEntity, AddressEntity> address;
    public static volatile ListAttribute<PersonEntity, HobbyEntity> hobbies;
    public static volatile ListAttribute<PersonEntity, PhoneEntity> phones;
    public static volatile SingularAttribute<PersonEntity, Integer> id;
    public static volatile SingularAttribute<PersonEntity, String> email;

}