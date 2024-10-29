import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ConfigType from './config-type';
import ConfigTypeDetail from './config-type-detail';
import ConfigTypeUpdate from './config-type-update';
import ConfigTypeDeleteDialog from './config-type-delete-dialog';

const ConfigTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ConfigType />} />
    <Route path="new" element={<ConfigTypeUpdate />} />
    <Route path=":id">
      <Route index element={<ConfigTypeDetail />} />
      <Route path="edit" element={<ConfigTypeUpdate />} />
      <Route path="delete" element={<ConfigTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConfigTypeRoutes;
