import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/config-type">
        <Translate contentKey="global.menu.entities.configType" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-config">
        <Translate contentKey="global.menu.entities.appConfig" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
