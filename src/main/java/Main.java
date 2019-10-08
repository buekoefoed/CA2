import entities.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
//public class Main {
//    public static void main(String[] args) {
//        Persistence.generateSchema("pu", null);
//
//        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
//        EntityManager em = emf.createEntityManager();
//
//        PersonEntity person = new PersonEntity();
//        person.setEmail("test@test.com");
//        person.setFirstName("Matias");
//        person.setLastName("Koefoed");
//
//        HobbyEntity hobby = new HobbyEntity();
//        hobby.setName("Programming");
//        hobby.setDescription("Loves suffering");
//        person.addHobby(hobby);
//
//        PhoneEntity phone = new PhoneEntity();
//        phone.setNumber("1234");
//        phone.setDescription("Home");
//        person.addPhone(phone);
//
//        CityInfoEntity cityInfo = new CityInfoEntity();
//        cityInfo.setCity("Rønne");
//        cityInfo.setZipCode("3700");
//
//        AddressEntity address = new AddressEntity();
//        address.setStreet("Malmøvej");
//        address.setAdditionalInfo("17");
//        person.addAddress(address);
//
//        cityInfo.addAddress(address);
//
//        try {
//            em.getTransaction().begin();
//            em.persist(person);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }
//}
