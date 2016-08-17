package com.dsi.dem.service.impl;

import com.dsi.dem.dao.ReferenceDao;
import com.dsi.dem.dao.impl.ReferenceDaoImpl;
import com.dsi.dem.model.*;
import com.dsi.dem.service.ReferenceService;

import java.util.List;

/**
 * Created by sabbir on 8/17/16.
 */
public class ReferenceServiceImpl implements ReferenceService {

    private static final ReferenceDao referenceDao = new ReferenceDaoImpl();

    @Override
    public List<RoleType> getAllRoles() {
        return referenceDao.getAllRoles();
    }

    @Override
    public List<EmployeeEmailType> getAllEmailTypes() {
        return referenceDao.getAllEmailTypes();
    }

    @Override
    public List<EmployeeContactType> getAllContactInfoTypes() {
        return referenceDao.getAllContactInfoTypes();
    }

    @Override
    public List<ProjectStatus> getAllProjectStatusNames() {
        return referenceDao.getAllProjectStatusNames();
    }

    @Override
    public List<LeaveStatus> getAllLeaveStatusNames() {
        return referenceDao.getAllLeaveStatusNames();
    }

    @Override
    public List<LeaveType> getAllLeaveTypes() {
        return referenceDao.getAllLeaveTypes();
    }
}
