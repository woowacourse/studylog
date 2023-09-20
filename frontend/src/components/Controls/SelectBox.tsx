/** @jsxImportSource @emotion/react */

import Select from 'react-select';
import React from 'react';
import { COLOR } from '../../enumerations/color';
import { css } from '@emotion/react';

const selectStyles = {
  container: (styles) => ({
    ...styles,
    fontSize: '1.4rem',
  }),
  control: (styles) => ({
    ...styles,
    outline: 'none',
    border: 'none',
    boxShadow: 'none',
    color: COLOR.DARK_GRAY_900,
    paddingLeft: '1rem',
    cursor: 'pointer',
    backgroundColor: COLOR.LIGHT_GRAY_100,
  }),
  indicatorsContainer: (styles) => ({ ...styles }),
  valueContainer: (styles) => ({ ...styles, padding: '0' }),
  menu: (styles) => ({
    ...styles,
    fontSize: '1.4rem',
    fontColor: COLOR.DARK_GRAY_900,
  }),
};

interface SelectOption {
  value: string | number;
  label: string;
}

/**
  FIXME: value props type SelectOption['value'] 로 변경되어야 함.
  아래 예시처럼 type을 좁힐 수 없는 문제가 있음.

  const CATEGORY_OPTIONS = [
    { value: '', label: '전체보기' },
    { value: 'frontend', label: '프론트엔드' },
    { value: 'backend', label: '백엔드' },
    { value: 'android', label: '안드로이드' },
  ];

  type CategoryOptions = typeof CATEGORY_OPTIONS[number]; 

  ->type CategoryOptions = {
      value: string;
      label: string;
    }
  위 처럼 value type을 좁힐 수 없음.
 */
interface SelectBoxProps {
  isMulti?: boolean;
  options: SelectOption[];
  placeholder?: string;
  onChange?: (option: { value: string; label: string }) => void;
  defaultOption?: SelectOption;
  value?: SelectOption;
  selectedSessionId?: string;
  isClearable?: boolean;
}

const SelectBox: React.VFC<SelectBoxProps> = ({
  isClearable = true,
  isMulti = false,
  options,
  placeholder,
  onChange,
  value,
  defaultOption,
}: SelectBoxProps) => (
  <div
    css={css`
      input {
        color: transparent !important;
        width: 100%;
      }

      > div {
        border: 2px solid transparent;
        border-radius: 0.6rem;
      }

      > div:hover {
        border: 2px solid ${COLOR.LIGHT_BLUE_500};
      }

      .css-clear-indicator {
        display: block;
      }
    `}
  >
    <Select
      isClearable={isClearable}
      isMulti={isMulti}
      options={options}
      placeholder={placeholder}
      onChange={onChange}
      styles={selectStyles}
      defaultValue={defaultOption}
      value={value}
    />
  </div>
);

export default SelectBox;
