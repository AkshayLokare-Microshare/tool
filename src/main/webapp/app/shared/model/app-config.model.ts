import dayjs from 'dayjs';
import { IConfigType } from 'app/shared/model/config-type.model';

export interface IAppConfig {
  id?: number;
  configKey?: string;
  configValue?: string;
  subType?: string | null;
  subType1?: string | null;
  subType2?: string | null;
  description?: string | null;
  encrypted?: boolean;
  createDate?: dayjs.Dayjs | null;
  updateDate?: dayjs.Dayjs | null;
  enabled?: boolean;
  configType?: IConfigType;
}

export const defaultValue: Readonly<IAppConfig> = {
  encrypted: false,
  enabled: false,
};
