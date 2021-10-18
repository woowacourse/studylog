import { css } from '@emotion/react';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router';
import { COLOR } from '../../constants';
import { ERROR_MESSAGE } from '../../constants/message';
import useMutation from '../../hooks/useMutation';
import useRequest from '../../hooks/useRequest';
import {
  requestAddAbility,
  requestDeleteAbility,
  requestEditAbility,
  requestGetAbilities,
  requestSetDefaultAbility,
} from '../../service/requests';
import AbilityListItem from './AbilityListItem';
import AddAbilityForm from './AddAbilityForm';

import { Container, AbilityList, Button, EditingListItem, ListHeader, NoContent } from './styles';

const AbilityPage = () => {
  const history = useHistory();
  const { username } = useParams();

  const [abilities, setAbilities] = useState([]);
  const [addFormStatus, setAddFormStatus] = useState({
    isOpened: false,
    name: '',
    description: '',
    color: '#f6d7fe',
  });

  const accessToken = localStorage.getItem('accessToken');
  const user = useSelector((state) => state.user.profile);
  const isMine = user.data && username === user.data.username;

  const setAddFormIsOpened = (status) => () => {
    setAddFormStatus((prevState) => ({ ...prevState, isOpened: status }));
  };

  const { fetchData: getData } = useRequest(
    [],
    () => requestGetAbilities(user.data.username, JSON.parse(accessToken)),
    (data) => {
      setAbilities(data);
    }
  );

  const addAbility = async ({ name, description, color, parent = null }) => {
    try {
      const response = await requestAddAbility(JSON.parse(accessToken), {
        name,
        description,
        color,
        parent,
      });

      if (!response.ok) {
        const json = await response.json();
        throw new Error(json.code);
      }

      await getData();
    } catch (error) {
      alert(ERROR_MESSAGE[error.message]);
      console.error(error);
    }
  };

  const deleteAbility = (id) => async () => {
    if (!window.confirm('삭제하시겠습니까?')) {
      return;
    }

    try {
      const response = await requestDeleteAbility(JSON.parse(accessToken), id);

      if (!response.ok) {
        const json = await response.json();
        throw new Error(json.code);
      }

      await getData();
    } catch (error) {
      alert(ERROR_MESSAGE[error.message]);
      console.error(error);
    }
  };

  const editAbility = async ({ id, name, description, color }) => {
    try {
      const response = await requestEditAbility(JSON.parse(accessToken), {
        id,
        name,
        description,
        color,
      });

      if (!response.ok) {
        const json = await response.json();
        throw new Error(json.code);
      }

      await getData();
    } catch (error) {
      alert(ERROR_MESSAGE[error.message]);
      console.error(error);
    }
  };

  const { mutate: addDefaultAbilities } = useMutation(
    (field) => requestSetDefaultAbility(JSON.parse(accessToken), field),
    () => {
      getData();
    },
    () => {
      alert('기본 역량을 등록하지 못했습니다.');
    }
  );

  useEffect(() => {
    if (user.data?.id) {
      getData();
    }
  }, [user]);

  if (user.data && !isMine) {
    alert('잘못된 접근입니다.');
    history.push(`/${username}`);
  }

  return (
    <Container>
      <div>
        <h2>역량</h2>
        <Button
          type="button"
          backgroundColor={COLOR.LIGHT_GRAY_50}
          onClick={setAddFormIsOpened(true)}
        >
          역량 추가 +
        </Button>
      </div>
      {addFormStatus.isOpened && (
        <AbilityList>
          <EditingListItem isParent={true}>
            <AddAbilityForm
              name={addFormStatus.name}
              color={addFormStatus.color}
              description={addFormStatus.description}
              isParent={true}
              setIsFormOpened={setAddFormIsOpened}
              onClose={setAddFormIsOpened(false)}
              onSubmit={addAbility}
            />
          </EditingListItem>
        </AbilityList>
      )}
      <AbilityList>
        <ListHeader>
          <div>
            역량<span>{`(총 ${abilities?.length}개)`}</span>
          </div>
        </ListHeader>

        {abilities
          ?.filter(({ isParent }) => isParent)
          .map(({ id, name, description, color, isParent, children }) => (
            <AbilityListItem
              key={id}
              id={id}
              name={name}
              description={description}
              color={color}
              isParent={isParent}
              subAbilities={children}
              onDelete={deleteAbility}
              onAdd={addAbility}
              onEdit={editAbility}
            />
          ))}

        {!abilities?.length && (
          <NoContent>
            <div>
              <h3>존재하는 역량이 없습니다.</h3>
              <span>
                오른쪽 위의 '역량추가+'버튼을 눌러 역량을 추가하시거나,
                <br />
                아래의 버튼을 눌러 기본으로 제공되는 역량 목록을 추가해보세요.
              </span>
            </div>
            <div>
              <button type="button" onClick={() => addDefaultAbilities('fe')}>
                프론트엔드
              </button>
              <button type="button" onClick={() => addDefaultAbilities('be')}>
                백엔드
              </button>
            </div>
          </NoContent>
        )}
      </AbilityList>
    </Container>
  );
};

export default AbilityPage;
