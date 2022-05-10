package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Service;

public interface ServiceService {
    Service findByName(String serviceName);
}
