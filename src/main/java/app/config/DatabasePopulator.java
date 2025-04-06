package app.config;

import app.daos.TripDAO;
import app.entities.Guide;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;

public class DatabasePopulator {

    public static void populate() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        TripDAO tripDAO = new TripDAO();

        em.getTransaction().begin();

        // Opret guides
        Guide guide1 = new Guide();
        guide1.setFirstname("John");
        guide1.setLastname("Doe");
        guide1.setEmail("john.doe@example.com");
        guide1.setPhone("12345678");
        guide1.setYearsOfExperience(5);
        em.persist(guide1);

        Guide guide2 = new Guide();
        guide2.setFirstname("Jane");
        guide2.setLastname("Smith");
        guide2.setEmail("jane.smith@example.com");
        guide2.setPhone("87654321");
        guide2.setYearsOfExperience(8);
        em.persist(guide2);

        em.getTransaction().commit();

        // Konverter til DTO'er for at bruge i DAO'en
        GuideDTO guideDTO1 = new GuideDTO(guide1.getFirstname(), guide1.getLastname(), guide1.getEmail(), guide1.getPhone(), guide1.getYearsOfExperience());
        GuideDTO guideDTO2 = new GuideDTO(guide2.getFirstname(), guide2.getLastname(), guide2.getEmail(), guide2.getPhone(), guide2.getYearsOfExperience());

        // Opret trips
        TripDTO trip1 = new TripDTO();
        trip1.setName("Mountain Adventure");
        trip1.setPrice(1200);
        trip1.setStarttime(LocalDateTime.now().plusDays(10));
        trip1.setEndtime(LocalDateTime.now().plusDays(15));
        trip1.setStartposition("Basecamp");
        trip1.setCategory(TripCategory.BEACH);

        TripDTO trip2 = new TripDTO();
        trip2.setName("Jungle Safari");
        trip2.setPrice(1500);
        trip2.setStarttime(LocalDateTime.now().plusDays(5));
        trip2.setEndtime(LocalDateTime.now().plusDays(10));
        trip2.setStartposition("Jungle Entrance");
        trip2.setCategory(TripCategory.SNOW);

        // Tilføj trips til databasen
        tripDAO.create(trip1);
        tripDAO.create(trip2);

        // Tilføj guides til trips
        tripDAO.addGuideToTrip(trip1.getId(), guide1.getId());
        tripDAO.addGuideToTrip(trip2.getId(), guide2.getId());

        System.out.println("Database populated successfully!");
    }

    public static void main(String[] args) {
        populate();
    }
}
