package app.daos;

import app.config.HibernateConfig;
import app.entities.Guide;
import app.entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TripDAO {

    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();



    public TripDTO create(TripDTO dto) {
        Trip trip = dtoToEntity(dto);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(trip);
        em.getTransaction().commit();
        em.close();
        return entityToDTO(trip);

    }

    public List<TripDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Trip> query = em.createQuery("FROM Trip", Trip.class);
        List<Trip> trips = query.getResultList();
        em.close();
        return trips.stream().map(this::entityToDTO).toList();
    }

    public TripDTO getById(int id) {
        EntityManager em = emf.createEntityManager();
        Trip trip = em.find(Trip.class, id);
        em.close();
        if(trip == null) throw new RuntimeException("trip not found");
        return entityToDTO(trip);
    }

    public TripDTO update(TripDTO dto) {
        EntityManager em = emf.createEntityManager();
        Trip trip = em.find(Trip.class, dto.getId());
        trip.setName(dto.getName());
        trip.setPrice(dto.getPrice());
        Guide guide = trip.getGuide();
        trip.setGuide(guide);
        em.getTransaction().begin();
        em.merge(trip);
        em.getTransaction().commit();
        em.close();
        return entityToDTO(trip);

    }

    public boolean delete(int id) {
        EntityManager em = emf.createEntityManager();
        Trip trip = em.find(Trip.class, id);
        em.getTransaction().begin();
        em.remove(trip);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public void addGuideToTrip(int tripId, int guideId) {
        EntityManager em = emf.createEntityManager();
        Trip trip = em.find(Trip.class, tripId);
        Guide guide = em.find(Guide.class, guideId);

        trip.setGuide(guide);
        em.getTransaction().begin();
        em.merge(trip);
        em.getTransaction().commit();
        em.close();
    }

    public Set<TripDTO> getTripsByGuide(int guideId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t WHERE t.guide.id = :guideId", Trip.class);
        query.setParameter("guideId", guideId);
        List<Trip> trips = query.getResultList();
        em.close();

        return new HashSet<>(trips.stream().map(this::entityToDTO).toList());

    }

    // Hjælpe-metoder til at konvertere mellem entity og DTO
    private Trip dtoToEntity(TripDTO dto) {
        Trip trip = new Trip();
        trip.setId(dto.getId());
        trip.setName(dto.getName());
        trip.setPrice(dto.getPrice());
        trip.setStarttime(dto.getStarttime());
        trip.setEndtime(dto.getEndtime());
        trip.setStartposition(dto.getStartposition());
        trip.setCategory(dto.getCategory());
        // Guide sættes via addGuideToTrip
        return trip;
    }

    private TripDTO entityToDTO(Trip trip) {
        TripDTO dto = new TripDTO();
        dto.setId(trip.getId());
        dto.setName(trip.getName());
        dto.setPrice(trip.getPrice());
        dto.setStarttime(trip.getStarttime());
        dto.setEndtime(trip.getEndtime());
        dto.setStartposition(trip.getStartposition());
        dto.setCategory(trip.getCategory());
        // Konverter guide hvis sat
        if (trip.getGuide() != null) {
            dto.setGuide(guideToDTO(trip.getGuide()));
        }
        return dto;
    }

    private GuideDTO guideToDTO(Guide guide) {
        GuideDTO dto = new GuideDTO();
        dto.setId(guide.getId());
        dto.setFirstname(guide.getFirstname());
        dto.setLastname(guide.getLastname());
        dto.setEmail(guide.getEmail());
        dto.setPhone(guide.getPhone());
        dto.setYearsOfExperience(guide.getYearsOfExperience());
        return dto;
    }
}
