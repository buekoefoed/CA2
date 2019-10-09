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

        PersonEntity p1;
        PersonEntity p2;
        PersonEntity p3;

        CityInfoEntity c1;
        CityInfoEntity c2;
        CityInfoEntity c3;

        p1 = new PersonEntity();
        p2 = new PersonEntity();
        p3 = new PersonEntity();

        p1.setEmail("matias@test.com");
        p1.setFirstName("Matias");
        p1.setLastName("Koefoed");

        p2.setEmail("karl@test.com");
        p2.setFirstName("Karl");
        p2.setLastName("Frödin");

        p3.setEmail("emil@test.com");
        p3.setFirstName("Emil");
        p3.setLastName("Hansen");

        HobbyEntity h1 = new HobbyEntity();
        h1.setName("CityFacade");
        h1.setDescription("Makes city facades");
        p1.addHobby(h1);

        HobbyEntity h2 = new HobbyEntity();
        h2.setName("HobbyFacade");
        h2.setDescription("Makes hobby facades");
        p2.addHobby(h2);

        HobbyEntity h3 = new HobbyEntity();
        h3.setName("PersonFacade");
        h3.setDescription("Makes person facades");
        p3.addHobby(h3);

        PhoneEntity ph1 = new PhoneEntity();
        ph1.setNumber("1111");
        ph1.setDescription("Home");
        p1.addPhone(ph1);

        PhoneEntity ph2 = new PhoneEntity();
        ph2.setNumber("2222");
        ph2.setDescription("Home");
        p2.addPhone(ph2);

        PhoneEntity ph3 = new PhoneEntity();
        ph3.setNumber("3333");
        ph3.setDescription("Home");
        p3.addPhone(ph3);

        c1 = new CityInfoEntity();
        c1.setCity("Rønne");
        c1.setZipCode("3700");

        c2 = new CityInfoEntity();
        c2.setCity("Allinge");
        c2.setZipCode("3770");

        c3 = new CityInfoEntity();
        c3.setCity("Gudhjem");
        c3.setZipCode("3760");

        AddressEntity a1 = new AddressEntity();
        a1.setStreet("Malmøvej");
        a1.setAdditionalInfo("17");
        p1.addAddress(a1);

        AddressEntity a2 = new AddressEntity();
        a2.setStreet("Ågade");
        a2.setAdditionalInfo("6");
        p2.addAddress(a2);

        AddressEntity a3 = new AddressEntity();
        a3.setStreet("Holkavej");
        a3.setAdditionalInfo("3");
        p3.addAddress(a3);

        c1.addAddress(a1);
        c2.addAddress(a2);
        c3.addAddress(a3);

        try {
            em.getTransaction().begin();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
