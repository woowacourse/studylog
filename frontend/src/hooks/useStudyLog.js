import { useState } from 'react';
import { ERROR_MESSAGE } from '../constants/message';
import {
  requestEditPost,
  requestGetStudyLog,
  requestGetStudyLogs,
  requestDeletePost,
} from '../service/requests';

const useStudyLog = (defaultValue) => {
  const [response, setResponse] = useState(defaultValue);
  const [error, setError] = useState('');

  const getAllData = async (query, accessToken) => {
    try {
      const response = await requestGetStudyLogs(query, accessToken);

      if (!response.ok) {
        throw new Error(await response.text());
      }
      const json = await response.json();

      setResponse({
        ...json,
        data: json.data.map(
          ({ id, author, content, mission, title, tags, createdAt, updatedAt, read, scrap }) => ({
            id,
            author,
            content,
            mission,
            title,
            tags,
            createdAt,
            updatedAt,
            isRead: read,
            isScrapped: scrap,
          })
        ),
      });
    } catch (error) {
      const errorResponse = JSON.parse(error.message);

      console.error(errorResponse);
      setError(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
    }
  };

  const getData = async (postId, accessToken) => {
    try {
      const response = await requestGetStudyLog(postId, accessToken);

      if (!response.ok) {
        throw new Error(await response.text());
      }
      const json = await response.json();

      setResponse(json);
    } catch (error) {
      const errorResponse = JSON.parse(error.message);

      console.error(errorResponse);
      setError(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
    }
  };

  const editData = async (postId, data, accessToken) => {
    try {
      const response = await requestEditPost(postId, data, accessToken);

      if (!response.ok) {
        throw new Error(await response.text());
      }

      setResponse(response);
    } catch (error) {
      const errorResponse = JSON.parse(error.message);

      console.error(errorResponse);
      setError(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);

      return ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT;
    }
  };

  const deleteData = async (postId, accessToken) => {
    try {
      const response = await requestDeletePost(postId, accessToken);

      if (!response.ok) {
        throw new Error(await response.text());
      }

      setResponse(response);
    } catch (error) {
      const errorResponse = JSON.parse(error.message);

      console.error(errorResponse);
      setError(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);

      return ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT;
    }
  };

  return { response, error, getAllData, getData, editData, deleteData };
};

export default useStudyLog;