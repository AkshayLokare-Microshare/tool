import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './app-config.reducer';

export const AppConfig = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const appConfigList = useAppSelector(state => state.appConfig.entities);
  const loading = useAppSelector(state => state.appConfig.loading);
  const totalItems = useAppSelector(state => state.appConfig.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="app-config-heading" data-cy="AppConfigHeading">
        <Translate contentKey="quanfigApp.appConfig.home.title">App Configs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="quanfigApp.appConfig.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/app-config/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="quanfigApp.appConfig.home.createLabel">Create new App Config</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {appConfigList && appConfigList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="quanfigApp.appConfig.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('configKey')}>
                  <Translate contentKey="quanfigApp.appConfig.configKey">Config Key</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('configKey')} />
                </th>
                <th className="hand" onClick={sort('configValue')}>
                  <Translate contentKey="quanfigApp.appConfig.configValue">Config Value</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('configValue')} />
                </th>
                <th className="hand" onClick={sort('subType')}>
                  <Translate contentKey="quanfigApp.appConfig.subType">Sub Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('subType')} />
                </th>
                <th className="hand" onClick={sort('subType1')}>
                  <Translate contentKey="quanfigApp.appConfig.subType1">Sub Type 1</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('subType1')} />
                </th>
                <th className="hand" onClick={sort('subType2')}>
                  <Translate contentKey="quanfigApp.appConfig.subType2">Sub Type 2</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('subType2')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="quanfigApp.appConfig.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('encrypted')}>
                  <Translate contentKey="quanfigApp.appConfig.encrypted">Encrypted</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('encrypted')} />
                </th>
                <th className="hand" onClick={sort('createDate')}>
                  <Translate contentKey="quanfigApp.appConfig.createDate">Create Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createDate')} />
                </th>
                <th className="hand" onClick={sort('updateDate')}>
                  <Translate contentKey="quanfigApp.appConfig.updateDate">Update Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('updateDate')} />
                </th>
                <th className="hand" onClick={sort('enabled')}>
                  <Translate contentKey="quanfigApp.appConfig.enabled">Enabled</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('enabled')} />
                </th>
                <th>
                  <Translate contentKey="quanfigApp.appConfig.configType">Config Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {appConfigList.map((appConfig, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/app-config/${appConfig.id}`} color="link" size="sm">
                      {appConfig.id}
                    </Button>
                  </td>
                  <td>{appConfig.configKey}</td>
                  <td>{appConfig.configValue}</td>
                  <td>{appConfig.subType}</td>
                  <td>{appConfig.subType1}</td>
                  <td>{appConfig.subType2}</td>
                  <td>{appConfig.description}</td>
                  <td>{appConfig.encrypted ? 'true' : 'false'}</td>
                  <td>{appConfig.createDate ? <TextFormat type="date" value={appConfig.createDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{appConfig.updateDate ? <TextFormat type="date" value={appConfig.updateDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{appConfig.enabled ? 'true' : 'false'}</td>
                  <td>
                    {appConfig.configType ? <Link to={`/config-type/${appConfig.configType.id}`}>{appConfig.configType.conType}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/app-config/${appConfig.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/app-config/${appConfig.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/app-config/${appConfig.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="quanfigApp.appConfig.home.notFound">No App Configs found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={appConfigList && appConfigList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default AppConfig;
