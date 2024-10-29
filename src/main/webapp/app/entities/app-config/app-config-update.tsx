import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IConfigType } from 'app/shared/model/config-type.model';
import { getEntities as getConfigTypes } from 'app/entities/config-type/config-type.reducer';
import { IAppConfig } from 'app/shared/model/app-config.model';
import { getEntity, updateEntity, createEntity, reset } from './app-config.reducer';

export const AppConfigUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const configTypes = useAppSelector(state => state.configType.entities);
  const appConfigEntity = useAppSelector(state => state.appConfig.entity);
  const loading = useAppSelector(state => state.appConfig.loading);
  const updating = useAppSelector(state => state.appConfig.updating);
  const updateSuccess = useAppSelector(state => state.appConfig.updateSuccess);

  const handleClose = () => {
    navigate('/app-config' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getConfigTypes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.createDate = convertDateTimeToServer(values.createDate);
    values.updateDate = convertDateTimeToServer(values.updateDate);

    const entity = {
      ...appConfigEntity,
      ...values,
      configType: configTypes.find(it => it.id.toString() === values.configType?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createDate: displayDefaultDateTime(),
          updateDate: displayDefaultDateTime(),
        }
      : {
          ...appConfigEntity,
          createDate: convertDateTimeFromServer(appConfigEntity.createDate),
          updateDate: convertDateTimeFromServer(appConfigEntity.updateDate),
          configType: appConfigEntity?.configType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="quanfigApp.appConfig.home.createOrEditLabel" data-cy="AppConfigCreateUpdateHeading">
            <Translate contentKey="quanfigApp.appConfig.home.createOrEditLabel">Create or edit a AppConfig</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="app-config-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('quanfigApp.appConfig.configKey')}
                id="app-config-configKey"
                name="configKey"
                data-cy="configKey"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('quanfigApp.appConfig.configValue')}
                id="app-config-configValue"
                name="configValue"
                data-cy="configValue"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('quanfigApp.appConfig.subType')}
                id="app-config-subType"
                name="subType"
                data-cy="subType"
                type="text"
              />
              <ValidatedField
                label={translate('quanfigApp.appConfig.subType1')}
                id="app-config-subType1"
                name="subType1"
                data-cy="subType1"
                type="text"
              />
              <ValidatedField
                label={translate('quanfigApp.appConfig.subType2')}
                id="app-config-subType2"
                name="subType2"
                data-cy="subType2"
                type="text"
              />
              <ValidatedField
                label={translate('quanfigApp.appConfig.description')}
                id="app-config-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('quanfigApp.appConfig.encrypted')}
                id="app-config-encrypted"
                name="encrypted"
                data-cy="encrypted"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('quanfigApp.appConfig.createDate')}
                id="app-config-createDate"
                name="createDate"
                data-cy="createDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('quanfigApp.appConfig.updateDate')}
                id="app-config-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('quanfigApp.appConfig.enabled')}
                id="app-config-enabled"
                name="enabled"
                data-cy="enabled"
                check
                type="checkbox"
              />
              <ValidatedField
                id="app-config-configType"
                name="configType"
                data-cy="configType"
                label={translate('quanfigApp.appConfig.configType')}
                type="select"
                required
              >
                <option value="" key="0" />
                {configTypes
                  ? configTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.conType}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-config" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AppConfigUpdate;
