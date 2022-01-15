import { css } from '@emotion/react';
import { COLOR } from '../../constants';
import { getTextColor } from '../../utils/textColorPicker';

export const bannerWrapperStyle = css`
  width: 100vw;
  height: 320px;

  box-shadow: 0 0 4px 0 rgba(0, 0, 0, 0.2);

  overflow: hidden;

  strong,
  h2 {
    font-family: 'BMHANNAPro';
    font-weight: normal;
  }

  strong {
    font-size: 2.4rem;
  }

  a {
    max-width: 15rem;

    padding: 0.8rem 0;

    text-align: center;
  }
`;

export const bannerInnerWrapperStyle = css`
  max-width: calc(100% - 60rem);
  height: 100%;
  margin: 0 auto;

  display: flex;
  justify-content: center;
  align-items: center;
`;

export const bannerTextAreaStyle = css`
  height: 100%;

  padding-right: 6rem;

  display: flex;
  flex-direction: column;
  justify-content: center;

  * {
    margin: 0;
    line-height: 1.25;
  }

  h2 {
    font-size: 6rem;
  }

  p {
    font-size: 2rem;
    line-height: 1.5;
  }
`;

export const getBannerThemeByBgColor = (
  backgroundColor = COLOR.WHITE,
  backgroundImage?: string
) => css`
  background-color: ${backgroundColor};
  ${backgroundImage &&
  css`
    background: url(${backgroundImage});
    background-repeat: no-repeat;
    background-size: cover;
  `};
  color: ${getTextColor(backgroundColor)};

  a {
    margin-top: 1rem;

    background-color: transparent;
    border: 1px solid ${getTextColor(backgroundColor)};
    color: ${getTextColor(backgroundColor)};

    :active,
    :hover {
      background-color: ${getTextColor(backgroundColor)};
      color: ${backgroundColor};
    }
  }
`;

export const getBannerSideImageStyle = (sideImageUrl, sideImagePadding, reverse) => css`
  width: calc(320px - ${sideImagePadding ?? 0 * 2}rem);
  height: calc(320px - ${sideImagePadding ?? 0 * 2}rem);

  background-image: url(${sideImageUrl});
  background-repeat: no-repeat;
  background-size: cover;

  ${reverse ? 'margin-left: 3rem;' : 'margin-right: 3rem;'}
`;
