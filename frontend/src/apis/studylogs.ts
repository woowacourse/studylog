import { AxiosPromise, AxiosResponse } from 'axios';
import { Nullable } from '../types/utils';
import {
  Mission,
  TempSavedStudyLog,
  Session,
  Studylog,
  StudylogForm,
  Tag,
  TempSavedStudyLogForm,
} from '../models/Studylogs';
import { createAxiosInstance } from '../utils/axiosInstance';

const customAxios = (accessToken?: string) => createAxiosInstance({ accessToken });

export const requestGetPopularStudylogs = ({ accessToken }: { accessToken?: string }) => {
  if (accessToken) {
    return customAxios(accessToken).get('/studylogs/popular');
  }
  return customAxios().get('/studylogs/popular');
};

export type StudylogQuery =
  | { type: 'searchParams'; data: URLSearchParams }
  | {
      type: 'filter';
      data: {
        postQueryParams: { key: string; value: string }[];
        filterQuery: { filterType: string; filterDetailId: string }[];
      };
    };

/**
 * @description 학습로그 조회 API, query type 이 복잡하므로 StudylogQuery type 참고
 * @param query 조회 조건으로 searchParams 혹은 filter type 으로 분기됨.
 * @param accessToken 유저의 accessToken
 * @todo query 간결화
 */
export const requestGetStudylogs = ({
  query,
  accessToken,
}: {
  query?: StudylogQuery;
  accessToken?: string;
}) => {
  const instance = accessToken ? customAxios(accessToken) : customAxios();

  if (!query) {
    return instance.get('/studylogs');
  }

  if (query.type === 'searchParams') {
    return instance.get(`/studylogs?${query.data.toString()}`);
  }

  if (query.type === 'filter') {
    const searchParams = Object.entries(query?.data?.postQueryParams).map(
      ([key, value]) => `${key}=${value}`
    );
    const filterQuery = query.data.filterQuery.length
      ? query.data.filterQuery.map(
          ({ filterType, filterDetailId }) => `${filterType}=${filterDetailId}`
        )
      : [];

    return instance.get(`/studylogs?${[...filterQuery, ...searchParams].join('&')}`);
  }
};

export type ResponseError = { code: number; messsage: string };

export const requestGetTags = (): Promise<AxiosResponse<Tag[]>> => customAxios().get('/tags');

export const requestGetMissions = ({ accessToken }): Promise<AxiosResponse<Mission[]>> =>
  customAxios(accessToken).get('/missions/mine');

export const requestGetSessions = ({ accessToken }): Promise<AxiosResponse<Session[]>> =>
  customAxios(accessToken).get('/sessions/mine');

export const requestGetStudylog = ({
  id,
  accessToken,
}: {
  id: string;
  accessToken: string;
}): AxiosPromise<AxiosResponse<Studylog>> =>
  customAxios(accessToken).get<AxiosResponse<Studylog>>(`/studylogs/${id}`);

/** 작성 및 수정 **/
export const requestPostStudylog = ({
  accessToken,
  data,
}: {
  accessToken: string;
  data: StudylogForm;
}): AxiosPromise<AxiosResponse<null>> => customAxios(accessToken).post('/studylogs', data);

export const requestEditStudylog = ({
  id,
  data,
  accessToken,
}: {
  id: string;
  accessToken: string;
  data: StudylogForm;
}): AxiosPromise<AxiosResponse<null>> => customAxios(accessToken).put(`/studylogs/${id}`, data);

/** 임시 저장 **/
export const requestGetTempSavedStudylog = async () => {
  const { data } = await customAxios().get<Nullable<TempSavedStudyLog>>('/studylogs/temp');

  return data;
};

export const requestPostTempSavedStudylog = (data: TempSavedStudyLogForm) =>
  customAxios().put('/studylogs/temp', data);
