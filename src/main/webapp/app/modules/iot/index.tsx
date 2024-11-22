import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import GatewayPage from './gateway/gateway';
import IotTest from './iottest/iottest';

const IotRoutes = () => (
  <div>
    <ErrorBoundaryRoutes>
      <Route path="gateway" element={<GatewayPage />} />
      <Route path="iottest" element={<IotTest />} />
    </ErrorBoundaryRoutes>
  </div>
);

export default IotRoutes;
