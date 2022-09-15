import styled from '@emotion/styled';
import { COLOR } from '../../enumerations/color';

export const EditorForm = styled.form`
  & .toastui-editor-toolbar {
    border-radius: 10px 10px 0 0;
  }
`;

export const SubmitButton = styled.button`
  width: 100%;
  padding: 1rem 0;
  border-radius: 1.6rem;

  margin-top: 12px;

  background-color: ${COLOR.LIGHT_BLUE_300};
  :hover {
    background-color: ${COLOR.LIGHT_BLUE_500};
  }

  :disabled {
    background-color: ${COLOR.LIGHT_GRAY_300};
  }
`;
