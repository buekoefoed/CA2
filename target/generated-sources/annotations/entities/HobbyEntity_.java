package entities;

import entities.PersonEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-09T10:11:06")
@StaticMetamodel(HobbyEntity.class)
public class HobbyEntity_ { 

    public static volatile ListAttribute<HobbyEntity, PersonEntity> persons;
    public static volatile SingularAttribute<HobbyEntity, Date> created;
    public static volatile SingularAttribute<HobbyEntity, String> name;
    public static volatile SingularAttribute<HobbyEntity, String> description;
    public static volatile SingularAttribute<HobbyEntity, Integer> id;
    public static volatile SingularAttribute<HobbyEntity, Date> lastEdited;

}