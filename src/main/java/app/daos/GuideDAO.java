package app.daos;

import app.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class GuideDAO implements IDAO<GuideDTO>{

    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();



    @Override
    public GuideDTO create(GuideDTO dto) {
        return null;
    }

    @Override
    public List<GuideDTO> getAll() {
        return List.of();
    }

    @Override
    public GuideDTO getById(int id) {
        return null;
    }

    @Override
    public GuideDTO update(GuideDTO dto) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
