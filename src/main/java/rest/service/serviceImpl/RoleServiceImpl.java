package rest.service.serviceImpl;

import org.springframework.stereotype.Service;
import rest.model.Role;
import rest.repository.RoleRepository;
import rest.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
