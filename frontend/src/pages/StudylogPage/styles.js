import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';
import { markdownStyle } from '../../styles/markdown.styles';

const ButtonList = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-bottom: 1rem;
`;

const EditButtonStyle = css`
  border: 1px solid ${COLOR.LIGHT_GRAY_200};

  background-color: ${COLOR.WHITE};
  color: ${COLOR.BLACK_800};

  margin-right: 1rem;

  &:hover {
    background-color: ${COLOR.LIGHT_GRAY_300};
  }
`;

const DeleteButtonStyle = css`
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  background-color: ${COLOR.RED_300};
  color: ${COLOR.BLACK_800};

  &:hover {
    background-color: ${COLOR.RED_400};
  }
`;

const CardInner = styled.div`
  display: flex;
  flex-direction: column;
  min-height: 38rem;
  height: fit-content;

  & > *:not(:last-child) {
    margin-bottom: 2rem;
  }

  .toastui-editor-contents {
    font-size: 1.6rem;
  }
`;

const SubHeader = styled.div`
  display: flex;
  justify-content: space-between;
`;

const SubHeaderRightContent = styled.div`
  display: flex;
  align-items: center;

  & > button {
    margin-left: 0.7rem;
    font-size: 1.5rem;
    color: ${COLOR.BLACK_OPACITY_600};
  }
`;

const Mission = styled.div`
  font-size: 2rem;
  color: ${COLOR.DARK_GRAY_900};
  font-weight: lighter;
`;

const Title = styled.div`
  font-size: 3.6rem;
  color: ${COLOR.DARK_GRAY_900};
  font-weight: bold;
  margin-bottom: 2rem;
`;

const Tags = styled.div`
  font-size: 1.4rem;
  color: ${COLOR.LIGHT_GRAY_900};
  display: flex;
  align-items: center;
`;

const IssuedDate = styled.div`
  color: ${COLOR.DARK_GRAY_800};
  font-size: 1.4rem;
`;

const ProfileChipStyle = css`
  border: none;
  padding: 0.8rem;
  cursor: pointer;

  &:hover {
    background-color: ${COLOR.LIGHT_BLUE_100};
  }
`;

const ViewerWrapper = styled.div`
  word-break: break-all;

  .toastui-editor-contents h1,
  .toastui-editor-contents h2,
  .toastui-editor-contents h3,
  .toastui-editor-contents h4,
  .toastui-editor-contents h5,
  .toastui-editor-contents h6 {
    margin-top: 24px;
    margin-bottom: 16px;
    font-weight: 600;
    line-height: 1.25;
  }

  .toastui-editor-contents h1 {
    padding-bottom: 0.3em;
    font-size: 2em;
    border-bottom: 1px solid hsl(210deg 18% 87%);
  }

  .toastui-editor-contents h2 {
    padding-bottom: 0.3em;
    font-size: 1.5em;
    border-bottom: 1px solid hsl(210deg 18% 87%);
  }

  .toastui-editor-contents h3 {
    font-size: 1.25em;
  }

  .toastui-editor-contents ul > li::before {
    background-color: #222;
  }

  .toastui-editor-contents ol > li::before {
    color: #222;
  }

  ${markdownStyle};
`;

const BottomContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: auto;
`;

const CommentsContainer = styled.div`
  padding: 28px 12px 0;

  & > div + div {
    padding-top: 18px;
    border-top: 1px solid ${COLOR.LIGHT_GRAY_200};
  }
`;

export const EditorForm = styled.form`
  & .toastui-editor-toolbar {
    border-radius: 10px 10px 0 0;
  }
`;

const SubmitButton = styled.button`
  width: 100%;
  background-color: ${COLOR.LIGHT_BLUE_300};
  padding: 1rem 0;
  border-radius: 1.6rem;

  margin-top: 12px;

  :hover {
    background-color: ${COLOR.LIGHT_BLUE_500};
  }
`;

export {
  ButtonList,
  EditButtonStyle,
  DeleteButtonStyle,
  CardInner,
  SubHeader,
  SubHeaderRightContent,
  Mission,
  Title,
  Tags,
  IssuedDate,
  ProfileChipStyle,
  BottomContainer,
  ViewerWrapper,
  CommentsContainer,
  SubmitButton,
};
