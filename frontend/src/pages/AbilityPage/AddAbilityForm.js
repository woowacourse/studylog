import { useState } from 'react';
import Chip from '../../components/Chip/Chip';
import { COLOR } from '../../constants';
import { ManageButtonList, Button, FormContainer, ListForm, ColorPicker } from './styles';

const AddAbilityForm = ({
  id,
  name,
  color,
  description,
  onClose,
  isParent,
  onSubmit,
  parentId = null,
}) => {
  const [formData, setFormData] = useState({
    name,
    description,
    color,
  });

  const onFormDataChange = (key) => (event) => {
    setFormData({ ...formData, [key]: event.target.value });
  };

  return (
    <FormContainer>
      <div>
        <Chip backgroundColor={formData.color} minWidth="3rem" fontSize="1.4rem">
          {formData.name || '라벨 미리보기'}
        </Chip>
      </div>
      <ListForm
        isParent={isParent}
        onSubmit={async (event) => {
          event.preventDefault();

          await onSubmit({
            name: formData.name,
            color: formData.color,
            description: formData.description,
            parent: parentId,
          });

          onClose();
        }}
      >
        <label>
          이름
          <input
            type="text"
            placeholder="이름"
            value={formData.name}
            maxLength={15}
            onChange={onFormDataChange('name')}
          />
        </label>
        <label>
          설명
          <input
            type="text"
            placeholder="설명"
            value={formData.description}
            onChange={onFormDataChange('description')}
          />
        </label>
        {isParent && (
          <label>
            색상
            <ColorPicker>
              <input type="color" value={formData.color} onChange={onFormDataChange('color')} />
              <input type="text" value={formData.color} onChange={onFormDataChange('color')} />
            </ColorPicker>
          </label>
        )}
        <ManageButtonList>
          <Button
            type="button"
            backgroundColor={COLOR.WHITE}
            color={COLOR.DARK_GRAY_900}
            borderColor={COLOR.DARK_BLUE_700}
            onClick={onClose}
          >
            취소
          </Button>
          <Button
            backgroundColor={COLOR.DARK_BLUE_700}
            color={COLOR.WHITE}
            disabled={!formData.name}
          >
            저장
          </Button>
        </ManageButtonList>
      </ListForm>
    </FormContainer>
  );
};

export default AddAbilityForm;
