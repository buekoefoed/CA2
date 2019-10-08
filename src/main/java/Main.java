import entities.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        Persistence.generateSchema("pu", null);

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
        EntityManager em = emf.createEntityManager();

        PersonEntity person = new PersonEntity();
        person.setEmail("test@test.com");
        person.setFirstName("Matias");
        person.setLastName("Koefoed");

        HobbyEntity hobby = new HobbyEntity();
        hobby.setName("Programming");
        hobby.setDescription("Loves suffering");
        person.addHobby(hobby);

        PhoneEntity phone = new PhoneEntity();
        phone.setNumber("1234");
        phone.setDescription("Home");
        person.addPhone(phone);

        CityInfoEntity cityInfo = new CityInfoEntity();
        cityInfo.setCity("Rønne");
        cityInfo.setZipCode("3700");

        AddressEntity address = new AddressEntity();
        address.setStreet("Malmøvej");
        address.setAdditionalInfo("17");
        person.addAddress(address);

        cityInfo.addAddress(address);

        PersonEntity person1 = new PersonEntity();
        person1.setEmail("igen@igen.com");
        person1.setFirstName("Mark");
        person1.setLastName("Muldjord");

        HobbyEntity hobby1 = new HobbyEntity();
        hobby1.setName("Lort");
        hobby1.setDescription("Han kan godt lide det");
        person1.addHobby(hobby1);

        PhoneEntity phone1 = new PhoneEntity();
        phone1.setNumber("6969");
        phone1.setDescription("nice");
        person1.addPhone(phone1);

        CityInfoEntity cityInfo1 = new CityInfoEntity();
        cityInfo1.setCity("Lorteby");
        cityInfo1.setZipCode("4200");

        AddressEntity address1= new AddressEntity();
        address1.setStreet("Bævej");
        address1.setAdditionalInfo("10");
        person1.addAddress(address1);

        cityInfo1.addAddress(address1);

        try {
            em.getTransaction().begin();
            em.persist(person);
            em.persist(person1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
