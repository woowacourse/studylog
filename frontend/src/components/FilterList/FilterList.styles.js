import { css } from '@emotion/react';
import styled from '@emotion/styled';

const DropdownToggledStyle = css`
  &:before {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 80;
    display: block;
    cursor: default;
    content: ' ';
    background: transparent;
  }
`;

const Container = styled.div`
  background-color: #f4f4f4;
  border: 1px solid #707070;
  border-radius: 1.4rem;
  padding: 0 3.2rem;
  display: flex;
  font-size: 1.4rem;
  height: inherit;
  align-items: center;

  ${({ isDropdownToggled }) => isDropdownToggled && DropdownToggledStyle}

  & > div:not(:last-child) {
    margin-right: 3.2rem;
  }

  & > div {
    input[type='search'] {
      font-weight: 500;
      padding: 1rem;
      font-size: 1.4rem;
      border: 1px solid #aaa;
      border-radius: 1rem;
      outline: none;

      :focus {
        border-color: #777;
      }
    }

    & > button {
      display: flex;
      align-items: center;
      height: 100%;
      font-size: 1.6rem;
      text-align: center;
      color: #666;

      ::after {
        content: '';
        width: 0;
        height: 0;
        transform: translateY(50%);
        margin-left: 0.2rem;
        border-top: 0.5rem solid #666;
        border-bottom: 0.5rem solid transparent;
        border-left: 0.5rem solid transparent;
        border-right: 0.5rem solid transparent;
      }
    }

    :hover {
      color: #222;

      ::after {
        border-top-color: #222;
      }
    }
  }
`;

const FilterDetail = styled.button`
  display: flex;
  align-items: center;
  gap: 0.4rem;

  & > img {
    width: 1.6rem;
    height: 1.6rem;
  }
`;

const ResetFilter = styled.div`
  margin-left: auto;
  color: #666666;
  cursor: pointer;
`;

const CheckIcon = styled.img`
  ${({ checked }) => !checked && 'visibility: hidden;'}
`;

export { Container, FilterDetail, ResetFilter, CheckIcon };
