import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import GatewayPage from './gateway/gateway';

const IotRoutes = () => (
  <div>
    <ErrorBoundaryRoutes>
      <Route path="gateway" element={<GatewayPage />} />
    </ErrorBoundaryRoutes>
  </div>
);

export default IotRoutes;
