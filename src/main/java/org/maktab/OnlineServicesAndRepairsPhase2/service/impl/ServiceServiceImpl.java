package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Service;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.impl.ServiceRepositoryImp;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.ServiceRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.ServiceService;

import javax.transaction.Transactional;

@org.springframework.stereotype.Service
@Transactional
public class ServiceServiceImpl implements ServiceService {
    private ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Service findByName(String serviceName) {
        return serviceRepository.findByName(serviceName);
    }
}
