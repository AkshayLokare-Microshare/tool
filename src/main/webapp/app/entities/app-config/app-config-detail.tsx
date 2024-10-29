import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-config.reducer';

export const AppConfigDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appConfigEntity = useAppSelector(state => state.appConfig.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appConfigDetailsHeading">
          <Translate contentKey="quanfigApp.appConfig.detail.title">AppConfig</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{appConfigEntity.id}</dd>
          <dt>
            <span id="configKey">
              <Translate contentKey="quanfigApp.appConfig.configKey">Config Key</Translate>
            </span>
          </dt>
          <dd>{appConfigEntity.configKey}</dd>
          <dt>
            <span id="configValue">
              <Translate contentKey="quanfigApp.appConfig.configValue">Config Value</Translate>
            </span>
          </dt>
          <dd>{appConfigEntity.configValue}</dd>
          <dt>
            <span id="subType">
              <Translate contentKey="quanfigApp.appConfig.subType">Sub Type</Translate>
            </span>
          </dt>
          <dd>{appConfigEntity.subType}</dd>
          <dt>
            <span id="subType1">
              <Translate contentKey="quanfigApp.appConfig.subType1">Sub Type 1</Translate>
            </span>
          </dt>
          <dd>{appConfigEntity.subType1}</dd>
          <dt>
            <span id="subType2">
              <Translate contentKey="quanfigApp.appConfig.subType2">Sub Type 2</Translate>
            </span>
          </dt>
          <dd>{appConfigEntity.subType2}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="quanfigApp.appConfig.description">Description</Translate>
            </span>
          </dt>
          <dd>{appConfigEntity.description}</dd>
          <dt>
            <span id="encrypted">
              <Translate contentKey="quanfigApp.appConfig.encrypted">Encrypted</Translate>
            </span>
          </dt>
          <dd>{appConfigEntity.encrypted ? 'true' : 'false'}</dd>
          <dt>
            <span id="createDate">
              <Translate contentKey="quanfigApp.appConfig.createDate">Create Date</Translate>
            </span>
          </dt>
          <dd>
            {appConfigEntity.createDate ? <TextFormat value={appConfigEntity.createDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="quanfigApp.appConfig.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {appConfigEntity.updateDate ? <TextFormat value={appConfigEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="enabled">
              <Translate contentKey="quanfigApp.appConfig.enabled">Enabled</Translate>
            </span>
          </dt>
          <dd>{appConfigEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="quanfigApp.appConfig.configType">Config Type</Translate>
          </dt>
          <dd>{appConfigEntity.configType ? appConfigEntity.configType.conType : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-config" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-config/${appConfigEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppConfigDetail;
