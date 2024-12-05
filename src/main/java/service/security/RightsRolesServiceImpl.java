package service.security;

import model.Role;
import repository.security.RightsRolesRepository;

import java.util.List;

public class RightsRolesServiceImpl implements RightsRolesService{
    private final RightsRolesRepository rightsRolesRepository;

    public RightsRolesServiceImpl(RightsRolesRepository rightsRolesRepository) {
        this.rightsRolesRepository = rightsRolesRepository;
    }
    @Override
    public List<Role> findRolesForUser(Long userId) {
        return rightsRolesRepository.findRolesForUser(userId);
    }
}
