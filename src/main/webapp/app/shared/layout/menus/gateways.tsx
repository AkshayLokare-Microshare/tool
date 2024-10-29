//dropdown button
import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Button, DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavDropdown } from './menu-components';
import { Translate, translate } from 'react-jhipster';

export default function GatewayDropdown() {
  return (
    <>
      <MenuItem icon="users" to="/iot/gateway">
        gateways
      </MenuItem>
    </>
  );
}
